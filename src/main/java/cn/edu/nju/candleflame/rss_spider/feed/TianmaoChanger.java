package cn.edu.nju.candleflame.rss_spider.feed;

import cn.edu.nju.candleflame.rss_spider.model.Item;
import cn.edu.nju.candleflame.rss_spider.model.RssDocument;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import java.io.IOException;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;




@Component
public class TianmaoChanger implements FeedChanger {
    @Override
    public RssDocument analysis() {
//		Item item =  new Item("title","link","content");
//		List<Item> itemList = Arrays.asList(new Item("title1","link1","content1"),
//				new Item("title2","link2","content2"));
        String title = "天猫商品信息";

        String description = "输出天猫商品需要搜索的所有商家的id和名称以及价格图片等信息";

        String input = "%BB%F0%B9%F8%B5%D7%C1%CF";//火锅底料
// 需要爬取商品信息的网站地址
        String testHtml1 = "https://list.tmall.com/search_product.htm?q=" + input;

        RssDocument rssDocument = new RssDocument(title, testHtml1, description);
// 动态模拟请求数据
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(testHtml1);
// 模拟浏览器浏览（user-agent的值可以通过浏览器浏览，查看发出请求的头文件获取）
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
// 获取响应状态码
        int statusCode = response.getStatusLine().getStatusCode();
        try {
            HttpEntity entity = response.getEntity();
            // 如果状态响应码为200，则获取html实体内容或者json文件
            if (statusCode == 200) {
                String html = EntityUtils.toString(entity, Consts.UTF_8);
                // 提取HTML得到商品信息结果
                Document doc = null;
                // doc获取整个页面的所有数据
                doc = Jsoup.parse(html);
                //输出doc可以看到所获取到的页面源代码
//      System.out.println(doc);
                // 通过浏览器查看商品页面的源代码，找到信息所在的div标签，再对其进行一步一步地解析
                Elements ulList = doc.select("div[class='view grid-nosku']");
                Elements liList = ulList.select("div[class='product']");
                // 循环liList的数据（具体获取的数据值还得看doc的页面源代码来获取，可能稍有变动）
                for (Element item : liList) {
                    // 商品ID
                    String id = item.select("div[class='product']").select("p[class='productStatus']").select("span[class='ww-light ww-small m_wangwang J_WangWang']").attr("data-item");
                    System.out.println("商品ID：" + id);
                    // 商品名称
                    String name = item.select("p[class='productTitle']").select("a").attr("title");
                    System.out.println("商品名称：" + name);
                    // 商品价格
                    String price = item.select("p[class='productPrice']").select("em").attr("title");
                    System.out.println("商品价格：" + price);
                    // 商品网址
                    String goodsUrl = item.select("p[class='productTitle']").select("a").attr("href");
                    System.out.println("商品网址：" + goodsUrl);
                    // 商品图片网址
                    String imgUrl = item.select("div[class='productImg-wrap']").select("a").select("img").attr("data-ks-lazyload");
                    System.out.println("商品图片网址：" + imgUrl);
                    System.out.println("------------------------------------");
                    Item movie = new Item("商品ID名称：" + id + "，" + name, "http:" + goodsUrl, "商品价格：" + price + "<br>" + "商品图片:<be><img src=\"http:" + imgUrl+"\">");
                    rssDocument.appendItem(movie);
                }
                // 消耗掉实体
                EntityUtils.consume(response.getEntity());
            } else {
                // 消耗掉实体
                EntityUtils.consume(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


//		rssDocument.appendAllItems(itemList);

        return rssDocument;
    }
}