package com.dgut.member.dao;

import java.util.Date;

import com.dgut.common.page.Pagination;
import com.dgut.member.entity.MemberLog;

public interface MemberLogDao {
	public Pagination getPage(Integer category,  Integer userId,
			String title, String ip, int pageNo, int pageSize);

	public MemberLog findById(Integer id);

	public MemberLog save(MemberLog bean);

	public MemberLog deleteById(Integer id);

	public int deleteBatch(Integer category,  Date before);
}
