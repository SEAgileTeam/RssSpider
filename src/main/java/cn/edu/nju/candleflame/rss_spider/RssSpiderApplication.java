package cn.edu.nju.candleflame.rss_spider;

import cn.edu.nju.candleflame.rss_spider.aop.RunningLog;
import cn.edu.nju.candleflame.rss_spider.proxy.IpProxy;
import cn.edu.nju.candleflame.rss_spider.proxy.ProxyReady;
import cn.edu.nju.candleflame.rss_spider.util.SpringBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RssSpiderApplication {

	private static final RunningLog LOGGER = RunningLog.getLog(RssSpiderApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(RssSpiderApplication.class, args);
		SpringBeanUtils.getInstance().setCfgContext(run);
	}

	@Autowired
	private ProxyReady proxyReady;

	@Bean
	public IpProxy getIpProxy(){
		IpProxy ipProxy = new IpProxy(proxyReady);
//		ipProxy.fetchIp();
		LOGGER.info("fetchIp end");
		return ipProxy;
	}

}
