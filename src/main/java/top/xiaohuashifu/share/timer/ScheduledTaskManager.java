package top.xiaohuashifu.share.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xiaohuashifu.share.timer.task.CachingSensitiveWordTask;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 计划任务管理器
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Component
public class ScheduledTaskManager {

    private final CachingSensitiveWordTask cachingSensitiveWordTask;

    /**
     * 缓存敏感词任务第一次执行的延迟时间
     */
    private static final long CACHING_SENSITIVE_WORD_TASK_DELAY = 10000;

    /**
     * 缓存敏感词任务后面每次执行的间隔时间
     */
    private static final long CACHING_SENSITIVE_WORD_TASK_PERIOD = 3600000;

    /**
     * 执行计划任务的执行器
     */
    private static final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);

    @Autowired
    public ScheduledTaskManager(CachingSensitiveWordTask cachingSensitiveWordTask) {
        this.cachingSensitiveWordTask = cachingSensitiveWordTask;
    }

    /**
     * 初始化函数
     */
    @PostConstruct
    private void init() {
        schedule.scheduleAtFixedRate(cachingSensitiveWordTask, CACHING_SENSITIVE_WORD_TASK_DELAY,
                CACHING_SENSITIVE_WORD_TASK_PERIOD, TimeUnit.MILLISECONDS);
    }

}
