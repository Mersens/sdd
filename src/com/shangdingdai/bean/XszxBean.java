package com.shangdingdai.bean;

import java.util.List;

public class XszxBean {
	private String code;
	private String page;
	private String pagesize;
	private String sum;
	private String ordertype;
	private List<BiaoBean> biaolist;

	public XszxBean(){
		
	}
	public XszxBean(String code, String page, String pagesize, String sum,
			String ordertype, List<BiaoBean> biaolist) {
		super();
		this.code = code;
		this.page = page;
		this.pagesize = pagesize;
		this.sum = sum;
		this.ordertype = ordertype;
		this.biaolist = biaolist;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public List<BiaoBean> getBiaolist() {
		return biaolist;
	}
	public void setBiaolist(List<BiaoBean> biaolist) {
		this.biaolist = biaolist;
	}

	
	
	@Override
	public String toString() {
		return "XszxBean [code=" + code + ", page=" + page + ", pagesize="
				+ pagesize + ", sum=" + sum + ", ordertype=" + ordertype
				+ ", biaolist.size()=" + biaolist.size() + "]";
	}
}
