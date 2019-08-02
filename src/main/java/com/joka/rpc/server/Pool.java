package com.joka.rpc.server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2019/8/2 9:41.
 *
 * @author zhaozengjie
 * Description :
 */
public class Pool {

    private static ThreadPoolExecutor threadPoolExecutor;

    public static void submit(Runnable task) {
        if (threadPoolExecutor == null) {
            synchronized (Pool.class) {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L,
                            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));
                }
            }
        }
        threadPoolExecutor.submit(task);
    }

}
