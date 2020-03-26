package cn.edu.nju.candleflame.rss_spider.feed;

import cn.edu.nju.candleflame.rss_spider.model.Item;
import cn.edu.nju.candleflame.rss_spider.model.RssDocument;
import org.springframework.stereotype.Component;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;


import com.google.common.base.CharMatcher;


@Component
public class DoubanChanger implements FeedChanger {
    @Override
    public RssDocument analysis(String html) {
//		Item item =  new Item("title","link","content");
//		List<Item> itemList = Arrays.asList(new Item("title1","link1","content1"),
//				new Item("title2","link2","content2"));
        String title="豆瓣电影排行榜";
        String url=html;
        String description="输出排名前10的豆瓣新片榜单，并列出电影的导演，主演和简介。";
        RssDocument rssDocument = new RssDocument(title, url, description);
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .maxBodySize(Integer.MAX_VALUE)
                    .data("query", "Java")
                    .cookie("auth", "token")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134")
                    .timeout(10000)
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(doc);

        //逐层分析html
        Elements a = doc.select("div[class=\"\"]");
        //System.out.println(a);
        Elements b= a.select("a[class=nbg]");
        //String c = b.attr("href");
        //System.out.println(b);
        //System.out.println(c);

        for(Element element : b){
            String video_url = element.attr("href");	// 电影链接地址
            String video_name = element.attr("title");	// 电影名字

            Elements d= element.select("img");
            String img_url = d.attr("src");             //电影图片

            System.out.println("video_name"+video_name);
            System.out.println("img_url"+img_url);
            System.out.println("video_url"+video_url);
            String content= null;
            try {
                content = get_each_movies(video_url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Item movie=new Item(video_name,video_url,content);
            rssDocument.appendItem(movie);

        }



//		rssDocument.appendAllItems(itemList);

        return rssDocument;
    }
    String get_each_movies(String url) throws IOException {
        //获取html
        Document doc = Jsoup.connect(url)
                .maxBodySize(Integer.MAX_VALUE)
                .data("query", "Java")
                .cookie("auth", "token")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/17.17134")
                .timeout(10000)
                .post();
        //	System.out.println(doc);

        Elements a = doc.select("script[type=\"application/ld+json\"]");
        String film_Json = a.toString();

        String film_json = film_Json.substring(film_Json.indexOf(">")+1,film_Json.lastIndexOf("<"));
        //System.out.println(film_json);

        try {
            JSONObject film_info = new JSONObject(film_json);
            //电影导演
            JSONArray director_data=film_info.getJSONArray("director");
            String directors_name="directors_name:  ";

            for(int j=0;j<director_data.length();j++) {
                String director_name = director_data.getJSONObject(j).getString("name");
                System.out.println("director_name:  " + director_name);
                directors_name=directors_name+director_name+'\n';
            }
            //电影主演
            JSONArray actor_data=film_info.getJSONArray("actor");
            String actors_name="actor_name:  ";
            for(int j=0;j<actor_data.length();j++) {
                String actor_name = actor_data.getJSONObject(j).getString("name");
                System.out.println("actor_name:  " + actor_name);
                actors_name=actors_name+actor_name+'\n';

            }
            //剧情简介
            Elements b = doc.select("span[property=\"v:summary\"]");
            String film_summary =CharMatcher.whitespace().trimFrom(b.text());
            System.out.println("film_summary:  "+film_summary);
            return(directors_name+actors_name+film_summary);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return film_Json;
    }
}
