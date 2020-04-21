package top.xiaohuashifu.share.service;

import com.github.pagehelper.PageInfo;
import top.xiaohuashifu.share.pojo.do0.AdminLogDO;
import top.xiaohuashifu.share.pojo.query.AdminLogQuery;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述: 管理员日志Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface AdminLogService {

    Result<AdminLogDO> saveAdminLog(AdminLogDO adminLogDO);

    Result<AdminLogDO> getAdminLog(Integer id);

    Result<PageInfo<AdminLogDO>> listAdminLogs(AdminLogQuery query);

}
