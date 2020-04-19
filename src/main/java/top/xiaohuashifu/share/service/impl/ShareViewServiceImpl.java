package top.xiaohuashifu.share.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.dao.ShareViewMapper;
import top.xiaohuashifu.share.pojo.do0.ShareDO;
import top.xiaohuashifu.share.pojo.do0.ShareViewDO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.ShareService;
import top.xiaohuashifu.share.service.ShareViewService;
import top.xiaohuashifu.share.service.constant.ShareConstant;

/**
 * 描述:分享观看服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Service("shareViewService")
public class ShareViewServiceImpl implements ShareViewService {

    private static final Logger logger = LoggerFactory.getLogger(ShareViewServiceImpl.class);

    private final ShareViewMapper shareViewMapper;

    private final ShareService shareService;

    @Autowired
    public ShareViewServiceImpl(ShareViewMapper shareViewMapper, ShareService shareService) {
        this.shareViewMapper = shareViewMapper;
        this.shareService = shareService;
    }

    // TODO: 2020/4/18 这里应该用事务
    /**
     * 保存ShareView
     * @param shareViewDO ShareViewDO
     * @return Result<ShareViewDO>
     */
    @Override
    public Result<ShareViewDO> saveShareView(ShareViewDO shareViewDO) {
        // 看看分享存不存在
        Result<ShareDO> result = shareService.getShare(shareViewDO.getShareId());
        if (!result.isSuccess()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The share of id={0} not exists.", shareViewDO.getShareId());
        }

        // 判断分享点赞记录是否已经存在了（是否已经点赞了）
        int count = shareViewMapper.countByUserIdAndShareId(shareViewDO.getUserId(), shareViewDO.getShareId());
        if (count >= 1) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT,
                    "The record of userId={0} and shareId={1} has been exists.",
                    shareViewDO.getUserId(), shareViewDO.getShareId());
        }

        count = shareViewMapper.insertShareView(shareViewDO);
        // 没有插入成功
        if (count < 1) {
            logger.error("Insert shareView fail. The userId={0} and shareId={1}.",
                    shareViewDO.getUserId(), shareViewDO.getShareId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert shareView fail.");
        }

        // 分享的点赞数+1
        shareService.updateShare(shareViewDO.getShareId(), ShareConstant.COLUMN_NAME_OF_VIEWS, Operator.INCREMENT);

        return getShareView(shareViewDO.getId());
    }

    // TODO: 2020/4/20 事务
    /**
     * 删除ShareView
     * @param userId 用户id
     * @param shareId 分享id
     * @return 提示信息
     */
    @Override
    public Result deleteShareView(Integer userId, Integer shareId) {
        // 判断分享观看记录是否已经存在了（是否已经观看了）
        int count = shareViewMapper.countByUserIdAndShareId(userId, shareId);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The record of userId={0} and shareId={1} is not exists.", userId, shareId);
        }

        count = shareViewMapper.deleteShareViewByUserIdAndShareId(userId, shareId);
        // 没有删除成功
        if (count < 1) {
            logger.error("Delete shareView fail. The userId={0} and shareId={1}.", userId, shareId);
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Delete shareView fail.");
        }

        // 分享的观看数-1
        shareService.updateShare(shareId, ShareConstant.COLUMN_NAME_OF_VIEWS, Operator.DECREMENT);

        return Result.success("Delete shareView success. The userId={0} and shareId={1}.", userId, shareId);
    }

    /**
     * 获取ShareViewDO
     * @param id 编号
     * @return ShareViewDO
     */
    @Override
    public Result<ShareViewDO> getShareView(Integer id) {
        ShareViewDO shareViewDO = shareViewMapper.getShareView(id);
        if (shareViewDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not such shareView of id={0}.", id);
        }

        return Result.success(shareViewDO);
    }

}
