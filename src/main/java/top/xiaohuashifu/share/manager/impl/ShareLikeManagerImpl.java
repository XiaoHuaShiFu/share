package top.xiaohuashifu.share.manager.impl;

import com.github.pagehelper.PageInfo;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xiaohuashifu.share.manager.ShareLikeManager;
import top.xiaohuashifu.share.pojo.do0.ShareLikeDO;
import top.xiaohuashifu.share.pojo.do0.UserDO;
import top.xiaohuashifu.share.pojo.query.ShareLikeQuery;
import top.xiaohuashifu.share.pojo.vo.ShareLikeVO;
import top.xiaohuashifu.share.pojo.vo.UserVO;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.ShareLikeService;
import top.xiaohuashifu.share.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 分享点赞管理层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Component("shareLikeManager")
public class ShareLikeManagerImpl implements ShareLikeManager {
    private final ShareLikeService shareLikeService;

    private final UserService userService;

    private final Mapper mapper;

    @Autowired
    public ShareLikeManagerImpl(ShareLikeService shareLikeService, UserService userService, Mapper mapper) {
        this.shareLikeService = shareLikeService;
        this.userService = userService;
        this.mapper = mapper;
    }

    /**
     * 获取PageInfo<ShareLikeVO>通过查询参数query
     *
     * @param query 查询参数
     * @return PageInfo<ShareLikeVO>
     */
    @Override
    public Result<PageInfo<ShareLikeVO>> listShareLikes(ShareLikeQuery query) {
        // 获取shareLikeList
        Result<PageInfo<ShareLikeDO>> listShareLikesResult = shareLikeService.listShareLikes(query);
        if (!listShareLikesResult.isSuccess()) {
            return Result.fail(listShareLikesResult);
        }

        // 组装
        List<ShareLikeDO> shareLikeDOList = listShareLikesResult.getData().getList();
        List<ShareLikeVO> shareLikeVOList = new ArrayList<>();
        for (ShareLikeDO shareLikeDO : shareLikeDOList) {
            Result<UserDO> getUserResult = userService.getUser(shareLikeDO.getUserId());
            if (!getUserResult.isSuccess()) {
                return Result.fail(getUserResult);
            }
            UserDO userDO = getUserResult.getData();
            UserVO userVO = mapper.map(userDO, UserVO.class);
            ShareLikeVO shareLikeVO = mapper.map(shareLikeDO, ShareLikeVO.class);
            shareLikeVO.setUser(userVO);
            shareLikeVOList.add(shareLikeVO);
        }
        PageInfo<ShareLikeVO> pageInfo = mapper.map(listShareLikesResult.getData(), PageInfo.class);
        pageInfo.setList(shareLikeVOList);
        return Result.success(pageInfo);
    }

}
