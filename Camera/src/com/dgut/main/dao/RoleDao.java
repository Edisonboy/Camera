package com.dgut.main.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.main.entity.Role;

public interface RoleDao {
	public List<Role> getList();

	public Role findById(Integer id);

	public Role save(Role bean);

	public Role updateByUpdater(Updater<Role> updater);

	public Role deleteById(Integer id);
}