package top.xiaohuashifu.share.controller.v1.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.AdminService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("v1/admin")
public class AdminController {
	
	private final AdminService adminService;
	
	@Autowired
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "admin/login";
	}
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
//	@TokenAuth(tokenType = {TokenType.ADMIN})
//	@AdminLog(value = "登录", type = AdminLogType.LOGIN)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("admin/index");
		model.addObject("token", request.getSession().getAttribute("token"));
		request.getSession().removeAttribute("token");
		Result<String> result = adminService.getAnnouncement();
		if (result.isSuccess()) {
			model.addObject("announcement", result.getData());
		}else {
			model.setViewName("admin/error");	
			model.addObject("error_msg", "服务器获取公告时发生错误");
		}
		return model;
	}


}
