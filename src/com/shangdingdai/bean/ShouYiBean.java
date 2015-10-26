package com.shangdingdai.bean;

public class ShouYiBean {
	private String shouyiid;
	private String jine;
	private String yinghuanshijian;
	
	
	public ShouYiBean(String shouyiid, String jine, String yinghuanshijian) {
		super();
		this.shouyiid = shouyiid;
		this.jine = jine;
		this.yinghuanshijian = yinghuanshijian;
	}
	public ShouYiBean(){
		
	}
	@Override
	public String toString() {
		return "ShouYiBean [shouyiid=" + shouyiid + ", jine=" + jine
				+ ", yinghuanshijian=" + yinghuanshijian + "]";
	}
	public String getShouyiid() {
		return shouyiid;
	}
	public void setShouyiid(String shouyiid) {
		this.shouyiid = shouyiid;
	}
	public String getJine() {
		return jine;
	}
	public void setJine(String jine) {
		this.jine = jine;
	}
	public String getYinghuanshijian() {
		return yinghuanshijian;
	}
	public void setYinghuanshijian(String yinghuanshijian) {
		this.yinghuanshijian = yinghuanshijian;
	}


}
