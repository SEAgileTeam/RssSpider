package cn.edu.nju.candleflame.rss_spider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "custom-analysis")
public class CustomAnalysisMapper {

	private List<Mapper> mappers = new ArrayList<>();

	public List<Mapper> getMappers() {
		return mappers;
	}

	public void setMappers(List<Mapper> mappers) {
		this.mappers = mappers;
	}
}
