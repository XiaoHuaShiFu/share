package top.xiaohuashifu.share.service;

import org.springframework.web.multipart.MultipartFile;
import top.xiaohuashifu.share.pojo.do0.UserDO;
import top.xiaohuashifu.share.pojo.query.UserQuery;
import top.xiaohuashifu.share.result.Result;

import java.util.List;

/**
 * 描述: 用户Service
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public interface UserService {

    Result<String> getOpenid(Integer userFormId);

    Result<UserDO> getUserByJobNumber(String jobNumber);

    Result<UserDO> saveUser(UserDO userDO, String code);

    Result<UserDO> getUser(Integer id);

    Result<List<UserDO>> listUsers(UserQuery query);

    Result<UserDO> updateUser(UserDO userDO);

    Result<UserDO> updateAvatar(Integer id, MultipartFile avatar);
}
