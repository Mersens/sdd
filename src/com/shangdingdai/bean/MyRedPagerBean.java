package com.shangdingdai.bean;

public class MyRedPagerBean {
	private String title;
	private String jine;
	private String datastatic;
	private String youxiaoqi;
	
	public MyRedPagerBean(){
		
	}
	
	public MyRedPagerBean(String title, String jine, String datastatic,
			String youxiaoqi) {
		super();
		this.title = title;
		this.jine = jine;
		this.datastatic = datastatic;
		this.youxiaoqi = youxiaoqi;
	}
	@Override
	public String toString() {
		return "MyRedPagerBean [title=" + title + ", jine=" + jine
				+ ", datastatic=" + datastatic + ", youxiaoqi=" + youxiaoqi
				+ "]";
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getJine() {
		return jine;
	}
	public void setJine(String jine) {
		this.jine = jine;
	}
	public String getDatastatic() {
		return datastatic;
	}
	public void setDatastatic(String datastatic) {
		this.datastatic = datastatic;
	}
	public String getYouxiaoqi() {
		return youxiaoqi;
	}
	public void setYouxiaoqi(String youxiaoqi) {
		this.youxiaoqi = youxiaoqi;
	}
}
