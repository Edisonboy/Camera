package com.dgut.main.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.BeanUtils;

import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberPhoto;

public class MemberDto {

	 /**
	 * Bean Validation 中内置的 constraint       
	 * @Null   被注释的元素必须为 null       
	 * @NotNull    被注释的元素必须不为 null       
	 * @AssertTrue     被注释的元素必须为 true       
	 * @AssertFalse    被注释的元素必须为 false       
	 * @Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值       
	 * @Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值       
	 * @DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值       
	 * @DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值       
	 * @Size(max=, min=)   被注释的元素的大小必须在指定的范围内       
	 * @Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内       
	 * @Past   被注释的元素必须是一个过去的日期       
	 * @Future     被注释的元素必须是一个将来的日期       
	 * @Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式       
	 * Hibernate Validator 附加的 constraint       
	 * @NotBlank(message =)   验证字符串非null，且长度必须大于0       
	 * @Email  被注释的元素必须是电子邮箱地址       
	 * @Length(min=,max=)  被注释的字符串的大小必须在指定的范围内       
	 * @NotEmpty   被注释的字符串的必须非空       
	 * @Range(min=,max=,message=)  被注释的元素必须在合适的范围内 
	 */
	
	
	private java.lang.Integer id;
	@Length(min=1,max=16,message="{memberpassword.range}")
	@NotNull(message="密码不能为空")
	private java.lang.String password;
	
	@Length(min=11,max=11,message="用户名长度必须为11位")
	private java.lang.String username;
	
	private java.lang.String icon;
	private java.util.Date registerTime;
	private java.lang.String registerIp;
	private java.util.Date lastLoginTime;
	private java.lang.String lastLoginIp;
	private java.lang.Integer loginCount;
	
	
	private java.lang.Boolean disabled;

	
	private java.lang.String realname;
	
	
	private java.lang.Boolean gender;

	private java.util.Date errorTime;
	private java.lang.Integer errorCount;
	private java.lang.String errorIp;
	private Set<MemberPhoto> memberPhoto = new HashSet<MemberPhoto>(0);
	
	
	public static void MemberToDto(Member member,MemberDto memberdto){
		BeanUtils.copyProperties(member,memberdto);
	}
	
	
	public static void DtoToMember(Member member,MemberDto memberdto){
		BeanUtils.copyProperties(memberdto,member);
	}
	
	
	
	
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.String getPassword() {
		return password;
	}
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	public java.lang.String getUsername() {
		return username;
	}
	public void setUsername(java.lang.String username) {
		this.username = username;
	}
	public java.lang.String getIcon() {
		return icon;
	}
	public void setIcon(java.lang.String icon) {
		this.icon = icon;
	}
	public java.util.Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(java.util.Date registerTime) {
		this.registerTime = registerTime;
	}
	public java.lang.String getRegisterIp() {
		return registerIp;
	}
	public void setRegisterIp(java.lang.String registerIp) {
		this.registerIp = registerIp;
	}
	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public java.lang.String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(java.lang.String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public java.lang.Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(java.lang.Integer loginCount) {
		this.loginCount = loginCount;
	}
	public java.lang.Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(java.lang.Boolean disabled) {
		this.disabled = disabled;
	}
	public java.lang.String getRealname() {
		return realname;
	}
	public void setRealname(java.lang.String realname) {
		this.realname = realname;
	}
	public java.lang.Boolean getGender() {
		return gender;
	}
	public void setGender(java.lang.Boolean gender) {
		this.gender = gender;
	}
	public java.util.Date getErrorTime() {
		return errorTime;
	}
	public void setErrorTime(java.util.Date errorTime) {
		this.errorTime = errorTime;
	}
	public java.lang.Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(java.lang.Integer errorCount) {
		this.errorCount = errorCount;
	}
	public java.lang.String getErrorIp() {
		return errorIp;
	}
	public void setErrorIp(java.lang.String errorIp) {
		this.errorIp = errorIp;
	}
	public Set<MemberPhoto> getMemberPhoto() {
		return memberPhoto;
	}
	public void setMemberPhoto(Set<MemberPhoto> memberPhoto) {
		this.memberPhoto = memberPhoto;
	}
	
	
	
	
}
