//package cn.edu.nju.candleflame.rss_spider.feed;
//
//import cn.edu.nju.candleflame.rss_spider.model.Item;
//import cn.edu.nju.candleflame.rss_spider.model.RssDocument;
//import cn.edu.nju.candleflame.rss_spider.service.FetchHtmlService;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//
//@Component
//public class ScholarChanger implements FeedChanger {
//
//	@Autowired
//	private FetchHtmlService fetchHtmlService;
//
//	@Override
//	public RssDocument analysis() {
//		String base_url = "http://xueshu.baidu.com/usercenter/journal/navigation?query=&language=1&category=10,1003";
//		Elements pages = Jsoup.parse(fetchHtmlService.postJsonParams(base_url, "")).select(".res-page-number");
//		ArrayList<Item> itemList = new ArrayList<>();
//		if (pages.size() == 0) {
//			itemList.addAll(fetchPage(base_url));
//		} else {
//			int max_page = Integer.parseInt(pages.get(pages.size() - 1).text());
//			for (int i=1; i<=max_page; i++) {
//				itemList.addAll(fetchPage(base_url + "&page=" + i));
//			}
//		}
//		RssDocument rssDocument = new RssDocument("百度学术-计算机中文期刊", "http://xueshu.baidu.com/usercenter/journal/navigation?query=&language=1&category=10,1003", "百度学术集成海量学术资源，融合人工智能、深度学习、大数据分析等技术，为科研工作者提供全面快捷的学术服务。");
//		rssDocument.appendAllItems(itemList);
//		return rssDocument;
//	}
//
//	ArrayList<Item> fetchPage(String url){
//		Document document = Jsoup.parse(fetchHtmlService.postJsonParams(url, ""));
//		Element root = document.selectFirst("#content_left");
//		Elements journals = root.select(".journal_detail");
//		ArrayList<Item> itemList = new ArrayList<>();
//		for (Element journal:journals) {
//			String journal_id = journal.getElementsByTag("a").get(0).attr("href").split("entity_id=")[1];
//			String journal_index_page = fetchHtmlService.postJsonParams("http://xueshu.baidu.com/usercenter/journal/baseinfo?entity_id=" + journal_id, "");
//			Document journal_doc = Jsoup.parse(journal_index_page);
//			Element articles_list = journal_doc.selectFirst(".magazine_brows_list");
//			for (Element article:articles_list.getElementsByTag("li")) {
//				Element article_url_link = article.getElementsByTag("h3").get(0).getElementsByTag("a").get(0);
//				String article_url = article_url_link.attr("href");
//				String article_title = article_url_link.text();
//				Document article_page = Jsoup.parse(fetchHtmlService.postJsonParams(article_url, ""));
//				String article_content = article_page.select(".abstract").get(0).text();
//				Item item = new Item(article_title, article_url, article_content);
//				itemList.add(item);
//			}
//		}
//		return itemList;
//	}
//
//}
