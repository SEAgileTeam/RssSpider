package cn.edu.nju.candleflame.rss_spider.dao.impl;

import cn.edu.nju.candleflame.rss_spider.dao.FeedRefreshDao;
import cn.edu.nju.candleflame.rss_spider.entity.FeedRefreshEntity;
import cn.edu.nju.candleflame.rss_spider.util.JDBCUtil;
import org.springframework.stereotype.Component;

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
				" where name in (?)";

		StringBuilder builder = new StringBuilder();
		for(String name : names){
			builder.append(name)
					.append(",");
		}
		builder.deleteCharAt(builder.length()-1);
		return jdbcUtil.queryForList(sql, new Object[]{builder.toString()}, FeedRefreshEntity.class);
	}
}
