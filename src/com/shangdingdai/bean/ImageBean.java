package com.shangdingdai.bean;

public class ImageBean {
	private String id;//标示id
	private String url;//图片地址
	
	public ImageBean(String id, String url) {
		super();
		this.id = id;
		this.url = url;
	}

	public ImageBean(){
		
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ImageBean [id=" + id + ", url=" + url + "]";
	}

}
