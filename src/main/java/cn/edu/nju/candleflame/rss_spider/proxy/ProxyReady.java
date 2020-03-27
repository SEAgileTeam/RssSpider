package cn.edu.nju.candleflame.rss_spider.proxy;

import org.springframework.stereotype.Component;

@Component
public class ProxyReady {

	public volatile boolean isReady = false;

	public synchronized void setReady(boolean ready) {
		isReady = ready;
	}
}
