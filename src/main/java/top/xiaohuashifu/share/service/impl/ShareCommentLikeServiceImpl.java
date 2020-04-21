package top.xiaohuashifu.share.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.dao.ShareCommentLikeMapper;
import top.xiaohuashifu.share.pojo.do0.ShareCommentDO;
import top.xiaohuashifu.share.pojo.do0.ShareCommentLikeDO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.ShareCommentLikeService;
import top.xiaohuashifu.share.service.ShareCommentService;
import top.xiaohuashifu.share.service.constant.ShareCommentConstant;

/**
 * 描述:分享评论点赞服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Service("shareCommentLikeService")
public class ShareCommentLikeServiceImpl implements ShareCommentLikeService {

    private static final Logger logger = LoggerFactory.getLogger(ShareCommentLikeServiceImpl.class);

    private final ShareCommentLikeMapper shareCommentLikeMapper;

    private final ShareCommentService shareCommentService;

    @Autowired
    public ShareCommentLikeServiceImpl(ShareCommentLikeMapper shareCommentLikeMapper,
                                       ShareCommentService shareCommentService) {
        this.shareCommentLikeMapper = shareCommentLikeMapper;
        this.shareCommentService = shareCommentService;
    }

    // TODO: 2020/4/18 这里应该用事务
    /**
     * 保存ShareCommentLike
     * @param shareCommentLikeDO ShareCommentLikeDO
     * @return Result<ShareCommentLikeDO>
     */
    @Override
    public Result<ShareCommentLikeDO> saveShareCommentLike(ShareCommentLikeDO shareCommentLikeDO) {
        // 看看分享的评论存不存在
        Result<ShareCommentDO> result = shareCommentService.getShareComment(shareCommentLikeDO.getShareCommentId());
        if (!result.isSuccess()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The shareComment of id={0} not exists.", shareCommentLikeDO.getShareCommentId());
        }

        // 判断分享评论的点赞记录是否已经存在了（是否已经点赞了）
        int count = shareCommentLikeMapper.countByUserIdAndShareCommentId(
                shareCommentLikeDO.getUserId(), shareCommentLikeDO.getShareCommentId());
        if (count >= 1) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT,
                    "The record of userId={0} and shareCommentId={1} has been exists.",
                    shareCommentLikeDO.getUserId(), shareCommentLikeDO.getShareCommentId());
        }

        count = shareCommentLikeMapper.insertShareCommentLike(shareCommentLikeDO);
        // 没有插入成功
        if (count < 1) {
            logger.error("Insert shareCommentLike fail. The userId={0} and shareCommentId={1}.",
                    shareCommentLikeDO.getUserId(), shareCommentLikeDO.getShareCommentId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert shareCommentLike fail.");
        }

        // 分享评论的点赞数+1
        shareCommentService.updateShareComment(shareCommentLikeDO.getShareCommentId(),
                ShareCommentConstant.COLUMN_NAME_OF_LIKES, Operator.INCREMENT);

        return getShareCommentLike(shareCommentLikeDO.getId());
    }

    // TODO: 2020/4/20 事务
    /**
     * 删除ShareCommentLike
     * @param userId 用户id
     * @param shareCommentId 分享评论id
     * @return 提示信息
     */
    @Override
    public Result deleteShareCommentLike(Integer userId, Integer shareCommentId) {
        // 判断分享评论点赞记录是否已经存在了（是否已经点赞了）
        int count = shareCommentLikeMapper.countByUserIdAndShareCommentId(userId, shareCommentId);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The record of userId={0} and shareCommentId={1} is not exists.", userId, shareCommentId);
        }

        count = shareCommentLikeMapper.deleteShareCommentLikeByUserIdAndShareCommentId(userId, shareCommentId);
        // 没有删除成功
        if (count < 1) {
            logger.error("Delete shareCommentLike fail. The userId={0} and shareCommentId={1}.", userId, shareCommentId);
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Delete shareCommentLike fail.");
        }

        // 分享评论的点赞数-1
        shareCommentService.updateShareComment(shareCommentId, ShareCommentConstant.COLUMN_NAME_OF_LIKES,
                Operator.DECREMENT);

        return Result.success("Delete ShareCommentLike success. The userId={0} and shareCommentId={1}.",
                userId, shareCommentId);
    }

    /**
     * 获取ShareCommentLikeDO
     * @param id 编号
     * @return ShareCommentLikeDO
     */
    @Override
    public Result<ShareCommentLikeDO> getShareCommentLike(Integer id) {
        ShareCommentLikeDO shareCommentLikeDO = shareCommentLikeMapper.getShareCommentLike(id);
        if (shareCommentLikeDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not such shareCommentLike of id={0}.", id);
        }

        return Result.success(shareCommentLikeDO);
    }

}
