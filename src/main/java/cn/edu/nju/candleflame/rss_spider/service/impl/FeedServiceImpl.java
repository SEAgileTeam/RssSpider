package cn.edu.nju.candleflame.rss_spider.service.impl;

import cn.edu.nju.candleflame.rss_spider.config.CustomAnalysisMapper;
import cn.edu.nju.candleflame.rss_spider.config.Mapper;
import cn.edu.nju.candleflame.rss_spider.dao.IFeedHistoryDao;
import cn.edu.nju.candleflame.rss_spider.entity.FeedHistoryEntity;
import cn.edu.nju.candleflame.rss_spider.feed.FeedChanger;
import cn.edu.nju.candleflame.rss_spider.service.FeedService;
import cn.edu.nju.candleflame.rss_spider.util.SpringBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class FeedServiceImpl implements FeedService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FeedServiceImpl.class);
	private static Map<String,FeedChanger> beanMap = new HashMap<>();

	private final CustomAnalysisMapper customAnalysisMapper;
	private final IFeedHistoryDao feedHistoryDao;

	public FeedServiceImpl(CustomAnalysisMapper customAnalysisMapper, IFeedHistoryDao feedHistoryDao) {
		this.customAnalysisMapper = customAnalysisMapper;
		this.feedHistoryDao = feedHistoryDao;
	}

	@Override
	public String getFeedContent(String feedName) {
		if (beanMap.size() == 0){
			getAllFeedNames();
		}
		if(beanMap.containsKey(feedName)){
			FeedHistoryEntity historyEntity = feedHistoryDao.getFirstByNameEqualsOrderByCreateTimeDesc(feedName);
			if (historyEntity == null){
				return "";
			}else{
				return historyEntity.getContent();
			}
		}else{
			return "";
		}

	}

	@Override
	public Set<String> getAllFeedNames() {

		if(beanMap.size()==0){
			synchronized (FeedServiceImpl.class){
				if (beanMap.size() == 0){
					List<Mapper> mappers = customAnalysisMapper.getMappers();

					for (Mapper mapper:mappers){
						judgeFeedCurrent(mapper);
					}
				}
			}
		}


		return beanMap.keySet();
	}

	private boolean judgeFeedCurrent(Mapper mapper){
		// 判断名字是否合法
		String name = mapper.getName();
		if (StringUtils.isEmpty(name)){
			LOGGER.error("config file has empty name");
			return false;
		}

		// 判断自定义的类是否存在
		Class customClass;
		try {

			customClass = this.getClass().getClassLoader().loadClass(mapper.getAnalysisClass());
		} catch (ClassNotFoundException e) {
			LOGGER.error("{} is not found", mapper.getAnalysisClass());
			return false;
		}

		// 手动保存 bean 和name 的对应关系
		FeedChanger bean = (FeedChanger) SpringBeanUtils.getInstance().getBean(customClass);
		beanMap.put(mapper.getName(), bean);
		return true;
	}
}
