package top.xiaohuashifu.share.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.dao.ShareCommentCommentMapper;
import top.xiaohuashifu.share.pojo.do0.ShareCommentCommentDO;
import top.xiaohuashifu.share.pojo.do0.ShareCommentDO;
import top.xiaohuashifu.share.pojo.query.ShareCommentCommentQuery;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.SensitiveWordService;
import top.xiaohuashifu.share.service.ShareCommentCommentService;
import top.xiaohuashifu.share.service.ShareCommentService;
import top.xiaohuashifu.share.service.UserService;
import top.xiaohuashifu.share.service.constant.ShareCommentConstant;
import top.xiaohuashifu.share.service.constant.UserConstant;

import java.util.Date;

/**
 * 描述:分享评论服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Service("shareCommentCommentService")
public class ShareCommentCommentServiceImpl implements ShareCommentCommentService {

    private static final Logger logger = LoggerFactory.getLogger(ShareCommentCommentServiceImpl.class);

    private final ShareCommentCommentMapper shareCommentCommentMapper;

    private final ShareCommentService shareCommentService;

    private final SensitiveWordService sensitiveWordService;

    private final UserService userService;

    @Autowired
    public ShareCommentCommentServiceImpl(ShareCommentCommentMapper shareCommentCommentMapper,
                                          ShareCommentService shareCommentService,
                                          SensitiveWordService sensitiveWordService, UserService userService) {
        this.shareCommentCommentMapper = shareCommentCommentMapper;
        this.shareCommentService = shareCommentService;
        this.sensitiveWordService = sensitiveWordService;
        this.userService = userService;
    }

    /**
     * 保存ShareCommentComment
     * @param shareCommentCommentDO ShareCommentCommentDO
     * @return Result<ShareCommentCommentDO>
     */
    @Override
    public Result<ShareCommentCommentDO> saveShareCommentComment(ShareCommentCommentDO shareCommentCommentDO) {
        // 判断分享评论存不存在
        Result<ShareCommentDO> getShareCommentResult =
                shareCommentService.getShareComment(shareCommentCommentDO.getShareCommentId());
        if (!getShareCommentResult.isSuccess()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                    "The shareComment of id={0} not exists.", shareCommentCommentDO.getShareCommentId());
        }

        // 判断父分享评论的评论在不在
        if (shareCommentCommentDO.getParentShareCommentCommentId() != 0) {
            int count = shareCommentCommentMapper.count(shareCommentCommentDO.getParentShareCommentCommentId());
            if (count < 1) {
                return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND,
                        "The parentShareCommentComment of id={0} not exists.",
                        shareCommentCommentDO.getParentShareCommentCommentId());
            }
        }

        // 是否存在敏感词
        Result<String> filterResult = sensitiveWordService.filter(shareCommentCommentDO.getContent());
        if (!filterResult.isSuccess()) {
            return Result.fail(filterResult);
        }

        // 保存分享评论
        shareCommentCommentDO.setCommentTime(new Date());
        int count = shareCommentCommentMapper.insertShareCommentComment(shareCommentCommentDO);
        // 没有插入成功
        if (count < 1) {
            logger.error("Insert shareCommentComment fail.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert shareCommentComment fail.");
        }

        // 用户的评论数+1
        userService.updateUser(shareCommentCommentDO.getUserId(),
                UserConstant.COLUMN_NAME_OF_COMMENTS, Operator.INCREMENT);
        // 分享评论的评论数+1
        shareCommentService.updateShareComment(shareCommentCommentDO.getShareCommentId(),
                ShareCommentConstant.COLUMN_NAME_OF_COMMENTS, Operator.INCREMENT);

        return getShareCommentComment(shareCommentCommentDO.getId());
    }

    /**
     * 获取ShareCommentCommentDO通过id
     *
     * @param id 分享评论的评论编号
     * @return ShareCommentCommentDO
     */
    @Override
    public Result<ShareCommentCommentDO> getShareCommentComment(Integer id) {
        ShareCommentCommentDO shareCommentCommentDO = shareCommentCommentMapper.getShareCommentComment(id);
        if (shareCommentCommentDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified id does not exist.");
        }
        return Result.success(shareCommentCommentDO);
    }

    /**
     * 获取ShareCommentCommentDOList通过查询参数query
     *
     * @param query 查询参数
     * @return ShareCommentCommentDOList
     */
    @Override
    public Result<PageInfo<ShareCommentCommentDO>> listShareCommentComments(ShareCommentCommentQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<ShareCommentCommentDO> pageInfo =
                new PageInfo<>(shareCommentCommentMapper.listShareCommentComments(query));
        if (pageInfo.getList().size() < 1) {
            Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(pageInfo);
    }

    /**
     * 使得对应属性的值+1
     * @param id 分享评论的评论编号
     * @param parameterName 属性名
     * @param operator 自增或自减
     * @return ShareCommentDO
     */
    @Override
    public Result<ShareCommentCommentDO> updateShareCommentComment(Integer id, String parameterName, Operator operator) {
        if (operator == Operator.INCREMENT) {
            shareCommentCommentMapper.increase(id, parameterName);
        } else if (operator == Operator.DECREMENT) {
            shareCommentCommentMapper.decrease(id, parameterName);
        }
        return getShareCommentComment(id);
    }


}
