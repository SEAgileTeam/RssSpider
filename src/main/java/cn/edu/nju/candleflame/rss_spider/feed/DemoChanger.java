package cn.edu.nju.candleflame.rss_spider.feed;

import cn.edu.nju.candleflame.rss_spider.model.Item;
import cn.edu.nju.candleflame.rss_spider.model.RssDocument;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;




@Component
public class DemoChanger implements FeedChanger {
	@Override
	public RssDocument analysis(String html) {
		Item item =  new Item("title","link","content");
		List<Item> itemList = Arrays.asList(new Item("title1","link1","content1"),
				new Item("title2","link2","content2"));
		String title="title";
		String url=html;
		String description="description";
		RssDocument rssDocument = new RssDocument(title, url, description);


		rssDocument.appendItem(item);
		rssDocument.appendAllItems(itemList);

		return rssDocument;

		}






}
