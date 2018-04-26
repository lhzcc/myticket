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
//import java.util.ArrayList;
//import java.util.List;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//
//import cn.ylh.ticket.bean.ImageBean;
//
///**
//*  作者 : YuanSir
//*  日期 : 2018年4月24日
//*  时间 : 下午8:58:38
//*  邮箱 : 951813281@qq.com
//*/
//public class ValidImage {
//	private ValidImage(){}
//	
//	public static String getAnswer() throws IOException{
//		//读取txt，获取需要切割图片的名字、大小
//		List<ImageBean> imageList = ReadTxtUtils.getImageList("image.txt");
//		//开始切割图片
//		CutImageUtils.cutImage("验证码.jpg", imageList, "");
//		//开始识别图片
//			//1.标题
//		String title = DisTitle.getTitleDisResult(imageList.get(0).getName());
//		JSONObject titleJson = JSONObject.parseObject(title);
//		JSONArray wordsJson = (JSONArray)titleJson.get("words_result");
//		title = (String) JSONObject.parseObject(wordsJson.getString(0)).get("words");
//		if(title==null||"".equals(title)) return null;
//		System.out.println(title);
//			//2.小图片
//		List<String> resultList = new ArrayList<String>();
//		for(int i=1;i<imageList.size();i++){
//			ImageBean imageBean = imageList.get(i);
//			String result = DisPicture.getDisResult(imageBean.getName());
//			resultList.add(result);
//		}
//
//		List<String> keywordList = new ArrayList<String>();
//		//获取keyword
//		for (String result : resultList) {
//			JSONObject parseObject = JSONObject.parseObject(result);
//			JSONArray parseArray =  (JSONArray) parseObject.get("result");
//			String keyword = "";
//			for (Object object : parseArray) {
//				JSONObject obj = (JSONObject) object;
//				keyword += obj.get("keyword");
//			}
//			keywordList.add(keyword);
//		}
//		String answer = "";
//		
////		String[] titlewords = title.split("");
//		
//		for(int i=0;i<keywordList.size();i++){
//			String keyword = keywordList.get(i);
//			boolean matcher = keyword.matches(".*"+title+".*");
//			if(matcher){
//				answer = "".equals(answer)?((i+1)+""):("," + (i+1));
//				break;
//			}
//		}
//		return answer;
//	}
//}
