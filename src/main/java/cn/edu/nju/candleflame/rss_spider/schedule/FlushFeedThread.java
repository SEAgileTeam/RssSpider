package cn.edu.nju.candleflame.rss_spider.schedule;

import cn.edu.nju.candleflame.rss_spider.aop.RunningLog;
import cn.edu.nju.candleflame.rss_spider.service.FeedService;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class FlushFeedThread implements Runnable{

	private static final RunningLog LOGGER = RunningLog.getLog(FlushFeedThread.class);

	private final FeedService feedService;

	public FlushFeedThread(FeedService feedService) {
		this.feedService = feedService;
	}


	@Override
	public void run() {

		synchronized (this){
			while (true){
				try {
					Thread.sleep(5*1000);
					LOGGER.info("flush feed thread runs");
					//TODO 向执行线程添加任务
					Set<String> allFeedNames = feedService.getAllFeedNames();
					LOGGER.info(allFeedNames.size()+"");
					Thread.sleep(60*1000);
				}catch (Throwable t){
					LOGGER.error(t.getLocalizedMessage());
					try {
						Thread.sleep(5*1000);
					} catch (InterruptedException e) {
						LOGGER.error("sleep error");
					}
				}
			}
		}
	}
}
