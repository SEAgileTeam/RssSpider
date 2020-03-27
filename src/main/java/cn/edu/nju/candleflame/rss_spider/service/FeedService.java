package cn.edu.nju.candleflame.rss_spider.service;

import cn.edu.nju.candleflame.rss_spider.feed.FeedChanger;

import java.util.Map;
import java.util.Set;

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
	Set<String> getAllFeedNames();

	/**
	 * 获得所有的bean map
	 * @return
	 */
	Map<String, FeedChanger> getBeanMap();
}
