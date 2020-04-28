package top.xiaohuashifu.share.dao;
import org.apache.ibatis.annotations.Param;
import top.xiaohuashifu.share.pojo.do0.ShareDO;
import top.xiaohuashifu.share.pojo.query.ShareQuery;

import java.util.List;

public interface ShareMapper {

    /**
     * 保存分享
     * @param share 分享
     * @return 保存的数量
     */
    int insertShare(ShareDO share);

    /**
     * 获取分享
     * @param id 分享编号
     * @return ShareDO
     */
    ShareDO getShare(Integer id);

    /**
     * 获取query过滤参数后的分享列表，包含pageNum，pageSize等过滤参数，
     *
     * @param query 查询参数
     * @return ShareDOList
     */
    List<ShareDO> listShares(ShareQuery query);

    /**
     * 获取该id的分享数量
     * @param id 编号
     * @return 该id的用户数量
     */
    int count(Integer id);


    /**
     * 通过id和用户id查询分享的数量
     * @param id 分享编号
     * @param userId 用户编号
     * @return 数量
     */
    int countByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);

    /**
     * 更新分享信息
     * @param shareDO 要更新的分享信息
     * @return 成功更新的条数
     */
    int updateShare(ShareDO shareDO);

    /**
     * 使得列值自增1
     * @param id 分享id
     * @param columnName 列名
     * @return 更新列数
     */
    int increase(@Param("id") Integer id, @Param("columnName") String columnName);

    /**
     * 使得列值自减1
     * @param id 分享id
     * @param columnName 列名
     * @return 更新列数
     */
    int decrease(@Param("id") Integer id, @Param("columnName") String columnName);

}
