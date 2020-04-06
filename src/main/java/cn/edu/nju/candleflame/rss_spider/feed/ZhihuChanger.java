package cn.edu.nju.candleflame.rss_spider.feed;

import cn.edu.nju.candleflame.rss_spider.aop.RunningLog;
import cn.edu.nju.candleflame.rss_spider.model.Item;
import cn.edu.nju.candleflame.rss_spider.model.RssDocument;
import cn.edu.nju.candleflame.rss_spider.service.FetchHtmlService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class ZhihuChanger implements FeedChanger {

	private static final RunningLog LOGGER = RunningLog.getLog(ZhihuChanger.class);
	private final FetchHtmlService fetchHtmlService;

	public ZhihuChanger(FetchHtmlService fetchHtmlService) {
		this.fetchHtmlService = fetchHtmlService;
	}

	@Override
	public RssDocument analysis() {
		Future<String> zhihuFeedFuture = fetchHtmlService.get("https://www.zhihu.com/rss", null);

		String content = "";
		try {
			content = zhihuFeedFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("fetch zhihu feed error",e.getMessage());
			return null;
		}

		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(new ByteArrayInputStream(content.getBytes()));
		} catch (DocumentException e) {
			LOGGER.error("zhihu feed content is error",e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}

		// 获取根元素
		Element root = document.getRootElement();
		Element channel = root.element("channel");

		Element title = channel.element("title");
		String titelContent = title.getTextTrim();

		Element description = channel.element("description");
		String descriptionContent = description.getTextTrim();

		String linkContent = channel.elementText("link");

		RssDocument result = new RssDocument(titelContent, linkContent, descriptionContent);

		@SuppressWarnings("unchecked")
		List<Element> items = channel.elements("item");

		for (int i =0;i<3 && i<items.size();i++){
			Element item = items.get(i);

			String itemTitle = item.elementText("title");
			String itemDescription = item.elementText("description");
			String itemLink = item.elementText("link");

			Item rssItem = new Item(itemTitle, itemLink, itemDescription);
			result.appendItem(rssItem);
		}

		return result;
	}
}
