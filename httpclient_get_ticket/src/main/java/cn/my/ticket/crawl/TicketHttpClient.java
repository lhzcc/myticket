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

package cn.my.ticket.crawl;
import java.io.File;
import java.io.FileOutputStream;
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
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.my.ticket.bean.Passenger;
import cn.my.ticket.bean.TicketInfo;
import cn.my.ticket.utils.PropertiesUtil;

public class TicketHttpClient {
	
	private CookieStore cookieStore = null;
    private BasicClientCookie cookie = null;
    private int count = 0;//验证码重试次数
    private Passenger passenger = new Passenger();//乘车人
    private TicketInfo ticketInfo = null;//存储需要购买的票的信息
    private Scanner scan = new Scanner(System.in);
    private RequestConfig requestConfig = RequestConfig.custom()
    		.setConnectTimeout(15*1000)
    		.setSocketTimeout(15*1000)
    		.build();
    
    //创建httpclient
  	private  CloseableHttpClient ticketClient = HttpClients.custom()
  			.setDefaultRequestConfig(requestConfig)//设置超时
            .setDefaultCookieStore(cookieStore)//设置Cookie
            .build();
  	 
	@SuppressWarnings({ "deprecation" })
	public void buyTrainTicket() throws ClientProtocolException, IOException, CloneNotSupportedException, InterruptedException{
		//定义验证码的坐标数组
		String[] valids = 
			{"","34,44","107,47","179,45","254,45","38,120","109,117","181,116","253,115"};
		System.out.println("-->查询余票信息...");
		List<TicketInfo> trainList = getTrainList();
		if(trainList==null){
			System.out.println("---->查询失败-_-");
			return;
		}
		System.out.println("---->查询成功^_^");
		System.out.println("车次\t出发站\t目的站\t出发时间\t到达时间\t历时\t日期\t\t无座\t硬座\t软座\t硬卧\t软卧\t特等座\t一等座\t二等座\t高级软卧");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
		String train_code = (String) PropertiesUtil.getUserProperties().get("train");
		for (TicketInfo Info : trainList) {
			if(Info.getStation_train_code().equals(train_code))
				ticketInfo = Info;
			System.out.println(Info);
			System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
		}
//		System.out.println("------>请选择票种的序号，从上往下(1-n)：");
//		int ticketNum = scan.nextInt();
//		ticketInfo = trainList.get(ticketNum-1);
		if(ticketInfo==null){
			System.out.println("------>车次选择失败,可能是您的车次填写错误-_-!!!");
			return;
		}
		System.out.println("------>车次选择成功^O^,你选择的是 :  ");
		System.out.println("车次\t出发站\t目的站\t出发时间\t到达时间\t历时\t日期\t\t无座\t硬座\t软座\t硬卧\t软卧\t特等座\t一等座\t二等座\t高级软卧");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println(ticketInfo);
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
////////////////////////////////////////////////////////////////////////////////////////////////////////
		boolean flag = true;
		while(flag){
			//2.带上JESSIONID 获取验证码
			CloseableHttpResponse validResp = getSubmit("https://kyfw.12306.cn/passport/captcha/captcha-image?login_site=E&module=login&rand=sjrand&0.3724751625887286");
			//3.
			//添加JSESSIONID的Cookie
			if(count==0){
				Header[] headers = validResp.getHeaders("Set-Cookie");
				String JSESSIONID = headers[0].getValue().split("=")[1].split(";")[0];
				cookieStore = new BasicCookieStore();
			    cookie = new BasicClientCookie("JSESSIONID",JSESSIONID);
			    cookie.setVersion(0);
			    cookie.setDomain("kyfw.12306.cn");
			    cookie.setPath("/passport");
			    cookieStore.addCookie(cookie);
			}
		    
			count++;
			//1）获取新的setcookie，并更新cookie
			setCookie(validResp);
	
			//2）将验证码写到本地
			System.out.println("-------->获取验证码...");
			InputStream validCodeInputStream = validResp.getEntity().getContent();
			FileOutputStream fos = new FileOutputStream(new File("验证码.jpg"));
			byte[] buf = new byte[1024];
			int len = 0;
			while((len=validCodeInputStream.read(buf))!=-1){
				fos.write(buf, 0, len);
			}
			fos.close();
			System.out.println("----------->获取验证码成功^0^");
			System.out.println("------------->请手动输入验证码(上面1-4,下面5-8),多个用英文逗号隔开");
			
			//使用百度api识别验证码,百度识别12306的图片效果太差，故没用(但是标题识别的准确率达90%)
//			String validInput = ValidImage.getAnswer();
//			if(validInput==null) continue;

			//人工识别验证码
			String validInput = scan.next();
			
			String validStr = "";
			String[] validPictureLocations = validInput.split(",");
			
			for(int i=0;i<validPictureLocations.length;i++){
				if(validPictureLocations.length-1==i)
					validStr += valids[Integer.parseInt(validPictureLocations[i])];
				else
					validStr += valids[Integer.parseInt(validPictureLocations[i])] + ",";
			}
			CloseableHttpResponse validResultResp = postSubmit("https://kyfw.12306.cn/passport/captcha/captcha-check", "answer="+URLEncoder.encode(validStr)+"&login_site=E&rand=sjrand");
			String validResultStr = EntityUtils.toString(validResultResp.getEntity());
			JSONObject validJsonObj = JSONObject.parseObject(validResultStr);
			Object valid_result_message = validJsonObj.get("result_message");
			Object valid_result_code = validJsonObj.get("result_code");
			System.out.println("-------------------->验证码" + valid_result_message);
			if(!"4".equals(valid_result_code)){
				System.out.println("---------------->重新获取验证码...");
				Thread.sleep(2*1000);
			}else
				flag = false;
		}
////////////////////////////////////////////////////校验账号和密码//////////////////////////////////////////////////////////////		
		System.out.println("---------------->校验账号和密码...");
		CloseableHttpResponse loginResp = postSubmit("https://kyfw.12306.cn/passport/web/login", 
				"username="+PropertiesUtil.getUserProperties().getProperty("username")
				+"&password="+PropertiesUtil.getUserProperties().getProperty("password")+"&appid=otn");
		String loginResultStr = EntityUtils.toString(loginResp.getEntity());
		JSONObject loginJsonObj = JSONObject.parseObject(loginResultStr);
		Object login_result_message = loginJsonObj.get("result_message");
		Object login_result_code = loginJsonObj.get("result_code");
		System.out.println("--------------------->" + login_result_message);
		if((Integer)login_result_code!=0) return ;
///////////////////////////////////////////////////获取token///////////////////////////////////////////////////////////////		
		//获取新的token 
			//-->  login:https://kyfw.12306.cn/passport/web/login
			//-->  uamtk：https://kyfw.12306.cn/passport/web/auth/uamtk 需要上一步返回的Set-Cookie: uamtk=S83umYIHP2V22FE5RzfbkJfsNaZTyQEZQ37xtR0aJe01p1110; Path=/passport
			//-->  uamauthclient：https://kyfw.12306.cn/otn/uamauthclient 
			//实现这三步才算真正登陆成功
		String uamtk = loginJsonObj.getString("uamtk");

		cookie.setAttribute("uamtk", uamtk);
		CloseableHttpResponse uamtkResp = postSubmit("https://kyfw.12306.cn/passport/web/auth/uamtk", "appid=otn");
		String uamtkResultStr = EntityUtils.toString(uamtkResp.getEntity());
		JSONObject uamtkResultStrJson = JSONObject.parseObject(uamtkResultStr);
		String uamtkresult_message = uamtkResultStrJson.getString("result_message");
		System.out.println("----------------------->第二步" + uamtkresult_message);
		JSONObject uamtkJsonObj = JSONObject.parseObject(uamtkResultStr);
		String newapptk = uamtkJsonObj.getString("newapptk");
		
		CloseableHttpResponse tkResp = postSubmit("https://kyfw.12306.cn/otn/uamauthclient", "tk="+newapptk);
		String tkRetsult = EntityUtils.toString(tkResp.getEntity());
		JSONObject tkRetsultJson = JSONObject.parseObject(tkRetsult);
		String tkresult_message = tkRetsultJson.getString("result_message");
		System.out.println("------------------------->第三步" + tkresult_message);
		Thread.sleep(1*1000);
/////////////////////////////////////////////验证是否登录成功/////////////////////////////////////////////////////////
//		HttpGet isLoginHttpGet = getHttpGet("https://kyfw.12306.cn/otn/modifyUser/initQueryUserInfo");
//		setCookie(tkResp);
//		CloseableHttpResponse isLoginResp = ticketClient.execute(isLoginHttpGet);
//		String isLoginStr = EntityUtils.toString(isLoginResp.getEntity());
//		System.out.println(isLoginStr);
//////////////////////////////////////////////////下单/////////////////////////////////////////////////////////		
//1.https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest			POST
		postSubmit("https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest", 
			"secretStr="+ticketInfo.getSecretStr()+"&train_date="+ticketInfo.getStart_train_date()
			+"&tour_flag=dc&purpose_codes=ADULT&query_from_station_name="
			+PropertiesUtil.getUserProperties().getProperty("startSation")
			+"&query_to_station_name="+PropertiesUtil.getUserProperties().getProperty("endSation")
			+"&undefined");	
//2.https://kyfw.12306.cn/otn/confirmPassenger/initDc	POST
		CloseableHttpResponse initDcrResp = postSubmit("https://kyfw.12306.cn/otn/confirmPassenger/initDc", "_json_att=");	
		String confirmPassengerResult = EntityUtils.toString(initDcrResp.getEntity());

//3. 获取 globalRepeatSubmitToken 	https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs				POST
		String REPEAT_SUBMIT_TOKEN = matcherStr("globalRepeatSubmitToken = '(\\w+)'",confirmPassengerResult);
		if(REPEAT_SUBMIT_TOKEN==null)	return;
		System.out.println("-------------------------->获取乘车人信息...");
		CloseableHttpResponse getPassengerDTOsResp = postSubmit("https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs", "_json_att=&REPEAT_SUBMIT_TOKEN="+REPEAT_SUBMIT_TOKEN);	
		String getPassengerDTOsResult = EntityUtils.toString(getPassengerDTOsResp.getEntity());
		//获取乘车人的信息
		JSONObject getPassengerDTOsResultJson = JSONObject.parseObject(getPassengerDTOsResult);
		JSONObject getPassengerDTOsData = (com.alibaba.fastjson.JSONObject) getPassengerDTOsResultJson.get("data");
		JSONArray normal_passengers = getPassengerDTOsData.getJSONArray("normal_passengers");
		
		for (Object obj : normal_passengers) {
			JSONObject jsonObj = (JSONObject) obj;
			String passenger_name = jsonObj.getString("passenger_name");
			if(PropertiesUtil.getUserProperties().get("realname").equals(passenger_name)){
				passenger.setPassenger_name(passenger_name);
				passenger.setMobile_no(jsonObj.getString("mobile_no"));
				passenger.setPassenger_id_no(jsonObj.getString("passenger_id_no"));
				passenger.setPassenger_id_type_code(jsonObj.getString("passenger_id_type_code"));
				break;
			}
		}
		if(passenger==null){
			System.out.println("---------------------->获取乘车人信息失败-_-!!");
			return;
		}
		System.out.println("----------------------------->乘车人信息获取成功^O^");
		System.out.println("乘车人姓名\t\t手机号码\t\t\t证件号");
		System.out.println(passenger);
		Thread.sleep(1*1000);
//4.https://kyfw.12306.cn/otn/confirmPassenger/checkOrderInfo         POST
//1(seatType),0,1(车票类型:ticket_type_codes),张三(passenger_name),
//1(证件类型:passenger_id_type_code),320xxxxxx(passenger_id_no),151xxxx(mobile_no),N		
		String passengerTicketStr = PropertiesUtil.getUserProperties().getProperty("seatType")
			+",0,1,"+passenger.getPassenger_name()
			+","+passenger.getPassenger_id_type_code()
			+","+passenger.getPassenger_id_no()
			+","+passenger.getMobile_no()+",N";
//oldPassengerStr这个参数的组合方式为：张三(passenger_name),
//	1(证件类型:passenger_id_type_code),320xxxxxx(passenger_id_no),1_
		String oldPassengerStr = passenger.getPassenger_name()+","
				+passenger.getPassenger_id_type_code()+","
				+passenger.getMobile_no()+",1_";
		System.out.println("------------------>开始校验订单信息...");
		CloseableHttpResponse checkOrderInfoResp =
				postSubmit("https://kyfw.12306.cn/otn/confirmPassenger/checkOrderInfo",
				"cancel_flag=2&bed_level_order_num=000000000000000000000000000000&passengerTicketStr="
				+passengerTicketStr
				+"&oldPassengerStr="+oldPassengerStr
				+"&tour_flag=dc&randCode=&whatsSelect=1&_json_att=&REPEAT_SUBMIT_TOKEN="
				+REPEAT_SUBMIT_TOKEN);	
		
		String checkOrderInfoResult = EntityUtils.toString(checkOrderInfoResp.getEntity());
		JSONObject  checkOrderInfoJson = (JSONObject) JSONObject.parseObject(checkOrderInfoResult);
		JSONObject checkOrderInfoData = (JSONObject) checkOrderInfoJson.get("data");
		String checkOrderInfoMessage =  checkOrderInfoJson.getString("messages");
		Boolean checkOrderInfoSubmitStatus = checkOrderInfoData.getBoolean("submitStatus");
		if(checkOrderInfoSubmitStatus)
			System.out.println("----------------------->订单信息校验成功^0^");
		else{
			System.out.println("----------------------->订单信息校验失败-_-!!"+checkOrderInfoMessage);
			return;
		}
			
//5.https://kyfw.12306.cn/otn/confirmPassenger/getQueueCount
//获取车票信息，排队人数。这个接口一直访问失败，找不到原因。
//		String time = matcherStr("'time':(\\d{13})",confirmPassengerResult);
//		String date = new Date(Long.parseLong(time)).toString();
//		String train_date = (date.toString()+"+0800 (中国标准时间)").replace("00:00:00 CST 2018", "2018 00:00:00 GMT");
//		String train_no = matcherStr("'train_no':'(\\w+)'",confirmPassengerResult);
//		String station_train_code = matcherStr("'station_train_code':'(\\w+)'",confirmPassengerResult);
		String seat_types = matcherStr("'seat_types':'(\\w+)'",confirmPassengerResult);
//		String from_station_telecode = matcherStr("'from_station_telecode':'(\\w+)'",confirmPassengerResult);
//		String to_station_telecode = matcherStr("'to_station_telecode':'(\\w+)'",confirmPassengerResult);
		String leftTicket = matcherStr("'leftTicketStr':'(((\\w)(%)?)+)'",confirmPassengerResult);
		String purpose_codes = matcherStr("'purpose_codes':'(\\w+)'",confirmPassengerResult);
		String train_location = matcherStr("'train_location':'(\\w+)'",confirmPassengerResult);
//		
//		String param = "train_date="+train_date+"&train_no="+train_no
//				+"&stationTrainCode="+station_train_code
//				+"&seatType="+seat_types+"&fromStationTelecode="+from_station_telecode
//				+"&toStationTelecode="+to_station_telecode+"&leftTicket="+leftTicket
//				+"&purpose_codes="+purpose_codes+"&train_location="+train_location
//				+"&_json_att=&REPEAT_SUBMIT_TOKEN="+REPEAT_SUBMIT_TOKEN;
//		
//		CloseableHttpResponse getQueueCountResp = postSubmit("https://kyfw.12306.cn/otn/confirmPassenger/getQueueCount", param);	
//		
//		String getQueueCountResult = EntityUtils.toString(getQueueCountResp.getEntity());
//		System.out.println(getQueueCountResult);
		
//6.https://kyfw.12306.cn/otn/confirmPassenger/confirmSingleForQueue
//			'key_check_isChange':'(\\w+)'
		System.out.println("------------------------->正在将订单加入队列中...");
		boolean confirmSingleForQueue = true;
		while(confirmSingleForQueue){
			String key_check_isChange = matcherStr("'key_check_isChange':'(\\w+)'",confirmPassengerResult);
			String confirmSingleForQueueParam = "passengerTicketStr="+passengerTicketStr+"&oldPassengerStr="+oldPassengerStr+"&randCode="
							+"&purpose_codes="+purpose_codes+"&key_check_isChange="+key_check_isChange
							+"&leftTicketStr="+leftTicket+"&train_location=" + train_location
							+"&choose_seats="+"&seatDetailType="+seat_types
							+"&whatsSelect=1&roomType=00&dwAll=N&_json_att=&REPEAT_SUBMIT_TOKEN="+REPEAT_SUBMIT_TOKEN;
			CloseableHttpResponse confirmSingleForQueueResp = postSubmit("https://kyfw.12306.cn/otn/confirmPassenger/confirmSingleForQueue",
					confirmSingleForQueueParam);	
			
			String confirmSingleForQueueResult = EntityUtils.toString(confirmSingleForQueueResp.getEntity());
			JSONObject  confirmSingleForQueueJson = (JSONObject) JSONObject.parseObject(confirmSingleForQueueResult);
			JSONObject confirmSingleForQueueData = (JSONObject) confirmSingleForQueueJson.get("data");
			String confirmSingleForQueueMessage =  confirmSingleForQueueJson.getString("messages");
			boolean confirmSingleForQueueSubmitStatus = confirmSingleForQueueData.getBoolean("submitStatus");
			if(confirmSingleForQueueSubmitStatus){
				confirmSingleForQueue = false;
				System.out.println("-------------------------->订单加入队列成功^0^");
			}
			else{
				System.out.println("-------------------------->订单订单加入队列失败-_-!!"+confirmSingleForQueueMessage);
				System.out.println("-------------------------->正在重试..."+confirmSingleForQueueMessage);
				Thread.sleep(1*1000);
			}
		}
//7.https://kyfw.12306.cn/otn/confirmPassenger/queryOrderWaitTime?random=1524460038057&tourFlag=dc&_json_att=&REPEAT_SUBMIT_TOKEN=dc3744704472a867dfd09c55f4d143a9
		String orderId = null;
		do{
			CloseableHttpResponse queryOrderWaitTimeResp = getSubmit("https://kyfw.12306.cn/otn/confirmPassenger/queryOrderWaitTime?random="+System.currentTimeMillis()+"&tourFlag=dc&_json_att=&REPEAT_SUBMIT_TOKEN="+REPEAT_SUBMIT_TOKEN);	
			String queryOrderWaitTimeResult = EntityUtils.toString(queryOrderWaitTimeResp.getEntity());
			JSONObject data = (JSONObject) JSONObject.parseObject(queryOrderWaitTimeResult).get("data");
			orderId = (String) data.get("orderId");
			String waitTime = data.getString("waitTime");
			int waitTimeInt = Integer.parseInt(waitTime);
			if(waitTimeInt>100){
				Thread.sleep(10*1000);
			}else if(waitTimeInt<100&&waitTimeInt>0){
				Thread.sleep(2*1000);
			}else if(orderId!=null&&waitTimeInt<0){
				System.out.println("---------------------------->下单成功，请在其他设备进行付款^O^");
				break;
			}
			System.out.println("-------------------------->正在排队,还需等待  : "+waitTime +"秒");
		}while(orderId==null);
//8.https://kyfw.12306.cn/otn/confirmPassenger/resultOrderForDcQueue
//这个好像需要付款完成才能调用成功
//		CloseableHttpResponse resultOrderForDcQueueResp = postSubmit("https://kyfw.12306.cn/otn/confirmPassenger/resultOrderForDcQueue",
//				"orderSequence_no="+orderId+"_json_att=&REPEAT_SUBMIT_TOKEN="+REPEAT_SUBMIT_TOKEN);	
//		
//		String resultOrderForDcQueueResult = EntityUtils.toString(resultOrderForDcQueueResp.getEntity());
//		JSONObject data = (JSONObject) JSONObject.parseObject(resultOrderForDcQueueResult).get("data");
//		boolean submitStatus = (boolean) data.get("submitStatus");
//		if(submitStatus)
//			System.out.println("------>下单成功，请在其他设备进行付款^O^。。。。。。");
//		else
//			System.out.println("------>下单失败。。。请重试。。。。-_-!");
		
	}
	
