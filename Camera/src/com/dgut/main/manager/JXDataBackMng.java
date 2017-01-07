package com.dgut.main.manager;

import java.sql.SQLException;
import java.util.List;

import com.dgut.main.entity.JXField;





public interface JXDataBackMng {
	public List<String> listTabels();
	
	public List<String> listTabels(String catalog);  

	public List<JXField> listFields(String tablename);

	public List<String> listDataBases();

	public String createTableDDL(String tablename);

	public List<Object[]> createTableData(String tablename);
	
	public String getDefaultCatalog()throws SQLException;
	
	public void setDefaultCatalog(String catalog) throws SQLException;

	public Boolean executeSQL(String sql);
}