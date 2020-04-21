package top.xiaohuashifu.share.manager;

import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.pojo.do0.ShareDO;
import top.xiaohuashifu.share.pojo.query.ShareQuery;
import top.xiaohuashifu.share.pojo.vo.ShareVO;
import top.xiaohuashifu.share.result.Result;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface ShareManager {

    Result<ShareVO> saveShare(ShareDO shareDO, List<MultipartFile> imageList);

    Result<ShareVO> getShare(Integer id, Integer userId);

    Result<PageInfo<ShareVO>> listShares(ShareQuery query, Integer userId);

    Result<ShareVO> updateShare(ShareDO shareDO);

    Result<ShareVO> updateShare(Integer id, String parameterName, Operator operator);

}
