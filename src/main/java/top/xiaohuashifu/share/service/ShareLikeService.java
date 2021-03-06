package top.xiaohuashifu.share.service;

import com.github.pagehelper.PageInfo;
import top.xiaohuashifu.share.pojo.do0.ShareLikeDO;
import top.xiaohuashifu.share.pojo.query.ShareLikeQuery;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述: 分享点赞Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface ShareLikeService {

    Result<ShareLikeDO> saveShareLike(ShareLikeDO shareLikeDO);

    Result deleteShareLike(Integer userId, Integer shareId);

    Result<ShareLikeDO> getShareLike(Integer id);

    Result<PageInfo<ShareLikeDO>> listShareLikes(ShareLikeQuery query);

    Result<Integer> countByUserIdAndShareId(Integer userId, Integer shareId);

}
