package cn.edu.nju.candleflame.rss_spider.schedule;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
