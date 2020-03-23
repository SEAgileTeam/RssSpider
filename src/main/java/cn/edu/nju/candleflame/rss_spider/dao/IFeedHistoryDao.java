package cn.edu.nju.candleflame.rss_spider.dao;

import cn.edu.nju.candleflame.rss_spider.entity.FeedHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFeedHistoryDao extends JpaRepository<FeedHistoryEntity,Long> {
	/**
	 * 获得最新的feed 数据
	 * @param feedName
	 * @return
	 */
	FeedHistoryEntity getFirstByNameEqualsOrderByCreateTimeDesc(String feedName);
}
