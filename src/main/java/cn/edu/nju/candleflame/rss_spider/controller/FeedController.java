package cn.edu.nju.candleflame.rss_spider.controller;

import cn.edu.nju.candleflame.rss_spider.aop.OperationLog;
import cn.edu.nju.candleflame.rss_spider.service.FeedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class FeedController {

	private final FeedService feedService;

	public FeedController(FeedService feedService) {
		this.feedService = feedService;
	}

	@OperationLog("get feed content")
	@GetMapping("/feed/{feedName}")
	public String getFeedContent(@PathVariable String feedName){
		if (feedName == null || feedName.length()==0){
			return "";
		}
		return feedService.getFeedContent(feedName);
	}

	@OperationLog("get all feed name")
	@GetMapping("/all")
	public Set<String> getAllFeed(){
		return feedService.getAllFeedNames();
	}
}
