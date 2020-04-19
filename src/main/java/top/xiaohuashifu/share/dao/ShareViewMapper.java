package top.xiaohuashifu.share.dao;

import org.apache.ibatis.annotations.Param;
import top.xiaohuashifu.share.pojo.do0.ShareViewDO;

public interface ShareViewMapper {

    /**
     * 保存分享观看
     * @param shareViewDO 分享观看
     * @return 保存的数量
     */
    int insertShareView(ShareViewDO shareViewDO);

    /**
     * 删除分享观看
     * @param userId 用户id
     * @param shareId 分享id
     * @return 删除的数量
     */
    int deleteShareViewByUserIdAndShareId(@Param("userId") Integer userId, @Param("shareId") Integer shareId);

    /**
     * 获取分享观看
     * @param id 编号
     * @return ShareViewDO
     */
    ShareViewDO getShareView(Integer id);

    /**
     * 对应userId和shareId的分享观看数量
     * @param userId 用户id
     * @param shareId 分享id
     * @return 分享观看数量
     */
    int countByUserIdAndShareId(@Param("userId") Integer userId, @Param("shareId") Integer shareId);

}