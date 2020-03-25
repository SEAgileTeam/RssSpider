package cn.edu.nju.candleflame.rss_spider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jdbc")
public class JDBCConfig {

	private String url;
	private String userName;
	private String password;
	private String driver;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	@Override
	public String toString() {
		return "JDBCConfig{" +
				"url='" + url + '\'' +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", driver='" + driver + '\'' +
				'}';
	}
}
