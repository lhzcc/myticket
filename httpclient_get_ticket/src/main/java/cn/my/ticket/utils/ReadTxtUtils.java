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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.my.ticket.bean.ImageBean;


/**
*  作者 : YuanSir
*  日期 : 2018年4月23日
*  时间 : 下午11:08:32
*  邮箱 : 951813281@qq.com
*/
public class ReadTxtUtils {
	private ReadTxtUtils(){}
	
	public static List<ImageBean> getImageList(String filepath) throws IOException{
		
		List<ImageBean> imageList = new ArrayList<ImageBean>();
		
		BufferedReader br = 
				new BufferedReader(new FileReader(filepath));
		
		String line = null;
		while((line=br.readLine())!=null){
			String[] s = line.split(",");
			ImageBean ib = new ImageBean();
			ib.setName(s[0]);
			ib.setX(Integer.parseInt(s[1]));
			ib.setY(Integer.parseInt(s[2]));
			ib.setWidth(Integer.parseInt(s[3]));
			ib.setHeight(Integer.parseInt(s[4]));
			imageList.add(ib);
		}
		if(br!=null)
			br.close();
		return imageList;
	}
}
