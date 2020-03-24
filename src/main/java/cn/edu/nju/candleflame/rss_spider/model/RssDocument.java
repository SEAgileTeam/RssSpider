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

	/**
	 * 添加 item 条目
	 * @param item
	 */
	public void appendItem(Item item){
		contents.add(item);
	}

	/**
	 * 批量添加 item 条目
	 * @param items
	 */
	public void appendAllItems(Collection<Item> items){
		contents.addAll(items);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Item> getContents() {
		return contents;
	}

	public void setContents(ArrayList<Item> contents) {
		this.contents = contents;
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
