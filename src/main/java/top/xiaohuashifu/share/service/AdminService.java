package top.xiaohuashifu.share.service;

import java.util.Map;

import top.xiaohuashifu.share.pojo.do0.AdminDO;
import top.xiaohuashifu.share.pojo.do0.AdminLogDO;
import top.xiaohuashifu.share.result.Result;

public interface AdminService {
	Result<AdminDO> getAdminByUsername(String username);

	Result saveAdminLog(AdminLogDO adminLogDO);

	Result<String> getAnnouncement();
	Result<Map<String, String>> updateAnnouncement(String announcement);
}
