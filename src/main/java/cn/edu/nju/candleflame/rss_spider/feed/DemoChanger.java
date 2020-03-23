package cn.edu.nju.candleflame.rss_spider.feed;

import cn.edu.nju.candleflame.rss_spider.model.RssDocument;
import org.springframework.stereotype.Component;

@Component
public class DemoChanger implements FeedChanger {
	@Override
	public RssDocument analysis(String html) {
		return new RssDocument("title","url", "description");
	}
}
