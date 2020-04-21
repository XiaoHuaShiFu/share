package top.xiaohuashifu.share.controller.v1.api;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.xiaohuashifu.share.aspect.annotation.ErrorHandler;
import top.xiaohuashifu.share.auth.TokenAuth;
import top.xiaohuashifu.share.constant.TokenType;
import top.xiaohuashifu.share.manager.ShareCommentCommentManager;
import top.xiaohuashifu.share.pojo.ao.TokenAO;
import top.xiaohuashifu.share.pojo.do0.ShareCommentCommentDO;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.pojo.query.ShareCommentCommentQuery;
import top.xiaohuashifu.share.pojo.vo.ShareCommentCommentVO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.validator.annotation.Id;

/**
 * 描述: 分享评论的评论模块
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@RestController
@RequestMapping("v1/shares/comments/comments")
@Validated
public class ShareCommentCommentController {

    private final ShareCommentCommentManager shareCommentCommentManager;

    @Autowired
    public ShareCommentCommentController(ShareCommentCommentManager shareCommentCommentManager) {
        this.shareCommentCommentManager = shareCommentCommentManager;
    }

    /**
     * 创建ShareCommentComment并返回ShareCommentComment
     * @param shareCommentCommentDO 分享评论的评论信息
     * @return ShareCommentCommentVO
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
    public Object post(TokenAO tokenAO, @Validated(GroupPost.class) ShareCommentCommentDO shareCommentCommentDO) {
        // 越权新建分享
        if (!tokenAO.getId().equals(shareCommentCommentDO.getUserId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        Result<ShareCommentCommentVO> result = shareCommentCommentManager.saveShareCommentComment(shareCommentCommentDO);
        return !result.isSuccess() ? result : result.getData();
    }

    /**
     * 获取分享评论的评论
     * @param id 分享评论的评论编号
     * @return ShareCommentCommentVO
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
    @TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN, TokenType.ANON})
    @ErrorHandler
    public Object get(TokenAO tokenAO, @PathVariable @Id Integer id) {
        Result<ShareCommentCommentVO> result;
        // 如果是admin-token或者anon-token
        if (tokenAO.getType() == TokenType.ADMIN || tokenAO.getType() == TokenType.ANON) {
            result = shareCommentCommentManager.getShareCommentComment(id, 0);
        }
        // user-token
        else if (tokenAO.getType() == TokenType.USER){
            result = shareCommentCommentManager.getShareCommentComment(id, tokenAO.getId());
        }
        // 非法权限token
        else {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        return !result.isSuccess() ? result : result.getData();
    }

    /**
     * 查询shareCommentComment
     * @param query 查询参数
     * @return PageInfo<ShareCommentCommentVO>
     *
     * @success:
     * HttpStatus.OK
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN, TokenType.ANON})
    @ErrorHandler
    public Object get(TokenAO tokenAO, ShareCommentCommentQuery query) {
        Result<PageInfo<ShareCommentCommentVO>> result;
        // 如果是admin-token或者anon-token
        if (tokenAO.getType() == TokenType.ADMIN || tokenAO.getType() == TokenType.ANON) {
            result = shareCommentCommentManager.listShareCommentComments(query, 0);
        }
        // user-token
        else if (tokenAO.getType() == TokenType.USER){
            result = shareCommentCommentManager.listShareCommentComments(query, tokenAO.getId());
        }
        // 非法权限token
        else {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        return !result.isSuccess() ? result : result.getData();
    }

}
