package com.data.manager.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import com.data.manager.config.ConstConfig;
import com.data.manager.util.ConvertHelper;
import com.data.manager.util.ConvertResult;

//查询结果，包含错误信息等
public class QueryResult {

	// 默认构造函数
	public QueryResult() {
		errorCode = 0;
	}

	// 构造函数，通过resultSet构建
	public QueryResult(ResultSet rs) {
		this();
		SetFieldByResultSet(rs);
	}

	// 通过ResultSet设置查询结果集字段信息
	@SuppressWarnings("unchecked")
	private void SetFieldByResultSet(ResultSet rs) {
		try {
			
			ConvertResult convertResult = ConvertHelper.ConvertToList(rs);
			if(convertResult.errorCode == 2)
			{
				this.errorCode = 2;
				this.errorMsg = String.format("查询数据量过大，仅显示前%d条信息", ConstConfig.MAXPERQUERYCOUNT);
			}
			this.listResult = (List<Map<String, String>>)convertResult.result;
		} catch (Exception e) {
			this.errorCode = 1;
			this.errorMsg = e.getMessage();
		}
	}

	// 查询结果-html形式
	public String htmlResult;

	// 查询结果-list形式
	public List<Map<String, String>> listResult;

	// 操作码
	public int errorCode;

	// 错误信息
	public String errorMsg;
}
