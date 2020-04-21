package top.xiaohuashifu.share.dao;

import org.apache.ibatis.annotations.Param;
import top.xiaohuashifu.share.pojo.do0.ShareCommentCommentDO;
import top.xiaohuashifu.share.pojo.query.ShareCommentCommentQuery;

import java.util.List;

public interface ShareCommentCommentMapper {

    /**
     * 保存分享评论的评论
     * @param shareCommentCommentDO 分享评论的评论
     * @return 保存的数量
     */
    int insertShareCommentComment(ShareCommentCommentDO shareCommentCommentDO);

    /**
     * 获取分享评论的评论
     * @param id 分享评论的评论编号
     * @return ShareCommentCommentDO
     */
    ShareCommentCommentDO getShareCommentComment(Integer id);

    /**
     * 获取query过滤参数后的分享评论的评论列表，包含pageNum，pageSize等过滤参数，
     *
     * @param query 查询参数
     * @return ShareCommentCommentDOList
     */
    List<ShareCommentCommentDO> listShareCommentComments(ShareCommentCommentQuery query);

    /**
     * 计算对应id的shareCommentComment的数量
     * @param id share评论的评论的id1
     * @return 对应id的数量
     */
    int count(Integer id);

    /**
     * 使得列值自增1
     * @param id 分享评论的评论id
     * @param columnName 列名
     * @return 更新列数
     */
    int increase(@Param("id") Integer id, @Param("columnName") String columnName);

    /**
     * 使得列值自减1
     * @param id 分享评论的评论id
     * @param columnName 列名
     * @return 更新列数
     */
    int decrease(@Param("id") Integer id, @Param("columnName") String columnName);

}
