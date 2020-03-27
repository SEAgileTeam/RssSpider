package cn.edu.nju.candleflame.rss_spider.schedule;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ExecThreadPool {

	private ExecutorService executorService ;

	public ExecThreadPool() {
		executorService = Executors.newFixedThreadPool(10,
				new ExecThreadFactory());
	}

	public void submit(Runnable r){
		executorService.execute(r);
	}
}
