package top.xiaohuashifu.share.dao;
import org.apache.ibatis.annotations.Param;
import top.xiaohuashifu.share.pojo.do0.UserDO;
import top.xiaohuashifu.share.pojo.query.UserQuery;

import java.util.List;

public interface UserMapper {

    /**
     * 保存用户
     * @param user 用户对象
     * @return 保存的数量
     */
    int insertUser(UserDO user);

    /**
     * 获取用户
     * @param id 用户编号
     * @return userDO
     */
    UserDO getUser(Integer id);

    /**
     * 获取用户
     * @param username 用户名
     * @return userDO
     */
    UserDO getUserByUsername(String username);

    /**
     * 获取query过滤参数后的用户列表，包含pageNum，pageSize等过滤参数，
     *
     * @param query 查询参数
     * @return UserDOList
     */
    List<UserDO> listUsers(UserQuery query);

    /**
     * 获取该username的用户数量
     * @param username 用户名
     * @return 该username的用户数量
     */
    int countByUsername(String username);

    /**
     * 获取该phone的用户数量
     * @param phone 手机号码
     * @return 该phone的用户数量
     */
    int countByPhone(String phone);

    /**
     * 获取该email的用户数量
     * @param email 电子邮箱
     * @return 该email的用户数量
     */
    int countByEmail(String email);

    /**
     * 更新用户信息
     * @param userDO0 要更新的用户信息
     * @return 成功更新的条数
     */
    int updateUser(UserDO userDO0);

    /**
     * 使得列值自增1
     * @param id 用户id
     * @param columnName 列名
     * @return 更新列数
     */
    int increase(@Param("id") Integer id, @Param("columnName") String columnName);

    /**
     * 使得列值自减1
     * @param id 用户id
     * @param columnName 列名
     * @return 更新列数
     */
    int decrease(@Param("id") Integer id, @Param("columnName") String columnName);
}
