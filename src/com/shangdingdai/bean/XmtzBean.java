package com.shangdingdai.bean;

public class XmtzBean {
	private String imgsrc ;
	private String title;
	private String datastatic;
	private String jine;
	private String lilv;
	private String yujishouyi;
	private String dkqy;
	
	public XmtzBean(){
	}
	
	@Override
	public String toString() {
		return "XmtzBean [imgsrc=" + imgsrc + ", title=" + title
				+ ", datastatic=" + datastatic + ", jine=" + jine + ", lilv="
				+ lilv + ", yujishouyi=" + yujishouyi + ", dkqy=" + dkqy + "]";
	}
	public XmtzBean(String imgsrc, String title, String datastatic,
			String jine, String lilv, String yujishouyi, String dkqy) {
		super();
		this.imgsrc = imgsrc;
		this.title = title;
		this.datastatic = datastatic;
		this.jine = jine;
		this.lilv = lilv;
		this.yujishouyi = yujishouyi;
		this.dkqy = dkqy;
	}
	
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDatastatic() {
		return datastatic;
	}
	public void setDatastatic(String datastatic) {
		this.datastatic = datastatic;
	}
	public String getJine() {
		return jine;
	}
	public void setJine(String jine) {
		this.jine = jine;
	}
	public String getLilv() {
		return lilv;
	}
	public void setLilv(String lilv) {
		this.lilv = lilv;
	}
	public String getYujishouyi() {
		return yujishouyi;
	}
	public void setYujishouyi(String yujishouyi) {
		this.yujishouyi = yujishouyi;
	}
	public String getDkqy() {
		return dkqy;
	}
	public void setDkqy(String dkqy) {
		this.dkqy = dkqy;
	}

	
}
