package cn.edu.nju.candleflame.rss_spider.model;

import java.util.ArrayList;
import java.util.Collection;

public class RssDocument {

	/**
	 * Rss源的名称
	 */
	private String title;
	/**
	 * Rss源的url
	 */
	private String url;
	/**
	 * Rss源的描述
	 */
	private String description;
	/**
	 * Rss源当前爬取的文章集合
	 */
	private ArrayList<Item> contents;

	public RssDocument(String title, String url, String description) {
		this.title = title;
		this.url = url;
		this.description = description;
		this.contents = new ArrayList<>();
	}

	public void appendItem(Item item){
		contents.add(item);
	}

	public void appendAllItems(Collection<Item> items){
		contents.addAll(items);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>")
				.append("<rss version=\"2.0\">")
				.append("<channel>")
				.append("<title>").append(this.title).append("</title>")
				.append("<link>").append(this.url).append("</link>")
				.append("<description>").append(this.description).append("</description>");

		for (Item item:contents){
			stringBuilder.append(item.toString());
		}

		stringBuilder.append("</channel>")
				.append("</rss>");

		return stringBuilder.toString();
	}
}
