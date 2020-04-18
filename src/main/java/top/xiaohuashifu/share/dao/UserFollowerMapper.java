package top.xiaohuashifu.share.dao;

import org.apache.ibatis.annotations.Param;
import top.xiaohuashifu.share.pojo.do0.UserFollowerDO;

public interface UserFollowerMapper {

    /**
     * 保存用户粉丝
     * @param userFollower 用户粉丝对象
     * @return 保存的数量
     */
    int insertUserFollower(UserFollowerDO userFollower);

    /**
     * 删除用户粉丝对象
     * @param followederId 被关注者id
     * @param followerId 关注者id
     * @return 删除的数量
     */
    int deleteUserFollowerByFollowederIdAndFollowerId(@Param("followederId") Integer followederId,
                                                      @Param("followerId") Integer followerId);

    /**
     * 获取userFollower
     * @param id 编号
     * @return UserFollowerDO
     */
    UserFollowerDO getUserFollower(Integer id);

    /**
     * 对应followederId和followerId的用户粉丝对象数量
     * @param followederId 被关注者id
     * @param followerId 关注者id
     * @return 用户粉丝对象数量
     */
    int countByFollowederIdAndFollowerId(@Param("followederId") Integer followederId,
                                                      @Param("followerId") Integer followerId);

}