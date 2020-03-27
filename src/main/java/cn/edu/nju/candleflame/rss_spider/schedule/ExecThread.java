package cn.edu.nju.candleflame.rss_spider.schedule;

import cn.edu.nju.candleflame.rss_spider.aop.RunningLog;
import cn.edu.nju.candleflame.rss_spider.dao.FeedHistoryDao;
import cn.edu.nju.candleflame.rss_spider.entity.FeedHistoryEntity;
import cn.edu.nju.candleflame.rss_spider.feed.FeedChanger;
import cn.edu.nju.candleflame.rss_spider.model.RssDocument;

public class ExecThread implements Runnable {

	private static final RunningLog LOGGER = RunningLog.getLog(ExecThread.class);

	private final String feedName;
	private final FeedChanger feedChanger;
	private final FeedHistoryDao feedHistoryDao;

	public ExecThread(String feedName, FeedChanger feedChanger, FeedHistoryDao feedHistoryDao) {
		this.feedName = feedName;
		this.feedChanger = feedChanger;
		this.feedHistoryDao = feedHistoryDao;
	}

	@Override
	public void run() {
		try {
			RssDocument document = feedChanger.analysis();
			FeedHistoryEntity feedHistoryEntity = new FeedHistoryEntity(feedName, document.toString());
			boolean insert = feedHistoryDao.insert(feedHistoryEntity);

			if (insert){
				LOGGER.info("feed {} update", feedName);
			}
		}catch (Exception e){
			LOGGER.error("feed run error",e);
		}

	}
}
