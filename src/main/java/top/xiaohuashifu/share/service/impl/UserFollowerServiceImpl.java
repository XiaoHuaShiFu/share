package top.xiaohuashifu.share.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.dao.UserFollowerMapper;
import top.xiaohuashifu.share.pojo.do0.UserDO;
import top.xiaohuashifu.share.pojo.do0.UserFollowerDO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.UserFollowerService;
import top.xiaohuashifu.share.service.UserService;
import top.xiaohuashifu.share.service.constant.UserConstant;

/**
 * 描述:用户粉丝服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Service("userFollowerService")
public class UserFollowerServiceImpl implements UserFollowerService {

    private static final Logger logger = LoggerFactory.getLogger(UserFollowerServiceImpl.class);

    private final UserFollowerMapper userFollowerMapper;

    private final UserService userService;

    @Autowired
    public UserFollowerServiceImpl(UserFollowerMapper userFollowerMapper, UserService userService) {
        this.userFollowerMapper = userFollowerMapper;
        this.userService = userService;
    }

    // TODO: 2020/4/18 这里应该用事务
    /**
     * 保存UserFollower
     * @param userFollowerDO UserFollower
     * @return Result<UserFollowerDO>
     */
    @Override
    public Result<UserFollowerDO> saveUserFollower(UserFollowerDO userFollowerDO) {
        // 不能关注自己
        if (userFollowerDO.getFollowederId().equals(userFollowerDO.getFollowerId())) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "The followederId and followerId must be not equals.");
        }

        // 判断被关注者存不存在
        Result<UserDO> result = userService.getUser(userFollowerDO.getFollowederId());
        if (!result.isSuccess()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The followeder not exists.");
        }

        // 判断用户粉丝记录是否已经存在了（是否已经关注了）
        int count = userFollowerMapper.countByFollowederIdAndFollowerId(
                userFollowerDO.getFollowederId(), userFollowerDO.getFollowerId());
        if (count >= 1) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT,
                    "The record of followederId={0} and followerId={1} has been exists.",
                    userFollowerDO.getFollowederId(), userFollowerDO.getFollowerId());
        }

        count = userFollowerMapper.insertUserFollower(userFollowerDO);
        // 没有插入成功
        if (count < 1) {
            logger.error("Insert userFollower fail. The followederId={0} and followerId={1}.",
                    userFollowerDO.getFollowederId(), userFollowerDO.getFollowerId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert userFollower fail.");
        }

        // 被关注者粉丝数+1
        userService.updateUser(userFollowerDO.getFollowederId(), UserConstant.COLUMN_NAME_OF_FOLLOWERS, Operator.INCREMENT);
        // 关注者关注数+1
        userService.updateUser(userFollowerDO.getFollowerId(), UserConstant.COLUMN_NAME_OF_FOLLOWINGS, Operator.INCREMENT);

        return getUserFollower(userFollowerDO.getId());
    }

    /**
     * 删除UserFollower
     * @param followederId 被关注者id
     * @param followerId 关注者id
     * @return 被删除的UserFollower
     */
    @Override
    public Result<String> deleteUserFollower(Integer followederId, Integer followerId) {
        // 判断用户粉丝记录是否已经存在了（是否已经关注了）
        int count = userFollowerMapper.countByFollowederIdAndFollowerId(followederId, followerId);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The record of followederId={0} and followerId={1} is not exists.", followederId, followerId);
        }

        count = userFollowerMapper.deleteUserFollowerByFollowederIdAndFollowerId(followederId, followerId);
        // 没有删除成功
        if (count < 1) {
            logger.error("Delete userFollower fail. The followederId={0} and followerId={1}.", followederId, followerId);
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Delete userFollower fail.");
        }

        // 被关注者粉丝数-1
        userService.updateUser(followederId, UserConstant.COLUMN_NAME_OF_FOLLOWERS, Operator.DECREMENT);
        // 关注者关注数-1
        userService.updateUser(followerId, UserConstant.COLUMN_NAME_OF_FOLLOWINGS, Operator.DECREMENT);

        return Result.success("Delete userFollower success. The followederId=" + followederId +
                " and followerId=" + followerId + ".");
    }

    /**
     * 获取UserFollowerDO
     * @param id 编号
     * @return UserFollowerDO
     */
    @Override
    public Result<UserFollowerDO> getUserFollower(Integer id) {
        UserFollowerDO userFollowerDO = userFollowerMapper.getUserFollower(id);
        if (userFollowerDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not such userFollower of id={0}.", id);
        }

        return Result.success(userFollowerDO);
    }

}
