package com.dgut.main.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dgut.common.web.ResponseUtils;
import com.dgut.main.entity.Admin;
import com.dgut.main.manager.AdminMng;
import com.dgut.main.web.CmsUtils;
import com.dgut.main.web.FrontUtils;
import com.dgut.main.web.WebErrors;

@Controller
public class PersonalAct {
	@RequestMapping("/personal/v_profile.do")
	public String profileEdit(HttpServletRequest request, ModelMap model) {
		FrontUtils.adminData(request, model);
		return "personal/profile";
	}

	@RequestMapping("/personal/o_profile.do")
	public String profileUpdate(String origPwd, String newPwd, String realname,
			HttpServletRequest request, ModelMap model) {
		Admin user = CmsUtils.getAdmin(request);
		WebErrors errors = validatePasswordSubmit(user.getId(), origPwd,
				newPwd,  realname, request);
		if (errors.hasErrors()) {
			model.addAttribute("errors", errors.getErrors());
			return profileEdit(request, model);
		}
		adminMng.updatePwdRealName(user.getId(), newPwd,realname);
		model.addAttribute("message", "global.success");
		return profileEdit(request, model);
	}

	/**
	 * 验证密码是否正确
	 * 
	 * @param origPwd
	 *            原密码
	 * @param request
	 * @param response
	 */
	@RequestMapping("/personal/v_checkPwd.do")
	public void checkPwd(String origPwd, HttpServletRequest request,
			HttpServletResponse response) {
		Admin user = CmsUtils.getAdmin(request);
		boolean pass = adminMng.isPasswordValid(user.getId(), origPwd);
		ResponseUtils.renderJson(response, pass ? "true" : "false");
	}

	private WebErrors validatePasswordSubmit(Integer id, String origPwd,
			String newPwd,  String realname,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifBlank(origPwd, "origPwd", 32)) {
			return errors;
		}
		if (errors.ifMaxLength(newPwd, "newPwd", 32)) {
			return errors;
		}
		if (errors.ifMaxLength(realname, "realname", 100)) {
			return errors;
		}
		if (!adminMng.isPasswordValid(id, origPwd)) {
			errors.addErrorString("密码错误");
			return errors;
		}
		return errors;
	}

	@Autowired
	private AdminMng adminMng;
}
