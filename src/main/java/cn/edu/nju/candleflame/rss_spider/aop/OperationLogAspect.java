package cn.edu.nju.candleflame.rss_spider.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class OperationLogAspect {

    private RunningLog logger = RunningLog.getLog(getClass());

    @Pointcut("@annotation(cn.edu.nju.candleflame.rss_spider.aop.OperationLog)")
    public void cutService() {
    }

    @Around("cutService()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {

        //先执行业务
        Object result = point.proceed();

        try {
            handle(point,result);
        } catch (Exception e) {
            logger.error("日志记录出错!", e);
        }

        return result;
    }

    private void handle(ProceedingJoinPoint point, Object result) throws Exception {
        //获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = currentMethod.getName();


        //获取拦截方法的参数
        String className = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();

        //获取操作名称
        OperationLog annotation = currentMethod.getAnnotation(OperationLog.class);
        String bussinessName = annotation.value();

        //获得操作传递的参数
        StringBuilder sb = new StringBuilder();
        for (Object param : params) {
            sb.append(param.toString());
            sb.append(" & ");
        }
        //获取ip
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ipAddr = request.getRemoteAddr();


        logger.info("{} {} {}#{} req:{} res:{}",ipAddr,bussinessName,className,methodName,sb.toString(), LogFilter.filterLog(result));

    }


}
