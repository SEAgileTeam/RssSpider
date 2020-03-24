package cn.edu.nju.candleflame.rss_spider.schedule;

import cn.edu.nju.candleflame.rss_spider.service.FeedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FlushFeedThread implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(FlushFeedThread.class);

	private final FeedService feedService;

	public FlushFeedThread(FeedService feedService) {
		this.feedService = feedService;
	}


	@Override
	public void run() {

		synchronized (this){
			while (true){
				try {
					LOGGER.info("flush feed thread runs");
					//TODO 向执行线程添加任务
					Thread.sleep(60*1000);
				}catch (Throwable t){
					LOGGER.error(t.getLocalizedMessage());
				}
			}
		}
	}
}
