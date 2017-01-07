package com.dgut.member.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.member.entity.Member;

public interface MemberDao {
	public Pagination getPage(String username,  String realname, int pageNo, int pageSize);
	
	public List getList(String username, Boolean disabled);

	public Member findById(Integer id);

	public Member findByUsername(String username);

	public int countByUsername(String username);

	public Member save(Member bean);

	public Member updateByUpdater(Updater<Member> updater);

	public Member deleteById(Integer id);
}
