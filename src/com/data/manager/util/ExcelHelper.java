package com.data.manager.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//excel操作工具
public class ExcelHelper {
    private final static String HEADER = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n<title>Datos</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\"/></head><body>";
    private final static String FOOTER = "</body></html>";

    private static String escapeSpecialChar(String orginStr){
        if(null == orginStr) return "";
        
        return orginStr.replace("<","&lt;").replace(">", "&gt;");
    }
    
    // 根据list<map<String,String>>生成html文件格式
    public static String ListToHtml(List<Map<String, String>> list) {
        
        if (null == list || 0 == list.size()) {
            return "查询不到任何数据！！";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<Table border='1'><TR>");
        List<String> columnList = new ArrayList<String>();
        
        for (String key : list.get(0).keySet()) {
            columnList.add(key);
            sb.append( "<TD>" );
            sb.append( escapeSpecialChar(key));
            sb.append( "</TD>" );
        }
        sb.append("</TR>");
        
        for (Map<String, String> entry : list) {
            sb.append("<TR>");
            for (String key : columnList) {
                sb.append("<TD>");
                sb.append(escapeSpecialChar(entry.get(key)));
                sb.append("</TD>");
            }
            sb.append("</TR>");
        }
        
        sb.append( "</table>" );
        return HEADER+sb.toString()+FOOTER;
    }

}
