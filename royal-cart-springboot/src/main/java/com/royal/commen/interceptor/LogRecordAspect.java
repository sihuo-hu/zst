package com.royal.commen.interceptor;

//import com.royal.commen.Logger;
import com.royal.entity.json.PageData;
import com.royal.util.JSONUtils;
import com.royal.commen.Logger;;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration//定义一个切面
public class LogRecordAspect {

    Logger logger = Logger.getLogger (this.getClass ());

    // 定义切点Pointcut
    @Pointcut("execution(* com.royal.controller..*.*(..))")
    public void excudeService() {
    }


    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes ();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest ();

        String url = request.getRequestURL ().toString ();
        String method = request.getMethod ();
        String uri = request.getRequestURI ();
        String queryString = request.getQueryString ();
        Object[] args = pjp.getArgs ();
        logger.info ("请求开始===地址:" + url + ",,,,,," + request.getContextPath ());
        logger.info ("请求开始===类型:" + method);
        //获取请求参数集合并进行遍历拼接
        if (args.length > 0) {
            if ("POST".equals (method)) {
//                Object object = args[0];
//                Map map = getKeyAndValue(object);
                PageData pd = new PageData (request);
                logger.info ("请求开始===参数:" + pd.toString ());
            } else if ("GET".equals (method)) {
                logger.info ("请求开始===参数:" + queryString);
            }
        }


        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed ();
        logger.info ("请求结束===返回值:" + JSONUtils.toJSONString (result));
        return result;
    }

    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<String, Object> ();
        // 得到类对象
        Class userCla = (Class) obj.getClass ();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields ();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible (true); // 设置些属性是可以访问的
            Object val = new Object ();
            try {
                val = f.get (obj);
                // 得到此属性的值
                map.put (f.getName (), val);// 设置键值
            } catch (IllegalArgumentException e) {
                e.printStackTrace ();
            } catch (IllegalAccessException e) {
                e.printStackTrace ();
            }

        }
        return map;
    }
}
