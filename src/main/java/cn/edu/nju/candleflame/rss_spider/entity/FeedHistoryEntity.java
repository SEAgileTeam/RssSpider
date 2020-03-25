package cn.edu.nju.candleflame.rss_spider.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name = "feed_history")
public class FeedHistoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String name;
	@Column(length = 10240)
	private String content;
	@Column
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
