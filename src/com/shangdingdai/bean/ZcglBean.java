package com.shangdingdai.bean;

public class ZcglBean {
	private String keyongyue;
	private String dongjiejine;
	private String zhanghuyue;
	private String touzijine;
	private String daishoubenxi;
	private String jingzichan;
	private String jiekuanjine;

	public ZcglBean() {

	}

	public ZcglBean(String keyongyue, String dongjiejine, String zhanghuyue,
			String touzijine, String daishoubenxi, String jingzichan,
			String jiekuanjine) {
		super();
		this.keyongyue = keyongyue;
		this.dongjiejine = dongjiejine;
		this.zhanghuyue = zhanghuyue;
		this.touzijine = touzijine;
		this.daishoubenxi = daishoubenxi;
		this.jingzichan = jingzichan;
		this.jiekuanjine = jiekuanjine;
	}

	public String getKeyongyue() {
		return keyongyue;
	}

	public void setKeyongyue(String keyongyue) {
		this.keyongyue = keyongyue;
	}

	public String getDongjiejine() {
		return dongjiejine;
	}

	public void setDongjiejine(String dongjiejine) {
		this.dongjiejine = dongjiejine;
	}

	public String getZhanghuyue() {
		return zhanghuyue;
	}

	public void setZhanghuyue(String zhanghuyue) {
		this.zhanghuyue = zhanghuyue;
	}

	public String getTouzijine() {
		return touzijine;
	}

	public void setTouzijine(String touzijine) {
		this.touzijine = touzijine;
	}

	public String getDaishoubenxi() {
		return daishoubenxi;
	}

	public void setDaishoubenxi(String daishoubenxi) {
		this.daishoubenxi = daishoubenxi;
	}

	public String getJingzichan() {
		return jingzichan;
	}

	public void setJingzichan(String jingzichan) {
		this.jingzichan = jingzichan;
	}

	public String getJiekuanjine() {
		return jiekuanjine;
	}

	public void setJiekuanjine(String jiekuanjine) {
		this.jiekuanjine = jiekuanjine;
	}

	@Override
	public String toString() {
		return "ZcglBean [keyongyue=" + keyongyue + ", dongjiejine="
				+ dongjiejine + ", zhanghuyue=" + zhanghuyue + ", touzijine="
				+ touzijine + ", daishoubenxi=" + daishoubenxi
				+ ", jingzichan=" + jingzichan + ", jiekuanjine=" + jiekuanjine
				+ "]";
	}
}
