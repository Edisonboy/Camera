package com.dgut.main.manager;

import java.io.Serializable;

public interface BaseMng<T> {

	public void save(T bean);
	
	public void delete(T bean);
	
	public void update(T bean);
	
	public T findById(Serializable id);
	
		
}
