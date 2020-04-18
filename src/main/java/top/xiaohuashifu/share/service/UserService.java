package top.xiaohuashifu.share.service;

import org.springframework.web.multipart.MultipartFile;
import top.xiaohuashifu.share.constant.Operator;
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

    Result<UserDO> saveUser(UserDO userDO, MultipartFile avatar);

    Result<UserDO> getUser(Integer id);

    Result<UserDO> getUserByUsername(String username);

    Result<List<UserDO>> listUsers(UserQuery query);

    Result<UserDO> updateUser(UserDO userDO, MultipartFile avatar);

    Result<UserDO> updateUser(Integer id, String parameterName, Operator operator);
}
