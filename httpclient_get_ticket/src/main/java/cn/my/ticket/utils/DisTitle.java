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
//import java.util.HashMap;
//
//import org.json.JSONObject;
//
//import com.baidu.aip.ocr.AipOcr;
//
///**
//*  作者 : YuanSir<br>
//*  日期 : 2018年4月20日<br>
//*  时间 : 下午7:22:35<br>
//*  邮箱 : 951813281@qq.com<br>
//*/
//public class DisTitle {
//	//设置APPID/AK/SK
//    public static final String APP_ID = "";
//    public static final String API_KEY = "";
//    public static final String SECRET_KEY = "";
//
//    public static String getTitleDisResult(String filename) {
//        // 初始化一个AipOcr
//        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
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
////        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
//
//        // 调用接口
//        JSONObject res = client.basicGeneral(filename, new HashMap<String, String>());
//        
//        return res.toString(2);
//    }
//}
