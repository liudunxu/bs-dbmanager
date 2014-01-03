package com.data.manager.dao;

//sqlserver数据库
public class SqlServerDao extends BaseDao {
	@Override
	protected String getDbTabsInfoSql() {
		return "select name from sysobjects where xtype='U'";
	}

	@Override
	protected String getTabFieldsSql(String tabName) {
		return "SELECT syscolumns.name as 列名,systypes.name as 类型,syscolumns.isnullable as 可空,syscolumns.length FROM syscolumns, systypes WHERE syscolumns.xusertype = systypes.xusertype AND syscolumns.id = object_id('"+tabName+"')";
	}
}
