package top.xiaohuashifu.share.controller.v1.api;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import top.xiaohuashifu.share.aspect.annotation.ErrorHandler;
import top.xiaohuashifu.share.auth.TokenAuth;
import top.xiaohuashifu.share.constant.TokenType;
import top.xiaohuashifu.share.pojo.ao.TokenAO;
import top.xiaohuashifu.share.pojo.do0.ShareCommentCommentLikeDO;
import top.xiaohuashifu.share.pojo.group.GroupDelete;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.pojo.vo.ShareCommentCommentLikeVO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.ShareCommentCommentLikeService;

/**
 * 描述: 分享评论的评论点赞模块
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@RestController
@RequestMapping("v1/shares/comments/comments/likes")
@Validated
public class ShareCommentCommentLikeController {

    private final Mapper mapper;

    private final ShareCommentCommentLikeService shareCommentCommentLikeService;

    @Autowired
    public ShareCommentCommentLikeController(Mapper mapper,
                                             ShareCommentCommentLikeService shareCommentCommentLikeService) {
        this.mapper = mapper;
        this.shareCommentCommentLikeService = shareCommentCommentLikeService;
    }

    /**
     * 创建ShareCommentCommentLikeDO并返回ShareCommentCommentLikeDO
     * @param shareCommentCommentLikeDO 分享评论的评论点赞信息
     * @return ShareCommentCommentLikeDO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INVALID_PARAMETER_NOT_FOUND
     * OPERATION_CONFLICT
     * INTERNAL_ERROR
     *
     * @bindErrors
     * INVALID_PARAMETER
     * INVALID_PARAMETER_IS_NULL
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @TokenAuth(tokenType = {TokenType.USER})
    @ErrorHandler
    public Object post(TokenAO tokenAO,
                       @Validated(GroupPost.class) ShareCommentCommentLikeDO shareCommentCommentLikeDO) {
        // 不能越权
        if (!tokenAO.getId().equals(shareCommentCommentLikeDO.getUserId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        Result<ShareCommentCommentLikeDO> result =
                shareCommentCommentLikeService.saveShareCommentCommentLike(shareCommentCommentLikeDO);
        return !result.isSuccess() ? result : mapper.map(result.getData(), ShareCommentCommentLikeVO.class);
    }

    /**
     * 删除ShareCommentCommentLike
     * @param shareCommentCommentLikeDO 分享评论的评论点赞信息
     * @return 提示信息
     *
     * @success:
     * HttpStatus.DELETE
     *
     * @errors:
     * INVALID_PARAMETER_NOT_FOUND
     * INTERNAL_ERROR
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_NULL
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @TokenAuth(tokenType = {TokenType.USER})
    @ErrorHandler
    public Object delete(TokenAO tokenAO,
                         @Validated(GroupDelete.class) ShareCommentCommentLikeDO shareCommentCommentLikeDO) {
        // 不能越权
        if (!tokenAO.getId().equals(shareCommentCommentLikeDO.getUserId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        Result result = shareCommentCommentLikeService.deleteShareCommentCommentLike(
                shareCommentCommentLikeDO.getUserId(), shareCommentCommentLikeDO.getShareCommentCommentId());
        return !result.isSuccess() ? result : result.getMessage();
    }

}
