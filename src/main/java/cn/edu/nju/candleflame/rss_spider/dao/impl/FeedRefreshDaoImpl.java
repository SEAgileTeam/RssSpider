package cn.edu.nju.candleflame.rss_spider.dao.impl;

import cn.edu.nju.candleflame.rss_spider.dao.FeedRefreshDao;
import cn.edu.nju.candleflame.rss_spider.entity.FeedRefreshEntity;
import cn.edu.nju.candleflame.rss_spider.util.JDBCUtil;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class FeedRefreshDaoImpl implements FeedRefreshDao {

	private final JDBCUtil jdbcUtil;

	public FeedRefreshDaoImpl(JDBCUtil jdbcUtil) {
		this.jdbcUtil = jdbcUtil;
	}

	@Override
	public List<FeedRefreshEntity> getAllLastViewedFeed(List<String> names) {
		String sql  = "select * from feed_refresh" +
				" where name in (";

		StringBuilder builder = new StringBuilder();
		builder.append(sql);
		for(String name : names){
			builder.append("'")
					.append(name)
					.append("'")
					.append(",");
		}
		builder.deleteCharAt(builder.length()-1);
		builder.append(")");
		return jdbcUtil.queryForList(builder.toString(), new Object[0], FeedRefreshEntity.class);
	}

	@Override
	public boolean updateFeed(String feedName, Timestamp freshTime) {
		if(feedName == null || freshTime == null){
			return false;
		}
		String sql = "update feed_refresh set freshtime = ? where name = ?";

		return jdbcUtil.update(sql, new Object[]{freshTime, feedName});
	}

	@Override
	public boolean insert(FeedRefreshEntity feedRefreshEntity) {

		if (feedRefreshEntity ==null ||
				feedRefreshEntity.getName() == null ||
				feedRefreshEntity.getCreatetime() == null ||
				feedRefreshEntity.getFreshtime() == null){
			return false;
		}

		String sql = "insert into feed_refresh(name,createtime, freshtime) values (?,?,?)";

		return jdbcUtil.update(sql, new Object[]{
				feedRefreshEntity.getName(),
				feedRefreshEntity.getFreshtime(),
				feedRefreshEntity.getFreshtime()});
	}
}
