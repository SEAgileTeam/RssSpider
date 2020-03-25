package cn.edu.nju.candleflame.rss_spider.controller;

import cn.edu.nju.candleflame.rss_spider.aop.OperationLog;
import cn.edu.nju.candleflame.rss_spider.config.CustomAnalysisMapper;
import cn.edu.nju.candleflame.rss_spider.config.Mapper;
import cn.edu.nju.candleflame.rss_spider.config.UserAgentQueue;
import cn.edu.nju.candleflame.rss_spider.util.JDBCUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConfigController {

	private final UserAgentQueue userAgentQueue;
	private final CustomAnalysisMapper customAnalysisMapper;
	private final JDBCUtil jdbcUtil;

	public ConfigController(UserAgentQueue userAgentQueue, CustomAnalysisMapper customAnalysisMapper, JDBCUtil jdbcUtil) {
		this.userAgentQueue = userAgentQueue;
		this.customAnalysisMapper = customAnalysisMapper;
		this.jdbcUtil = jdbcUtil;
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

}
