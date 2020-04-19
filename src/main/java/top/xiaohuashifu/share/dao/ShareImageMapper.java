package top.xiaohuashifu.share.dao;

import top.xiaohuashifu.share.pojo.do0.ShareImageDO;

import java.util.List;

public interface ShareImageMapper {

    /**
     * 保存分享图片
     * @param shareImage 分享图片
     * @return 保存的数量
     */
    int insertShareImage(ShareImageDO shareImage);

    /**
     * 获取分享图片
     * @param id 分享图片编号
     * @return ShareImageDO
     */
    ShareImageDO getShareImage(Integer id);

    /**
     * 获取shareId对应的分享图片列表
     *
     * @param shareId 分享的编号
     * @return ShareImageDOList
     */
    List<ShareImageDO> listShareImages(Integer shareId);

}
