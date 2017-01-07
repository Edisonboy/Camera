package com.dgut.main.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


import com.dgut.main.entity.Role;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.manager.RoleMng;

import com.dgut.main.web.WebErrors;


@Controller
public class RoleAct {
	private static final Logger log = LoggerFactory.getLogger(AdminLoginAct.class);
	
	@Autowired
	private RoleMng roleMng;
	
	@Autowired
	private AdminLogMng adminLogMng;
	 

	@RequestMapping("/role/v_list.do")
	public String list(HttpServletRequest request, ModelMap model) {
		List<Role> list = roleMng.getList();
		model.addAttribute("list", list);
		return "role/list";
	}
	
	@RequestMapping("/role/v_add.do")
	public String add(ModelMap model) {
		return "role/add";
	}
	
	@RequestMapping("/role/v_save.do")
	public String save(Role bean,String[] perms,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = roleMng.save(bean,splitPerms(perms));
		log.info("save Role id={}", bean.getId());
		
		return "redirect:v_list.do";
	}
	
	@RequestMapping("/role/o_update.do")
	public String update(Role bean, String[] perms,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = roleMng.update(bean, splitPerms(perms));
		log.info("update CmsRole id={}.", bean.getId());
		/*adminLogMng.operating(request, "cmsRole.log.add", "id=" + bean.getId()
				+ ";name=" + bean.getName());*/
		return list(request, model);
	}
	
	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		return errors;
	}
	
	@RequestMapping("/role/v_delete.do")
	public String delete(Integer[] ids, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Role[] beans = roleMng.deleteByIds(ids);
		for (Role bean : beans) {
			log.info("delete CmsRole id={}", bean.getId());
			
		}
		return list(request, model);
	}
	
	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		for (Integer id : ids) {
			vldExist(id, errors);
		}
		return errors;
	}

	private boolean vldExist(Integer id, WebErrors errors) {
		// TODO Auto-generated method stub
		if (errors.ifNull(id, "id")) {
			return true;
		}
		Role entity = roleMng.findById(id);
		if (errors.ifNotExist(entity, Role.class, id)) {
			return true;
		}
		return false;
	}

	private Set<String> splitPerms(String[] perms) {
		Set<String> set = new HashSet<String>();
		if (perms != null) {
			for (String perm : perms) {
				for (String p : StringUtils.split(perm, ',')) {
					if (!StringUtils.isBlank(p)) {
						set.add(p);
					}
				}
			}
		}
		return set;
	}

	private WebErrors validateSave(Role bean, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}
}
