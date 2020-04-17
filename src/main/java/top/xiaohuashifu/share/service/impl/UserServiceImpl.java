package top.xiaohuashifu.share.service.impl;

import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.xiaohuashifu.share.dao.UserMapper;
import top.xiaohuashifu.share.pojo.do0.UserDO;
import top.xiaohuashifu.share.pojo.query.UserQuery;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.FileService;
import top.xiaohuashifu.share.service.UserService;
import top.xiaohuashifu.share.service.constant.UserConstant;
import top.xiaohuashifu.share.util.BeanUtils;

import java.util.List;

/**
 * 描述:用户服务层
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserMapper userMapper;

    private final FileService fileService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, FileService fileService) {
        this.userMapper = userMapper;
        this.fileService = fileService;
    }

    /**
     * 保存User
     * @param userDO UserDO
     * @param avatar 头像
     * @return Result<UserDO>
     */
    @Override
    public Result<UserDO> saveUser(UserDO userDO, MultipartFile avatar) {
        // 判断用户名存不存在
        int count = userMapper.countByUsername(userDO.getUsername());
        if (count >= 1) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT,
                    "Insert false due to conflict, the username already exists.");
        }

        // 判断手机号码存不存在
        count = userMapper.countByPhone(userDO.getPhone());
        if (count >= 1) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT,
                    "Insert false due to conflict, the phone already exists.");
        }

        // 判断电子邮箱存不存在
        count = userMapper.countByEmail(userDO.getEmail());
        if (count >= 1) {
            return Result.fail(ErrorCode.OPERATION_CONFLICT,
                    "Insert false due to conflict, the email already exists.");
        }

        // 保存头像并获取Url
        String avatarUrl = fileService.saveAndGetUrl(avatar, UserConstant.PREFIX_AVATAR_FILE_DIRECTORY);
        userDO.setAvatarUrl(avatarUrl);
        count = userMapper.insertUser(userDO);
        // 没有插入成功
        if (count < 1) {
            logger.error("Insert user fail.");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Insert user fail.");
        }

        return getUser(userDO.getId());
    }

    /**
     * 获取UserDO通过id
     *
     * @param id 用户编号
     * @return UserDO
     */
    @Override
    public Result<UserDO> getUser(Integer id) {
        UserDO userDO = userMapper.getUser(id);
        if (userDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified id does not exist.");
        }
        return Result.success(userDO);
    }

    /**
     * 获取UserDO通过username
     *
     * @param username 用户名
     * @return Result<UserDO>
     */
    @Override
    public Result<UserDO> getUserByUsername(String username) {
        UserDO userDO = userMapper.getUserByUsername(username);
        if (userDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The specified user for username="
                    + username + " does not exist.");
        }
        return Result.success(userDO);
    }

    /**
     * 获取UserDOList通过查询参数query
     *
     * @param query 查询参数
     * @return UserDOList
     */
    @Override
    public Result<List<UserDO>> listUsers(UserQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<UserDO> userDOList = userMapper.listUsers(query);
        if (userDOList.size() < 1) {
            Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "Not found.");
        }

        return Result.success(userDOList);
    }

    /**
     * 更新用户信息
     *
     * @param userDO 要更新的信息
     * @param avatar 要更新的头像
     * @return 更新后的用户信息
     */
    @Override
    public Result<UserDO> updateUser(UserDO userDO, MultipartFile avatar) {
        // 若头像参数不为空，更新头像
        if (avatar != null) {
            return updateAvatar(userDO.getId(), avatar);
        }

        // 只能更新指定属性
        UserDO userDO0 = new UserDO();
        userDO0.setPassword(userDO.getPassword());
        userDO0.setNickName(userDO.getNickName());
        userDO0.setGender(userDO.getGender());
        userDO0.setPhone(userDO.getPhone());
        userDO0.setEmail(userDO.getEmail());
        userDO0.setAvailable(userDO.getAvailable());
        //所有参数都为空
        if (BeanUtils.allFieldIsNull(userDO0)) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_IS_BLANK,
                    "The required parameter must be not all null.");
        }

        userDO0.setId(userDO.getId());
        int count = userMapper.updateUser(userDO0);
        if (count < 1) {
            logger.error("Update avatar failed. userId: {}", userDO0.getId());
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Update user failed.");
        }

        return getUser(userDO0.getId());
    }

    /**
     * 更新头像
     *
     * @param id userId
     * @param avatar MultipartFile
     * @return 新文件url
     */
    private Result<UserDO> updateAvatar(Integer id, MultipartFile avatar) {
        // 获取用户信息，主要是为了获取旧文件Url
        UserDO userDO = userMapper.getUser(id);

        // 更新头像文件
        String newAvatarUrl = fileService.updateFile(avatar, userDO.getAvatarUrl(),
                UserConstant.PREFIX_AVATAR_FILE_DIRECTORY);

        // 更新数据库里的avatar_url
        UserDO userDO0 = new UserDO();
        userDO0.setId(id);
        userDO0.setAvatarUrl(newAvatarUrl);
        int count = userMapper.updateUser(userDO0);
        if (count < 1) {
            logger.error("Update avatar failed. id: {}", id);
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Update avatar failed.");
        }

        userDO.setAvatarUrl(newAvatarUrl);
        return Result.success(userDO);
    }

}
