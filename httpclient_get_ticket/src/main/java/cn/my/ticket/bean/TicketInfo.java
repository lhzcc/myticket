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

package cn.my.ticket.bean;////////////////////////////////////////////////////////////////////////////////////////////////
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

import java.io.Serializable;
import java.util.Map;

import cn.my.ticket.utils.PropertiesUtil;

public class TicketInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String secretStr;	//不知道干什么的，下单的时候要用到。  0
	private String station_train_code;//车次
	private String start_station;//列车起始站   4
	private String end_station;//列车终点站	5
	private String from_station;//出发站		6
	private String to_station;//目的站		7
	private String start_time;//出发时间		8
	private String arrive_time;//结束时间		9
	private String use_time;//历时			10
	private String start_train_date;//列车的出发日期	13
	//商务特等座：32,一等座：31,二等座：30,高级软卧：21,软卧：23,动卧：33,硬卧：28,软座：24,硬座：29,无座：26,其他：22,备注：1
	private Map<String,String> seat;//座位的类型	
	
	
	public String getStation_train_code() {
		return station_train_code;
	}
	public void setStation_train_code(String station_train_code) {
		this.station_train_code = station_train_code;
	}
	public String getSecretStr() {
		return secretStr;
	}
	public void setSecretStr(String secretStr) {
		this.secretStr = secretStr;
	}
	public String getStart_station() {
		return start_station;
	}
	public void setStart_station(String start_station) {
		this.start_station = start_station;
	}
	public String getEnd_station() {
		return end_station;
	}
	public void setEnd_station(String end_station) {
		this.end_station = end_station;
	}
	public String getFrom_station() {
		return from_station;
	}
	public void setFrom_station(String from_station) {
		this.from_station = from_station;
	}
	public String getTo_station() {
		return to_station;
	}
	public void setTo_station(String to_station) {
		this.to_station = to_station;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getArrive_time() {
		return arrive_time;
	}
	public void setArrive_time(String arrive_time) {
		this.arrive_time = arrive_time;
	}
	public String getUse_time() {
		return use_time;
	}
	public void setUse_time(String use_time) {
		this.use_time = use_time;
	}
	public String getStart_train_date() {
		return start_train_date;
	}
	public void setStart_train_date(String start_train_date) {
		this.start_train_date = start_train_date.replaceAll("(\\d{4})(\\d{2})(\\d{2})", "$1-$2-$3");
	}

	public Map<String, String> getSeat() {
		return seat;
	}
	public void setSeat(Map<String, String> seat) {
		this.seat = seat;
	}
	@Override
	public String toString() {
		return station_train_code+"\t"+PropertiesUtil.getStation2Properties().getProperty(from_station)
				+ "\t" + PropertiesUtil.getStation2Properties().getProperty(to_station) + "\t"
				+ start_time + "\t" + arrive_time + "\t" + use_time + "\t"
				+ start_train_date + "\t" + seat.get("无座")
				+"\t" + seat.get("硬座")+"\t" + seat.get("软座")
				+"\t" + seat.get("硬卧")+"\t" + seat.get("软卧")
				+"\t" + seat.get("商务特等座")+"\t" + seat.get("一等座")
				+"\t" + seat.get("二等座")+"\t" + seat.get("高级软卧");
	}
}
