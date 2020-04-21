package top.xiaohuashifu.share.dao;

import org.apache.ibatis.annotations.Param;
import top.xiaohuashifu.share.pojo.do0.ShareCommentCommentLikeDO;

public interface ShareCommentCommentLikeMapper {

    /**
     * 保存分享评论的评论点赞
     * @param shareCommentCommentLikeDO 分享评论的评论点赞
     * @return 保存的数量
     */
    int insertShareCommentCommentLike(ShareCommentCommentLikeDO shareCommentCommentLikeDO);

    /**
     * 删除分享评论的评论点赞
     * @param userId 用户id
     * @param shareCommentCommentId 分享评论的评论id
     * @return 删除的数量
     */
    int deleteShareCommentCommentLikeByUserIdAndShareCommentCommentId(
            @Param("userId") Integer userId, @Param("shareCommentCommentId") Integer shareCommentCommentId);

    /**
     * 获取分享评论的评论点赞
     * @param id 编号
     * @return ShareCommentCommentLikeDO
     */
    ShareCommentCommentLikeDO getShareCommentCommentLike(Integer id);

    /**
     * 对应userId和shareCommentCommentId的分享评论的评论点赞数量
     * @param userId 用户id
     * @param shareCommentCommentId 分享评论的评论id
     * @return 分享评论的评论点赞数量
     */
    int countByUserIdAndShareCommentCommentId(@Param("userId") Integer userId,
                                              @Param("shareCommentCommentId") Integer shareCommentCommentId);

}