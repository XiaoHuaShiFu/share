package top.xiaohuashifu.share.manager.impl;

import com.github.pagehelper.PageInfo;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.manager.ShareCommentCommentManager;
import top.xiaohuashifu.share.pojo.do0.ShareCommentCommentDO;
import top.xiaohuashifu.share.pojo.do0.UserDO;
import top.xiaohuashifu.share.pojo.query.ShareCommentCommentQuery;
import top.xiaohuashifu.share.pojo.vo.ShareCommentCommentVO;
import top.xiaohuashifu.share.pojo.vo.UserVO;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.ShareCommentCommentService;
import top.xiaohuashifu.share.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 分享评论的评论管理层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Component("shareCommentCommentManager")
public class ShareCommentCommentManagerImpl implements ShareCommentCommentManager {

    private final ShareCommentCommentService shareCommentCommentService;

    private final UserService userService;

    private final Mapper mapper;

    @Autowired
    public ShareCommentCommentManagerImpl(ShareCommentCommentService shareCommentCommentService,
                                          UserService userService, Mapper mapper) {
        this.shareCommentCommentService = shareCommentCommentService;
        this.userService = userService;
        this.mapper = mapper;
    }

    /**
     * 保存分享评论的评论
     * @param shareCommentCommentDO ShareCommentCommentDO
     * @return Result<ShareCommentCommentVO>
     */
    @Override
    public Result<ShareCommentCommentVO> saveShareCommentComment(ShareCommentCommentDO shareCommentCommentDO) {
        // 保存分享的文字信息
        Result<ShareCommentCommentDO> saveShareCommentCommentResult =
                shareCommentCommentService.saveShareCommentComment(shareCommentCommentDO);
        if (!saveShareCommentCommentResult.isSuccess()) {
            return Result.fail(saveShareCommentCommentResult);
        }

        return getShareCommentComment(shareCommentCommentDO.getId());
    }

    /**
     * 获取ShareCommentCommentVO通过id
     *
     * @param id 分享评论的评论编号
     * @return ShareCommentCommentVO
     */
    @Override
    public Result<ShareCommentCommentVO> getShareCommentComment(Integer id) {
        // 获取shareCommentComment
        Result<ShareCommentCommentDO> getShareCommentCommentResult =
                shareCommentCommentService.getShareCommentComment(id);
        if (!getShareCommentCommentResult.isSuccess()) {
            return Result.fail(getShareCommentCommentResult);
        }
        ShareCommentCommentDO shareCommentCommentDO = getShareCommentCommentResult.getData();

        // 如果有父评论，获取父评论的用户
        UserVO parentShareCommentCommentUser = null;
        if (shareCommentCommentDO.getParentShareCommentCommentId() != 0) {
            Result<ShareCommentCommentDO> getParentShareCommentCommentResult =
                    shareCommentCommentService.getShareCommentComment(
                            shareCommentCommentDO.getParentShareCommentCommentId());
            if (!getParentShareCommentCommentResult.isSuccess()) {
                return Result.fail(getParentShareCommentCommentResult);
            }

            ShareCommentCommentDO parentShareCommentCommentDO = getParentShareCommentCommentResult.getData();
            Result<UserDO> getUserResult = userService.getUser(parentShareCommentCommentDO.getUserId());
            if (!getUserResult.isSuccess()) {
                return Result.fail(getUserResult);
            }

            UserDO userDO = getUserResult.getData();
            UserVO userVO = mapper.map(userDO, UserVO.class);
            parentShareCommentCommentUser = userVO;
        }

        // 获取user
        Result<UserDO> getUserResult = userService.getUser(shareCommentCommentDO.getUserId());
        if (!getUserResult.isSuccess()) {
            return Result.fail(getUserResult);
        }

        // 组装
        ShareCommentCommentVO shareCommentCommentVO = mapper.map(shareCommentCommentDO, ShareCommentCommentVO.class);
        UserDO userDO = getUserResult.getData();
        UserVO userVO = mapper.map(userDO, UserVO.class);
        shareCommentCommentVO.setUser(userVO);
        shareCommentCommentVO.setParentShareCommentCommentUser(parentShareCommentCommentUser);
        return Result.success(shareCommentCommentVO);
    }

    /**
     * 获取PageInfo<ShareCommentCommentVO>通过查询参数query
     *
     * @param query 查询参数
     * @return PageInfo<ShareCommentCommentVO>
     */
    @Override
    public Result<PageInfo<ShareCommentCommentVO>> listShareCommentComments(ShareCommentCommentQuery query) {
        // 获取shareCommentList
        Result<PageInfo<ShareCommentCommentDO>> listShareCommentCommentsResult =
                shareCommentCommentService.listShareCommentComments(query);
        if (!listShareCommentCommentsResult.isSuccess()) {
            return Result.fail(listShareCommentCommentsResult);
        }

        // 组装
        List<ShareCommentCommentDO> shareCommentCommentDOList = listShareCommentCommentsResult.getData().getList();
        List<ShareCommentCommentVO> shareCommentCommentVOList = new ArrayList<>();
        for (ShareCommentCommentDO shareCommentCommentDO : shareCommentCommentDOList) {
            Result<ShareCommentCommentVO> getShareCommentCommentResult =
                    getShareCommentComment(shareCommentCommentDO.getId());
            if (!getShareCommentCommentResult.isSuccess()) {
                return Result.fail(getShareCommentCommentResult);
            }
            shareCommentCommentVOList.add(getShareCommentCommentResult.getData());
        }
        PageInfo<ShareCommentCommentVO> pageInfo = mapper.map(listShareCommentCommentsResult.getData(), PageInfo.class);
        pageInfo.setList(shareCommentCommentVOList);
        return Result.success(pageInfo);
    }

    /**
     * 使得对应属性的值+1
     * @param id 分享评论的评论编号
     * @param parameterName 属性名
     * @param operator 自增或自减
     * @return ShareCommentCommentVO
     */
    @Override
    public Result<ShareCommentCommentVO> updateShareCommentComment(Integer id, String parameterName, Operator operator) {
        Result<ShareCommentCommentDO> result =
                shareCommentCommentService.updateShareCommentComment(id, parameterName, operator);
        if (!result.isSuccess()) {
            return Result.fail(result);
        }

        return getShareCommentComment(result.getData().getId());
    }
}
