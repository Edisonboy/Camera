package com.dgut.main.action;

import static com.dgut.common.page.SimplePage.cpn;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dgut.common.page.Pagination;
import com.dgut.common.security.encoder.PwdEncoder;
import com.dgut.common.web.CookieUtils;
import com.dgut.common.web.RequestUtils;
import com.dgut.common.web.ResponseUtils;
import com.dgut.main.dto.MemberDto;
import com.dgut.main.entity.Admin;
import com.dgut.main.entity.Role;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.manager.AdminMng;
import com.dgut.main.manager.RoleMng;
import com.dgut.main.web.CmsUtils;
import com.dgut.main.web.WebErrors;
import com.dgut.member.manager.MemberLogMng;
import com.dgut.member.manager.MemberMng;
import com.dgut.member.entity.Member;
import javax.validation.Valid;

@Controller
@RequestMapping(value="/member_global")
public class CmsMemberGlobalAct {
	
	private static final Logger log = LoggerFactory
			.getLogger(CmsAdminGlobalAct.class);
	

	
	@RequestMapping(value="/v_list.do")
	public String list(HttpServletRequest request, ModelMap model,
			String username,String realname,Integer pageNo) {
		//Admin currUser = CmsUtils.getAdmin(request);
		Pagination pagination = memberMng.getPage(username,realname,cpn(pageNo), CookieUtils.getPageSize(request));
		//List<Role> roleList = roleMng.getList();
		model.addAttribute("pagination", pagination);
		//model.addAttribute("roleList", roleList);
		model.addAttribute("username", username);
		model.addAttribute("realname", realname);
		return "member/global/list";
	}
	
	
	@RequestMapping(value="/v_add.do")
	public String add(HttpServletRequest request, ModelMap model) {
		return "member/global/add";
	}
	
	
	@RequestMapping(value="/v_save.do",method=RequestMethod.POST)
	public String save(HttpServletRequest request, ModelMap model,
			@Valid MemberDto beanDto, BindingResult result)throws Exception{
		if(result.hasErrors()){
			WebErrors errors = WebErrors.create(request);
			errors.resultToModel(model, result);
			return "member/global/add";
		}else{
			Member bean = new Member();
			MemberDto.DtoToMember(bean, beanDto);
			memberMng.create(bean, request);
		}
		return "redirect:v_list.do";
	}
	
	
	@RequestMapping(value="/v_delete.do",method=RequestMethod.GET)
	public String delete(HttpServletRequest request, ModelMap model,@RequestParam Integer id){
		System.out.println("---"+id);
		WebErrors errors = validateDelete(id,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		Member bean = memberMng.deleteById(id);
		
		log.info("delete CmsMember id={}", bean.getId());
		memberLogMng.operating(request, "cms.member.delete", "id="
				+ bean.getId() + ";username=" + bean.getUsername());
		
		return "redirect:v_list.do";
	}
	
	
	
	@RequestMapping(value="/v_edit.do")
	public String edit(HttpServletRequest request, ModelMap model,
			@RequestParam Integer id) {
		
		Member member = memberMng.findById(id);
		model.addAttribute("cmsMember", member);
		return "member/global/edit";
	}
	
	
	@RequestMapping(value="/v_update.do")
	public String update(HttpServletRequest request, ModelMap model, 
			@Valid MemberDto beanDto,BindingResult result){
		
		if(result.hasErrors()){
			WebErrors errors = WebErrors.create(request);
			errors.resultToModel(model, result);
			model.addAttribute("cmsMember",beanDto);
			return "member/global/edit";
		}else{
			Member bean = new Member();
			MemberDto.DtoToMember(bean, beanDto);
			memberMng.updateMember(bean, beanDto.getPassword());
		}
		return "redirect:v_list.do";
	}
	
	
	@RequestMapping(value = "/v_check_username.do")
	public void checkUsername(String username, HttpServletResponse response) {
		String pass;
		if (StringUtils.isBlank(username)) {
			pass = "false";
		} else {
			pass = memberMng.usernameNotExist(username) ? "true" : "false";
		}
		ResponseUtils.renderJson(response, pass);
	}
	
	
	private WebErrors validateDelete(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		vldExist(id, errors);
		return errors;
	}

	private boolean vldExist(Integer id, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		Member entity = memberMng.findById(id);
		if (errors.ifNotExist(entity, Member.class, id)) {
			return true;
		}
		return false;
	}
	
	
	
	
	
	@Autowired
	private MemberMng memberMng;
	
	@Autowired
	private MemberLogMng memberLogMng;
	
	@Autowired
	private RoleMng roleMng;
	
	@Autowired
	private AdminLogMng adminLogMng;
	
	@Autowired
	private AdminMng manager;
	
	@Autowired
	private PwdEncoder pwdEncoder;

}
