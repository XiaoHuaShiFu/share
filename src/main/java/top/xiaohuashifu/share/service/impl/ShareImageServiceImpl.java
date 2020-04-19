package top.xiaohuashifu.share.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.xiaohuashifu.share.dao.ShareImageMapper;
import top.xiaohuashifu.share.pojo.do0.ShareImageDO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.FileService;
import top.xiaohuashifu.share.service.ShareImageService;
import top.xiaohuashifu.share.service.constant.ShareImageConstant;

import java.util.List;

/**
 * 描述:分享图片服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Service("shareImageService")
public class ShareImageServiceImpl implements ShareImageService {

    private static final Logger logger = LoggerFactory.getLogger(ShareImageServiceImpl.class);

    private final ShareImageMapper shareImageMapper;

    private final FileService fileService;

    @Autowired
    public ShareImageServiceImpl(ShareImageMapper shareImageMapper, FileService fileService) {
        this.shareImageMapper = shareImageMapper;
        this.fileService = fileService;
    }

    /**
     * 保存分享图片
     * @param shareImageDO ShareImageDO
     * @param image 分享的图片
     * @return Result<ShareImageDO>
     */
    @Override
    public Result<ShareImageDO> saveShareImage(ShareImageDO shareImageDO, MultipartFile image) {
        // 保存头像并获取Url
        String shareImageUrl = fileService.saveAndGetUrl(image, ShareImageConstant.PREFIX_SHARE_IMAGE_FILE_DIRECTORY);
        shareImageDO.setImageUrl(shareImageUrl);
        int count = shareImageMapper.insertShareImage(shareImageDO);
        // 没有插入成功
        if (count < 1) {
            logger.error("Insert shareImage fail.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert shareImage fail.");
        }

        return getShareImage(shareImageDO.getId());
    }

    /**
     * 获取ShareImageDO通过id
     *
     * @param id 分享图片编号
     * @return ShareImageDO
     */
    @Override
    public Result<ShareImageDO> getShareImage(Integer id) {
        ShareImageDO shareImageDO = shareImageMapper.getShareImage(id);
        if (shareImageDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified id does not exist.");
        }
        return Result.success(shareImageDO);
    }

    /**
     * 获取List<ShareImageDO>通过shareId
     *
     * @param shareId 分享编号
     * @return List<ShareImageDO>
     */
    @Override
    public Result<List<ShareImageDO>> listShareImages(Integer shareId) {
        List<ShareImageDO> shareImageDOList = shareImageMapper.listShareImages(shareId);
        if (shareImageDOList.isEmpty()) {
            Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(shareImageDOList);
    }

}
