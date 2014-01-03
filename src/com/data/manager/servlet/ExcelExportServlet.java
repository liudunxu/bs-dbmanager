package com.data.manager.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.data.manager.config.DbConfigEntry;
import com.data.manager.config.DbConfigs;
import com.data.manager.dao.IDao;
import com.data.manager.dao.QueryResult;
import com.data.manager.dao.SimpleDaoFactory;
import com.data.manager.util.ExcelHelper;

/**
 * Servlet implementation class ExcelExportServlet
 */
public class ExcelExportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExcelExportServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String sql = request.getParameter("txtarea_Sql");
		String dbName = request.getParameter("ddl_Dbs");

		DbConfigEntry dbConfig = DbConfigs.GetInstance().GetDbByName(dbName);
		if (null != dbConfig) {
			IDao dao = SimpleDaoFactory.GetDao(dbConfig);
			if (null != dao) {
				QueryResult result = dao.GetQueryResult(dbConfig, sql);
				
				OutputStream out = null;
				if (0==result.errorCode) {
					 out = response.getOutputStream();
					   response.reset();
					   response.setContentType("text/html;charset="
					     + "utf-8");
					   String filename2 = "result.xls";
					   response.setHeader("content-disposition", "attachment; filename="
					     + filename2);

					   out.write(ExcelHelper.ListToHtml(result.listResult).getBytes("utf-8"));
					   
				} else {
					response.getWriter().write(result.errorMsg);
				}
			}
		}

	}

}
