package concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @program: nettystudy
 * @description: JDK任务调度测试
 * @author: zhaoxudong
 * @create: 2020-02-06 19:12
 **/
public class ScheduleTest {

    private static ScheduledExecutorService scheduledExecutorService =
            Executors.newScheduledThreadPool(10);

    public static void main(String[] args) {
        ScheduledFuture future = scheduledExecutorService.schedule(new Runnable() {
            public void run() {
                System.err.println("测试");
            }
        }, 10, TimeUnit.SECONDS);
    }
}
