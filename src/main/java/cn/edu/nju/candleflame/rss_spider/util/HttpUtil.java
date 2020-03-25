package cn.edu.nju.candleflame.rss_spider.util;

import cn.edu.nju.candleflame.rss_spider.aop.RunningLog;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HttpUtil {

	private static final RunningLog LOGGER = RunningLog.getLog(HttpUtil.class);

	public static String get(String url, String charset){
		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建http GET请求
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpclient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				//请求体内容
				return EntityUtils.toString(response.getEntity(), charset);
			}
		} catch (IOException e) {
			LOGGER.error(e.getLocalizedMessage());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					LOGGER.error(e.getLocalizedMessage());
				}
			}
			//相当于关闭浏览器
			try {
				httpclient.close();
			} catch (IOException e) {
				LOGGER.error(e.getLocalizedMessage());
			}
		}
		return "";
	}

	public static boolean telnet(String hostname, int port, int timeout){
		Socket socket = new Socket();
		boolean isConnected = false;
		try {
			socket.connect(new InetSocketAddress(hostname, port), timeout);
			isConnected = socket.isConnected();
		} catch (IOException e) {
			LOGGER.error(e.getLocalizedMessage());
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				LOGGER.error(e.getLocalizedMessage());
			}
		}
		return isConnected;
	}

	private HttpUtil() {
	}
}
