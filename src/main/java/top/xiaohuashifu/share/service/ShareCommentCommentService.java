package top.xiaohuashifu.share.service;

import com.github.pagehelper.PageInfo;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.pojo.do0.ShareCommentCommentDO;
import top.xiaohuashifu.share.pojo.query.ShareCommentCommentQuery;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述: 分享评论的评论Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface ShareCommentCommentService {

    Result<ShareCommentCommentDO> saveShareCommentComment(ShareCommentCommentDO shareCommentCommentDO);

    Result<ShareCommentCommentDO> getShareCommentComment(Integer id);

    Result<PageInfo<ShareCommentCommentDO>> listShareCommentComments(ShareCommentCommentQuery query);

    Result<ShareCommentCommentDO> updateShareCommentComment(Integer id, String parameterName, Operator operator);
}
