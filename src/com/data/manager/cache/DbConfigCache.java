/**
 * 
 */
package com.data.manager.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.data.manager.config.DbConfigEntry;

/**
 * @author liudunxu 数据库信息缓存
 */
public class DbConfigCache {

	private static Map<String, List<Map<String, String>>> dbTabsMap = new HashMap<String, List<Map<String, String>>>();
	private static Map<String, List<Map<String, String>>> tabFieldsMap = new HashMap<String, List<Map<String, String>>>();

	// 获得数据库表集合缓存
	public static List<Map<String, String>> GetDbTableCache(
			DbConfigEntry dbConfig) {
		return dbTabsMap.get(dbConfig.displayName);
	}

	// 写入数据库表集合缓存
	public static void WriteDbTableCache(DbConfigEntry dbConfig,
			List<Map<String, String>> data) {
		dbTabsMap.put(dbConfig.displayName, data);
	}

	// 获得表结构缓存
	public static List<Map<String, String>> GetTableFieldsCache(
			DbConfigEntry dbConfig,String tabName) {
		return tabFieldsMap.get(dbConfig.displayName+tabName);
	}

	// 写入表结构缓存
	public static void WriteTableFieldsCache(DbConfigEntry dbConfig,String tabName,
			List<Map<String, String>> data) {
		tabFieldsMap.put(dbConfig.displayName+tabName, data);
	}

}
