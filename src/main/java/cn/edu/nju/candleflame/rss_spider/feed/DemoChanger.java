package cn.edu.nju.candleflame.rss_spider.feed;

import cn.edu.nju.candleflame.rss_spider.model.Item;
import cn.edu.nju.candleflame.rss_spider.model.RssDocument;
import cn.edu.nju.candleflame.rss_spider.service.FetchHtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;




@Component
public class DemoChanger implements FeedChanger {

	@Override
	public RssDocument analysis() {
		Item item2 =  new Item("《兽娘2》我不爽你很久了！——2019年度吐槽大会 ","https://www.bilibili.com/read/cv4349555","岁末年初也是各种盘点总结的时候，音乐播放器会告诉你过去的一年里听了哪些歌，视频网站告诉你去年你追哪部剧的时候最狂热。就算是平时不会上网冲浪的人，也会绞尽脑汁地“编”出一份年终总结交给老板。我承认这个猕猴桃我有赌的成分19年的二次元圈子颇为不平静，各种槽点满满的事件和作品层出不穷。今天ink娘就给大家整一个二次元的“吐槽大会”，给大家不负责任地捋一捋在平成令和交替之际，二次元有什么让人印象深刻的事件吧~年度最屑——《动物世界》谁不会做啊！哦~吼~《兽娘动物园》动画本来是IP跨媒体运营的其中一环，结<br><img src=\"https://i0.hdslb.com/bfs/article/c2fdd5d1aa2f1d0fec05dbeb34019b3d438b3e6f.jpg\" referrerpolicy=\"no-referrer\">");
		Item item1 = new Item("新封神·哪吒重生》官宣暑期档，《FGO》剧场版可能引进","https://www.bilibili.com/read/cv4344895","粉丝群：733985893INK周播报，汇集一周ACG趣事————————————————————————————————所有的内容都会在这里首发<br><img src=\"https://i0.hdslb.com/bfs/article/ccf13e73313f8121fafbe5d0d6188e0adcd59eb0.jpg\" referrerpolicy=\"no-referrer\">");

		List<Item> itemList = Arrays.asList(item1,item2);
		String title="ink娘和多宝喵 的 bilibili 专栏";
		String url="https://space.bilibili.com/334958638/#/article";
		String description="ink娘和多宝喵 的 bilibili 专栏 ";
		RssDocument rssDocument = new RssDocument(title, url, description);


		rssDocument.appendAllItems(itemList);

		return rssDocument;

	}






}
