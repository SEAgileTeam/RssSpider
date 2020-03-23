package cn.edu.nju.candleflame.rss_spider.feed;

import cn.edu.nju.candleflame.rss_spider.model.RssDocument;

public interface FeedChanger {

	public RssDocument analysis(String html);
}
