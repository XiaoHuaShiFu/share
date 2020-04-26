package top.xiaohuashifu.share.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xiaohuashifu.share.aspect.annotation.AdminLog;
import top.xiaohuashifu.share.constant.AdminLogType;
import top.xiaohuashifu.share.constant.TokenType;
import top.xiaohuashifu.share.pojo.ao.TokenAO;
import top.xiaohuashifu.share.pojo.do0.AdminLogDO;
import top.xiaohuashifu.share.pojo.do0.UserDO;
import top.xiaohuashifu.share.pojo.vo.SensitiveWordVO;
import top.xiaohuashifu.share.pojo.vo.UserVO;
import top.xiaohuashifu.share.service.AdminLogService;


/**
 * 管理员日志切面
 */
@Aspect
@Component
public class AdminLogAspect {

    private final AdminLogService adminLogService;

	@Autowired
	public AdminLogAspect(AdminLogService adminLogService) {
        this.adminLogService = adminLogService;
    }

	@Pointcut("@annotation(top.xiaohuashifu.share.aspect.annotation.AdminLog) && @annotation(adminLog) " +
            "&& within(top.xiaohuashifu.share.service.impl.*)")
	public void servicePoint(AdminLog adminLog) {}

	/**
	 * 管理员日志切面
	 *
	 * @param joinPoint ProceedingJoinPoint
     * @param  adminLog 表示需要添加管理员日志的注解
     * @return Object
	 */
    @Around(value = "@annotation(top.xiaohuashifu.share.aspect.annotation.AdminLog) && @annotation(adminLog) && args(..)")
	public Object serviceLog(ProceedingJoinPoint joinPoint, AdminLog adminLog) throws Throwable {
        Object result =  joinPoint.proceed();

		// 敏感词添加
		if (adminLog.type().equals(AdminLogType.SENSITIVE_WORD_ADD)) {
		    if (result instanceof SensitiveWordVO) {
                SensitiveWordVO sensitiveWordVO = (SensitiveWordVO) result;
                AdminLogDO adminLogDO = new AdminLogDO();
                adminLogDO.setAdminId(1);
                adminLogDO.setContent("管理员添加了敏感词：" + sensitiveWordVO.getSensitiveWord());
                adminLogService.saveAdminLog(adminLogDO);
            }
		}
		// 敏感词删除
		else if (adminLog.type().equals(AdminLogType.SENSITIVE_WORD_DELETE)) {
            if (result instanceof String) {
                // 获取敏感词
                String message = (String) result;
                String sensitiveWord = message.substring(message.indexOf(":")+ 1);
                sensitiveWord = sensitiveWord.substring(0, sensitiveWord.indexOf(" "));
                // 保存敏感词
                AdminLogDO adminLogDO = new AdminLogDO();
                adminLogDO.setAdminId(1);
                adminLogDO.setContent("管理员删除了敏感词：" + sensitiveWord);
                adminLogService.saveAdminLog(adminLogDO);
            }
        }
		// 用户开放和禁用
		else if (adminLog.type().equals(AdminLogType.USER)) {
            Object[] args = joinPoint.getArgs();
            if (result instanceof UserVO) {
                // 获取参数
                TokenAO tokenAO = null;
                UserDO userDO = null;
                for (Object arg : args) {
                    if (arg instanceof TokenAO) {
                        tokenAO = (TokenAO) arg;
                    }
                    if (arg instanceof UserDO) {
                        userDO = (UserDO) arg;
                    }
                }

                // 值对token-type是ADMIN的进行日志操作
                if (tokenAO.getType() == TokenType.ADMIN) {
                    UserVO userVO = (UserVO) result;
                    AdminLogDO adminLogDO = new AdminLogDO();
                    adminLogDO.setAdminId(1);
                    // 这里是开放
                    if (userDO.getAvailable()) {
                        adminLogDO.setContent("管理员开放了用户账号：id=" + userVO.getId() +
                                "，用户名=" + userVO.getUsername() + "，用户名=" + userVO.getNickName());
                    }
                    // 这里是禁用
                    else {
                        adminLogDO.setContent("管理员禁用了用户账号：id=" + userVO.getId() +
                                "，用户名=" + userVO.getUsername() + "，用户名=" + userVO.getNickName());
                    }
                    adminLogService.saveAdminLog(adminLogDO);
                }
            }
        }

		return result;
	}

}
