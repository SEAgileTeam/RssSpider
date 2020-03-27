package cn.edu.nju.candleflame.rss_spider.service.impl;

import cn.edu.nju.candleflame.rss_spider.config.OkHttpUtil;
import cn.edu.nju.candleflame.rss_spider.service.FetchHtmlService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.Future;

@Service
public class FetchHtmlServiceImpl implements FetchHtmlService {


	private final OkHttpUtil okHttpUtil;

	public FetchHtmlServiceImpl(OkHttpUtil okHttpUtil) {
		this.okHttpUtil = okHttpUtil;
	}

	@Override
	@Async
	public Future<String> postFormParams(String url, Map<String, String> params) {
		return new AsyncResult<>(okHttpUtil.postFormParams(url, params));
	}

	@Override
	@Async
	public Future<String> get(String url, Map<String, String> queries) {
		return new AsyncResult<>(okHttpUtil.get(url, queries));
	}

	@Override
	@Async
	public Future<String> postJsonParams(String url, String jsonParams) {
		return new AsyncResult<>(okHttpUtil.postJsonParams(url, jsonParams));
	}
}
