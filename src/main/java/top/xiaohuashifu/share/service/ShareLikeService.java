package top.xiaohuashifu.share.service;

import top.xiaohuashifu.share.pojo.do0.ShareLikeDO;
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

    Result<Integer> countByUserIdAndShareId(Integer userId, Integer shareId);

}
