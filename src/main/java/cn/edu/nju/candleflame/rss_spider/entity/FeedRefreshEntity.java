package cn.edu.nju.candleflame.rss_spider.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name = "feed_refresh")
public class FeedRefreshEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String name;
	@Column
	private Timestamp freshTime;
	@Column
	private Timestamp createTime;

	public FeedRefreshEntity() {
	}

	public FeedRefreshEntity(String name) {
		this.name = name;
		this.freshTime = null;
		this.createTime = new Timestamp(System.currentTimeMillis());
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

	public Timestamp getFreshTime() {
		return freshTime;
	}

	public void setFreshTime(Timestamp freshTime) {
		this.freshTime = freshTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
