package cn.edu.nju.candleflame.rss_spider.dao.impl;

import cn.edu.nju.candleflame.rss_spider.dao.FeedHistoryDao;
import cn.edu.nju.candleflame.rss_spider.entity.FeedHistoryEntity;
import cn.edu.nju.candleflame.rss_spider.util.JDBCUtil;
import org.springframework.stereotype.Component;

@Component
public class FeedHistoryDaoImpl implements FeedHistoryDao {

	private final JDBCUtil jdbcUtil;

	public FeedHistoryDaoImpl(JDBCUtil jdbcUtil) {
		this.jdbcUtil = jdbcUtil;
	}

	@Override
	public FeedHistoryEntity getLastContent(String feedName) {

		String sql = "select * from feed_history where name = ?" +
				" order by createtime desc" +
				" limit 1";

		return jdbcUtil.queryForBean(sql, new Object[]{feedName}, FeedHistoryEntity.class);
	}

	@Override
	public boolean insert(FeedHistoryEntity feedHistoryEntity) {

		if (feedHistoryEntity ==null ||
				feedHistoryEntity.getContent() == null||
				feedHistoryEntity.getCreatetime() ==null){
			return false;
		}

		String sql = "insert into feed_history(name,content, createtime) values (?,?,?)";

		return jdbcUtil.update(sql, new Object[]{
				feedHistoryEntity.getName(),
				feedHistoryEntity.getContent(),
				feedHistoryEntity.getCreatetime()});
	}
}
