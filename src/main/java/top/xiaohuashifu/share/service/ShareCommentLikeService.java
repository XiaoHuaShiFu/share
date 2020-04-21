package top.xiaohuashifu.share.service;

import top.xiaohuashifu.share.pojo.do0.ShareCommentLikeDO;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述: 分享的评论点赞Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface ShareCommentLikeService {

    Result<ShareCommentLikeDO> saveShareCommentLike(ShareCommentLikeDO shareCommentLikeDO);

    Result deleteShareCommentLike(Integer userId, Integer shareCommentId);

    Result<ShareCommentLikeDO> getShareCommentLike(Integer id);

    Result<Integer> countByUserIdAndShareCommentId(Integer userId, Integer shareCommentId);

}
