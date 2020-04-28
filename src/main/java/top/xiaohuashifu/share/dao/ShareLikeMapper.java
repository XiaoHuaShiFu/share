package top.xiaohuashifu.share.dao;

import org.apache.ibatis.annotations.Param;
import top.xiaohuashifu.share.pojo.do0.ShareLikeDO;
import top.xiaohuashifu.share.pojo.query.ShareLikeQuery;

import java.util.List;

public interface ShareLikeMapper {

    /**
     * 保存分享点赞
     * @param shareLikeDO 分享点赞
     * @return 保存的数量
     */
    int insertShareLike(ShareLikeDO shareLikeDO);

    /**
     * 删除分享点赞
     * @param userId 用户id
     * @param shareId 分享id
     * @return 删除的数量
     */
    int deleteShareLikeByUserIdAndShareId(@Param("userId") Integer userId, @Param("shareId") Integer shareId);

    /**
     * 获取分享点赞
     * @param id 编号
     * @return ShareLikeDO
     */
    ShareLikeDO getShareLike(Integer id);

    /**
     * 获取query过滤参数后的分享点赞列表，包含pageNum，pageSize等过滤参数，
     *
     * @param query 查询参数
     * @return ShareLikeDOList
     */
    List<ShareLikeDO> listShareLikes(ShareLikeQuery query);


    /**
     * 对应userId和shareId的分享点赞数量
     * @param userId 用户id
     * @param shareId 分享id
     * @return 分享点赞数量
     */
    int countByUserIdAndShareId(@Param("userId") Integer userId, @Param("shareId") Integer shareId);

}