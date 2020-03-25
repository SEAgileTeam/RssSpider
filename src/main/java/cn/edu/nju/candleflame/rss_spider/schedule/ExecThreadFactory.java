package cn.edu.nju.candleflame.rss_spider.schedule;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 执行线程的线程工厂
 */
public class ExecThreadFactory implements ThreadFactory {

	private final AtomicInteger threadNumber = new AtomicInteger(1);
	private final String namePrefix;

	public ExecThreadFactory() {
		namePrefix = "exec-pool";
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);

		t.setName(namePrefix + threadNumber.getAndIncrement());

		if (t.isDaemon()){
			t.setDaemon(false);
		}

		if (t.getPriority() != Thread.NORM_PRIORITY){
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}
}
