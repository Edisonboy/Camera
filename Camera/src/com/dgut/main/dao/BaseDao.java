package com.dgut.main.dao;

import java.io.Serializable;

public interface BaseDao<T> {

	public void save(T bean);
	
	public void delete(T bean);
	
	public void update(T bean);
	
	public T findById(Serializable id);
	
	
}
