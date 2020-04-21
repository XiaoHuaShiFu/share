package top.xiaohuashifu.share.dao;

import top.xiaohuashifu.share.pojo.do0.AdminLogDO;
import top.xiaohuashifu.share.pojo.query.AdminLogQuery;

import java.util.List;

public interface AdminLogMapper {
	/**
	 * 插入adminlog
	 * @param adminLogDO 管理员日志
	 * @return 插入数量
	 */
	Integer insertAdminLog(AdminLogDO adminLogDO);

	/**
	 * 查询管理员日志
	 * @param id 管理员日志id
	 * @return AdminLogDO
	 */
	AdminLogDO getAdminLog(Integer id);

	/**
	 * 查询adminLog
	 * @param query 查询参数
	 * @return AdminLogDOList
	 */
	List<AdminLogDO> listAdminLogs(AdminLogQuery query);

}
