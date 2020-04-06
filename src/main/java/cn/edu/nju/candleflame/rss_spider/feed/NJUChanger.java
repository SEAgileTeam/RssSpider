package cn.edu.nju.candleflame.rss_spider.feed;

import cn.edu.nju.candleflame.rss_spider.model.Item;
import cn.edu.nju.candleflame.rss_spider.model.RssDocument;
import cn.edu.nju.candleflame.rss_spider.service.FetchHtmlService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class NJUChanger implements FeedChanger {

	@Autowired
	private FetchHtmlService fetchHtmlService;

	@Override
	public RssDocument analysis() {
		try {
			String base_url = "https://www.nju.edu.cn/20/list.htm";
			int page_num = Integer.parseInt(Jsoup.parse(fetchHtmlService.get(base_url, null).get()).select(".all_pages").get(0).text());
			ArrayList<Item> itemList = new ArrayList<>();
			for (int page=1; page<=page_num; page++) {
				itemList.addAll(fetchPage("https://www.nju.edu.cn/20/list" + page + ".htm"));
			}
			RssDocument rssDocument = new RssDocument("南大官网-公告通知", "https://www.nju.edu.cn/20/list.htm", "诚朴雄伟 励学敦行");
			rssDocument.appendAllItems(itemList);
			return rssDocument;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	ArrayList<Item> fetchPage(String url) throws Exception {
		Elements noti_list = Jsoup.parse(fetchHtmlService.get(url, null).get()).select(".column-news-item");
		ArrayList<Item> itemList = new ArrayList<>();
		for (int i = 0;i<3 && i< noti_list.size();i++){
			Element noti =  noti_list.get(i);
			String article_link = "https://www.nju.edu.cn" + noti.attr("href");
			String article_page = fetchHtmlService.get(article_link, null).get();
			Document article_page_doc = Jsoup.parse(article_page);
			String title = article_page_doc.select(".arti-title").get(0).text();
			String content = article_page_doc.select(".wp_articlecontent").get(0).html();
			Item item = new Item(title, article_link, content);
			itemList.add(item);
		}
		return itemList;
	}

}
