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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import cn.my.ticket.bean.ImageBean;


/**
*  作者 : YuanSir
*  日期 : 2018年4月24日
*  时间 : 上午12:20:02
*  邮箱 : 951813281@qq.com
*/
public class CutImageUtils {
	private CutImageUtils(){}
	
	
	public static void cutImage(String sourcePath,List<ImageBean> list,String targetPath) throws IOException{
		
		BufferedImage source = ImageIO.read(new File(sourcePath));
//		int height = source.getHeight();
//		int width = source.getWidth();
		for (ImageBean imageBean : list) {
			ImageIO.write(source.getSubimage(imageBean.getX(), imageBean.getY(), imageBean.getWidth(), imageBean.getHeight()), 
					"jpg",
					new File(targetPath+imageBean.getName()));
			System.out.println(imageBean.getName() +  "----完成");
		}
	}
}
