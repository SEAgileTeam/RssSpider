package cn.edu.nju.candleflame.rss_spider.config;

import cn.edu.nju.candleflame.rss_spider.proxy.IpProxy;
import cn.edu.nju.candleflame.rss_spider.proxy.IpProxyPair;
import com.alibaba.fastjson.JSON;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Iterator;
import java.util.Map;

public class OkHttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

    private final OkHttpClient.Builder builder;
    private IpProxy ipProxy;
    private UserAgentQueue userAgentQueue;

    public OkHttpUtil(OkHttpClient.Builder builder, IpProxy ipProxy, UserAgentQueue userAgentQueue){
        this.builder = builder;
        this.ipProxy = ipProxy;
        this.userAgentQueue = userAgentQueue;
    }
    /**
     * 根据map获取get请求参数
     *
     * @param queries
     * @return
     */
    public StringBuffer getQueryString(String url, Map<String, String> queries) {
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        return sb;
    }

    /**
     * 调用okhttp的newCall方法
     *
     * @param request
     * @return
     */
    private String execNewCall(Request request) {
        Response response = null;
        long curr = System.currentTimeMillis();
        try {
//            IpProxyPair nextIp = ipProxy.getNextIp();
//            logger.info("ip:{} port:{}" , nextIp.getIp(), nextIp.getPort());
            OkHttpClient okHttpClient = builder
//                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(nextIp.getIp(), nextIp.getPort())))
                    .build();
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                logger.info("reponse is: {}", responseBody);
                return responseBody;
            }
            logger.info("cost {}, reponse is: {}, {}", System.currentTimeMillis() - curr, status, JSON.toJSONString(response));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("cost {},okhttp3 put error >> ex = {}", System.currentTimeMillis() - curr, e.getLocalizedMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }

    /**
     * get
     *
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return
     */
    public String get(String url, Map<String, String> queries) {
        StringBuffer sb = getQueryString(url, queries);
        Request request = new Request.Builder()
                .url(sb.toString())
                .header("User-Agent", userAgentQueue.getNextUserAgent())
                .build();
        return execNewCall(request);
    }

    /**
     * post
     *
     * @param url    请求的url
     * @param params post form 提交的参数
     * @return
     */
    public String postFormParams(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .header("User-Agent", userAgentQueue.getNextUserAgent())
                .build();
        return execNewCall(request);
    }


    /**
     * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的JSON
     * 参数三：请求回调
     */
    public String postJsonParams(String url, String jsonParams) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        logger.info("request url:{} params:{}", url, jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .header("User-Agent", userAgentQueue.getNextUserAgent())
                .build();
        return execNewCall(request);
    }

    /**
     * Post请求发送xml数据....
     * 参数一：请求Url
     * 参数二：请求的xmlString
     * 参数三：请求回调
     */
    public String postXmlParams(String url, String xml) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), xml);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .header("User-Agent", userAgentQueue.getNextUserAgent())
                .build();
        return execNewCall(request);
    }
}