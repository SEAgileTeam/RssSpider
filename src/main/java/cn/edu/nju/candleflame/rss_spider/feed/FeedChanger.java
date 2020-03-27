package cn.edu.nju.candleflame.rss_spider.feed;

import cn.edu.nju.candleflame.rss_spider.model.RssDocument;

/**
 * html to feed 的调度工具
 */
public interface FeedChanger {

	RssDocument analysis();
}
