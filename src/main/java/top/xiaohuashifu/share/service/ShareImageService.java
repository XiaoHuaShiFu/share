package top.xiaohuashifu.share.service;

import org.springframework.web.multipart.MultipartFile;
import top.xiaohuashifu.share.pojo.do0.ShareImageDO;
import top.xiaohuashifu.share.result.Result;

import java.util.List;

/**
 * 描述: 分享图片Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface ShareImageService {

    Result<ShareImageDO> saveShareImage(ShareImageDO shareImageDO, MultipartFile image);

    Result<ShareImageDO> getShareImage(Integer id);

    Result<List<ShareImageDO>> listShareImages(Integer shareId);

}
