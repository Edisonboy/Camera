package com.dgut.main.entity.base;

import java.io.Serializable;

import com.dgut.main.entity.Admin;
import com.dgut.main.entity.Role;

/**
 * This is an object that contains data related to the jc_user table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_user"
 */

public abstract class BaseAdmin  implements Serializable {



	// constructors
	public BaseAdmin () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseAdmin (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseAdmin (
		java.lang.Integer id,
		java.lang.String username,
		java.lang.String password,
		java.util.Date registerTime,
		java.lang.String registerIp,
		java.lang.Integer loginCount,
		java.lang.Integer rank,
		java.lang.Boolean admin,
		java.lang.Boolean disabled) {

		this.setId(id);
		this.setUsername(username);
		this.setPassword(password);
		this.setRegisterTime(registerTime);
		this.setRegisterIp(registerIp);
		this.setLoginCount(loginCount);
		this.setRank(rank);
		this.setAdmin(admin);
		this.setDisabled(disabled);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;
	private java.lang.String password;
	// fields
	private java.lang.String username;
	private java.lang.String email;
	private java.util.Date registerTime;
	private java.lang.String registerIp;
	private java.util.Date lastLoginTime;
	private java.lang.String lastLoginIp;
	private java.lang.Integer loginCount;
	private java.lang.Integer rank;
	private java.lang.Boolean admin;
	private java.lang.Boolean disabled;


	private java.lang.String realname;
	private java.lang.Boolean gender;
	private Role role;

	private java.util.Date errorTime;
	private java.lang.Integer errorCount;
	private java.lang.String errorIp;
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
	 * Return the value associated with the column: email
	 */
	public java.lang.String getEmail () {
		return email;
	}

	/**
	 * Set the value related to the column: email
	 * @param email the email value
	 */
	public void setEmail (java.lang.String email) {
		this.email = email;
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
	 * Return the value associated with the column: rank
	 */
	public java.lang.Integer getRank () {
		return rank;
	}

	/**
	 * Set the value related to the column: rank
	 * @param rank the rank value
	 */
	public void setRank (java.lang.Integer rank) {
		this.rank = rank;
	}




	/**
	 * Return the value associated with the column: is_admin
	 */
	public java.lang.Boolean getAdmin () {
		return admin;
	}

	/**
	 * Set the value related to the column: is_admin
	 * @param admin the is_admin value
	 */
	public void setAdmin (java.lang.Boolean admin) {
		this.admin = admin;
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
	


	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof Admin)) return false;
		else {
			Admin u = (Admin) obj;
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