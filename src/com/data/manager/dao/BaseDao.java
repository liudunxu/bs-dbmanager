package com.data.manager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.data.manager.cache.DbConfigCache;
import com.data.manager.config.DbConfigEntry;
import com.data.manager.config.DbConfigs;
import com.data.manager.util.HtmlHelper;

//数据库操作抽象类
public abstract class BaseDao implements IDao {

    //静态构造函数
    static{
     // 加载数据库引擎，返回给定字符串名的类
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
   
	//私有函数，获取sql查询结果
	protected QueryResult queryResult(DbConfigEntry dbConfig, String querySql){
		
		QueryResult result = null;
		String connectDB = dbConfig.connectStr;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		try {
			con = DriverManager.getConnection(connectDB, dbConfig.userName,
					dbConfig.password);// 连接数据库对象
			stmt = con.createStatement();// 创建SQL命令对象
			rs = stmt.executeQuery(querySql);// 返回SQL语句查询结果集(集合)
			result = new QueryResult(rs);
		} catch (SQLException e) {
			if(null == result){
				result = new QueryResult();
			}
			System.out.println("***********"+e.getMessage()+"***************");
			result.errorCode = 1;
			result.errorMsg = e.getMessage();
		} finally {
			if (null != stmt) {
				try {
					stmt.cancel();
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (null != con) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	//获取查询数据库表集合的sql语句
	protected abstract String getDbTabsInfoSql();
	//获取查询数据表结构的sql语句
	protected abstract String getTabFieldsSql(String tabName); 
	
	@Override
	// 获取查询结果
	public QueryResult GetQueryResult(DbConfigEntry dbConfig, String querySql) {
		QueryResult result = this.queryResult(dbConfig, querySql);
		return result;
	}

	@Override
	public QueryResult GetDbTabsInfo(DbConfigEntry dbConfig) {
		QueryResult result = null;
		
		//是否在缓存中存在数据
		List<Map<String, String>> tabsCache = DbConfigCache.GetDbTableCache(dbConfig);
		if(tabsCache != null)
		{
			result = new QueryResult();
			result.listResult = tabsCache;
		}else
		{
			//拼接sql查询语句
			String sql = this.getDbTabsInfoSql();
			result = this.GetQueryResult(dbConfig, sql);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("{'databaseType':'");
		sb.append(DbConfigs.GetInstance().GetDbType(dbConfig.dbType));
		sb.append("',data:\"");
		int rowFlag = 0;
		
		if(null!=result && null!= result.listResult){
			
			//由于查询表名只有一列，所以直接获取列名
			String colName = null;
			for(String name:result.listResult.get(0).keySet())
			{
				colName = name;
				break;
			}
			
			for(Map<String, String> entry:result.listResult){
				sb.append("<a href='#' name='hrefTab' tabName='");
				sb.append(entry.get(colName));
				sb.append("'>");
				sb.append(entry.get(colName));
				sb.append("</a>,&nbsp;&nbsp;");
				rowFlag++;
				if(rowFlag%5==0){
					sb.append("</br>");
				}
			}
			
			sb.append("\"}");
			
			result.htmlResult = sb.toString();
			//写入缓存
			DbConfigCache.WriteDbTableCache(dbConfig,result.listResult); 
		}

		return result;
	}

	@Override
	public QueryResult GetTabFields(DbConfigEntry dbConfig, String tabName) {
		QueryResult result = null;
		
		//查询缓存是否存在
		List<Map<String, String>> fieldsCache = DbConfigCache.GetTableFieldsCache(dbConfig,tabName);
		if(fieldsCache != null)
		{
			result = new QueryResult();
			result.listResult = fieldsCache;
		}
		else 
		{
			String sql = this.getTabFieldsSql(tabName);
			result = this.GetQueryResult(dbConfig, sql);
		}
		
		if(null!=result && null!= result.listResult){
			result.htmlResult="<p>表名：<b>"+tabName+"</b></p>"+HtmlHelper.ListToHtml(result.listResult);
			//写入缓存
			DbConfigCache.WriteTableFieldsCache(dbConfig, tabName,result.listResult);
		}
		return result;
	}

}
