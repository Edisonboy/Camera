package com.dgut.member.dao;

import java.util.Date;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.member.entity.MemberAuthentication;

public interface MemberAuthenticationDao {


	public int deleteExpire(Date d);

	public MemberAuthentication getByUserId(Long userId);

	public Pagination getPage(int pageNo, int pageSize);

	public MemberAuthentication findById(String id);

	public MemberAuthentication save(MemberAuthentication bean);

	public MemberAuthentication updateByUpdater(Updater<MemberAuthentication> updater);

	public MemberAuthentication deleteById(String id);
}
