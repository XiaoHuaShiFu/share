package top.xiaohuashifu.share.service;

import com.github.pagehelper.PageInfo;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.pojo.do0.ShareDO;
import top.xiaohuashifu.share.pojo.query.ShareQuery;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述: 分享Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface ShareService {

    Result<ShareDO> saveShare(ShareDO shareDO);

    Result<ShareDO> getShare(Integer id);

    Result<PageInfo<ShareDO>> listShares(ShareQuery query);

    Result<ShareDO> updateShare(ShareDO shareDO);

    Result<ShareDO> updateShare(Integer id, String parameterName, Operator operator);
}
