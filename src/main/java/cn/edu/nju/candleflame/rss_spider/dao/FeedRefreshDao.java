package cn.edu.nju.candleflame.rss_spider.dao;

import cn.edu.nju.candleflame.rss_spider.entity.FeedRefreshEntity;

import java.util.List;

public interface FeedRefreshDao {

	/**
	 * 获得信息
	 * @return
	 */
	List<FeedRefreshEntity> getAllLastViewedFeed(List<String> names);
}
