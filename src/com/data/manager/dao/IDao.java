package com.data.manager.dao;

import com.data.manager.config.*;

//数据库连接接口
public interface IDao {
	//根据sql语句获取数据集
	QueryResult GetQueryResult(DbConfigEntry dbConfig,String querySql); 
	//获取数据库表结构信息
	QueryResult GetDbTabsInfo(DbConfigEntry dbConfig);
	//获取数据表字段信息
	QueryResult GetTabFields(DbConfigEntry dbConfig,String tabName);
}
