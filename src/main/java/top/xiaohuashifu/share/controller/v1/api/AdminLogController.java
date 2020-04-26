package top.xiaohuashifu.share.controller.v1.api;

import com.github.pagehelper.PageInfo;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.xiaohuashifu.share.aspect.annotation.ErrorHandler;
import top.xiaohuashifu.share.auth.TokenAuth;
import top.xiaohuashifu.share.constant.TokenType;
import top.xiaohuashifu.share.pojo.ao.TokenAO;
import top.xiaohuashifu.share.pojo.do0.AdminLogDO;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.pojo.query.AdminLogQuery;
import top.xiaohuashifu.share.pojo.vo.AdminLogVO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.AdminLogService;
import top.xiaohuashifu.share.validator.annotation.Id;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述: 管理员日志模块
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@RestController
@RequestMapping("v1/admins/logs")
@Validated
public class AdminLogController {

    private final Mapper mapper;

    private final AdminLogService adminLogService;

    @Autowired
    public AdminLogController(Mapper mapper, AdminLogService adminLogService) {
        this.mapper = mapper;
        this.adminLogService = adminLogService;
    }

    /**
     * 创建AdminLog并返回AdminLog
     * @param adminLogDO 管理员日志
     * @return AdminLogVO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INVALID_PARAMETER
     * FORBIDDEN_SUB_USER
     *
     * @bindErrors
     * INVALID_PARAMETER
     * INVALID_PARAMETER_IS_NULL
     * INVALID_PARAMETER_IS_BLANK
     * INVALID_PARAMETER_SIZE
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @TokenAuth(tokenType = TokenType.ADMIN)
    @ErrorHandler
    public Object post(TokenAO tokenAO, @Validated(GroupPost.class) AdminLogDO adminLogDO) {
        // 越权新建
        if (!tokenAO.getId().equals(adminLogDO.getAdminId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        Result<AdminLogDO> result = adminLogService.saveAdminLog(adminLogDO);
        return !result.isSuccess() ? result : mapper.map(result.getData(), AdminLogVO.class);
    }

    /**
     * 获取管理员日志
     * @param id 管理员日志编号
     * @return AdminLogVO
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     *
     * @bindErrors
     */
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = {TokenType.ADMIN})
    @ErrorHandler
    public Object get(@PathVariable @Id Integer id) {
        Result<AdminLogDO> result = adminLogService.getAdminLog(id);

        return !result.isSuccess() ? result : mapper.map(result.getData(), AdminLogVO.class);
    }

    /**
     * 查询AdminLog
     * @param query 查询参数
     * @return PageInfo<AdminLogVO>
     *
     * @success:
     * HttpStatus.OK
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = {TokenType.ADMIN})
    @ErrorHandler
    public Object get(AdminLogQuery query) {
        Result<PageInfo<AdminLogDO>> result = adminLogService.listAdminLogs(query);

        PageInfo<AdminLogDO> pageInfo1 = result.getData();
        List<AdminLogDO> adminLogDOList = pageInfo1.getList();
        List<AdminLogVO> adminLogVOList = adminLogDOList.stream()
                .map(adminLogDO -> mapper.map(adminLogDO, AdminLogVO.class))
                .collect(Collectors.toList());
        PageInfo<AdminLogVO> pageInfo2 = mapper.map(pageInfo1, PageInfo.class);
        pageInfo2.setList(adminLogVOList);
        return !result.isSuccess() ? result : pageInfo2;
    }

}
