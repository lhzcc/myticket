package cn.my.ticket.utils;
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

//package cn.ylh.ticket.utils;
//
//import java.io.IOException;
//import java.util.HashMap;
//
//import org.json.JSONObject;
//
//import com.baidu.aip.imageclassify.AipImageClassify;
//import com.baidu.aip.util.Util;
//
///**
//*  作者 : YuanSir
//*  日期 : 2018年4月24日
//*  时间 : 上午9:48:05
//*  邮箱 : 951813281@qq.com
//*/
//public class DisPicture {
//	private DisPicture(){}
//	
//	public static final String APP_ID = "";
//    public static final String API_KEY = "";
//    public static final String SECRET_KEY = "";
//	
//	public static String getDisResult(String filename) throws IOException{
//		 // 初始化一个AipImageClassifyClient
//        AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
//
//        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//
//        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
////        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
////        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
//
//        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
//        // 也可以直接通过jvm启动参数设置此环境变量
////        System.setProperty("aip.log4j.conf", "log4j.properties");
//
//        // 传入可选参数调用接口
//        HashMap<String, String> options = new HashMap<String, String>();
//        
//        // 参数为本地图片路径
////        String image = "test.jpg";
////        JSONObject res = client.advancedGeneral(image, options);
////        System.out.println(res.toString(2));
//
//        // 参数为本地图片二进制数组
//        byte[] file = Util.readFileByBytes(filename);
//        JSONObject res = client.advancedGeneral(file, options);
//        
//        return res.toString(2);
//	}
//	
//}
