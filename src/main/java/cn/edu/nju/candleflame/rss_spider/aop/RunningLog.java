package cn.edu.nju.candleflame.rss_spider.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RunningLog {
    private Logger logger;

    private String name;

    static ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

    };

    private RunningLog(Class c) {
        this.name = c.getName();
        logger = LoggerFactory.getLogger(c);
    }

    public static RunningLog getLog(Class class1) {
        return new RunningLog(class1);
    }

    public void error(String string) {
        if (logger != null) {
            logger.error(string);
        } else {
            System.out.println(getNowTime() + " " + name + " ERROR " + string);
        }
    }
    public void error(String string,Object... objects){
        if (isInfoEnabled()) {
            if (logger != null) {
                logger.error(string,objects);
            } else {
                StringBuilder stringBuilder=new StringBuilder();
                stringBuilder.append(getNowTime() + " " + name + " ERROR "
                        + string);
                for (Object obj: objects) {
                    stringBuilder.append(" "+objects);
                }
                System.out.println(string.toString());
            }
        }
    }
    public void info(String string,Object... objects){
        if (isInfoEnabled()) {
            if (logger != null) {
                logger.info(string,objects);
            } else {
                StringBuilder stringBuilder=new StringBuilder();
                stringBuilder.append(getNowTime() + " " + name + " INFO "
                        + string);
                for (Object obj: objects) {
                    stringBuilder.append(" "+objects);
                }
                System.out.println(string.toString());
            }
        }
    }

    public void info(String string) {
        if (isInfoEnabled()) {
            if (logger != null) {
                logger.info(string);
            } else {
                System.out.println(getNowTime() + " " + name + " INFO "
                        + string);
            }
        }
    }

    public void error(String string, Throwable e) {
        if (logger != null) {
            logger.error(string, e);
        } else {
            System.out.println(getNowTime() + " " + name + " ERROR " + string);
        }
    }

    public void warn(String string) {
        if (logger != null) {
            logger.warn(string);
        } else {
            System.out.println(getNowTime() + " " + name + " WARN " + string);
        }

    }

    public void warn(String string, Throwable e) {
        if (logger != null) {
            logger.warn(string, e);
        } else {
            System.out.println(getNowTime() + " " + name + " WARN " + string);
        }
    }

    public void debug(String string) {
        if (isDebugEnabled()) {
            if (logger != null) {
                logger.warn(string);
            } else {
                System.out.println(getNowTime() + " " + name + " DEBUG "
                        + string);
            }
        }

    }

    public boolean isDebugEnabled() {
        return false;
    }

    public boolean isInfoEnabled() {
        return true;
    }

    public String getNowTime() {
        return formatDateToSecond(new Date());
    }


    private static String formatDateToSecond(Date date) {
        return sdf.get().format(date);
    }

}
