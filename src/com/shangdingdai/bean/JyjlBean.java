package com.shangdingdai.bean;

public class JyjlBean {
	//1充值 2投标 3提现 4借款 5还款 6转让 7收益
	public static String JYJL_CZ="充值";
	public static String JYJL_TB="投标";
	public static String JYJL_TX="提现";
	public static String JYJL_JK="借款";
	public static String JYJL_HK="还款";
	public static String JYJL_ZR="转让";
	public static String JYJL_SY="收益";
	private String typeid ;
	private String jiaoyijine; 
	private String jiaoyitime;
	
	@Override
	public String toString() {
		return "JyjlBean [typeid=" + typeid + ", jiaoyijine=" + jiaoyijine
				+ ", jiaoyitime=" + jiaoyitime + "]";
	}

	public JyjlBean(){
	}
	
	public JyjlBean(String typeid, String jiaoyijine, String jiaoyitime) {
		super();
		this.typeid = typeid;
		this.jiaoyijine = jiaoyijine;
		this.jiaoyitime = jiaoyitime;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getJiaoyijine() {
		return jiaoyijine;
	}
	public void setJiaoyijine(String jiaoyijine) {
		this.jiaoyijine = jiaoyijine;
	}
	public String getJiaoyitime() {
		return jiaoyitime;
	}
	public void setJiaoyitime(String jiaoyitime) {
		this.jiaoyitime = jiaoyitime;
	}

}
