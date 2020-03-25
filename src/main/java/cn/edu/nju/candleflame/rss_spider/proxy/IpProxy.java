package cn.edu.nju.candleflame.rss_spider.proxy;

import cn.edu.nju.candleflame.rss_spider.aop.RunningLog;
import cn.edu.nju.candleflame.rss_spider.util.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class IpProxy {

	private static final RunningLog LOGGER = RunningLog.getLog(IpProxy.class);
	private ConcurrentLinkedQueue<IpProxyPair> ipSet = new ConcurrentLinkedQueue<>();

	@Value("${proxy.ip}")
	private String proxyIp = "https://www.kuaidaili.com/free/inha/";

	private static final String REGEX_IP_ADDR = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

	public synchronized void fetchIp(){

		List<IpProxyPair> ipList = new ArrayList<>();

		for (int i=0 ;i<10;i++){

			String proxyCurrentIp = proxyIp+ (i==0?"":i);

			String content = HttpUtil.get(proxyCurrentIp, "UTF-8");
			Document document = Jsoup.parse(content);
			Element listElement = document.selectFirst("div#list");
			Elements items = listElement.select("tbody tr");

			for (Element item : items){
				Elements children = item.children();
				if (children.size()<2){
					continue;
				}

				String ip = children.get(0).text().trim();
				boolean matches = Pattern.matches(REGEX_IP_ADDR, ip);

				int port = -1;
				try {
					port= Integer.parseInt(children.get(1).text().trim());
				}catch (NumberFormatException e){
					continue;
				}
				matches &= (port>=0 && port<=65535);
				if (matches){
					boolean canConnect = HttpUtil.telnet(ip, port, 500);
					if (canConnect){
						ipList.add(new IpProxyPair(ip, port));
					}
				}
			}
		}

		ipSet.clear();
		ipSet.addAll(ipList);

		LOGGER.info(ipSet.stream().map(IpProxyPair::toString).collect(Collectors.joining(" ")));
	}

	public static void main(String[] args) {
		IpProxy ipProxy = new IpProxy();
		ipProxy.fetchIp();
	}

}
