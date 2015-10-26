package com.shangdingdai.bean;

public class GfggBean {
	private String id;
	private String title;
	private String time;
	private String jianjie;
	
	
	public GfggBean(){
		
	}
	
	public GfggBean(String id, String title, String time, String jianjie) {
		super();
		this.id = id;
		this.title = title;
		this.time = time;
		this.jianjie = jianjie;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getJianjie() {
		return jianjie;
	}
	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
	}
	
	@Override
	public String toString() {
		return "GfggBean [id=" + id + ", title=" + title + ", time=" + time
				+ ", jianjie=" + jianjie + "]";
	}
}
