package top.xiaohuashifu.share.manager.impl;

import com.github.pagehelper.PageInfo;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.manager.ShareCommentManager;
import top.xiaohuashifu.share.pojo.do0.ShareCommentDO;
import top.xiaohuashifu.share.pojo.do0.UserDO;
import top.xiaohuashifu.share.pojo.query.ShareCommentQuery;
import top.xiaohuashifu.share.pojo.vo.ShareCommentVO;
import top.xiaohuashifu.share.pojo.vo.UserVO;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.ShareCommentService;
import top.xiaohuashifu.share.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 分享评论管理层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Component("shareCommentManager")
public class ShareCommentManagerImpl implements ShareCommentManager {

    private final ShareCommentService shareCommentService;

    private final UserService userService;

    private final Mapper mapper;

    @Autowired
    public ShareCommentManagerImpl(ShareCommentService shareCommentService, UserService userService, Mapper mapper) {
        this.shareCommentService = shareCommentService;
        this.userService = userService;
        this.mapper = mapper;
    }

    /**
     * 保存分享评论
     * @param shareCommentDO ShareCommentDO
     * @return Result<ShareCommentVO>
     */
    @Override
    public Result<ShareCommentVO> saveShareComment(ShareCommentDO shareCommentDO) {
        // 保存分享的文字信息
        Result<ShareCommentDO> saveShareCommentResult = shareCommentService.saveShareComment(shareCommentDO);
        if (!saveShareCommentResult.isSuccess()) {
            return Result.fail(saveShareCommentResult);
        }

        return getShareComment(shareCommentDO.getId());
    }

    /**
     * 获取ShareCommentVO通过id
     *
     * @param id 分享评论编号
     * @return ShareCommentVO
     */
    @Override
    public Result<ShareCommentVO> getShareComment(Integer id) {
        // 获取shareComment
        Result<ShareCommentDO> getShareCommentResult = shareCommentService.getShareComment(id);
        if (!getShareCommentResult.isSuccess()) {
            return Result.fail(getShareCommentResult);
        }

        ShareCommentDO shareCommentDO = getShareCommentResult.getData();
        // 获取user
        Result<UserDO> getUserResult = userService.getUser(shareCommentDO.getUserId());
        if (!getUserResult.isSuccess()) {
            return Result.fail(getUserResult);
        }

        // 组装
        ShareCommentVO shareCommentVO = mapper.map(shareCommentDO, ShareCommentVO.class);
        UserDO userDO = getUserResult.getData();
        UserVO userVO = mapper.map(userDO, UserVO.class);
        shareCommentVO.setUser(userVO);
        return Result.success(shareCommentVO);
    }

    /**
     * 获取PageInfo<ShareCommentVO>通过查询参数query
     *
     * @param query 查询参数
     * @return PageInfo<ShareCommentVO>
     */
    @Override
    public Result<PageInfo<ShareCommentVO>> listShareComments(ShareCommentQuery query) {
        // 获取shareCommentList
        Result<PageInfo<ShareCommentDO>> listShareCommentsResult = shareCommentService.listShareComments(query);
        if (!listShareCommentsResult.isSuccess()) {
            return Result.fail(listShareCommentsResult);
        }

        // 组装
        List<ShareCommentDO> shareCommentDOList = listShareCommentsResult.getData().getList();
        List<ShareCommentVO> shareCommentVOList = new ArrayList<>();
        for (ShareCommentDO shareCommentDO : shareCommentDOList) {
            Result<ShareCommentVO> getShareCommentResult = getShareComment(shareCommentDO.getId());
            if (!getShareCommentResult.isSuccess()) {
                return Result.fail(getShareCommentResult);
            }
            shareCommentVOList.add(getShareCommentResult.getData());
        }
        PageInfo<ShareCommentVO> pageInfo = mapper.map(listShareCommentsResult.getData(), PageInfo.class);
        pageInfo.setList(shareCommentVOList);
        return Result.success(pageInfo);
    }

    /**
     * 使得对应属性的值+1
     * @param id 分享评论编号
     * @param parameterName 属性名
     * @param operator 自增或自减
     * @return ShareCommentVO
     */
    @Override
    public Result<ShareCommentVO> updateShareComment(Integer id, String parameterName, Operator operator) {
        Result<ShareCommentDO> result = shareCommentService.updateShareComment(id, parameterName, operator);
        if (!result.isSuccess()) {
            return Result.fail(result);
        }

        return getShareComment(result.getData().getId());
    }
}
