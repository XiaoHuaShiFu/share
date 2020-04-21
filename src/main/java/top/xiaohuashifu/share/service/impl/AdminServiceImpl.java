package top.xiaohuashifu.share.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xiaohuashifu.share.dao.AdminMapper;
import top.xiaohuashifu.share.pojo.do0.AdminDO;
import top.xiaohuashifu.share.result.ErrorCode;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.AdminService;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	private final AdminMapper adminMapper;
	
	@Autowired
	public AdminServiceImpl(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
	}

	/**
	 * 获取AdminDO通过jobNumber
	 *
	 * @param jobNumber 工号
	 * @return Result<AdminDO>
	 */
	@Override
	public Result<AdminDO> getAdminByUsername(String jobNumber) {
		AdminDO admin = adminMapper.getAdminByUsername(jobNumber);
		if (admin == null) {
			return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "The admin for jobNumber: "
					+ jobNumber + " does not exist.");
		}
		return Result.success(admin);
	}

}
