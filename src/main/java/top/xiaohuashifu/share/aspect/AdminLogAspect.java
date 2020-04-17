package top.xiaohuashifu.share.aspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;


import top.xiaohuashifu.share.aspect.annotation.AdminLog;
import top.xiaohuashifu.share.constant.AdminLogType;
import top.xiaohuashifu.share.pojo.ao.TokenAO;
import top.xiaohuashifu.share.pojo.do0.AdminLogDO;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.AdminService;
import top.xiaohuashifu.share.service.TokenService;


/**
 * 管理员日志切面
 * 
 */
@Aspect
@Component
public class AdminLogAspect {
	
	private Integer currentAdminId;
	private final AdminService adminService;
	private final TokenService tokenService;
	private static final Logger logger = LoggerFactory.getLogger(AdminLogAspect.class);
	
	@Autowired
	public AdminLogAspect(AdminService adminService, TokenService tokenService) {
		this.currentAdminId = null;
		this.adminService = adminService;
		this.tokenService = tokenService;
	}
	
	@Pointcut("@annotation(top.xiaohuashifu.share.aspect.annotation.AdminLog) && @annotation(adminLog)")
	public void loginPoint(AdminLog adminLog) {}
	
	@Pointcut("@annotation(top.xiaohuashifu.share.aspect.annotation.AdminLog) && @annotation(adminLog) && within(top.xiaohuashifu.share.service.impl.*)")
	public void servicePoint(AdminLog adminLog) {}
	
	@AfterReturning(value = "loginPoint(adminLog)", returning = "model")
	public void loginLog(AdminLog adminLog, ModelAndView model) {
		String token = (String) model.getModel().get("token");
        Result<TokenAO> result = tokenService.getToken(token);
        if (!result.isSuccess()) {
        	logger.error("token获取失败");
        	return;
        }
        currentAdminId = result.getData().getId();
        AdminLogDO adminLogDO = new AdminLogDO(currentAdminId, adminLog.value());
		adminService.saveAdminLog(adminLogDO);
	}
	
	/**
	 * type为update时，要返回一个HashMap，存放一个key为"oldValue"的旧值，和一个key为"newValue"的新值
	 * 
	 * @param adminLog 对应的注解信息
	 */
	@AfterReturning(value = "servicePoint(adminLog)", returning = "result")
	public void serviceLog(AdminLog adminLog, Result result) {  //这里不能在Result加入泛型参数, 否则类型不对应而不能进入此切面
		if (!result.isSuccess()) {
			logger.error("操作失败");
			return;
		}
		Object data = result.getData();
		AdminLogDO adminLogDO = new AdminLogDO();
		adminLogDO.setAdminId(currentAdminId);
		if (adminLog.type().equals(AdminLogType.INSERT)) {
			adminLogDO.setContent(adminLog.value() + ", 添加的数据 : " + data.toString());
		}else if (adminLog.type().equals(AdminLogType.UPDATE)) {
			if (data instanceof HashMap) {
				Map<String, Object> dataMap = (HashMap<String, Object>) data;
				adminLogDO.setContent(adminLog.value() + "。更新前的数据 : " + dataMap.get("oldValue") 
					+ ";  更新后的数据 : " + dataMap.get("newValue"));
			}else {
				adminLogDO.setContent(adminLog.value() + ", 更新的数据 : " + data.toString());
			}
		}else if (adminLog.type().equals(AdminLogType.DELETE)) {
			adminLogDO.setContent(adminLog.value() + ", 删除的数据id是 : " + data.toString());
		}
		adminService.saveAdminLog(adminLogDO);
	}
	
}
