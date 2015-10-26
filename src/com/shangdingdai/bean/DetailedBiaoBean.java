package com.shangdingdai.bean;

public class DetailedBiaoBean {
	private String biaoid;
	private String title;
	private String imgsrc;
	private String loanjine;
	private String lilv;
	private String dkqx;
	private String datastatic;
	private String yitoubili;
	private String timeval;
	private String huankuanval;
	private String jieshao;
	private String addtime;
	private String shengyujine;
	private String yitoujine;
	private String biaotypeid;

	public DetailedBiaoBean(){
		
	}
	
	public DetailedBiaoBean(String biaoid, String title, String imgsrc,
			String loanjine, String lilv, String dkqx, String datastatic,
			String yitoubili, String timeval, String huankuanval,
			String jieshao, String addtime, String shengyujine,
			String yitoujine, String biaotypeid) {
		super();
		this.biaoid = biaoid;
		this.title = title;
		this.imgsrc = imgsrc;
		this.loanjine = loanjine;
		this.lilv = lilv;
		this.dkqx = dkqx;
		this.datastatic = datastatic;
		this.yitoubili = yitoubili;
		this.timeval = timeval;
		this.huankuanval = huankuanval;
		this.jieshao = jieshao;
		this.addtime = addtime;
		this.shengyujine = shengyujine;
		this.yitoujine = yitoujine;
		this.biaotypeid = biaotypeid;
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
	public String getTimeval() {
		return timeval;
	}
	public void setTimeval(String timeval) {
		this.timeval = timeval;
	}
	public String getHuankuanval() {
		return huankuanval;
	}
	public void setHuankuanval(String huankuanval) {
		this.huankuanval = huankuanval;
	}
	public String getJieshao() {
		return jieshao;
	}
	public void setJieshao(String jieshao) {
		this.jieshao = jieshao;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getShengyujine() {
		return shengyujine;
	}
	public void setShengyujine(String shengyujine) {
		this.shengyujine = shengyujine;
	}
	public String getYitoujine() {
		return yitoujine;
	}
	public void setYitoujine(String yitoujine) {
		this.yitoujine = yitoujine;
	}
	public String getBiaotypeid() {
		return biaotypeid;
	}
	public void setBiaotypeid(String biaotypeid) {
		this.biaotypeid = biaotypeid;
	}
	

	@Override
	public String toString() {
		return "DetailedBiaoBean [biaoid=" + biaoid + ", title=" + title
				+ ", imgsrc=" + imgsrc + ", loanjine=" + loanjine + ", lilv="
				+ lilv + ", dkqx=" + dkqx + ", datastatic=" + datastatic
				+ ", yitoubili=" + yitoubili + ", timeval=" + timeval
				+ ", huankuanval=" + huankuanval + ", jieshao=" + jieshao
				+ ", addtime=" + addtime + ", shengyujine=" + shengyujine
				+ ", yitoujine=" + yitoujine + ", biaotypeid=" + biaotypeid
				+ "]";
	}

}
