package top.xiaohuashifu.share.service;

import top.xiaohuashifu.share.pojo.do0.ShareCommentCommentLikeDO;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述: 分享的评论的评论点赞Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface ShareCommentCommentLikeService {

    Result<ShareCommentCommentLikeDO> saveShareCommentCommentLike(ShareCommentCommentLikeDO shareCommentCommentLikeDO);

    Result deleteShareCommentCommentLike(Integer userId, Integer shareCommentCommentId);

    Result<ShareCommentCommentLikeDO> getShareCommentCommentLike(Integer id);

    Result<Integer> countByUserIdAndShareCommentCommentId(Integer userId, Integer shareCommentCommentId);

}
