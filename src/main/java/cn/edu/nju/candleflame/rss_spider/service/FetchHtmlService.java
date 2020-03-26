package cn.edu.nju.candleflame.rss_spider.service;

import java.util.Map;

public interface FetchHtmlService {

	/**
	 * post
	 *
	 * @param url    请求的url
	 * @param params post form 提交的参数
	 * @return
	 */
	String postFormParams(String url, Map<String, String> params);

	/**
	 * get
	 *
	 * @param url     请求的url
	 * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
	 * @return
	 */
	String get(String url, Map<String, String> queries);

	/**
	 * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
	 * 参数一：请求Url
	 * 参数二：请求的JSON
	 * 参数三：请求回调
	 */
	String postJsonParams(String url, String jsonParams);


}
