package top.xiaohuashifu.share.manager;

import com.github.pagehelper.PageInfo;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.pojo.do0.ShareCommentCommentDO;
import top.xiaohuashifu.share.pojo.query.ShareCommentCommentQuery;
import top.xiaohuashifu.share.pojo.vo.ShareCommentCommentVO;
import top.xiaohuashifu.share.result.Result;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface ShareCommentCommentManager {

    Result<ShareCommentCommentVO> saveShareCommentComment(ShareCommentCommentDO shareCommentCommentDO);

    Result<ShareCommentCommentVO> getShareCommentComment(Integer id, Integer userId);

    Result<PageInfo<ShareCommentCommentVO>> listShareCommentComments(ShareCommentCommentQuery query, Integer userId);

    Result<ShareCommentCommentVO> updateShareCommentComment(Integer id, String parameterName, Operator operator);

}
