package com.shangdingdai.bean;

public class BannerMsgBean {
	private String title;
	private String imgsrc;
	private String center;
	private String typeid;

	public BannerMsgBean(){
		
	}
	public BannerMsgBean(String title, String imgsrc, String center,
			String typeid) {
		super();
		this.title = title;
		this.imgsrc = imgsrc;
		this.center = center;
		this.typeid = typeid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String getCenter() {
		return center;
	}
	public void setCenter(String center) {
		this.center = center;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	
	@Override
	public String toString() {
		return "BannerMsgBean [title=" + title + ", imgsrc=" + imgsrc
				+ ", center=" + center + ", typeid=" + typeid + "]";
	}
}
