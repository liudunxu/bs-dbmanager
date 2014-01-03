/**
 * 
 */
package com.data.manager.config;

import java.util.List;

/**
 * @author liudunxu 数据库配置信息
 */
public class DbConfigEntry {

	public DbConfigEntry(){
		
	}
	// 构造函数
	public DbConfigEntry(String displayName, String connectStr, DbType dbType,
			String userName, String password) {
		super();
		this.displayName = displayName;
		this.connectStr = connectStr;
		this.dbType = dbType;
		this.userName = userName;
		this.password = password;
	}
	
	// 页面显示名称
	public String displayName;

	// 连接字符串
	public String connectStr;

	// 数据库类型
	public DbType dbType;

	//连接用户名
	public String userName;
	
	//连接密码
	public String password;
	
	//数据库下的数据表集合
	public List<String> dbTabs;
	
	
}
