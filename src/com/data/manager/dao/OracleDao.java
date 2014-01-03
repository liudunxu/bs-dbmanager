package com.data.manager.dao;

//oracle数据库连接器
public class OracleDao extends BaseDao{
	@Override
	protected String getDbTabsInfoSql() {
		return "select concat(owner,concat('.',table_name)) from all_tables where tablespace_name not in ('SYSTEM','SYSAUX')";
	}

	@Override
	protected String getTabFieldsSql(String tabName) {
		// TODO Auto-generated method stub
	    String [] tabStrings = tabName.split("\\.");
	    String processedTabName;
	    if(tabStrings.length >= 2){
	        processedTabName = tabStrings[1];
	    }else{
	        processedTabName = tabName;
	    }
		return "select CoLUMN_NAME,DATA_TYPE,DATA_LENGTH from all_tab_columns where table_name='"+processedTabName+"'";
	}
}
