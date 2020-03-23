package cn.edu.nju.candleflame.rss_spider.controller;

import cn.edu.nju.candleflame.rss_spider.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FeedController {

	@Autowired
	private FeedService feedService;

	@GetMapping("/feed/{feedName}")
	public String getFeedContent(@PathVariable String feedName){
		if (feedName == null || feedName.length()==0){
			return "";
		}
		return feedService.getFeedContent(feedName);
	}

	@GetMapping("/all")
	public List<String> getAllFeed(){
		return feedService.getAllFeedNames();
	}
}
