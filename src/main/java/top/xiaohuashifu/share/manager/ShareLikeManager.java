package top.xiaohuashifu.share.manager;

import com.github.pagehelper.PageInfo;
import top.xiaohuashifu.share.pojo.query.ShareLikeQuery;
import top.xiaohuashifu.share.pojo.vo.ShareLikeVO;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface ShareLikeManager {

    Result<PageInfo<ShareLikeVO>> listShareLikes(ShareLikeQuery query);

}
