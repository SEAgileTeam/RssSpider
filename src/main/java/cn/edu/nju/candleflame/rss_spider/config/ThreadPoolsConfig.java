package cn.edu.nju.candleflame.rss_spider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolsConfig {
	/**
	 * 支付线程相关参数
	 */
	@Value("${async.threadNamePrefix}")
	private String threadNamePrefix;    // 配置线程池中的线程名称前缀

	@Value("${async.corePoolSize}")
	private Integer corePoolSize;       // 配置线程池中的核心线程数

	@Value("${async.maxPoolSize}")
	private Integer maxPoolSize;        // 配置最大线程数

	@Value("${async.queueCapacity}")
	private Integer queueCapacity;      // 配置队列大小

	/**
	 * 支付线程池配置
	 *
	 * @return
	 */
	@Bean
	public AsyncTaskExecutor paymentTaskExexutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix(threadNamePrefix);
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		// 设
		executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				// TODO
			}
		});
		return executor;
	}

}