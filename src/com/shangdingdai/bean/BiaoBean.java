package com.shangdingdai.bean;

public class BiaoBean {
	private String biaoid;
	private String title;
	private String imgsrc;
	private String loanjine;
	private String lilv;
	private String dkqx;
	private String datastatic;
	private String yitoubili;
	private String yitoujine;
	private String shengyujine;
	private String timeval;
	
	public BiaoBean(){
		
	}
	
	public BiaoBean(String biaoid, String title, String imgsrc,
			String loanjine, String lilv, String dkqx, String datastatic,
			String yitoubili, String yitoujine, String shengyujine,
			String timeval) {
		super();
		this.biaoid = biaoid;
		this.title = title;
		this.imgsrc = imgsrc;
		this.loanjine = loanjine;
		this.lilv = lilv;
		this.dkqx = dkqx;
		this.datastatic = datastatic;
		this.yitoubili = yitoubili;
		this.yitoujine = yitoujine;
		this.shengyujine = shengyujine;
		this.timeval = timeval;
	}
	public String getBiaoid() {
		return biaoid;
	}
	public void setBiaoid(String biaoid) {
		this.biaoid = biaoid;
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
	public String getLoanjine() {
		return loanjine;
	}
	public void setLoanjine(String loanjine) {
		this.loanjine = loanjine;
	}
	public String getLilv() {
		return lilv;
	}
	public void setLilv(String lilv) {
		this.lilv = lilv;
	}
	public String getDkqx() {
		return dkqx;
	}
	public void setDkqx(String dkqx) {
		this.dkqx = dkqx;
	}
	public String getDatastatic() {
		return datastatic;
	}
	public void setDatastatic(String datastatic) {
		this.datastatic = datastatic;
	}
	public String getYitoubili() {
		return yitoubili;
	}
	public void setYitoubili(String yitoubili) {
		this.yitoubili = yitoubili;
	}
	public String getYitoujine() {
		return yitoujine;
	}
	public void setYitoujine(String yitoujine) {
		this.yitoujine = yitoujine;
	}
	public String getShengyujine() {
		return shengyujine;
	}
	public void setShengyujine(String shengyujine) {
		this.shengyujine = shengyujine;
	}
	public String getTimeval() {
		return timeval;
	}
	public void setTimeval(String timeval) {
		this.timeval = timeval;
	}
	
	@Override
	public String toString() {
		return "BiaoBean [biaoid=" + biaoid + ", title=" + title + ", imgsrc="
				+ imgsrc + ", loanjine=" + loanjine + ", lilv=" + lilv
				+ ", dkqx=" + dkqx + ", datastatic=" + datastatic
				+ ", yitoubili=" + yitoubili + ", yitoujine=" + yitoujine
				+ ", shengyujine=" + shengyujine + ", timeval=" + timeval + "]";
	}
}
