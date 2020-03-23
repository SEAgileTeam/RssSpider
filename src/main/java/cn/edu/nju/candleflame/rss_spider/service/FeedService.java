package cn.edu.nju.candleflame.rss_spider.service;

import java.util.List;

public interface FeedService {
	/**
	 * 根据feedName 获取到相应的feed属性
	 * @param feedName
	 * @return
	 */
	String getFeedContent(String feedName);

	/**
	 * 获取目前配置的所有的
	 * @return
	 */
	List<String> getAllFeedNames();
}
