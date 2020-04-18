package top.xiaohuashifu.share.timer.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xiaohuashifu.share.service.SensitiveWordService;

/**
 * 描述: 缓存sensitiveWord的任务
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Component
public class CachingSensitiveWordTask implements Runnable {

    private final SensitiveWordService sensitiveWordService;

    @Autowired
    public CachingSensitiveWordTask(SensitiveWordService sensitiveWordService) {
        this.sensitiveWordService = sensitiveWordService;
    }

    @Override
    public void run() {
        sensitiveWordService.cachingSensitiveWord();
    }

}
