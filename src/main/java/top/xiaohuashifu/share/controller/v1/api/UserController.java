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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述: 用户模块
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@RestController
@RequestMapping("v1/users")
@Validated
public class UserController {

    private final Mapper mapper;

    private final UserService userService;

    @Autowired
    public UserController(Mapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    /**
     * 创建User并返回User
     * @param code 微信小程序的wx.login()接口返回值
     * @param userDO 用户信息
     * @return UserVO
     *
     * @success:
     * HttpStatus.CREATED
     *
     * @errors:
     * INVALID_PARAMETER: The code is not valid.
     * OPERATION_CONFLICT: Request was denied due to conflict, the openid already exists.
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
    public Object post(
            @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The code must be not blank.") String code,
            @Validated(GroupPost.class) UserDO userDO) {
        Result<UserDO> result = userService.saveUser(userDO, code);
        return !result.isSuccess() ? result : mapper.map(result.getData(), UserVO.class);
    }

    // TODO: 2020/3/31 这里在不同权限下应该返回不同的数据,
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

        //非法权限token
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
     * @return UserVO
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * INTERNAL_ERROR: Update avatar failed.
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
    public Object put(TokenAO tokenAO, @Validated(Group.class) UserDO userDO) {
        if (!userDO.getId().equals(tokenAO.getId())) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }
        Result<UserDO> result = userService.updateUser(userDO);

        return !result.isSuccess() ? result : mapper.map(result.getData(), UserVO.class);
    }

    /**
     * 修改头像
     *
     * @param tokenAO TokenAO
     * @param avatar MultipartFile
     * @return UserVO
     *
     * @success:
     * HttpStatus.OK
     *
     * @errors:
     * INTERNAL_ERROR: Upload file failed.
     * INTERNAL_ERROR: Delete file failed.
     * INTERNAL_ERROR: Update avatar exception.
     *
     * @bindErrors
     * INVALID_PARAMETER_IS_NULL
     */
    @RequestMapping(value="/avatar", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @TokenAuth(tokenType = TokenType.USER)
    @ErrorHandler
    public Object putAvatar(
            TokenAO tokenAO,
            @NotNull(message = "INVALID_PARAMETER_IS_BLANK: The id must be not blank.") @Id Integer id,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The required avatar must be not null.") MultipartFile avatar) {
        if (!tokenAO.getId().equals(id)) {
            return Result.fail(ErrorCode.FORBIDDEN_SUB_USER);
        }
        Result<UserDO> result = userService.updateAvatar(tokenAO.getId(), avatar);

        return result.isSuccess() ? mapper.map(result.getData(), UserVO.class) : result;
    }

}
