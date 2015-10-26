package com.shangdingdai.bean;

public class BiaoFileBean {
	private String biaofileid;
	private String imgsrc;
	private String typeid;
	private String description;


	public BiaoFileBean(){
		
	}
	
	public BiaoFileBean(String biaofileid, String imgsrc, String typeid,
			String description) {
		super();
		this.biaofileid = biaofileid;
		this.imgsrc = imgsrc;
		this.typeid = typeid;
		this.description = description;
	}
	public String getBiaofileid() {
		return biaofileid;
	}
	public void setBiaofileid(String biaofileid) {
		this.biaofileid = biaofileid;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "BiaoFileBean [biaofileid=" + biaofileid + ", imgsrc=" + imgsrc
				+ ", typeid=" + typeid + ", description=" + description + "]";
	}
}
