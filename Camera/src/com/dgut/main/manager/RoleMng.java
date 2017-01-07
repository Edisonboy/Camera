package com.dgut.main.manager;

import java.util.List;
import java.util.Set;

import com.dgut.main.entity.Role;

public interface RoleMng {
	public List<Role> getList();

	public Role findById(Integer id);

	public Role save(Role bean, Set<String> perms);

	public Role update(Role bean, Set<String> perms);

	public Role deleteById(Integer id);

	public Role[] deleteByIds(Integer[] ids);
}