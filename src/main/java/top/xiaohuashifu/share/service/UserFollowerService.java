package top.xiaohuashifu.share.service;

import top.xiaohuashifu.share.pojo.do0.UserFollowerDO;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述: 用户粉丝Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface UserFollowerService {

    Result<UserFollowerDO> saveUserFollower(UserFollowerDO userFollowerDO);

    Result deleteUserFollower(Integer followederId, Integer followerId);

    Result<UserFollowerDO> getUserFollower(Integer id);

}
