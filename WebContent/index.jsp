<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.data.manager.config.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>线上数据库连接</title>
<link href="./css/coreStyle.css" type="text/css" rel="stylesheet" />
<script src="./jquery-1.7.js" type="text/javascript"></script>
<script src="./SqlQuery.js" type="text/javascript"></script>
</head>
<body>

	<form id="submitForm" action="ExcelExportServlet" method="post">
		<table id="tabContent" class="mytable">
			<tr>
				<td>数据库名称：</td>
				<td><select id="ddl_Dbs" name="ddl_Dbs">
						<%
							ArrayList<String> dbs = DbConfigs.GetInstance().GetAllDbName();
							for (String dbName : dbs) {
								out.print(" <option value='" + dbName + "'>" + dbName
										+ "</option>");
							}
						%>
				</select></td>
			</tr>

			<tr>
				<td>数据库类型：</td>
				<td><span id="spanDatabaseType"></span></td>
			</tr>
		</table>
		<table class="mytable">
			<tr>
				<td>
					<div id="div_dbInfo" style="display: none"></div>
				</td>
			</tr>
		</table>
		<div id="div_fieldInfo" style="display: none"></div>
		<table class="mytable">
			<tr>
				<td>sql查询语句：</td>
				<td><textarea id="txtarea_Sql" name="txtarea_Sql" cols="100"
						rows="10"></textarea></td>
			</tr>
		</table>

		<div id="div_button">
			<input type="button" id="btnSqlQuery" name="btnSqlQuery" class="btn"
				value="执行sql语句" /> <input type="submit" id="btnExcelOutput"
				name="btnExcelOutput" value="导出结果到excel" class="btn" /> <input
				type="button" id="btnClearResult" name="btnClearResult"
				value="清空查询结果" class="btn" />
		</div>

		<div id="div_result"></div>
	</form>
</body>
</html>