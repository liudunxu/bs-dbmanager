package com.data.manager.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.data.manager.config.*;
import com.data.manager.dao.*;
import com.data.manager.util.HtmlHelper;

/**
 * Servlet implementation class SqlQueryServlet
 */
public class SqlQueryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SqlQueryServlet() {
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
        String sql = request.getParameter("sql");
        String dbName = request.getParameter("dbName");
        String queryType = request.getParameter("type");
        DbConfigEntry dbConfig = DbConfigs.GetInstance().GetDbByName(dbName);
        if (null != dbConfig) {
            IDao dao = SimpleDaoFactory.GetDao(dbConfig);
            if (null != dao) {
                QueryResult result = null;
                if (queryType.equals("sqlQuery")) {
                    result = dao.GetQueryResult(dbConfig, sql);
                    result.htmlResult = HtmlHelper.ListToHtml(result.listResult);
                } else if (queryType.equals("dbTabs")) {
                    result = dao.GetDbTabsInfo(dbConfig);
                } else {
                    result = dao.GetTabFields(dbConfig,
                            request.getParameter("tabName"));
                }
                if (0 == result.errorCode) {
                    response.getWriter().write(result.htmlResult);
                } else if (2 == result.errorCode) {
                    response.getWriter().write(
                            result.errorMsg + "</br>" + result.htmlResult);
                } else {
                    response.getWriter().write(result.errorMsg);
                }
            }
        }
    }

}
