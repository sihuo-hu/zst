package cn.stylefeng.guns.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ThreadPoolUtil {
    private static final Logger log = LoggerFactory.getLogger(ThreadPoolUtil.class);
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    /**
     *
     */
    public static void pushOne(final String loginName,final String msg) {
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    PushUtil.push(loginName, msg, null);
                } catch (Exception e) {
                    log.error("推送失败：", e);
                }
            }
        });
    }

    public static void pushList(final List<String> loginNameList,final String msg) {
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    PushUtil.push(loginNameList, msg, null);
                } catch (Exception e) {
                    log.error("推送失败：", e);
                }
            }
        });
    }


}
