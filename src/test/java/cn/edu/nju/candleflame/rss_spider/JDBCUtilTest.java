//package cn.edu.nju.candleflame.rss_spider;
//
//import cn.edu.nju.candleflame.rss_spider.entity.FeedHistoryEntity;
//import cn.edu.nju.candleflame.rss_spider.util.JDBCUtil;
//
//public class JDBCUtilTest {
//
//	public static void main(String[] args) {
//
////		String sql = "select * from feed_history";
////		FeedHistoryEntity feedHistoryEntity = JDBCUtil.queryForBean(sql, null, FeedHistoryEntity.class);
////		System.out.println(feedHistoryEntity);
//
//		String sql1 = "select count(*) count from feed_history";
//		Count integer = JDBCUtil.queryForBean(sql1, null, Count.class);
//		System.out.println(integer.count);
//
//	}
//	public static class Count{
//		private Long count;
//
//		public Long getCount() {
//			return count;
//		}
//
//		public void setCount(Long count) {
//			this.count = count;
//		}
//	}
//}
