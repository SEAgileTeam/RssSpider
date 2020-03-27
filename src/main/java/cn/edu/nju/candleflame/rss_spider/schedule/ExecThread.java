package cn.edu.nju.candleflame.rss_spider.schedule;

import cn.edu.nju.candleflame.rss_spider.feed.FeedChanger;

public class ExecThread implements Runnable {

	private final FeedChanger feedChanger;
	public ExecThread(FeedChanger feedChanger) {
		this.feedChanger = feedChanger;
	}

	@Override
	public void run() {
		feedChanger.analysis();
	}
}
