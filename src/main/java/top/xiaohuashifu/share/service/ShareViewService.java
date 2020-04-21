package top.xiaohuashifu.share.service;

import top.xiaohuashifu.share.pojo.do0.ShareViewDO;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述: 分享观看Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface ShareViewService {

    Result<ShareViewDO> saveShareView(ShareViewDO shareViewDO);

    Result deleteShareView(Integer userId, Integer shareId);

    Result<ShareViewDO> getShareView(Integer id);

    Result<Integer> countByUserIdAndShareId(Integer userId, Integer shareId);

}
