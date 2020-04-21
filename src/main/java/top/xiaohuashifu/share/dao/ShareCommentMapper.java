package top.xiaohuashifu.share.dao;

import org.apache.ibatis.annotations.Param;
import top.xiaohuashifu.share.pojo.do0.ShareCommentDO;
import top.xiaohuashifu.share.pojo.query.ShareCommentQuery;

import java.util.List;

public interface ShareCommentMapper {

    /**
     * 保存分享评论
     * @param shareCommentDO 分享评论
     * @return 保存的数量
     */
    int insertShareComment(ShareCommentDO shareCommentDO);

    /**
     * 获取分享评论
     * @param id 分享评论编号
     * @return ShareCommentDO
     */
    ShareCommentDO getShareComment(Integer id);

    /**
     * 获取query过滤参数后的分享评论列表，包含pageNum，pageSize等过滤参数，
     *
     * @param query 查询参数
     * @return ShareCommentDOList
     */
    List<ShareCommentDO> listShareComments(ShareCommentQuery query);

    /**
     * 使得列值自增1
     * @param id 分享评论id
     * @param columnName 列名
     * @return 更新列数
     */
    int increase(@Param("id") Integer id, @Param("columnName") String columnName);

    /**
     * 使得列值自减1
     * @param id 分享评论id
     * @param columnName 列名
     * @return 更新列数
     */
    int decrease(@Param("id") Integer id, @Param("columnName") String columnName);
}
