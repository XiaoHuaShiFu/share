package top.xiaohuashifu.share.manager;

import com.github.pagehelper.PageInfo;
import top.xiaohuashifu.share.pojo.do0.ShareCollectionDO;
import top.xiaohuashifu.share.pojo.query.ShareCollectionQuery;
import top.xiaohuashifu.share.pojo.vo.ShareCollectionVO;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述: 分享收藏Manager
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface ShareCollectionManager {

    Result<ShareCollectionVO> getShareCollection(Integer id, Integer userId);

    Result<PageInfo<ShareCollectionVO>> listShareCollections(ShareCollectionQuery query);
}
