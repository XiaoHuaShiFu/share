package top.xiaohuashifu.share.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xiaohuashifu.share.dao.AdminLogMapper;
import top.xiaohuashifu.share.pojo.do0.AdminLogDO;
import top.xiaohuashifu.share.pojo.query.AdminLogQuery;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.AdminLogService;

/**
 * 描述:管理员日志服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Service("adminLogService")
public class AdminLogServiceImpl implements AdminLogService {

    private static final Logger logger = LoggerFactory.getLogger(AdminLogServiceImpl.class);

    private final AdminLogMapper adminLogMapper;

    @Autowired
    public AdminLogServiceImpl(AdminLogMapper adminLogMapper) {
        this.adminLogMapper = adminLogMapper;
    }

    /**
     * 保存分享日志
     * @param adminLogDO AdminLogDO
     * @return Result<AdminLogDO>
     */
    @Override
    public Result<AdminLogDO> saveAdminLog(AdminLogDO adminLogDO) {
        int count = adminLogMapper.insertAdminLog(adminLogDO);
        // 没有插入成功
        if (count < 1) {
            logger.error("Insert adminLog fail.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert adminLog fail.");
        }

        return getAdminLog(adminLogDO.getId());
    }

    /**
     * 获取AdminLogDO通过id
     *
     * @param id 管理员日志编号
     * @return AdminLogDO
     */
    @Override
    public Result<AdminLogDO> getAdminLog(Integer id) {
        AdminLogDO adminLogDO = adminLogMapper.getAdminLog(id);
        if (adminLogDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified id does not exist.");
        }
        return Result.success(adminLogDO);
    }

    /**
     * 获取PageInfo<AdminLogDO>通过查询参数query
     *
     * @param query 查询参数
     * @return PageInfo<AdminLogDO>
     */
    @Override
    public Result<PageInfo<AdminLogDO>> listAdminLogs(AdminLogQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<AdminLogDO> pageInfo = new PageInfo<>(adminLogMapper.listAdminLogs(query));
        if (pageInfo.getList().isEmpty()) {
            Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(pageInfo);
    }

}
