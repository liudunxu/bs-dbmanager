package com.data.manager.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//html工具方法，用于将后台数据结构转换为html
public class HtmlHelper {

    // 忽略html特殊字符
    public static String EscapeSpecialHtml(String str) {
        if (null == str)
            return "";
        return str.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    // 生成html表头表尾，带编码格式
    public static String HtmlWrapper(String rawHtml) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n<title>Datos</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\"/></head><body>");
        sb.append(rawHtml);
        sb.append("</body></html>");
        return sb.toString();
    }

    // 根据list<map<String,String>>生成html表格
    public static String ListToHtml(List<Map<String, String>> list) {
        if (null == list || 0 == list.size()) {
            return "查询不到任何数据！！";
        }

        StringBuilder sb = new StringBuilder();
        List<String> columnList = new ArrayList<String>();
        sb.append("<table border='1'>");
        sb.append("<tr>");
        for (String key : list.get(0).keySet()) {
            sb.append("<th>");
            sb.append(EscapeSpecialHtml(key));
            sb.append("</th>");
            columnList.add(key);
        }

        for (Map<String, String> entry : list) {
            sb.append("<tr>");
            for (String key : columnList) {
                sb.append("<td>");
                sb.append(EscapeSpecialHtml(entry.get(key)));
                sb.append("</td>");
            }
            sb.append("</tr>");
        }

        sb.append("</table>");
        return sb.toString();

    }

    // 根据resultset生成html表格
    public static String ResultSetToHtml(ResultSet rs) throws SQLException {

        if (null == rs) {
            return "";
        }
        ResultSetMetaData md;
        StringBuilder sb = new StringBuilder();

        md = rs.getMetaData();
        int count = md.getColumnCount();
        sb.append("<table border='1'>");
        sb.append("<tr>");
        for (int i = 1; i <= count; i++) {
            sb.append("<th>");
            sb.append(md.getColumnLabel(i));
            sb.append("</th>");
        }
        sb.append("</tr>");
        while (rs.next()) {
            sb.append("<tr>");
            for (int i = 1; i <= count; i++) {
                sb.append("<td>");
                sb.append(EscapeSpecialHtml(rs.getString(i)));
            }
            sb.append("</tr>");
        }
        sb.append("</table>");

        return sb.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
