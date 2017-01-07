package com.dgut.member.entity.base;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberPhoto;


public abstract class BaseMember  implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	// constructors
	public BaseMember() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMember (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}



	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;
	private java.lang.String password;
	// fields
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
	
	public java.lang.String getIcon() {
		return icon;
	}

	public void setIcon(java.lang.String icon) {
		this.icon = icon;
	}

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="user_id"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}


	
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	
	
	public java.lang.String getPassword() {
		return password;
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

	/**
	 * Return the value associated with the column: username
	 */
	public java.lang.String getUsername () {
		return username;
	}

	/**
	 * Set the value related to the column: username
	 * @param username the username value
	 */
	public void setUsername (java.lang.String username) {
		this.username = username;
	}
	
	public java.lang.Boolean getGender() {
		return gender;
	}
	public void setGender(java.lang.Boolean gender) {
		this.gender = gender;
	}
	public java.lang.String getRealname() {
		return realname;
	}
	public void setRealname(java.lang.String realname) {
		this.realname = realname;
	}


	/**
	 * Return the value associated with the column: register_time
	 */
	public java.util.Date getRegisterTime () {
		return registerTime;
	}

	/**
	 * Set the value related to the column: register_time
	 * @param registerTime the register_time value
	 */
	public void setRegisterTime (java.util.Date registerTime) {
		this.registerTime = registerTime;
	}


	/**
	 * Return the value associated with the column: register_ip
	 */
	public java.lang.String getRegisterIp () {
		return registerIp;
	}

	/**
	 * Set the value related to the column: register_ip
	 * @param registerIp the register_ip value
	 */
	public void setRegisterIp (java.lang.String registerIp) {
		this.registerIp = registerIp;
	}


	/**
	 * Return the value associated with the column: last_login_time
	 */
	public java.util.Date getLastLoginTime () {
		return lastLoginTime;
	}

	/**
	 * Set the value related to the column: last_login_time
	 * @param lastLoginTime the last_login_time value
	 */
	public void setLastLoginTime (java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}


	/**
	 * Return the value associated with the column: last_login_ip
	 */
	public java.lang.String getLastLoginIp () {
		return lastLoginIp;
	}

	/**
	 * Set the value related to the column: last_login_ip
	 * @param lastLoginIp the last_login_ip value
	 */
	public void setLastLoginIp (java.lang.String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}


	/**
	 * Return the value associated with the column: login_count
	 */
	public java.lang.Integer getLoginCount () {
		return loginCount;
	}

	/**
	 * Set the value related to the column: login_count
	 * @param loginCount the login_count value
	 */
	public void setLoginCount (java.lang.Integer loginCount) {
		this.loginCount = loginCount;
	}




	/**
	 * Return the value associated with the column: is_disabled
	 */
	public java.lang.Boolean getDisabled () {
		return disabled;
	}

	/**
	 * Set the value related to the column: is_disabled
	 * @param disabled the is_disabled value
	 */
	public void setDisabled (java.lang.Boolean disabled) {
		this.disabled = disabled;
	}
	
	
	public Set<MemberPhoto> getMemberPhoto() {
		return memberPhoto;
	}

	public void setMemberPhoto(Set<MemberPhoto> memberPhoto) {
		this.memberPhoto = memberPhoto;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof Member)) return false;
		else {
			Member u = (Member) obj;
			if (null == this.getId() || null == u.getId()) return false;
			else return (this.getId().equals(u.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}

}
