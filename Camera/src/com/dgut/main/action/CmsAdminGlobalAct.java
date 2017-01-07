package com.dgut.main.action;

import static com.dgut.common.page.SimplePage.cpn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dgut.common.page.Pagination;
import com.dgut.common.web.CookieUtils;
import com.dgut.common.web.RequestUtils;
import com.dgut.common.web.ResponseUtils;
import com.dgut.main.entity.Admin;
import com.dgut.main.entity.Role;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.manager.AdminMng;
import com.dgut.main.manager.RoleMng;

import com.dgut.main.web.CmsUtils;
import com.dgut.main.web.WebErrors;



/**
 * 全站管理员ACTION
 * 
 * @author liufang
 * 
 */
@Controller
public class CmsAdminGlobalAct  {
	private static final Logger log = LoggerFactory
			.getLogger(CmsAdminGlobalAct.class);

	@RequestMapping("/admin_global/v_list.do")
	public String list(String queryUsername,Integer queryRoleId, String queryEmail,
			Boolean queryDisabled, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		
		Admin currUser = CmsUtils.getAdmin(request);
		Pagination pagination = manager.getPage(queryUsername,queryRoleId, queryEmail,
				queryDisabled, null, currUser.getRank(),
				cpn(pageNo), CookieUtils.getPageSize(request));
		List<Role> roleList = roleMng.getList();
		model.addAttribute("pagination", pagination);
		model.addAttribute("roleList", roleList);
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryEmail", queryEmail);
		model.addAttribute("queryDisabled", queryDisabled);
		model.addAttribute("queryRoleId", queryRoleId);
		return "admin/global/list";
	}
	

	@RequestMapping("/admin_global/v_add.do")
	public String add(HttpServletRequest request, ModelMap model) {
		Admin currUser = CmsUtils.getAdmin(request);
		List<Role> roleList = roleMng.getList();
		model.addAttribute("roleList", roleList);
		model.addAttribute("currRank", currUser.getRank());
		return "admin/global/add";
	}

	
	@RequestMapping("/admin_global/v_edit.do")
	public String edit(Integer id,  Boolean queryDisabled,
			HttpServletRequest request, ModelMap model) {
		String queryUsername = RequestUtils.getQueryParam(request,
				"queryUsername");
		String queryEmail = RequestUtils.getQueryParam(request, "queryEmail");
		Admin currUser = CmsUtils.getAdmin(request);
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Admin admin = manager.findById(id);

		List<Role> roleList = roleMng.getList();

		model.addAttribute("cmsAdmin", admin);
		model.addAttribute("roleList", roleList);
		model.addAttribute("currRank", currUser.getRank());

		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryEmail", queryEmail);
		model.addAttribute("queryDisabled", queryDisabled);
		return "admin/global/edit";
	}

	
	@RequestMapping("/admin_global/o_save.do")
	public String save(Admin bean,  String username,
			String email, String password, Integer rank, Boolean gender,String realname,
			Integer roleId,  HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		String ip = RequestUtils.getIpAddr(request);
		bean = manager.saveAdmin(username, email, password, ip, rank,gender,realname,roleId);
		log.info("save CmsAdmin id={}", bean.getId());
		adminLogMng.operating(request, "cms.admin.add", "id=" + bean.getId()
				+ ";username=" + bean.getUsername());
		return "redirect:v_list.do";
	}

	
	@RequestMapping("/admin_global/o_update.do")
	public String update(Admin bean, String password,
			Integer roleId, String queryUsername,Integer queryRoleId, String queryEmail,
			Boolean queryDisabled, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), bean.getRank(),request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.updateAdmin(bean,  password,  roleId);
		log.info("update CmsAdmin id={}.", bean.getId());
		adminLogMng.operating(request, "cms.admin.update", "id=" + bean.getId()
				+ ";username=" + bean.getUsername());
		return list(queryUsername,queryRoleId, queryEmail,  queryDisabled,
				pageNo, request, model);
	}
	

	@RequestMapping("/admin_global/o_delete.do")
	public String delete(Integer[] ids,Integer queryRoleId, Boolean queryDisabled, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		String queryUsername = RequestUtils.getQueryParam(request,
				"queryUsername");
		String queryEmail = RequestUtils.getQueryParam(request, "queryEmail");
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Admin[] beans = manager.deleteByIds(ids);
		for (Admin bean : beans) {
			log.info("delete CmsAdmin id={}", bean.getId());
			adminLogMng.operating(request, "cms.admin.delete", "id="
					+ bean.getId() + ";username=" + bean.getUsername());
		}
		return list(queryUsername,queryRoleId, queryEmail, queryDisabled,
				pageNo, request, model);
	}


	@RequestMapping(value = "/admin_global/v_check_username.do")
	public void checkUsername(String username, HttpServletResponse response) {
		String pass;
		if (StringUtils.isBlank(username)) {
			pass = "false";
		} else {
			pass = manager.usernameNotExist(username) ? "true" : "false";
		}
		ResponseUtils.renderJson(response, pass);
	}

	@RequestMapping(value = "/admin_global/v_check_email.do")
	public void checkEmail(String email, HttpServletResponse response) {
		String pass;
		if (StringUtils.isBlank(email)) {
			pass = "false";
		} else {
			pass = manager.emailNotExist(email) ? "true" : "false";
		}
		ResponseUtils.renderJson(response, pass);
	}

	
	private WebErrors validateSave(Admin bean,  HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}

	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		// TODO 检查管理员rank
		return errors;
	}

	private WebErrors validateUpdate(Integer id, Integer rank, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		// TODO 检查管理员rank
		if (vldParams(id,rank, request, errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			vldExist(id, errors);
		}
		return errors;
	}

	private boolean vldExist(Integer id, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		Admin entity = manager.findById(id);
		if (errors.ifNotExist(entity, Admin.class, id)) {
			return true;
		}
		return false;
	}
	
	private boolean vldParams(Integer id,Integer rank, HttpServletRequest request,
			WebErrors errors) {
		Admin user = CmsUtils.getAdmin(request);
		Admin entity = manager.findById(id);
		//提升等级大于当前登录用户
		if (rank > user.getRank()) {
			errors.addErrorCode("error.noPermissionToRaiseRank", id);
			return true;
		}
		//修改的用户等级大于当前登录用户 无权限
		if (entity.getRank() > user.getRank()) {
			errors.addErrorCode("error.noPermission", Admin.class, id);
			return true;
		}
		return false;
	}
	
	@Autowired
	private RoleMng roleMng;
	
	@Autowired
	private AdminLogMng adminLogMng;
	
	@Autowired
	private AdminMng manager;

}