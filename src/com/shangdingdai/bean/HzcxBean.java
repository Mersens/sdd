package com.shangdingdai.bean;

import java.util.List;

public class HzcxBean {
	private String today;
	private String monthlaterdate;
	private String onemonthshouyijine;
    private List<ShouYiBean> list;
   
	@Override
	public String toString() {
		return "HzcxBean [today=" + today + ", monthlaterdate="
				+ monthlaterdate + ", onemonthshouyijine=" + onemonthshouyijine
				+ ", list=" + list + "]";
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public String getMonthlaterdate() {
		return monthlaterdate;
	}
	public void setMonthlaterdate(String monthlaterdate) {
		this.monthlaterdate = monthlaterdate;
	}
	public String getOnemonthshouyijine() {
		return onemonthshouyijine;
	}
	public void setOnemonthshouyijine(String onemonthshouyijine) {
		this.onemonthshouyijine = onemonthshouyijine;
	}
	public List<ShouYiBean> getList() {
		return list;
	}
	public void setList(List<ShouYiBean> list) {
		this.list = list;
	}
	public HzcxBean(){
		
	}
	



}
