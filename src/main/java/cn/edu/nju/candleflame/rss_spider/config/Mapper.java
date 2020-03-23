package cn.edu.nju.candleflame.rss_spider.config;

public class Mapper {
	private String name;
	private String analysisClass;

	public Mapper() {
	}

	public Mapper(String name, String analysisClass) {
		this.name = name;
		this.analysisClass = analysisClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAnalysisClass() {
		return analysisClass;
	}

	public void setAnalysisClass(String analysisClass) {
		this.analysisClass = analysisClass;
	}

	@Override
	public String toString() {
		return "Mapper{" +
				"name='" + name + '\'' +
				", analysisClass='" + analysisClass + '\'' +
				'}';
	}
}
