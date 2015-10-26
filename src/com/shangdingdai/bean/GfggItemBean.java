package com.shangdingdai.bean;

public class GfggItemBean {
	private String id;
	private String name;
	private String center;
	private String time;
	private String laiyuan;
	
	
	public GfggItemBean(){
		
	}
	
	
	public GfggItemBean(String id, String name, String center, String time,
			String laiyuan) {
		super();
		this.id = id;
		this.name = name;
		this.center = center;
		this.time = time;
		this.laiyuan = laiyuan;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCenter() {
		return center;
	}
	public void setCenter(String center) {
		this.center = center;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLaiyuan() {
		return laiyuan;
	}
	public void setLaiyuan(String laiyuan) {
		this.laiyuan = laiyuan;
	}

}
