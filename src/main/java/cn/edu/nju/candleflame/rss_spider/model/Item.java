package cn.edu.nju.candleflame.rss_spider.model;

public class Item {

	/**
	 * 文章的标题
	 */
	private String title;

	/**
	 * 文章的链接
	 */
	private String link;

	/**
	 * 文章内容
	 */
	private String content;

	public Item(String title, String link, String content) {
		this.title = title;
		this.link = link;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("<item>")
				.append("<title> <![CDATA[").append(this.title).append("]]> </title>")
				.append("<link>").append(this.link).append("</link>")
				.append("<description> <![CDATA[").append(this.content).append("]]> </description>")
				.append("</item>");

		return stringBuilder.toString();
	}
}
