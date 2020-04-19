package top.xiaohuashifu.share.dao;

import org.apache.ibatis.annotations.Param;
import top.xiaohuashifu.share.pojo.do0.ShareCollectionDO;
import top.xiaohuashifu.share.pojo.query.ShareCollectionQuery;

import java.util.List;

public interface ShareCollectionMapper {

    /**
     * 保存分享收藏
     * @param shareCollectionDO 分享收藏
     * @return 保存的数量
     */
    int insertShareCollection(ShareCollectionDO shareCollectionDO);

    /**
     * 删除分享收藏
     * @param userId 用户id
     * @param shareId 分享id
     * @return 删除的数量
     */
    int deleteShareCollectionByUserIdAndShareId(@Param("userId") Integer userId, @Param("shareId") Integer shareId);

    /**
     * 获取分享收藏
     * @param id 编号
     * @return ShareLikeDO
     */
    ShareCollectionDO getShareCollection(Integer id);

    /**
     * 查询分享收藏列表
     * @param query 查询参数
     * @return List<ShareCollectionDO>
     */
    List<ShareCollectionDO> listShareCollections(ShareCollectionQuery query);

    /**
     * 对应userId和shareId的分享收藏数量
     * @param userId 用户id
     * @param shareId 分享id
     * @return 分享收藏数量
     */
    int countByUserIdAndShareId(@Param("userId") Integer userId, @Param("shareId") Integer shareId);

}