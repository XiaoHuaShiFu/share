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
import top.xiaohuashifu.share.pojo.do0.ShareLikeDO;
import top.xiaohuashifu.share.pojo.group.GroupDelete;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.pojo.vo.ShareLikeVO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.ShareLikeService;

/**
 * 描述: 分享点赞模块
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@RestController
@RequestMapping("v1/shares/likes")
@Validated
public class ShareLikeController {

    private final Mapper mapper;

    private final ShareLikeService shareLikeService;

    @Autowired
    public ShareLikeController(Mapper mapper, ShareLikeService shareLikeService) {
        this.mapper = mapper;
        this.shareLikeService = shareLikeService;
    }

    /**
     * 创建ShareLikeDO并返回ShareLikeDO
     * @param shareLikeDO 分享点赞信息
     * @return ShareLikeDO
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
    public Object post(TokenAO tokenAO, @Validated(GroupPost.class) ShareLikeDO shareLikeDO) {
        // 不能越权
        if (!tokenAO.getId().equals(shareLikeDO.getUserId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        Result<ShareLikeDO> result = shareLikeService.saveShareLike(shareLikeDO);
        return !result.isSuccess() ? result : mapper.map(result.getData(), ShareLikeVO.class);
    }

    /**
     * 删除ShareLike
     * @param shareLikeDO 分享点赞信息
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
    public Object delete(TokenAO tokenAO, @Validated(GroupDelete.class) ShareLikeDO shareLikeDO) {
        // 不能越权
        if (!tokenAO.getId().equals(shareLikeDO.getUserId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        Result result = shareLikeService.deleteShareLike(shareLikeDO.getUserId(), shareLikeDO.getShareId());
        return !result.isSuccess() ? result : result.getMessage();
    }

}
