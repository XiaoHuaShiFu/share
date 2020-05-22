package top.xiaohuashifu.share.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xiaohuashifu.share.constant.Operator;
import top.xiaohuashifu.share.dao.ShareMapper;
import top.xiaohuashifu.share.pojo.do0.ShareDO;
import top.xiaohuashifu.share.pojo.query.ShareQuery;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.SensitiveWordService;
import top.xiaohuashifu.share.service.ShareService;
import top.xiaohuashifu.share.service.UserService;
import top.xiaohuashifu.share.service.constant.UserConstant;
import top.xiaohuashifu.share.util.BeanUtils;

import java.util.Date;

/**
 * 描述:分享服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Service("shareService")
public class ShareServiceImpl implements ShareService {

    private static final Logger logger = LoggerFactory.getLogger(ShareServiceImpl.class);

    private final ShareMapper shareMapper;

    private final SensitiveWordService sensitiveWordService;

    private final UserService userService;

    @Autowired
    public ShareServiceImpl(ShareMapper shareMapper, SensitiveWordService sensitiveWordService,
                            UserService userService) {
        this.shareMapper = shareMapper;
        this.sensitiveWordService = sensitiveWordService;
        this.userService = userService;
    }

    /**
     * 保存分享
     * @param shareDO ShareDO
     * @return Result<ShareDO>
     */
    @Override
    public Result<ShareDO> saveShare(ShareDO shareDO) {
        // 判断内容是否存在敏感词
        Result<String> filterResult = sensitiveWordService.filter(shareDO.getContent());
        if (!filterResult.isSuccess()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, filterResult.getMessage());
        }

        // TODO: 2020/4/19 这里还未做插入图片
        shareDO.setShareTime(new Date());
        int count = shareMapper.insertShare(shareDO);
        // 没有插入成功
        if (count < 1) {
            logger.error("Insert share fail.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert share fail.");
        }

        // 用户的分享数+1
        userService.updateUser(shareDO.getUserId(), UserConstant.COLUMN_NAME_OF_SHARES, Operator.INCREMENT);

        return getShare(shareDO.getId());
    }

    /**
     * 获取ShareDO通过id
     *
     * @param id 分享编号
     * @return ShareDO
     */
    @Override
    public Result<ShareDO> getShare(Integer id) {
        ShareDO shareDO = shareMapper.getShare(id);
        if (shareDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified id does not exist.");
        }
        return Result.success(shareDO);
    }

    /**
     * 获取PageInfo<ShareDO>通过查询参数query
     *
     * @param query 查询参数
     * @return PageInfo<ShareDO>
     */
    @Override
    public Result<PageInfo<ShareDO>> listShares(ShareQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<ShareDO> pageInfo = new PageInfo<>(shareMapper.listShares(query));
        if (pageInfo.getList().isEmpty()) {
            Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(pageInfo);
    }

    /**
     * 更新分享信息
     *
     * @param shareDO 要更新的信息
     * @return 更新后的分享信息
     */
    @Override
    public Result<ShareDO> updateShare(ShareDO shareDO) {
        // 判断userId和id是否配对
        int count = shareMapper.countByIdAndUserId(shareDO.getId(), shareDO.getUserId());
        if (count < 1) {
            Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        // 只能更新指定属性
        ShareDO shareDO0 = new ShareDO();
        shareDO0.setContent(shareDO.getContent());
        shareDO0.setOpen(shareDO.getOpen());
        //所有参数都为空
        if (BeanUtils.allFieldIsNull(shareDO0)) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_IS_BLANK,
                    "The required parameter must be not all null.");
        }

        shareDO0.setId(shareDO.getId());
        count = shareMapper.updateShare(shareDO0);
        if (count < 1) {
            logger.error("Update share failed. id: {}", shareDO0.getId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Update share failed.");
        }

        return getShare(shareDO0.getId());
    }

    /**
     * 使得对应属性的值+1
     * @param id 分享编号
     * @param parameterName 属性名
     * @param operator 自增或自减
     * @return ShareDO
     */
    @Override
    public Result<ShareDO> updateShare(Integer id, String parameterName, Operator operator) {
        if (operator == Operator.INCREMENT) {
            shareMapper.increase(id, parameterName);
        } else if (operator == Operator.DECREMENT) {
            shareMapper.decrease(id, parameterName);
        }
        return getShare(id);
    }

}
