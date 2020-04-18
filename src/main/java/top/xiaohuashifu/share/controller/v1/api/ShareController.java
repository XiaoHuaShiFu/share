package top.xiaohuashifu.share.controller.v1.api;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.xiaohuashifu.share.aspect.annotation.ErrorHandler;
import top.xiaohuashifu.share.auth.TokenAuth;
import top.xiaohuashifu.share.constant.TokenType;
import top.xiaohuashifu.share.pojo.ao.TokenAO;
import top.xiaohuashifu.share.pojo.do0.UserDO;
import top.xiaohuashifu.share.pojo.group.Group;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.pojo.query.UserQuery;
import top.xiaohuashifu.share.pojo.vo.UserVO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.UserService;
import top.xiaohuashifu.share.validator.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述: 分享模块
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@RestController
@RequestMapping("v1/shares")
@Validated
public class ShareController {

    private final Mapper mapper;

    private final UserService userService;

    @Autowired
    public ShareController(Mapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    /**
     * 创建User并返回User
     * @param userDO 用户信息
     * @param avatar 头像
     * @return UserVO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INVALID_PARAMETER
     * OPERATION_CONFLICT
     *
     * @bindErrors
     * INVALID_PARAMETER
     * INVALID_PARAMETER_IS_NULL
     * INVALID_PARAMETER_IS_BLANK
     * INVALID_PARAMETER_SIZE
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ErrorHandler
    public Object post(@Validated(GroupPost.class) UserDO userDO,
                       @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required avatar must be not null.")
                               MultipartFile avatar) {
        Result<UserDO> result = userService.saveUser(userDO, avatar);
        return !result.isSuccess() ? result : mapper.map(result.getData(), UserVO.class);
    }

    // TODO: 这里在不同权限下应该返回不同的数据,
    //  ADMIN返回的信息应该多过USER
    /**
     * 获取user
     * @param tokenAO TokenAO
     * @param id 用户编号
     * @return UserVO
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * FORBIDDEN_SUB_USER
     *
     * @bindErrors
     * INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.
     */
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
    @ErrorHandler
    public Object get(TokenAO tokenAO, @PathVariable @Id Integer id) {
        TokenType type = tokenAO.getType();

        if (type == TokenType.USER) {
            Result<UserDO> result = userService.getUser(id);
            return !result.isSuccess() ? result : mapper.map(result.getData(), UserVO.class);
        }
        if (type == TokenType.ADMIN) {
            Result<UserDO> result = userService.getUser(id);
            return !result.isSuccess() ? result : mapper.map(result.getData(), UserVO.class);
        }

        // 非法权限token
        return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
    }

    /**
     * 查询user
     * @param query 查询参数
     * @return UserVOList
     *
     * @success:
     * HttpStatus.OK
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
    @ErrorHandler
    public Object get(UserQuery query) {
        Result<List<UserDO>> result = userService.listUsers(query);
        if (!result.isSuccess()) {
            return result;
        }

        return result.getData().stream()
                .map(userDO -> mapper.map(userDO, UserVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 更新User并返回User
     * @param tokenAO TokenAO
     * @param userDO User信息
     * @param avatar 要更新的头像
     * @return UserVO
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * INTERNAL_ERROR
     *
     * @bindErrors
     * INVALID_PARAMETER
     * INVALID_PARAMETER_IS_NULL
     * INVALID_PARAMETER_IS_BLANK
     * INVALID_PARAMETER_SIZE
     * INVALID_PARAMETER_VALUE_BELOW
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    // TODO: 2020/3/26 这里应该分开两个权限，分别进行权限控制
    // TODO: 2020/3/31 ADMIN可以控制修改一些信息，USER可以修改一些信息
    @TokenAuth(tokenType = {TokenType.USER, TokenType.ADMIN})
    @ErrorHandler
    public Object put(TokenAO tokenAO, @Validated(Group.class) UserDO userDO, MultipartFile avatar) {
        // 管理员
        if (tokenAO.getType() == TokenType.ADMIN) {
            Result<UserDO> result = userService.updateUser(userDO, avatar);
            return result.isSuccess() ? mapper.map(result.getData(), UserVO.class) : result;
        }
        // 用户本人
        else if (userDO.getId().equals(tokenAO.getId())) {
            Result<UserDO> result = userService.updateUser(userDO, avatar);
            return result.isSuccess() ? mapper.map(result.getData(), UserVO.class) : result;
        }

        // 非法权限token
        return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
    }

}
