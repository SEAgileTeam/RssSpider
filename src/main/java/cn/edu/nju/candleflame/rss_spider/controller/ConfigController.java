package cn.edu.nju.candleflame.rss_spider.controller;

import cn.edu.nju.candleflame.rss_spider.aop.OperationLog;
import cn.edu.nju.candleflame.rss_spider.config.CustomAnalysisMapper;
import cn.edu.nju.candleflame.rss_spider.config.Mapper;
import cn.edu.nju.candleflame.rss_spider.config.UserAgentQueue;
import cn.edu.nju.candleflame.rss_spider.dao.FeedRefreshDao;
import cn.edu.nju.candleflame.rss_spider.entity.FeedRefreshEntity;
import cn.edu.nju.candleflame.rss_spider.util.JDBCUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ConfigController {

	private final UserAgentQueue userAgentQueue;
	private final CustomAnalysisMapper customAnalysisMapper;
	private final JDBCUtil jdbcUtil;
	private final FeedRefreshDao feedRefreshDao;

	public ConfigController(UserAgentQueue userAgentQueue, CustomAnalysisMapper customAnalysisMapper, JDBCUtil jdbcUtil, FeedRefreshDao feedRefreshDao) {
		this.userAgentQueue = userAgentQueue;
		this.customAnalysisMapper = customAnalysisMapper;
		this.jdbcUtil = jdbcUtil;
		this.feedRefreshDao = feedRefreshDao;
	}

	@OperationLog("get user agent")
	@GetMapping("/test1")
	public String test1(){
		return userAgentQueue.getNextUserAgent();
	}

	@OperationLog("get user feeds")
	@GetMapping("/test2")
	public String test2(){
		List<Mapper> mappers = customAnalysisMapper.getMappers();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{");
		for(Mapper mapper : mappers){
			stringBuilder.append(mapper.toString());
			stringBuilder.append(";");
		}
		stringBuilder.append("}");

		return stringBuilder.toString();
	}

	@OperationLog("get jdbc feeds")
	@GetMapping("/test3")
	public String test3(){
		String sql1 = "select count(*) count from feed_history";
		Count integer = jdbcUtil.queryForBean(sql1, null, Count.class);
		System.out.println(integer.count);
		return integer.count+"";
	}

	public static class Count{
		private Long count;

		public Long getCount() {
			return count;
		}

		public void setCount(Long count) {
			this.count = count;
		}
	}

	@OperationLog("get all lastviewed")
	@GetMapping("/test4")
	public String test4(){
		List<Mapper> mappers = customAnalysisMapper.getMappers();
		List<String> allNames = new ArrayList<>();
		for(Mapper mapper: mappers){
			allNames.add(mapper.getName());
		}
		List<FeedRefreshEntity> allLastViewedFeed = feedRefreshDao.getAllLastViewedFeed(allNames);
		return JSON.toJSONString(allLastViewedFeed);
	}

	@OperationLog("insert new record")
	@GetMapping("/test5")
	public boolean test5(){
		return feedRefreshDao.insert(new FeedRefreshEntity("test1"));
	}

	@OperationLog("update new record")
	@GetMapping("/test6")
	public boolean test6(){
		return feedRefreshDao.updateFeed("test1", new Timestamp(System.currentTimeMillis()));
	}

}
