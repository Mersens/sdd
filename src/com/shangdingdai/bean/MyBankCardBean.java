package com.shangdingdai.bean;

public class MyBankCardBean {
	private String kahao;
	private String yinhang;
	
	
	@Override
	public String toString() {
		return "MyBankCardBean [kahao=" + kahao + ", yinhang=" + yinhang + "]";
	}
	public String getKahao() {
		return kahao;
	}
	public void setKahao(String kahao) {
		this.kahao = kahao;
	}
	public String getYinhang() {
		return yinhang;
	}
	public void setYinhang(String yinhang) {
		this.yinhang = yinhang;
	}

}
