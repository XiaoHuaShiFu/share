package top.xiaohuashifu.share.dao;

import top.xiaohuashifu.share.pojo.do0.AdminDO;

public interface AdminMapper {
	/**
	 * 通过username获取admin
	 * @param username 用户名
	 * @return AdminDO
	 */
	AdminDO getAdminByUsername(String username);

}
