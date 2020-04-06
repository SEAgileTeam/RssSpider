package cn.edu.nju.candleflame.rss_spider;

import cn.edu.nju.candleflame.rss_spider.feed.*;
import cn.edu.nju.candleflame.rss_spider.model.RssDocument;

import java.net.URL;

public class FeedChangerLocalTest {

	public static void main(String[] args) {
		FeedChanger changer = new NJUChanger();
		System.out.println(changer.analysis());
//		FeedChanger changer = new ScholarChanger();
//		System.out.println(changer.analysis());
	}

}
