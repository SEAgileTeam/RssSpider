package cn.edu.nju.candleflame.rss_spider.entity;


import java.sql.Timestamp;


public class FeedRefreshEntity {


	private Long id;

	private String name;

	private Timestamp freshtime;

	private Timestamp createtime;

	public FeedRefreshEntity() {
	}

	public FeedRefreshEntity(String name) {
		this.name = name;
		this.freshtime = new Timestamp(System.currentTimeMillis());
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

	public Timestamp getFreshtime() {
		return freshtime;
	}

	public void setFreshtime(Timestamp freshTime) {
		this.freshtime = freshTime;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createTime) {
		this.createtime = createTime;
	}

	@Override
	public String toString() {
		return "FeedRefreshEntity{" +
				"id=" + id +
				", name='" + name + '\'' +
				", freshTime=" + freshtime +
				", createTime=" + createtime +
				'}';
	}
}
