package cn.edu.nju.candleflame.rss_spider.dao;

import cn.edu.nju.candleflame.rss_spider.entity.FeedRefreshEntity;

import java.sql.Timestamp;
import java.util.List;

public interface FeedRefreshDao {

	/**
	 * 获得信息
	 * @return
	 */
	List<FeedRefreshEntity> getAllLastViewedFeed(List<String> names);

	/**
	 * 更新一条刷新时间
	 */
	boolean updateFeed(String feedName, Timestamp freshTime);

	/**
	 * 新增一条feed记录
	 */
	boolean insert(FeedRefreshEntity feedRefreshEntity);

}
