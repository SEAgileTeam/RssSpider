package cn.edu.nju.candleflame.rss_spider.dao;


import cn.edu.nju.candleflame.rss_spider.entity.FeedRefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFeedRefreshhHistoryDao extends JpaRepository<FeedRefreshEntity,Long> {

	List<FeedRefreshEntity> findAll();

	@Override
	<S extends FeedRefreshEntity> List<S> saveAll(Iterable<S> iterable);


}
