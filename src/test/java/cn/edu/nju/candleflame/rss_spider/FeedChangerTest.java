package cn.edu.nju.candleflame.rss_spider;

import cn.edu.nju.candleflame.rss_spider.feed.DemoChanger;
import cn.edu.nju.candleflame.rss_spider.feed.FeedChanger;
import cn.edu.nju.candleflame.rss_spider.model.RssDocument;

public class FeedChangerTest {

	public static void main(String[] args) {
		// 使用自己的类替换
		FeedChanger feedChanger = new DemoChanger();

		// 将HTML内容转为一个字符串
		String testHtml = "<html><head>Test</head></html>";
		RssDocument rssDocument = feedChanger.analysis(testHtml);
		System.out.println(rssDocument);
	}
}
