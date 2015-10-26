package com.shangdingdai.bean;

public class BiaoHuanKuanBean {
	private String huankuanid ;
	private String huankuandate ;
	private String huankuanqishu ;
	private String huankuanjine ;
	private String huankuanstatic ;

	public BiaoHuanKuanBean(){
		
	}
	public BiaoHuanKuanBean(String huankuanid, String huankuandate,
			String huankuanqishu, String huankuanjine, String huankuanstatic) {
		super();
		this.huankuanid = huankuanid;
		this.huankuandate = huankuandate;
		this.huankuanqishu = huankuanqishu;
		this.huankuanjine = huankuanjine;
		this.huankuanstatic = huankuanstatic;
	}
	public String getHuankuanid() {
		return huankuanid;
	}
	public void setHuankuanid(String huankuanid) {
		this.huankuanid = huankuanid;
	}
	public String getHuankuandate() {
		return huankuandate;
	}
	public void setHuankuandate(String huankuandate) {
		this.huankuandate = huankuandate;
	}
	public String getHuankuanqishu() {
		return huankuanqishu;
	}
	public void setHuankuanqishu(String huankuanqishu) {
		this.huankuanqishu = huankuanqishu;
	}
	public String getHuankuanjine() {
		return huankuanjine;
	}
	public void setHuankuanjine(String huankuanjine) {
		this.huankuanjine = huankuanjine;
	}
	public String getHuankuanstatic() {
		return huankuanstatic;
	}
	public void setHuankuanstatic(String huankuanstatic) {
		this.huankuanstatic = huankuanstatic;
	}
	
	@Override
	public String toString() {
		return "BiaoHuanKuanBean [huankuanid=" + huankuanid + ", huankuandate="
				+ huankuandate + ", huankuanqishu=" + huankuanqishu
				+ ", huankuanjine=" + huankuanjine + ", huankuanstatic="
				+ huankuanstatic + "]";
	}
}
