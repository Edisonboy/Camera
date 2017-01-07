package com.dgut.member.manager;

import javax.servlet.http.HttpServletRequest;

import com.dgut.common.page.Pagination;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.MemberLog;

public interface MemberLogMng {
	public Pagination getPage(Integer category,
			String username, String title, String ip, int pageNo, int pageSize);

	public MemberLog findById(Integer id);

	public MemberLog operating(HttpServletRequest request, String title,
			String content);

	public MemberLog loginFailure(HttpServletRequest request, String title,
			String content);

	public MemberLog loginSuccess(HttpServletRequest request, Member user,
			String title);

	public MemberLog save(MemberLog bean);

	public MemberLog deleteById(Integer id);

	public MemberLog[] deleteByIds(Integer[] ids);

	public int deleteBatch(Integer category, Integer days);
}
