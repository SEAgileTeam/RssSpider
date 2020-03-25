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
	private Timestamp freshtime;
	@Column
	private Timestamp createtime;

	public FeedRefreshEntity() {
	}

	public FeedRefreshEntity(String name) {
		this.name = name;
		this.freshtime = null;
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
