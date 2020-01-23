package cn.edu.nju.candleflame.rssSpider.util;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

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
				String content = EntityUtils.toString(response.getEntity(), charset);
				return content;
			}
		} catch (ClientProtocolException e) {
			LOGGER.error(e.getLocalizedMessage());
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
			isConnected = false;
		}finally{
			try {
				socket.close();
			} catch (IOException e) {
				LOGGER.error(e.getLocalizedMessage());
			}
		}
		return isConnected;
	}
}
