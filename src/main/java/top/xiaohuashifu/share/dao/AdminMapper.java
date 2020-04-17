package top.xiaohuashifu.share.dao;

import org.apache.ibatis.annotations.Param;

import top.xiaohuashifu.share.pojo.do0.AdminDO;
import top.xiaohuashifu.share.pojo.do0.AdminLogDO;

public interface AdminMapper {
	AdminDO getAdminByUsername(String username);

	void insertAdminLog(@Param("adminLog") AdminLogDO adminLogDO);

	String getAnnouncement();

	void updateAnnouncement(@Param("announcement") String announcement);
}
