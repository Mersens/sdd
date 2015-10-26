package com.shangdingdai.bean;

public class BiaoTouZiBean {
	private String touziid;
	private String touzijine;
	private String touziuser;
	private String touziaddtime;

	public BiaoTouZiBean(){
		
	}
	
	public BiaoTouZiBean(String touziid, String touzijine, String touziuser,
			String touziaddtime) {
		super();
		this.touziid = touziid;
		this.touzijine = touzijine;
		this.touziuser = touziuser;
		this.touziaddtime = touziaddtime;
	}
	public String getTouziid() {
		return touziid;
	}
	public void setTouziid(String touziid) {
		this.touziid = touziid;
	}
	public String getTouzijine() {
		return touzijine;
	}
	public void setTouzijine(String touzijine) {
		this.touzijine = touzijine;
	}
	public String getTouziuser() {
		return touziuser;
	}
	public void setTouziuser(String touziuser) {
		this.touziuser = touziuser;
	}
	public String getTouziaddtime() {
		return touziaddtime;
	}
	public void setTouziaddtime(String touziaddtime) {
		this.touziaddtime = touziaddtime;
	}
	
	@Override
	public String toString() {
		return "BiaoTouZiBean [touziid=" + touziid + ", touzijine=" + touzijine
				+ ", touziuser=" + touziuser + ", touziaddtime=" + touziaddtime
				+ "]";
	}
}
