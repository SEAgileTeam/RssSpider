package cn.edu.nju.candleflame.rss_spider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
@ConfigurationProperties(prefix = "user-agent")
public class UserAgentQueue {

	private ConcurrentLinkedQueue<String> allUserAgent;
	private List<String> items = new ArrayList<>();

	public void setItems(List<String> items) {
		this.items = items;
		allUserAgent = new ConcurrentLinkedQueue<>(items);
	}

	public String getNextUserAgent(){
		String current = allUserAgent.poll();
		if (current == null){
			synchronized (this){
				if (allUserAgent.isEmpty()){
					allUserAgent.addAll(items);
					current = allUserAgent.poll();
				}
			}
		}
		allUserAgent.add(current);
		return current;
	}
}
