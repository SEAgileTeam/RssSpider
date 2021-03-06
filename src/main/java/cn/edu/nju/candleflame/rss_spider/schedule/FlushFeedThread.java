package cn.edu.nju.candleflame.rss_spider.schedule;

import cn.edu.nju.candleflame.rss_spider.aop.RunningLog;
import cn.edu.nju.candleflame.rss_spider.config.CustomAnalysisMapper;
import cn.edu.nju.candleflame.rss_spider.config.Mapper;
import cn.edu.nju.candleflame.rss_spider.dao.FeedHistoryDao;
import cn.edu.nju.candleflame.rss_spider.dao.FeedRefreshDao;
import cn.edu.nju.candleflame.rss_spider.entity.FeedRefreshEntity;
import cn.edu.nju.candleflame.rss_spider.feed.FeedChanger;
import cn.edu.nju.candleflame.rss_spider.proxy.ProxyReady;
import cn.edu.nju.candleflame.rss_spider.service.FeedService;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FlushFeedThread implements Runnable{

	private static final RunningLog LOGGER = RunningLog.getLog(FlushFeedThread.class);

	private final FeedService feedService;

	private final FeedRefreshDao feedRefreshDao;
	private final CustomAnalysisMapper customAnalysisMapper;
	private final ExecThreadPool execThreadPool;
	private final FeedHistoryDao feedHistoryDao;
	private final static int MINITE = 60000;
	private final ProxyReady proxyReady;

	public FlushFeedThread(FeedService feedService, FeedRefreshDao feedRefreshDao, CustomAnalysisMapper customAnalysisMapper, ExecThreadPool execThreadPool, FeedHistoryDao feedHistoryDao, ProxyReady proxyReady) {
		this.feedService = feedService;
		this.feedRefreshDao = feedRefreshDao;
		this.customAnalysisMapper = customAnalysisMapper;
		this.execThreadPool = execThreadPool;
		this.feedHistoryDao = feedHistoryDao;
		this.proxyReady = proxyReady;
	}


	@Override
	public void run() {

		synchronized (this){
			while (true){

				try {
//					if (proxyReady.isReady){

						Long currentTime = System.currentTimeMillis();

						LOGGER.info("flush feed thread runs");
						Map<String, FeedChanger> beanMap = feedService.getBeanMap();

						List<String> allNameList = new ArrayList<>(beanMap.keySet());
						List<FeedRefreshEntity> allLastViewedFeed = feedRefreshDao.getAllLastViewedFeed(allNameList);


						Map<String, Timestamp> feedFrushMap = new HashMap<>();

						if (allLastViewedFeed!=null && allLastViewedFeed.size()>0){
							feedFrushMap = allLastViewedFeed
									.stream()
									.collect(Collectors.toMap(
											FeedRefreshEntity::getName,
											FeedRefreshEntity::getFreshtime,
											(i,j)->i
									));
						}


						List<Mapper> mappers = customAnalysisMapper.getMappers();
						Map<String, Integer> feedFrequencyMap = mappers
								.stream()
								.collect(Collectors.toMap(
										Mapper::getName,
										Mapper::getFrequency));

						for (String feedName : feedFrequencyMap.keySet()){
							Integer minutes = feedFrequencyMap.get(feedName);
							if (minutes == null || minutes<=0){
								LOGGER.error("feed {} frequency is error",feedName);
								continue;
							}

							if (beanMap.get(feedName) == null){
								LOGGER.error("feed {} bean is not created",feedName);
								continue;
							}

							Timestamp lastFrushTime = feedFrushMap.get(feedName);

							if (lastFrushTime == null){
								execThreadPool.submit(new ExecThread(feedName, beanMap.get(feedName),feedHistoryDao));
								feedRefreshDao.insert(new FeedRefreshEntity(feedName));
							}else if (lastFrushTime.getTime()+minutes*MINITE<currentTime){
								execThreadPool.submit(new ExecThread(feedName, beanMap.get(feedName),feedHistoryDao));
								feedRefreshDao.updateFeed(feedName, new Timestamp(currentTime));
							}
						}
//					}

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
