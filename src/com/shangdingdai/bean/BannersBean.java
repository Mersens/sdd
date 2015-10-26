package com.shangdingdai.bean;

import java.util.List;

public class BannersBean {

	private int code;
	private int size;
	private List<BannerMsgBean> banners;
	

	public BannersBean(){
		
	}
	
	public BannersBean(int code, int size, List<BannerMsgBean> banners) {
		super();
		this.code = code;
		this.size = size;
		this.banners = banners;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<BannerMsgBean> getBanners() {
		return banners;
	}
	public void setBanners(List<BannerMsgBean> banners) {
		this.banners = banners;
	}
	
	
	@Override
	public String toString() {
		return "BannersBean [code=" + code + ", size=" + size + ", banners="
				+ banners.size() + "]";
	}

}
