package top.xiaohuashifu.share.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.dao.ShareCollectionMapper;
import top.xiaohuashifu.share.pojo.do0.ShareCollectionDO;
import top.xiaohuashifu.share.pojo.do0.ShareDO;
import top.xiaohuashifu.share.pojo.query.ShareCollectionQuery;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.ShareCollectionService;
import top.xiaohuashifu.share.service.ShareService;
import top.xiaohuashifu.share.service.UserService;
import top.xiaohuashifu.share.service.constant.ShareConstant;
import top.xiaohuashifu.share.service.constant.UserConstant;

/**
 * 描述:分享收藏服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Service("shareCollectionService")
public class ShareCollectionServiceImpl implements ShareCollectionService {

    private static final Logger logger = LoggerFactory.getLogger(ShareCollectionServiceImpl.class);

    private final ShareCollectionMapper shareCollectionMapper;

    private final ShareService shareService;

    private final UserService userService;

    @Autowired
    public ShareCollectionServiceImpl(ShareCollectionMapper shareCollectionMapper, ShareService shareService,
                                      UserService userService) {
        this.shareCollectionMapper = shareCollectionMapper;
        this.shareService = shareService;
        this.userService = userService;
    }

    // TODO: 2020/4/18 这里应该用事务
    /**
     * 保存ShareCollection
     * @param shareCollectionDO ShareCollectionDO
     * @return Result<ShareCollectionDO>
     */
    @Override
    public Result<ShareCollectionDO> saveShareCollection(ShareCollectionDO shareCollectionDO) {
        // 看看分享存不存在
        Result<ShareDO> result = shareService.getShare(shareCollectionDO.getShareId());
        if (!result.isSuccess()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The share of id={0} not exists.", shareCollectionDO.getShareId());
        }

        // 判断分享收藏记录是否已经存在了（是否已经收藏了）
        int count = shareCollectionMapper.countByUserIdAndShareId(shareCollectionDO.getUserId(),
                shareCollectionDO.getShareId());
        if (count >= 1) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT,
                    "The record of userId={0} and shareId={1} has been exists.",
                    shareCollectionDO.getUserId(), shareCollectionDO.getShareId());
        }

        count = shareCollectionMapper.insertShareCollection(shareCollectionDO);
        // 没有插入成功
        if (count < 1) {
            logger.error("Insert shareCollection fail. The userId={0} and shareId={1}.",
                    shareCollectionDO.getUserId(), shareCollectionDO.getShareId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert shareCollection fail.");
        }

        // 分享的收藏数+1
        shareService.updateShare(shareCollectionDO.getShareId(), ShareConstant.COLUMN_NAME_OF_COLLECTIONS,
                Operator.INCREMENT);
        // 用户的收藏数+1
        userService.updateUser(shareCollectionDO.getUserId(), UserConstant.COLUMN_NAME_OF_COLLECTIONS,
                Operator.INCREMENT);

        return getShareCollection(shareCollectionDO.getId());
    }

    // TODO: 2020/4/20 事务
    /**
     * 删除ShareCollection
     * @param userId 用户id
     * @param shareId 分享id
     * @return 提示信息
     */
    @Override
    public Result deleteShareCollection(Integer userId, Integer shareId) {
        // 判断分享收藏记录是否已经存在了（是否已经收藏了）
        int count = shareCollectionMapper.countByUserIdAndShareId(userId, shareId);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The record of userId={0} and shareId={1} is not exists.", userId, shareId);
        }

        count = shareCollectionMapper.deleteShareCollectionByUserIdAndShareId(userId, shareId);
        // 没有删除成功
        if (count < 1) {
            logger.error("Delete shareCollection fail. The userId={0} and shareId={1}.", userId, shareId);
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Delete shareCollection fail.");
        }

        // 分享的收藏数-1
        shareService.updateShare(shareId, ShareConstant.COLUMN_NAME_OF_COLLECTIONS, Operator.DECREMENT);
        // 用户的收藏数-1
        userService.updateUser(userId, UserConstant.COLUMN_NAME_OF_COLLECTIONS, Operator.DECREMENT);

        return Result.success("Delete ShareCollection success. The userId={0} and shareId={1}.", userId, shareId);
    }

    /**
     * 获取ShareCollectionDO
     * @param id 编号
     * @return ShareCollectionDO
     */
    @Override
    public Result<ShareCollectionDO> getShareCollection(Integer id) {
        ShareCollectionDO shareCollectionDO = shareCollectionMapper.getShareCollection(id);
        if (shareCollectionDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not such shareCollection of id={0}.", id);
        }

        return Result.success(shareCollectionDO);
    }

    /**
     * 计算shareCollection的数量
     * @param userId 用户id
     * @param shareId 分享id
     * @return ShareCollection的数量
     */
    @Override
    public Result<Integer> countByUserIdAndShareId(Integer userId, Integer shareId) {
        return Result.success(shareCollectionMapper.countByUserIdAndShareId(userId, shareId));
    }

    /**
     * 获取PageInfo<ShareCollectionDO>通过查询参数query
     *
     * @param query 查询参数
     * @return PageInfo<ShareCollectionDO>
     */
    @Override
    public Result<PageInfo<ShareCollectionDO>> listShareCollections(ShareCollectionQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<ShareCollectionDO> pageInfo = new PageInfo<>(shareCollectionMapper.listShareCollections(query));
        if (pageInfo.getList().isEmpty()) {
            Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(pageInfo);
    }

}
