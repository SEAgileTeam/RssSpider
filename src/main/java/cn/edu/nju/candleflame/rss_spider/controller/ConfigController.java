package cn.edu.nju.candleflame.rss_spider.controller;

import cn.edu.nju.candleflame.rss_spider.config.CustomAnalysisMapper;
import cn.edu.nju.candleflame.rss_spider.config.Mapper;
import cn.edu.nju.candleflame.rss_spider.config.UserAgentQueue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConfigController {

	private final UserAgentQueue userAgentQueue;
	private final CustomAnalysisMapper customAnalysisMapper;

	public ConfigController(UserAgentQueue userAgentQueue, CustomAnalysisMapper customAnalysisMapper) {
		this.userAgentQueue = userAgentQueue;
		this.customAnalysisMapper = customAnalysisMapper;
	}

	@GetMapping("/test1")
	public String test1(){
		return userAgentQueue.getNextUserAgent();
	}

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
}
