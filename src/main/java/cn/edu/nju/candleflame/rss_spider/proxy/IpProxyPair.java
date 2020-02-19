package cn.edu.nju.candleflame.rss_spider.proxy;

/**
 * 报错代理的对应关系
 */
public class IpProxyPair {

	private final String ip;
	private final int port;

	public IpProxyPair(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	@Override
	public String toString() {
		return ip+":"+port;
	}
}
