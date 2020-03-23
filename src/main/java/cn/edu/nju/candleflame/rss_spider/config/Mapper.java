package cn.edu.nju.candleflame.rss_spider.config;

public class Mapper {
	private String name;
	private String analysisClass;
	private int frequency;

	public Mapper() {
	}

	public Mapper(String name, String analysisClass, int frequency) {
		this.name = name;
		this.analysisClass = analysisClass;
		this.frequency = frequency;
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

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	@Override
	public String toString() {
		return "Mapper{" +
				"name='" + name + '\'' +
				", analysisClass='" + analysisClass + '\'' +
				", frequency=" + frequency +
				'}';
	}
}
