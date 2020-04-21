package top.xiaohuashifu.share.controller.v1.api;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.xiaohuashifu.share.aspect.annotation.ErrorHandler;
import top.xiaohuashifu.share.auth.TokenAuth;
import top.xiaohuashifu.share.constant.TokenType;
import top.xiaohuashifu.share.manager.ShareManager;
import top.xiaohuashifu.share.pojo.ao.TokenAO;
import top.xiaohuashifu.share.pojo.do0.ShareDO;
import top.xiaohuashifu.share.pojo.group.Group;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.pojo.query.ShareQuery;
import top.xiaohuashifu.share.pojo.vo.ShareVO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.validator.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.List;

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

    private final ShareManager shareManager;

    @Autowired
    public ShareController(ShareManager shareManager) {
        this.shareManager = shareManager;
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
    public Object post(TokenAO tokenAO, @Validated(GroupPost.class) ShareDO shareDO,
                       @NotNull(message = "INVALID_PARAMETER_IS_NULL: At least one of image.")
                               List<MultipartFile> imageList) {
        // 越权新建分享
        if (!tokenAO.getId().equals(shareDO.getUserId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        Result<ShareVO> result = shareManager.saveShare(shareDO, imageList);
        return !result.isSuccess() ? result : result.getData();
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
     */
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ErrorHandler
    public Object get(@PathVariable @Id Integer id) {
        Result<ShareVO> result = shareManager.getShare(id);
        return !result.isSuccess() ? result : result.getData();
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
        Result<PageInfo<ShareVO>> result = shareManager.listShares(query);
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
            Result<ShareVO> result = shareManager.updateShare(shareDO);
            return result.isSuccess() ? result.getData() : result;
        }
        // 用户本人
        else if (shareDO.getUserId().equals(tokenAO.getId())) {
            Result<ShareVO> result = shareManager.updateShare(shareDO);
            return result.isSuccess() ? result.getData() : result;
        }

        // 非法权限token
        return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
    }

}
