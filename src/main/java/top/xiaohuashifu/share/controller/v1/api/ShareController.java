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
import top.xiaohuashifu.share.pojo.do0.ShareDO;
import top.xiaohuashifu.share.pojo.group.Group;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.pojo.query.ShareQuery;
import top.xiaohuashifu.share.pojo.vo.ShareVO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.ShareService;
import top.xiaohuashifu.share.validator.annotation.Id;

/**
 * 描述: 分享模块
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@RestController
@RequestMapping("v1/shares")
@Validated
public class ShareController {

    private final Mapper mapper;

    private final ShareService shareService;

    @Autowired
    public ShareController(Mapper mapper, ShareService shareService) {
        this.mapper = mapper;
        this.shareService = shareService;
    }

    /**
     * 创建Share并返回Share
     * @param shareDO 分享信息
     * @return ShareVO
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
    @TokenAuth(tokenType = TokenType.USER)
    @ErrorHandler
    public Object post(TokenAO tokenAO, @Validated(GroupPost.class) ShareDO shareDO) {
        // 越权新建分享
        if (!tokenAO.getId().equals(shareDO.getUserId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        Result<ShareDO> result = shareService.saveShare(shareDO);
        return !result.isSuccess() ? result : mapper.map(result.getData(), ShareVO.class);
    }

    /**
     * 获取分享
     * @param id 分享编号
     * @return ShareVO
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * FORBIDDEN_SUB_USER
     *
     * @bindErrors
     * INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.
     */
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ErrorHandler
    public Object get(@PathVariable @Id Integer id) {
        Result<ShareDO> result = shareService.getShare(id);
        return !result.isSuccess() ? result : mapper.map(result.getData(), ShareVO.class);
    }

    /**
     * 查询share
     * @param query 查询参数
     * @return PageInfo<ShareVO>
     *
     * @success:
     * HttpStatus.OK
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ErrorHandler
    public Object get(ShareQuery query) {
        Result<PageInfo<ShareDO>> result = shareService.listShares(query);
        return !result.isSuccess() ? result : result.getData();
    }

    /**
     * 更新Share并返回Share
     * @param tokenAO TokenAO
     * @param shareDO Share信息
     * @return ShareVO
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * INTERNAL_ERROR
     *
     * @bindErrors
     * INVALID_PARAMETER
     * INVALID_PARAMETER_IS_NULL
     * INVALID_PARAMETER_IS_BLANK
     * INVALID_PARAMETER_SIZE
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    // TODO: 2020/3/26 这里应该分开两个权限，分别进行权限控制
    // TODO: 2020/3/31 ADMIN可以控制修改一些信息，USER可以修改一些信息
    @TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
    @ErrorHandler
    public Object put(TokenAO tokenAO, @Validated(Group.class) ShareDO shareDO) {
        // 管理员
        if (tokenAO.getType() == TokenType.ADMIN) {
            Result<ShareDO> result = shareService.updateShare(shareDO);
            return result.isSuccess() ? mapper.map(result.getData(), ShareVO.class) : result;
        }
        // 用户本人
        else if (shareDO.getUserId().equals(tokenAO.getId())) {
            Result<ShareDO> result = shareService.updateShare(shareDO);
            return result.isSuccess() ? mapper.map(result.getData(), ShareVO.class) : result;
        }

        // 非法权限token
        return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
    }

}
