package com.data.manager.dao;

import com.data.manager.config.*;

//数据操作层工厂
public class SimpleDaoFactory {

	public static BaseDao GetDao(DbConfigEntry dbConfig) {
		if (null == dbConfig) {
			return null;
		}

		switch (dbConfig.dbType) {
		case MySql:
			return new MySqlDao();
		case SqlServer:
			return new SqlServerDao();
		case Oracle:
			return new OracleDao();
		}

		return null;
	}
}
