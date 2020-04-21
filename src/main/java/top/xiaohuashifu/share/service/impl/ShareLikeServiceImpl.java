package top.xiaohuashifu.share.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.dao.ShareLikeMapper;
import top.xiaohuashifu.share.pojo.do0.ShareDO;
import top.xiaohuashifu.share.pojo.do0.ShareLikeDO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.ShareLikeService;
import top.xiaohuashifu.share.service.ShareService;
import top.xiaohuashifu.share.service.constant.ShareConstant;

/**
 * 描述:分享点赞服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Service("shareLikeService")
public class ShareLikeServiceImpl implements ShareLikeService {

    private static final Logger logger = LoggerFactory.getLogger(ShareLikeServiceImpl.class);

    private final ShareLikeMapper shareLikeMapper;

    private final ShareService shareService;

    @Autowired
    public ShareLikeServiceImpl(ShareLikeMapper shareLikeMapper, ShareService shareService) {
        this.shareLikeMapper = shareLikeMapper;
        this.shareService = shareService;
    }

    // TODO: 2020/4/18 这里应该用事务
    /**
     * 保存ShareLike
     * @param shareLikeDO ShareLikeDO
     * @return Result<ShareLikeDO>
     */
    @Override
    public Result<ShareLikeDO> saveShareLike(ShareLikeDO shareLikeDO) {
        // 看看分享存不存在
        Result<ShareDO> result = shareService.getShare(shareLikeDO.getShareId());
        if (!result.isSuccess()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The share of id={0} not exists.", shareLikeDO.getShareId());
        }

        // 判断分享点赞记录是否已经存在了（是否已经点赞了）
        int count = shareLikeMapper.countByUserIdAndShareId(shareLikeDO.getUserId(), shareLikeDO.getShareId());
        if (count >= 1) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT,
                    "The record of userId={0} and shareId={1} has been exists.",
                    shareLikeDO.getUserId(), shareLikeDO.getShareId());
        }

        count = shareLikeMapper.insertShareLike(shareLikeDO);
        // 没有插入成功
        if (count < 1) {
            logger.error("Insert shareLike fail. The userId={0} and shareId={1}.",
                    shareLikeDO.getUserId(), shareLikeDO.getShareId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert shareLike fail.");
        }

        // 分享的点赞数+1
        shareService.updateShare(shareLikeDO.getShareId(), ShareConstant.COLUMN_NAME_OF_LIKES, Operator.INCREMENT);

        return getShareLike(shareLikeDO.getId());
    }

    // TODO: 2020/4/20 事务
    /**
     * 删除ShareLike
     * @param userId 用户id
     * @param shareId 分享id
     * @return 提示信息
     */
    @Override
    public Result deleteShareLike(Integer userId, Integer shareId) {
        // 判断分享点赞记录是否已经存在了（是否已经点赞了）
        int count = shareLikeMapper.countByUserIdAndShareId(userId, shareId);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The record of userId={0} and shareId={1} is not exists.", userId, shareId);
        }

        count = shareLikeMapper.deleteShareLikeByUserIdAndShareId(userId, shareId);
        // 没有删除成功
        if (count < 1) {
            logger.error("Delete shareLike fail. The userId={0} and shareId={1}.", userId, shareId);
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Delete shareLike fail.");
        }

        // 分享的点赞数-1
        shareService.updateShare(shareId, ShareConstant.COLUMN_NAME_OF_LIKES, Operator.DECREMENT);

        return Result.success("Delete ShareLike success. The userId={0} and shareId={1}.", userId, shareId);
    }

    /**
     * 获取ShareLikeDO
     * @param id 编号
     * @return ShareLikeDO
     */
    @Override
    public Result<ShareLikeDO> getShareLike(Integer id) {
        ShareLikeDO shareLikeDO = shareLikeMapper.getShareLike(id);
        if (shareLikeDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not such shareLike of id={0}.", id);
        }

        return Result.success(shareLikeDO);
    }

    /**
     * 获取ShareLike的数量
     * @param userId 用户id
     * @param shareId 分享id
     * @return ShareLike的数量
     */
    @Override
    public Result<Integer> countByUserIdAndShareId(Integer userId, Integer shareId) {
        return Result.success(shareLikeMapper.countByUserIdAndShareId(userId, shareId));
    }

}
