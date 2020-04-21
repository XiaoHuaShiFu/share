package top.xiaohuashifu.share.service;

import top.xiaohuashifu.share.pojo.do0.AdminDO;
import top.xiaohuashifu.share.result.Result;

public interface AdminService {
	Result<AdminDO> getAdminByUsername(String username);
}
