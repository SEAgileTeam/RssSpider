package cn.edu.nju.candleflame.rss_spider.schedule;

import cn.edu.nju.candleflame.rss_spider.aop.RunningLog;
import cn.edu.nju.candleflame.rss_spider.service.FeedService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class FlushFeedExecutor {

	private static final RunningLog LOGGER = RunningLog.getLog(FlushFeedExecutor.class);

	private ExecutorService excutor = null;

	private final FeedService feedService;

	public FlushFeedExecutor(FeedService feedService) {
		this.feedService = feedService;
	}

	@PostConstruct
	public void init() {
		Thread thread = new Thread(new FlushFeedThread(feedService));
		thread.setName("feed_flush_thread");

		LOGGER.info("flush feed executor is created");
		excutor = Executors.newSingleThreadExecutor();
		excutor.execute(thread);
	}
}
