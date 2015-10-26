package com.shangdingdai.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangdingdai.bean.MyRedPagerBean;
import com.shangdingdai.view.R;

public class MyRedPagerAdapter extends BaseListAdapter<MyRedPagerBean>{
	private List<MyRedPagerBean> list;

	public MyRedPagerAdapter(List<MyRedPagerBean> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
		this.list=list;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		View v=mInflater.inflate(R.layout.layout_item_myredpager, null);
		TextView tv_hb_je=(TextView) v.findViewById(R.id.tv_hb_je);
		TextView tv_hb_title=(TextView) v.findViewById(R.id.tv_hb_title);
		TextView tv_hb_time=(TextView) v.findViewById(R.id.tv_hb_time);
		ImageView icon_hb_static=(ImageView) v.findViewById(R.id.icon_hb_static);
		MyRedPagerBean bean=list.get(position);
		tv_hb_je.setText(bean.getJine()+" 元");
		tv_hb_title.setText("来源："+bean.getTitle());
		tv_hb_time.setText(bean.getYouxiaoqi());
		String img_static=bean.getDatastatic();
		if("1".equals(img_static)){
			//已使用
			icon_hb_static.setImageResource(R.drawable.icon_myredpager_ysy);
		}else if("2".equals(img_static)){
			//已过期
			icon_hb_static.setImageResource(R.drawable.icon_myredpager_ygq);
		}
		return v;
	}
}
