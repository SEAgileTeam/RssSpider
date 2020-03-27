package cn.edu.nju.candleflame.rss_spider.schedule;

import cn.edu.nju.candleflame.rss_spider.aop.RunningLog;
import cn.edu.nju.candleflame.rss_spider.config.CustomAnalysisMapper;
import cn.edu.nju.candleflame.rss_spider.dao.FeedHistoryDao;
import cn.edu.nju.candleflame.rss_spider.dao.FeedRefreshDao;
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
	private final FeedRefreshDao feedRefreshDao;
	private final CustomAnalysisMapper customAnalysisMapper;
	private final ExecThreadPool execThreadPool;
	private final FeedHistoryDao feedHistoryDao;

	public FlushFeedExecutor(FeedService feedService, FeedRefreshDao feedRefreshDao, CustomAnalysisMapper customAnalysisMapper, ExecThreadPool execThreadPool, FeedHistoryDao feedHistoryDao) {
		this.feedService = feedService;
		this.feedRefreshDao = feedRefreshDao;
		this.customAnalysisMapper = customAnalysisMapper;
		this.execThreadPool = execThreadPool;
		this.feedHistoryDao = feedHistoryDao;
	}

	@PostConstruct
	public void init() {
		Thread thread = new Thread(new FlushFeedThread(feedService,
				feedRefreshDao,
				customAnalysisMapper,
				execThreadPool,
				feedHistoryDao));
		thread.setName("feed_flush_thread");

		LOGGER.info("flush feed executor is created");
		excutor = Executors.newSingleThreadExecutor();
		excutor.execute(thread);
	}
}
