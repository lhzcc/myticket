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
package cn.my.ticket.bean;
import java.io.Serializable;
/**
*  作者 : YuanSir<br>
*  日期 : 2018年4月20日<br>
*  时间 : 下午<br>
*  邮箱 : 951813281@qq.com<br>
*/
public class Passenger implements Serializable {
	private static final long serialVersionUID = 1L;
	private String passenger_name;//姓名
	private String mobile_no;//手机号码
	private String passenger_id_no;//证件号
	private String passenger_id_type_code;//证件类型
	private String ticket_type_codes;//车票类型
	public String getPassenger_name() {
		return passenger_name;
	}
	public void setPassenger_name(String passenger_name) {
		this.passenger_name = passenger_name;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getPassenger_id_no() {
		return passenger_id_no;
	}
	public void setPassenger_id_no(String passenger_id_no) {
		this.passenger_id_no = passenger_id_no;
	}
	public String getPassenger_id_type_code() {
		return passenger_id_type_code;
	}
	public void setPassenger_id_type_code(String passenger_id_type_code) {
		this.passenger_id_type_code = passenger_id_type_code;
	}
	public String getTicket_type_codes() {
		return ticket_type_codes;
	}
	public void setTicket_type_codes(String ticket_type_codes) {
		this.ticket_type_codes = ticket_type_codes;
	}
	@Override
	public String toString() {
		return passenger_name +"\t\t" + mobile_no +"\t\t" +passenger_id_no;
	}
}
