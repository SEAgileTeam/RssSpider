package cn.edu.nju.candleflame.rss_spider.dao;

import cn.edu.nju.candleflame.rss_spider.entity.FeedHistoryEntity;

public interface FeedHistoryDao {
	/**
	 * 根据名字获取最新的feed内容
	 * @param feedName
	 * @return
	 */
	FeedHistoryEntity getLastContent(String feedName);
}
