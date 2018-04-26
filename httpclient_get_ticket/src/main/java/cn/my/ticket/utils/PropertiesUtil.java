////////////////////////////////////////////////////////////////////////////////////////////////
//*******___*********___***************____************************___**********___***********//
//*******\   \******/   /*************|    |**********************|   |********|   |**********//
//********\   \****/   /**************|    |**********************|   |********|   |**********//
//*********\___\__/___/***************|    |**********************|   |********|   |**********//
//*************|   |******************|    |**********************|   |________|   |**********//
//*************|   |******************|    |**********************|    ________    |**********//
//*************|   |******************|    |**********************|   |********|   |**********//	
//*************|   |******************|    |_________*************|   |********|   |**********//	
//*************|   |******************|   		     |************|   |********|   |**********//
//*************|___|******************|______________|************|___|********|___|**********//	
////////////////////////////////////////////////////////////////////////////////////////////////

package cn.my.ticket.utils;

import java.util.Properties;

/**
*  作者 : YuanSir
*  日期 : 2018年4月22日
*  时间 : 下午5:01:08
*  邮箱 : 951813281@qq.com
*/
public final class PropertiesUtil {
	
	private static Properties station1Prop;
	private static Properties station2Prop;
	private static Properties userProp;
	
	static{
		station1Prop = new Properties();
		station2Prop = new Properties();
		userProp = new Properties();
		try {
			station1Prop.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("stations1.properties"));
			station2Prop.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("stations2.properties"));
			userProp.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("user.properties"));
		} catch (Exception e) {
			throw new RuntimeException("用户配置文件读取失败。。。。");
		}
	}
	
	public static Properties getStation1Properties(){
		return station1Prop;
	}
	
	public static Properties getStation2Properties(){
		return station2Prop;
	}
	
	public static Properties getUserProperties(){
		return userProp;
	}
}