	/**
	 * @return 返回响应头信息
	 */
	private void setCookie(CloseableHttpResponse response) throws ClientProtocolException, IOException{
		//获取相应的cookies信息
		Header[] validSetCookie = response.getHeaders("Set-Cookie");
		for (Header header : validSetCookie) {
			String value = header.getValue().split(";")[0];
			String name = value.substring(0, value.indexOf("="));
			String value2 = value.substring(value.indexOf("=")+1);
			if("JSESSIONID".equals(name))
				cookie.setValue(value2);
			else
				cookie.setAttribute(name, value2);
		}		
	}
	
	/**
	 * 给定匹配规则和源字符串获取目标字符串
	 * @param reg 匹配规则
	 * @param sourceStr 源字符串
	 * @return 目标字符串
	 */
	public String matcherStr(String reg,String sourceStr){
		//从字符串中提取指定的字符串
		String s=sourceStr;
		//书写正则表达式
		String regex=reg;
		//将正则表达式转成正则对象
		Pattern pattern =Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s);
		if(matcher.find()){
			return matcher.group(1);
		}
		return null;
	}
	
	/**
	 * @return 响应信息
	 * @param url 访问的url地址
	 * @param entity post提交的实体内容	
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	private CloseableHttpResponse postSubmit(String url,String entity) throws ClientProtocolException, IOException{
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
		httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
		httpPost.addHeader("Accept", "*/*");
		//构造实体内容
		StringEntity sEntity = new StringEntity(entity,"utf-8");
		// 发送Json格式的数据请求
		httpPost.setEntity(sEntity);
		CloseableHttpResponse response = ticketClient.execute(httpPost);
		if(count!=0)
			setCookie(response);
		return response;
	}
	
	/**
	 * @return 响应信息
	 * @param url 访问的url地址
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	private CloseableHttpResponse getSubmit(String url) throws ClientProtocolException, IOException{
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
		httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpGet.addHeader("Accept-Encoding", "gzip, deflate, br");
		httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
		httpGet.addHeader("Accept", "*/*");
		CloseableHttpResponse response = ticketClient.execute(httpGet);
		if(count!=0)
			setCookie(response);
		return response;
	}
	
	/**
	 * 获取当日所有列车的票数
	 * @param result 列车信息集合
	 * @return
	 */
	public List<TicketInfo> getTrainList() throws ClientProtocolException, IOException{
		//创建httpclient
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		//创建get请求
		HttpGet hg = new HttpGet("https://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date="
		+PropertiesUtil.getUserProperties().getProperty("date")
		+"&leftTicketDTO.from_station="+PropertiesUtil.getStation1Properties().getProperty(PropertiesUtil.getUserProperties().getProperty("startSation"))
		+"&leftTicketDTO.to_station="+PropertiesUtil.getStation1Properties().getProperty(PropertiesUtil.getUserProperties().getProperty("endSation"))
		+"&purpose_codes=ADULT");
		//执行get请求，返回响应信息
		CloseableHttpResponse response = closeableHttpClient.execute(hg);
		//获取实体内容
		HttpEntity entity = response.getEntity();
		//将实体内容转成String方便处理
		String jsonStr = EntityUtils.toString(entity);
		
		return parseJson(jsonStr);
	}
	
	
	/**
	 * 获取当日所有列车的的信息
	 * @param content 待处理的json字符串
	 * @return
	 */
	private List<TicketInfo> parseJson(String content){
		JSONObject contentDetail = JSONObject.parseObject(content);
		JSONObject dataJson =  (JSONObject) contentDetail.get("data");
		JSONArray ticketDetail = (JSONArray) dataJson.get("result");
		
		List<TicketInfo> ticketInfoList = new ArrayList<TicketInfo>();
		for (Object obj : ticketDetail) {
			String ticket = (String) obj;
			
			String[] trains = ticket.split("\\|");
			
			TicketInfo ticketInfo  = new TicketInfo();
			
			ticketInfo.setStation_train_code(trains[3]);
			ticketInfo.setSecretStr(trains[0]);
			ticketInfo.setStart_station(trains[4]);
			ticketInfo.setEnd_station(trains[5]);
			ticketInfo.setFrom_station(trains[6]);
			ticketInfo.setTo_station(trains[7]);
			ticketInfo.setStart_time(trains[8]);
			ticketInfo.setArrive_time(trains[9]);
			ticketInfo.setUse_time(trains[10]);
			ticketInfo.setStart_train_date(trains[13]);
			
			Map<String,String> seat = new HashMap<String,String>();
			seat.put("硬卧", "".equals(trains[28])?"*":trains[28]);
			seat.put("软座", "".equals(trains[24])?"*":trains[24]);
			seat.put("硬座", "".equals(trains[29])?"*":trains[29]);
			seat.put("无座", "".equals(trains[26])?"*":trains[26]);
			seat.put("商务特等座", "".equals(trains[32])?"*":trains[32]);
			seat.put("一等座", "".equals(trains[31])?"*":trains[31]);
			seat.put("二等座", "".equals(trains[30])?"*":trains[30]);
			seat.put("高级软卧", "".equals(trains[21])?"*":trains[21]);
			seat.put("软卧", "".equals(trains[23])?"*":trains[23]);
			seat.put("动卧", "".equals(trains[33])?"*":trains[33]);
			ticketInfo.setSeat(seat);
			
			ticketInfoList.add(ticketInfo);
		}
		return ticketInfoList;
	}
}
