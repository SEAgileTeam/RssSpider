package cn.edu.nju.candleflame.rss_spider.aop;


import com.google.gson.Gson;

public class LogFilter {

    /**
     * 对象转为去掉空白符号的日志字符串
     * @param rawObject
     * @return
     */
    public static String filterLog(Object rawObject){
        Gson gson=new Gson();
        String rawString;
        if (!(rawObject instanceof String)){
            try {
                rawString = gson.toJson(rawObject);
            }catch (Exception e){
                rawString=e.toString();
            }
        }else{
            rawString= (String) rawObject;
        }
        return rawString.replaceAll("\n|\r|\t", "");
    }
}
