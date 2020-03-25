package cn.edu.nju.candleflame.rss_spider.entity;


import java.sql.Timestamp;


public class FeedHistoryEntity {

	private Long id;

	private String name;

	private String content;

	private Timestamp createtime;

	public FeedHistoryEntity() {
	}

	public FeedHistoryEntity(String name,String content) {
		this.name = name;
		this.content = content;
		this.createtime = new Timestamp(System.currentTimeMillis());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createTime) {
		this.createtime = createTime;
	}

	@Override
	public String toString() {
		return "FeedHistoryEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", content='" + content + '\'' +
				", createTime=" + createtime +
				'}';
	}
}
