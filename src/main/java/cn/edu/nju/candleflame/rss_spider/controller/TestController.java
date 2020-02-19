package cn.edu.nju.candleflame.rss_spider.controller;

import cn.edu.nju.candleflame.rss_spider.config.UserAgentQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Autowired
	private UserAgentQueue userAgentQueue;

	@GetMapping("/")
	public String test(){
		return userAgentQueue.getNextUserAgent();
	}
}
