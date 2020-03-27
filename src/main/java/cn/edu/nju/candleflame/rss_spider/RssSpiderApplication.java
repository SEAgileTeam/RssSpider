package cn.edu.nju.candleflame.rss_spider;

import cn.edu.nju.candleflame.rss_spider.util.SpringBeanUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RssSpiderApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(RssSpiderApplication.class, args);
		SpringBeanUtils.getInstance().setCfgContext(run);
	}

}
