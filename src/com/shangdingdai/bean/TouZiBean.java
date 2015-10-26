package com.shangdingdai.bean;

public class TouZiBean {
	private String biaoid;
	private String shengyujine;
	private String mintouzi;
	private String title;
	private String useryue;
	
	public TouZiBean(){
		
	}
	
	public TouZiBean(String biaoid, String shengyujine, String mintouzi,
			String title, String useryue) {
		super();
		this.biaoid = biaoid;
		this.shengyujine = shengyujine;
		this.mintouzi = mintouzi;
		this.title = title;
		this.useryue = useryue;
	}
	@Override
	public String toString() {
		return "HongBaoBean [biaoid=" + biaoid + ", shengyujine=" + shengyujine
				+ ", mintouzi=" + mintouzi + ", title=" + title + ", useryue="
				+ useryue + "]";
	}
	public String getBiaoid() {
		return biaoid;
	}
	public void setBiaoid(String biaoid) {
		this.biaoid = biaoid;
	}
	public String getShengyujine() {
		return shengyujine;
	}
	public void setShengyujine(String shengyujine) {
		this.shengyujine = shengyujine;
	}
	public String getMintouzi() {
		return mintouzi;
	}
	public void setMintouzi(String mintouzi) {
		this.mintouzi = mintouzi;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUseryue() {
		return useryue;
	}
	public void setUseryue(String useryue) {
		this.useryue = useryue;
	}

}
