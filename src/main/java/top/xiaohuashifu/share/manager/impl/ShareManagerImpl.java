package top.xiaohuashifu.share.manager.impl;

import com.github.pagehelper.PageInfo;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.manager.ShareManager;
import top.xiaohuashifu.share.pojo.do0.ShareDO;
import top.xiaohuashifu.share.pojo.do0.ShareImageDO;
import top.xiaohuashifu.share.pojo.do0.UserDO;
import top.xiaohuashifu.share.pojo.query.ShareQuery;
import top.xiaohuashifu.share.pojo.vo.ShareImageVO;
import top.xiaohuashifu.share.pojo.vo.ShareVO;
import top.xiaohuashifu.share.pojo.vo.UserVO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述: 分享管理层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Component("shareManager")
public class ShareManagerImpl implements ShareManager {

    private final ShareService shareService;

    private final ShareImageService shareImageService;

    private final UserService userService;

    private final Mapper mapper;

    @Autowired
    public ShareManagerImpl(ShareService shareService, ShareImageService shareImageService, UserService userService,
                            Mapper mapper) {
        this.shareService = shareService;
        this.shareImageService = shareImageService;
        this.userService = userService;
        this.mapper = mapper;
    }

    /**
     * 保存分享
     * @param shareDO ShareDO
     * @param imageList 分享的图片列表
     * @return Result<ShareVO>
     */
    @Override
    public Result<ShareVO> saveShare(ShareDO shareDO, List<MultipartFile> imageList) {
        // 图片数量不能大于6
        if (imageList.size() < 1 || imageList.size() > 6) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "The number of image must be between 1 to 6.");
        }

        // 保存分享的文字信息
        Result<ShareDO> saveShareResult = shareService.saveShare(shareDO);
        if (!saveShareResult.isSuccess()) {
            return Result.fail(saveShareResult);
        }

        // 保存分享的图片信息
        for (int i = 0; i < imageList.size(); i++) {
            ShareImageDO shareImageDO = new ShareImageDO();
            shareImageDO.setShareId(shareDO.getId());
            shareImageDO.setImageIndex(i);
            Result<ShareImageDO> saveShareImageResult = shareImageService.saveShareImage(shareImageDO, imageList.get(i));
            if (!saveShareImageResult.isSuccess()) {
                return Result.fail(saveShareImageResult);
            }
        }

        return getShare(shareDO.getId());
    }

    /**
     * 获取ShareVO通过id
     *
     * @param id 分享编号
     * @return ShareVO
     */
    @Override
    public Result<ShareVO> getShare(Integer id) {
        // 获取share
        Result<ShareDO> getShareResult = shareService.getShare(id);
        if (!getShareResult.isSuccess()) {
            return Result.fail(getShareResult);
        }

        // 获取shareImage
        Result<List<ShareImageDO>> listShareImagesResult = shareImageService.listShareImages(id);
        if (!listShareImagesResult.isSuccess()) {
            return Result.fail(listShareImagesResult);
        }

        ShareDO shareDO = getShareResult.getData();
        // 获取user
        Result<UserDO> getUserResult = userService.getUser(shareDO.getUserId());
        if (!getUserResult.isSuccess()) {
            return Result.fail(getUserResult);
        }

        // 组装
        ShareVO shareVO = mapper.map(shareDO, ShareVO.class);
        List<ShareImageDO> shareImageDOList = listShareImagesResult.getData();
        List<ShareImageVO> shareImageVOList = shareImageDOList.stream()
                .map(shareImageDO -> mapper.map(shareImageDO, ShareImageVO.class))
                .collect(Collectors.toList());
        shareVO.setShareImageList(shareImageVOList);
        UserDO userDO = getUserResult.getData();
        UserVO userVO = mapper.map(userDO, UserVO.class);
        shareVO.setUser(userVO);
        return Result.success(shareVO);
    }

    /**
     * 获取PageInfo<ShareVO>通过查询参数query
     *
     * @param query 查询参数
     * @return PageInfo<ShareVO>
     */
    @Override
    public Result<PageInfo<ShareVO>> listShares(ShareQuery query) {
        // 获取shareList
        Result<PageInfo<ShareDO>> listSharesResult = shareService.listShares(query);
        if (!listSharesResult.isSuccess()) {
            return Result.fail(listSharesResult);
        }

        // 组装
        List<ShareDO> shareDOList = listSharesResult.getData().getList();
        List<ShareVO> shareVOList = new ArrayList<>();
        for (ShareDO shareDO : shareDOList) {
            Result<ShareVO> getShareResult = getShare(shareDO.getId());
            if (!getShareResult.isSuccess()) {
                return Result.fail(getShareResult);
            }
            shareVOList.add(getShareResult.getData());
        }
        PageInfo<ShareVO> pageInfo = mapper.map(listSharesResult.getData(), PageInfo.class);
        pageInfo.setList(shareVOList);
        return Result.success(pageInfo);
    }

    /**
     * 更新分享信息
     *
     * @param shareDO 要更新的信息
     * @return 更新后的分享信息
     */
    @Override
    public Result<ShareVO> updateShare(ShareDO shareDO) {
        Result<ShareDO> result = shareService.updateShare(shareDO);
        if (!result.isSuccess()) {
            return Result.fail(result);
        }

        return getShare(result.getData().getId());
    }

    /**
     * 使得对应属性的值+1
     * @param id 分享编号
     * @param parameterName 属性名
     * @param operator 自增或自减
     * @return ShareVO
     */
    @Override
    public Result<ShareVO> updateShare(Integer id, String parameterName, Operator operator) {
        Result<ShareDO> result = shareService.updateShare(id, parameterName, operator);
        if (!result.isSuccess()) {
            return Result.fail(result);
        }

        return getShare(result.getData().getId());
    }
}
