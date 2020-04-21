package top.xiaohuashifu.share.manager;

import com.github.pagehelper.PageInfo;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.pojo.do0.ShareCommentDO;
import top.xiaohuashifu.share.pojo.query.ShareCommentQuery;
import top.xiaohuashifu.share.pojo.vo.ShareCommentVO;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface ShareCommentManager {

    Result<ShareCommentVO> saveShareComment(ShareCommentDO shareCommentDO);

    Result<ShareCommentVO> getShareComment(Integer id);

    Result<PageInfo<ShareCommentVO>> listShareComments(ShareCommentQuery query);

    Result<ShareCommentVO> updateShareComment(Integer id, String parameterName, Operator operator);

}
