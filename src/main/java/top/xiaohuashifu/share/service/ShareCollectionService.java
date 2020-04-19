package top.xiaohuashifu.share.service;

import com.github.pagehelper.PageInfo;
import top.xiaohuashifu.share.pojo.do0.ShareCollectionDO;
import top.xiaohuashifu.share.pojo.query.ShareCollectionQuery;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述: 分享收藏Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface ShareCollectionService {

    Result<ShareCollectionDO> saveShareCollection(ShareCollectionDO shareCollectionDO);

    Result deleteShareCollection(Integer userId, Integer shareId);

    Result<ShareCollectionDO> getShareCollection(Integer id);

    Result<PageInfo<ShareCollectionDO>> listShareCollections(ShareCollectionQuery query);
}
