package com.data.manager.dao;

//mysql数据库连接器
public class MySqlDao extends BaseDao {

    @Override
	protected String getDbTabsInfoSql() {
		return "show tables;";
	}

	@Override
	protected String getTabFieldsSql(String tabName) {
		return "desc "+tabName;
	}

}
