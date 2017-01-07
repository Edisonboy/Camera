package com.dgut.member.entity.base;

import java.io.Serializable;

import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberLog;

public class BaseMemberLog  implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	// constructors
	public BaseMemberLog () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseMemberLog (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMemberLog (
		java.lang.Integer id,
		java.lang.Integer category,
		java.util.Date time) {

		this.setId(id);
		this.setCategory(category);
		this.setTime(time);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer category;
	private java.util.Date time;
	private java.lang.String ip;
	private java.lang.String url;
	private java.lang.String title;
	private java.lang.String content;

	// many to one
	private Member user;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="log_id"
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




	/**
	 * Return the value associated with the column: category
	 */
	public java.lang.Integer getCategory () {
		return category;
	}

	/**
	 * Set the value related to the column: category
	 * @param category the category value
	 */
	public void setCategory (java.lang.Integer category) {
		this.category = category;
	}


	/**
	 * Return the value associated with the column: log_time
	 */
	public java.util.Date getTime () {
		return time;
	}

	/**
	 * Set the value related to the column: log_time
	 * @param time the log_time value
	 */
	public void setTime (java.util.Date time) {
		this.time = time;
	}


	/**
	 * Return the value associated with the column: ip
	 */
	public java.lang.String getIp () {
		return ip;
	}

	/**
	 * Set the value related to the column: ip
	 * @param ip the ip value
	 */
	public void setIp (java.lang.String ip) {
		this.ip = ip;
	}


	/**
	 * Return the value associated with the column: url
	 */
	public java.lang.String getUrl () {
		return url;
	}

	/**
	 * Set the value related to the column: url
	 * @param url the url value
	 */
	public void setUrl (java.lang.String url) {
		this.url = url;
	}


	/**
	 * Return the value associated with the column: title
	 */
	public java.lang.String getTitle () {
		return title;
	}

	/**
	 * Set the value related to the column: title
	 * @param title the title value
	 */
	public void setTitle (java.lang.String title) {
		this.title = title;
	}


	/**
	 * Return the value associated with the column: content
	 */
	public java.lang.String getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: content
	 * @param content the content value
	 */
	public void setContent (java.lang.String content) {
		this.content = content;
	}


	public void setUser(Member user) {
		this.user = user;
	}
	
	public Member getUser() {
		return user;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof MemberLog)) return false;
		else {
			MemberLog cmsLog = (MemberLog) obj;
			if (null == this.getId() || null == cmsLog.getId()) return false;
			else return (this.getId().equals(cmsLog.getId()));
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
