package cn.edu.nju.candleflame.rss_spider.service.impl;

import cn.edu.nju.candleflame.rss_spider.service.FetchHtmlService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FetchHtmlServiceImpl implements FetchHtmlService {

	@Override
	public String postFormParams(String url, Map<String, String> params) {
		return null;
	}

	@Override
	public String get(String url, Map<String, String> queries) {
		return null;
	}

	@Override
	public String postJsonParams(String url, String jsonParams) {
		return null;
	}
}
