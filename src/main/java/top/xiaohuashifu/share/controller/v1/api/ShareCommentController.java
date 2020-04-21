package top.xiaohuashifu.share.controller.v1.api;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.xiaohuashifu.share.aspect.annotation.ErrorHandler;
import top.xiaohuashifu.share.auth.TokenAuth;
import top.xiaohuashifu.share.constant.TokenType;
import top.xiaohuashifu.share.manager.ShareCommentManager;
import top.xiaohuashifu.share.pojo.ao.TokenAO;
import top.xiaohuashifu.share.pojo.do0.ShareCommentDO;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.pojo.query.ShareCommentQuery;
import top.xiaohuashifu.share.pojo.vo.ShareCommentVO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.validator.annotation.Id;

/**
 * 描述: 分享评论模块
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@RestController
@RequestMapping("v1/shares/comments")
@Validated
public class ShareCommentController {

    private final ShareCommentManager shareCommentManager;

    @Autowired
    public ShareCommentController(ShareCommentManager shareCommentManager) {
        this.shareCommentManager = shareCommentManager;
    }

    /**
     * 创建ShareComment并返回ShareComment
     * @param shareCommentDO 分享评论信息
     * @return ShareCommentVO
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
    public Object post(TokenAO tokenAO, @Validated(GroupPost.class) ShareCommentDO shareCommentDO) {
        // 越权新建分享
        if (!tokenAO.getId().equals(shareCommentDO.getUserId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        Result<ShareCommentVO> result = shareCommentManager.saveShareComment(shareCommentDO);
        return !result.isSuccess() ? result : result.getData();
    }

    /**
     * 获取分享评论
     * @param id 分享评论编号
     * @return ShareCommentVO
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
        Result<ShareCommentVO> result = shareCommentManager.getShareComment(id);
        return !result.isSuccess() ? result : result.getData();
    }

    /**
     * 查询shareComment
     * @param query 查询参数
     * @return PageInfo<ShareCommentVO>
     *
     * @success:
     * HttpStatus.OK
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ErrorHandler
    public Object get(ShareCommentQuery query) {
        Result<PageInfo<ShareCommentVO>> result = shareCommentManager.listShareComments(query);
        return !result.isSuccess() ? result : result.getData();
    }

}
