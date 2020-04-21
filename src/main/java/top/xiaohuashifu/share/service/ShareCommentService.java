package top.xiaohuashifu.share.service;

import com.github.pagehelper.PageInfo;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.pojo.do0.ShareCommentDO;
import top.xiaohuashifu.share.pojo.query.ShareCommentQuery;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述: 分享评论Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface ShareCommentService {

    Result<ShareCommentDO> saveShareComment(ShareCommentDO shareCommentDO);

    Result<ShareCommentDO> getShareComment(Integer id);

    Result<PageInfo<ShareCommentDO>> listShareComments(ShareCommentQuery query);

    Result<ShareCommentDO> updateShareComment(Integer id, String parameterName, Operator operator);
}
