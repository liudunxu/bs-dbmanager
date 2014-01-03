var dbMap = {
	"Mysql数据库" : "select * from {tabName} limit 30",
	"SqlServer数据库" : "select top 30 * from {tabName} with(nolock)",
	"Oracle数据库" : "select * from {tabName} where rownum<=30"
};

var SqlQuery = {

	// 页面初始化
	PageInit : function() {
		SqlQuery.PostQueryBiz("dbTabs", function(data) {
			var obj = eval('(' + data + ')');
			$('#spanDatabaseType').html('<b>' + obj.databaseType + '</b>');
			$('#div_dbInfo').html(obj.data);
			$('#div_dbInfo').show();
			SqlQuery.AddTabHrefEvent();
		});

		SqlQuery.EventInit();

	},
	// 事件初始化
	EventInit : function() {

		$('#ddl_Dbs').change(function() {
			SqlQuery.PostQueryBiz("dbTabs", function(data) {
				var obj = eval('(' + data + ')');
				$('#spanDatabaseType').html('<b>' + obj.databaseType + '</b>');
				$('#div_dbInfo').html(obj.data);
				$('#div_fieldInfo').html('');
				SqlQuery.AddTabHrefEvent();
			});
		});

		$('#btnSqlQuery').click(function() {
			SqlQuery.PostQueryBiz("sqlQuery", function(data) {
				document.getElementById('div_result').innerHTML = data;
			});
		});

		$('#btnClearResult').click(function() {
			$('#div_result').html('');
		});

		$('#submitForm').submit(function() {
			if ($('#txtarea_Sql').val() == '') {
				alert("必须输入sql语句");
				return false;
			}
			return true;
		});

	},
	//添加表格选择事件
	AddTabHrefEvent : function() {
		
		$("a[name='hrefTab']").click(function() {
			var tabName = $(this).text();
			var dbSql = dbMap[$('#spanDatabaseType').text()];
			$('#txtarea_Sql').val(dbSql.replace(/{tabName}/g,tabName));
			if (!!tabName && '' != tabName) {
				$.post('SqlQueryServlet', {
					dbName : $('#ddl_Dbs').val(),
					sql : $('#txtarea_Sql').val(),
					type : "tabFields",
					tabName : tabName
				}, function(data) {
					$('#div_fieldInfo').html(data);
					$('#div_fieldInfo').show();
				});
			}
		});
	},

	// 提交查询逻辑
	PostQueryBiz : function(type, callback) {
		$.post('SqlQueryServlet', {
			dbName : $('#ddl_Dbs').val(),
			sql : $('#txtarea_Sql').val(),
			type : type
		}, function(data) {
			callback(data);
		});
	}
};

// 页面事件
$(document).ready(function() {
	SqlQuery.PageInit();
}); 