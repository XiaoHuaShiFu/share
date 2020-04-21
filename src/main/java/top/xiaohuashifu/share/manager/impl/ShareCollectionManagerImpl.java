package top.xiaohuashifu.share.manager.impl;

import com.github.pagehelper.PageInfo;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xiaohuashifu.share.manager.ShareCollectionManager;
import top.xiaohuashifu.share.manager.ShareManager;
import top.xiaohuashifu.share.pojo.do0.ShareCollectionDO;
import top.xiaohuashifu.share.pojo.query.ShareCollectionQuery;
import top.xiaohuashifu.share.pojo.vo.ShareCollectionVO;
import top.xiaohuashifu.share.pojo.vo.ShareVO;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.ShareCollectionService;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 分享收藏管理层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Component("shareCollectionManager")
public class ShareCollectionManagerImpl implements ShareCollectionManager {

    private final ShareManager shareManager;

    private final ShareCollectionService shareCollectionService;

    private final Mapper mapper;

    @Autowired
    public ShareCollectionManagerImpl(ShareManager shareManager, ShareCollectionService shareCollectionService,
                                      Mapper mapper) {
        this.shareManager = shareManager;
        this.shareCollectionService = shareCollectionService;
        this.mapper = mapper;
    }

    /**
     * 获取ShareCollectionVO通过id
     *
     * @param id 分享收藏编号
     * @return ShareCollectionVO
     */
    @Override
    public Result<ShareCollectionVO> getShareCollection(Integer id) {
        // 获取shareCollection
        Result<ShareCollectionDO> getShareCollectionResult = shareCollectionService.getShareCollection(id);
        if (!getShareCollectionResult.isSuccess()) {
            return Result.fail(getShareCollectionResult);
        }

        ShareCollectionDO shareCollectionDO = getShareCollectionResult.getData();
        // 获取share
        Result<ShareVO> getShareResult = shareManager.getShare(shareCollectionDO.getShareId(), 0);
        if (!getShareResult.isSuccess()) {
            return Result.fail(getShareResult);
        }

        // 组装
        ShareCollectionVO shareCollectionVO = mapper.map(shareCollectionDO, ShareCollectionVO.class);
        ShareVO shareVO = getShareResult.getData();
        shareCollectionVO.setShare(shareVO);
        return Result.success(shareCollectionVO);
    }

    /**
     * 获取PageInfo<ShareCollectionVO>通过查询参数query
     *
     * @param query 查询参数
     * @return PageInfo<ShareCollectionVO>
     */
    @Override
    public Result<PageInfo<ShareCollectionVO>> listShareCollections(ShareCollectionQuery query) {
        // 获取shareCollectionList
        Result<PageInfo<ShareCollectionDO>> listShareCollectionsResult =
                shareCollectionService.listShareCollections(query);
        if (!listShareCollectionsResult.isSuccess()) {
            return Result.fail(listShareCollectionsResult);
        }

        // 组装
        List<ShareCollectionDO> shareCollectionDOList = listShareCollectionsResult.getData().getList();
        List<ShareCollectionVO> shareCollectionVOList = new ArrayList<>();
        for (ShareCollectionDO shareCollectionDO : shareCollectionDOList) {
            Result<ShareCollectionVO> getShareCollectionResult = getShareCollection(shareCollectionDO.getId());
            if (!getShareCollectionResult.isSuccess()) {
                return Result.fail(getShareCollectionResult);
            }
            shareCollectionVOList.add(getShareCollectionResult.getData());
        }
        PageInfo<ShareCollectionVO> pageInfo = mapper.map(listShareCollectionsResult.getData(), PageInfo.class);
        pageInfo.setList(shareCollectionVOList);
        return Result.success(pageInfo);
    }

}
