package cn.edu.nju.candleflame.rss_spider;

import cn.edu.nju.candleflame.rss_spider.feed.DemoChanger;
import cn.edu.nju.candleflame.rss_spider.feed.FeedChanger;
import cn.edu.nju.candleflame.rss_spider.feed.TianmaoChanger;
import cn.edu.nju.candleflame.rss_spider.model.RssDocument;

public class FeedChangerTest {

	public static void main(String[] args) {
		// 使用自己的类替换
		FeedChanger feedChanger = new DemoChanger();
		FeedChanger feedChanger1=new TianmaoChanger();

		// 将HTML内容转为一个字符串
		String testHtml = "http://movie.douban.com/chart";
		String input = "%BB%F0%B9%F8%B5%D7%C1%CF";//火锅底料
// 需要爬取商品信息的网站地址
		String testHtml1 = "https://list.tmall.com/search_product.htm?q=" + input;
//		RssDocument rssDocument = feedChanger.analysis(testHtml);
		RssDocument rssDocument1 = feedChanger.analysis();
//		System.out.println(rssDocument);
		System.out.println(rssDocument1);

	}
}
