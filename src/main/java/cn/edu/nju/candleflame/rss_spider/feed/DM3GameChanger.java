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
public class DM3GameChanger implements FeedChanger {

	@Autowired
	private FetchHtmlService fetchHtmlService;

	@Override
	public RssDocument analysis() {
		String base_url = "https://www.3dmgame.com/original_40_";
		int max_page_num = Integer.parseInt(Jsoup.parse(fetchHtmlService.postJsonParams(base_url, "")).select(".last a").get(0).attr("data-page")) + 1;
		ArrayList<Item> itemList = new ArrayList<>();
		for (int i=1; i<=max_page_num; i++) {
			itemList.addAll(fetchPage(base_url + i));
		}
		String desc = "3DM游戏网为玩家提供最新的游戏新闻、攻略、单机游戏资源、汉化资源、游戏补丁、游戏论坛等,经过多年努力已成为游戏玩家首要选择的游戏资讯、游戏资源网站。";
		RssDocument rssDocument = new RssDocument("3DM Game-游戏评测", base_url + "1", desc);
		rssDocument.appendAllItems(itemList);
		return rssDocument;
	}

	ArrayList<Item> fetchPage(String url){
		Document document = Jsoup.parse(fetchHtmlService.postJsonParams(url, ""));
		Elements game_box = document.select(".a_bt");
		ArrayList<Item> itemList = new ArrayList<>();
		for (Element game:game_box) {
			String game_title = game.text();
			String game_url = game.attr("href");
			String game_article = fetchHtmlService.postJsonParams(game_url, "");
			Document game_article_doc = Jsoup.parse(game_article);
			Element content_div = game_article_doc.select(".zl_cent").get(1);
			String content = content_div.html();
			Item item = new Item(game_title, game_url, content);
			itemList.add(item);
		}
		return itemList;
	}

}
