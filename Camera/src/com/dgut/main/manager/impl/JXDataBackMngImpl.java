package com.dgut.main.manager.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.main.dao.JXDataBackDao;
import com.dgut.main.entity.JXField;
import com.dgut.main.manager.JXDataBackMng;




@Service
@Transactional
public class JXDataBackMngImpl implements JXDataBackMng {

	@Transactional(readOnly = true)
	public String createTableDDL(String tablename) {
		return dao.createTableDDL(tablename);
	}

	@Transactional(readOnly = true)
	public List<Object[]> createTableData(String tablename) {
		return dao.createTableData(tablename);
	}

	@Transactional(readOnly = true)
	public List<JXField> listFields(String tablename) {
		return dao.listFields(tablename);
	}

	@Transactional(readOnly = true)
	public List<String> listTabels() {
		return dao.listTables();
	}

	@Transactional(readOnly = true)  
    public List<String> listTabels(String catalog) {  
        return dao.listTabels(catalog);  
    }  
	
	
	@Transactional(readOnly = true)
	public List<String> listDataBases() {
		return dao.listDataBases();
	}

	@Transactional(readOnly = true)
	public String getDefaultCatalog() throws SQLException {
		return dao.getDefaultCatalog();
	}
	
	public void setDefaultCatalog(String catalog) throws SQLException{
		 dao.setDefaultCatalog(catalog);
	}

	public Boolean executeSQL(String sql) {
		return dao.executeSQL(sql);
	}

	private JXDataBackDao dao;

	@Autowired
	public void setDao(JXDataBackDao dao) {
		this.dao = dao;
	}

}