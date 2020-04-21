package top.xiaohuashifu.share.dao;

import org.apache.ibatis.annotations.Param;
import top.xiaohuashifu.share.pojo.do0.ShareCommentLikeDO;

public interface ShareCommentLikeMapper {

    /**
     * 保存分享评论点赞
     * @param shareCommentLikeDO 分享评论点赞
     * @return 保存的数量
     */
    int insertShareCommentLike(ShareCommentLikeDO shareCommentLikeDO);

    /**
     * 删除分享评论点赞
     * @param userId 用户id
     * @param shareCommentId 分享评论id
     * @return 删除的数量
     */
    int deleteShareCommentLikeByUserIdAndShareCommentId(@Param("userId") Integer userId,
                                                        @Param("shareCommentId") Integer shareCommentId);

    /**
     * 获取分享评论点赞
     * @param id 编号
     * @return ShareCommentLikeDO
     */
    ShareCommentLikeDO getShareCommentLike(Integer id);

    /**
     * 对应userId和shareCommentId的分享评论点赞数量
     * @param userId 用户id
     * @param shareCommentId 分享评论id
     * @return 分享评论点赞数量
     */
    int countByUserIdAndShareCommentId(@Param("userId") Integer userId, @Param("shareCommentId") Integer shareCommentId);

}