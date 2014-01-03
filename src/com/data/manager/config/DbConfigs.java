package com.data.manager.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * @author liudunxu 数据库配置集合,单例
 */
public class DbConfigs {

	private static DbConfigs _instance;

	// 获取单例
	public static DbConfigs GetInstance() {
		if (_instance == null) {
			_instance = new DbConfigs();
			_instance.Init();
		}
		return _instance;
	}

	private HashMap<String, DbConfigEntry> _configs = new HashMap<String, DbConfigEntry>();

	// 数据配置初始化,根据constConfig类配置初始化
	public void Init() {
		
		for(String[] dbConfig : ConstConfig.DBCONFIGS)
		{
			_configs.put(dbConfig[0], new DbConfigEntry( dbConfig[0],
					dbConfig[1],
					GetDbEnumByString(dbConfig[2]),dbConfig[3], dbConfig[4]));
		}
	}

	// 获取页面显示的所有数据库
	public ArrayList<String> GetAllDbName() {
		ArrayList<String> result = new ArrayList<String>();
		Iterator<Entry<String, DbConfigEntry>> iter = _configs.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Entry<String, DbConfigEntry> entry = iter.next();
			result.add(entry.getKey());
		}
		return result;
	}

	// 根据名称获取数据库详细配置
	public DbConfigEntry GetDbByName(String dbName) {
		if (_configs.containsKey(dbName)) {
			return _configs.get(dbName);
		}

		return null;
	}
	
	//根据配置获取数据库类型
	public DbType GetDbEnumByString(String str)
	{
		if(null == str) return DbType.MySql;//默认为mysql
		str = str.toLowerCase();
		if("sqlserver".equals(str))
			return DbType.SqlServer;
		else if ("mysql".equals(str))
			return DbType.MySql;
		else
			return DbType.Oracle;
	}
	
	// 根据DbType获取数据库类型
	public String GetDbType(DbType type){
		switch (type) {
		case MySql:
			return "Mysql数据库";
		case SqlServer:
			return "SqlServer数据库";
		case Oracle:
			return "Oracle数据库";
		default:
			break;
		}
		
		return "";
	}

}
