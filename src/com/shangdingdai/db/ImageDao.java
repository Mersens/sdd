package com.shangdingdai.db;

import java.util.List;

import com.shangdingdai.bean.ImageBean;

public interface ImageDao {
	public void add(String id,String url);
	public void delete(String id);
	public void update(String id,String url);
	public List<ImageBean> select(String id);

}
