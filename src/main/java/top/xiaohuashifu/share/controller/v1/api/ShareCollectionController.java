package top.xiaohuashifu.share.controller.v1.api;

import com.github.pagehelper.PageInfo;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import top.xiaohuashifu.share.aspect.annotation.ErrorHandler;
import top.xiaohuashifu.share.auth.TokenAuth;
import top.xiaohuashifu.share.constant.TokenType;
import top.xiaohuashifu.share.manager.ShareCollectionManager;
import top.xiaohuashifu.share.pojo.ao.TokenAO;
import top.xiaohuashifu.share.pojo.do0.ShareCollectionDO;
import top.xiaohuashifu.share.pojo.group.GroupDelete;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.pojo.query.ShareCollectionQuery;
import top.xiaohuashifu.share.pojo.vo.ShareCollectionVO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.ShareCollectionService;

/**
 * 描述: 分享收藏模块
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@RestController
@RequestMapping("v1/shares/collections")
@Validated
public class ShareCollectionController {

    private final Mapper mapper;

    private final ShareCollectionService shareCollectionService;

    private final ShareCollectionManager shareCollectionManager;

    @Autowired
    public ShareCollectionController(Mapper mapper, ShareCollectionService shareCollectionService,
                                     ShareCollectionManager shareCollectionManager) {
        this.mapper = mapper;
        this.shareCollectionService = shareCollectionService;
        this.shareCollectionManager = shareCollectionManager;
    }

    /**
     * 创建ShareCollectionDO并返回ShareCollectionDO
     * @param shareCollectionDO 分享收藏信息
     * @return ShareCollectionDO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INVALID_PARAMETER_NOT_FOUND
     * OPERATION_CONFLICT
     * INTERNAL_ERROR
     *
     * @bindErrors
     * INVALID_PARAMETER
     * INVALID_PARAMETER_IS_NULL
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @TokenAuth(tokenType = {TokenType.USER})
    @ErrorHandler
    public Object post(TokenAO tokenAO, @Validated(GroupPost.class) ShareCollectionDO shareCollectionDO) {
        // 不能越权
        if (!tokenAO.getId().equals(shareCollectionDO.getUserId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        Result<ShareCollectionDO> result = shareCollectionService.saveShareCollection(shareCollectionDO);
        return !result.isSuccess() ? result : mapper.map(result.getData(), ShareCollectionVO.class);
    }

    /**
     * 删除ShareCollection
     * @param shareCollectionDO 分享收藏信息
     * @return 提示信息
     *
     * @success:
     * HttpStatus.DELETE
     *
     * @errors:
     * INVALID_PARAMETER_NOT_FOUND
     * INTERNAL_ERROR
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_NULL
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @TokenAuth(tokenType = {TokenType.USER})
    @ErrorHandler
    public Object delete(TokenAO tokenAO, @Validated(GroupDelete.class) ShareCollectionDO shareCollectionDO) {
        // 不能越权
        if (!tokenAO.getId().equals(shareCollectionDO.getUserId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        Result result = shareCollectionService.deleteShareCollection(shareCollectionDO.getUserId(),
                shareCollectionDO.getShareId());
        return !result.isSuccess() ? result : result.getMessage();
    }

    /**
     * 查询shareCollection
     * @param query 查询参数
     * @return PageInfo<ShareCollectionVO>
     *
     * @success:
     * HttpStatus.OK
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ErrorHandler
    public Object get(ShareCollectionQuery query) {
        Result<PageInfo<ShareCollectionVO>> result = shareCollectionManager.listShareCollections(query);
        return !result.isSuccess() ? result : result.getData();
    }

}
