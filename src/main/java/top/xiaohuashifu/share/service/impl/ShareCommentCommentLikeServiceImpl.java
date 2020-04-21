package top.xiaohuashifu.share.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.dao.ShareCommentCommentLikeMapper;
import top.xiaohuashifu.share.pojo.do0.ShareCommentCommentDO;
import top.xiaohuashifu.share.pojo.do0.ShareCommentCommentLikeDO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.ShareCommentCommentLikeService;
import top.xiaohuashifu.share.service.ShareCommentCommentService;
import top.xiaohuashifu.share.service.constant.ShareCommentCommentConstant;

/**
 * 描述:分享评论的评论点赞服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Service("shareCommentCommentLikeService")
public class ShareCommentCommentLikeServiceImpl implements ShareCommentCommentLikeService {

    private static final Logger logger = LoggerFactory.getLogger(ShareCommentCommentLikeServiceImpl.class);

    private final ShareCommentCommentLikeMapper shareCommentCommentLikeMapper;

    private final ShareCommentCommentService shareCommentCommentService;

    @Autowired
    public ShareCommentCommentLikeServiceImpl(ShareCommentCommentLikeMapper shareCommentCommentLikeMapper,
                                              ShareCommentCommentService shareCommentCommentService) {
        this.shareCommentCommentLikeMapper = shareCommentCommentLikeMapper;
        this.shareCommentCommentService = shareCommentCommentService;
    }

    // TODO: 2020/4/18 这里应该用事务
    /**
     * 保存ShareCommentCommentLike
     * @param shareCommentCommentLikeDO ShareCommentCommentLikeDO
     * @return Result<ShareCommentCommentLikeDO>
     */
    @Override
    public Result<ShareCommentCommentLikeDO> saveShareCommentCommentLike(
            ShareCommentCommentLikeDO shareCommentCommentLikeDO) {
        // 看看分享的评论的评论存不存在
        Result<ShareCommentCommentDO> result = shareCommentCommentService.getShareCommentComment(
                shareCommentCommentLikeDO.getShareCommentCommentId());
        if (!result.isSuccess()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The shareCommentComment of id={0} not exists.",
                    shareCommentCommentLikeDO.getShareCommentCommentId());
        }

        // 判断分享评论的评论的点赞记录是否已经存在了（是否已经点赞了）
        int count = shareCommentCommentLikeMapper.countByUserIdAndShareCommentCommentId(
                shareCommentCommentLikeDO.getUserId(), shareCommentCommentLikeDO.getShareCommentCommentId());
        if (count >= 1) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT,
                    "The record of userId={0} and shareCommentCommentId={1} has been exists.",
                    shareCommentCommentLikeDO.getUserId(), shareCommentCommentLikeDO.getShareCommentCommentId());
        }

        count = shareCommentCommentLikeMapper.insertShareCommentCommentLike(shareCommentCommentLikeDO);
        // 没有插入成功
        if (count < 1) {
            logger.error("Insert shareCommentCommentLike fail. The userId={0} and shareCommentCommentId={1}.",
                    shareCommentCommentLikeDO.getUserId(), shareCommentCommentLikeDO.getShareCommentCommentId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert shareCommentCommentLike fail.");
        }

        // 分享评论的评论的点赞数+1
        shareCommentCommentService.updateShareCommentComment(shareCommentCommentLikeDO.getShareCommentCommentId(),
                ShareCommentCommentConstant.COLUMN_NAME_OF_LIKES, Operator.INCREMENT);

        return getShareCommentCommentLike(shareCommentCommentLikeDO.getId());
    }

    // TODO: 2020/4/20 事务
    /**
     * 删除ShareCommentCommentLike
     * @param userId 用户id
     * @param shareCommentCommentId 分享评论的评论id
     * @return 提示信息
     */
    @Override
    public Result deleteShareCommentCommentLike(Integer userId, Integer shareCommentCommentId) {
        // 判断分享评论的评论点赞记录是否已经存在了（是否已经点赞了）
        int count = shareCommentCommentLikeMapper.countByUserIdAndShareCommentCommentId(userId, shareCommentCommentId);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The record of userId={0} and shareCommentCommentId={1} is not exists.",
                    userId, shareCommentCommentId);
        }

        count = shareCommentCommentLikeMapper.deleteShareCommentCommentLikeByUserIdAndShareCommentCommentId(
                userId, shareCommentCommentId);
        // 没有删除成功
        if (count < 1) {
            logger.error("Delete shareCommentCommentLike fail. The userId={0} and shareCommentCommentId={1}.",
                    userId, shareCommentCommentId);
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Delete shareCommentCommentLike fail.");
        }

        // 分享评论的点赞数-1
        shareCommentCommentService.updateShareCommentComment(shareCommentCommentId,
                ShareCommentCommentConstant.COLUMN_NAME_OF_LIKES,
                Operator.DECREMENT);

        return Result.success("Delete ShareCommentCommentLike success. The userId={0} and shareCommentCommentId={1}.",
                userId, shareCommentCommentId);
    }

    /**
     * 获取ShareCommentCommentLikeDO
     * @param id 编号
     * @return ShareCommentCommentLikeDO
     */
    @Override
    public Result<ShareCommentCommentLikeDO> getShareCommentCommentLike(Integer id) {
        ShareCommentCommentLikeDO shareCommentCommentLikeDO =
                shareCommentCommentLikeMapper.getShareCommentCommentLike(id);
        if (shareCommentCommentLikeDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "Not such shareCommentCommentLike of id={0}.", id);
        }

        return Result.success(shareCommentCommentLikeDO);
    }

    /**
     * 查询ShareCommentComment的数量
     * @param userId 用户id
     * @param shareCommentCommentId 分享评论的评论id
     * @return ShareCommentComment的数量
     */
    @Override
    public Result<Integer> countByUserIdAndShareCommentCommentId(Integer userId, Integer shareCommentCommentId) {
        return Result.success(shareCommentCommentLikeMapper.countByUserIdAndShareCommentCommentId(
                userId, shareCommentCommentId));
    }

}
