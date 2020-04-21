package top.xiaohuashifu.share.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.dao.ShareCommentMapper;
import top.xiaohuashifu.share.pojo.do0.ShareCommentDO;
import top.xiaohuashifu.share.pojo.do0.ShareDO;
import top.xiaohuashifu.share.pojo.query.ShareCommentQuery;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.SensitiveWordService;
import top.xiaohuashifu.share.service.ShareCommentService;
import top.xiaohuashifu.share.service.ShareService;
import top.xiaohuashifu.share.service.UserService;
import top.xiaohuashifu.share.service.constant.ShareConstant;
import top.xiaohuashifu.share.service.constant.UserConstant;

import java.util.Date;

/**
 * 描述:分享评论服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Service("shareCommentService")
public class ShareCommentServiceImpl implements ShareCommentService {

    private static final Logger logger = LoggerFactory.getLogger(ShareCommentServiceImpl.class);

    private final ShareCommentMapper shareCommentMapper;

    private final ShareService shareService;

    private final SensitiveWordService sensitiveWordService;

    private final UserService userService;

    @Autowired
    public ShareCommentServiceImpl(ShareCommentMapper shareCommentMapper, ShareService shareService,
                                   SensitiveWordService sensitiveWordService, UserService userService) {
        this.shareCommentMapper = shareCommentMapper;
        this.shareService = shareService;
        this.sensitiveWordService = sensitiveWordService;
        this.userService = userService;
    }

    /**
     * 保存ShareComment
     * @param shareCommentDO ShareCommentDO
     * @return Result<ShareCommentDO>
     */
    @Override
    public Result<ShareCommentDO> saveShareComment(ShareCommentDO shareCommentDO) {
        // 判断分享存不存在
        Result<ShareDO> getShareResult = shareService.getShare(shareCommentDO.getShareId());
        if (!getShareResult.isSuccess()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The share of id={0} not exists.", shareCommentDO.getShareId());
        }

        // 是否存在敏感词
        Result<String> filterResult = sensitiveWordService.filter(shareCommentDO.getContent());
        if (!filterResult.isSuccess()) {
            return Result.fail(filterResult);
        }

        // 保存分享评论
        shareCommentDO.setCommentTime(new Date());
        int count = shareCommentMapper.insertShareComment(shareCommentDO);
        // 没有插入成功
        if (count < 1) {
            logger.error("Insert shareComment fail.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert shareComment fail.");
        }

        // 用户的评论数+1
        userService.updateUser(shareCommentDO.getUserId(), UserConstant.COLUMN_NAME_OF_COMMENTS, Operator.INCREMENT);
        // 分享的评论数+1
        shareService.updateShare(shareCommentDO.getShareId(), ShareConstant.COLUMN_NAME_OF_COMMENTS, Operator.INCREMENT);

        return getShareComment(shareCommentDO.getId());
    }

    /**
     * 获取ShareCommentDO通过id
     *
     * @param id 分享评论编号
     * @return ShareCommentDO
     */
    @Override
    public Result<ShareCommentDO> getShareComment(Integer id) {
        ShareCommentDO shareCommentDO = shareCommentMapper.getShareComment(id);
        if (shareCommentDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified id does not exist.");
        }
        return Result.success(shareCommentDO);
    }

    /**
     * 获取ShareCommentDOList通过查询参数query
     *
     * @param query 查询参数
     * @return ShareCommentDOList
     */
    @Override
    public Result<PageInfo<ShareCommentDO>> listShareComments(ShareCommentQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<ShareCommentDO> pageInfo = new PageInfo<>(shareCommentMapper.listShareComments(query));
        if (pageInfo.getList().size() < 1) {
            Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(pageInfo);
    }

    /**
     * 使得对应属性的值+1
     * @param id 分享评论编号
     * @param parameterName 属性名
     * @param operator 自增或自减
     * @return ShareCommentDO
     */
    @Override
    public Result<ShareCommentDO> updateShareComment(Integer id, String parameterName, Operator operator) {
        if (operator == Operator.INCREMENT) {
            shareCommentMapper.increase(id, parameterName);
        } else if (operator == Operator.DECREMENT) {
            shareCommentMapper.decrease(id, parameterName);
        }
        return getShareComment(id);
    }


}
