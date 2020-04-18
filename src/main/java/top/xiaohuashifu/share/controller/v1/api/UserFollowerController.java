package top.xiaohuashifu.share.controller.v1.api;

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
import top.xiaohuashifu.share.pojo.ao.TokenAO;
import top.xiaohuashifu.share.pojo.do0.UserFollowerDO;
import top.xiaohuashifu.share.pojo.group.GroupDelete;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.pojo.vo.UserFollowerVO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.UserFollowerService;

/**
 * 描述: 用户关注模块
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@RestController
@RequestMapping("v1/users/followers")
@Validated
public class UserFollowerController {

    private final Mapper mapper;

    private final UserFollowerService userFollowerService;

    @Autowired
    public UserFollowerController(Mapper mapper, UserFollowerService userFollowerService) {
        this.mapper = mapper;
        this.userFollowerService = userFollowerService;
    }

    /**
     * 创建userFollowerDO并返回userFollowerDO
     * @param userFollowerDO 用户粉丝信息
     * @return UserFollowerDO
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
    public Object post(TokenAO tokenAO, @Validated(GroupPost.class) UserFollowerDO userFollowerDO) {
        // 不能越权关注
        if (!tokenAO.getId().equals(userFollowerDO.getFollowerId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        Result<UserFollowerDO> result = userFollowerService.saveUserFollower(userFollowerDO);
        return !result.isSuccess() ? result : mapper.map(result.getData(), UserFollowerVO.class);
    }

    /**
     * 删除UserFollower
     * @param userFollowerDO 用户粉丝信息
     * @return String
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
    public Object delete(TokenAO tokenAO, @Validated(GroupDelete.class) UserFollowerDO userFollowerDO) {
        // 不能越权取关
        if (!tokenAO.getId().equals(userFollowerDO.getFollowerId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }

        Result<String> result = userFollowerService.deleteUserFollower(
                userFollowerDO.getFollowederId(), userFollowerDO.getFollowerId());
        return !result.isSuccess() ? result : result.getData();
    }

}
