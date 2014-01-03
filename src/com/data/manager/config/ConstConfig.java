package com.data.manager.config;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;  
//程序常量配置
public final class ConstConfig {
	
	//每次查询的最大值
	public static int MAXPERQUERYCOUNT = 2000;
	
	//数据库空字符串的默认显示值
	public static String NULLDEFAULTVALUE = "NULL";	
	
	//数据库连接配置
	public static List<String[]> DBCONFIGS;

	static{
	    String resource = "DBConfig.xml";  
	    DBCONFIGS = new ArrayList<String[]>();    
	    try {
	        XMLConfiguration  config = new XMLConfiguration(resource);
            List<HierarchicalConfiguration> fields = 
                    config.configurationsAt("DB");
                for(HierarchicalConfiguration sub : fields){
                    
                String [] db = {
                        sub.getString("[@name]"),
                        sub.getString("[@connectionStr]"),
                        sub.getString("[@type]"),
                        sub.getString("[@username]"),
                        sub.getString("[@password]"),
                };
                DBCONFIGS.add(db);
            }
            
        } catch (ConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
	}
	
	public static void main(String []args){
	    return ;
	}
}
