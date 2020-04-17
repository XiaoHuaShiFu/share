package top.xiaohuashifu.share.controller.v1.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.xiaohuashifu.share.constant.TokenType;
import top.xiaohuashifu.share.pojo.ao.TokenAO;
import top.xiaohuashifu.share.result.Result;
import top.xiaohuashifu.share.service.TokenService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("v1/login")
public class LoginController {

	private final TokenService tokenService;
	
	@Autowired
	public LoginController(TokenService tokenService) {
		this.tokenService = tokenService;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "admin", method = RequestMethod.POST)
	public String adminLogin(HttpServletRequest request, HttpServletResponse response, 
					@RequestParam("jobNumber") String jobNumber, @RequestParam("password") String password) throws Exception {
		Result<TokenAO> result = tokenService.createAndSaveToken(TokenType.ADMIN, jobNumber, password);
		if(result.isSuccess()) {
			request.getSession().setAttribute("token", result.getData().getToken());
			response.sendRedirect(request.getContextPath() + "/v1/admin/index");
			return "ok";
		}
		return "error";
	}
	
}
