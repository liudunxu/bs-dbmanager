package com.data.manager.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.data.manager.config.ConstConfig;

//不同类型转换器，类似于dang包的econvert
public class ConvertHelper {

    private static SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
	// Object转换为string
	public static String ToString(Object obj) {
		if (null == obj) {
			return ConstConfig.NULLDEFAULTVALUE;
		}
		if(obj instanceof Date){
		    return _dateFormat.format(obj);
		}
		
		return obj.toString();
	}

	// 将resultset转换为List
	public static ConvertResult ConvertToList(ResultSet rs) throws Exception {
		ConvertResult result = new ConvertResult();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		// 添加查询限制，查询结果最大不能超过配置的最大值，默认为2000
		int curCount = 0;

		while (rs.next()) {
			Map<String, String> rowData = new HashMap<String, String>();
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i),
						ConvertHelper.ToString(rs.getObject(i)));
			}
			list.add(rowData);
			curCount++;
			if (curCount > ConstConfig.MAXPERQUERYCOUNT) {
				break;
			}
		}
		if (curCount > ConstConfig.MAXPERQUERYCOUNT) {
			result.errorCode = 2;
		}
		result.result = list;
		return result;
	}
}
