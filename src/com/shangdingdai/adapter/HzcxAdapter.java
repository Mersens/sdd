package com.shangdingdai.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shangdingdai.bean.ShouYiBean;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.R;

public class HzcxAdapter extends BaseListAdapter<ShouYiBean> {
	private List<ShouYiBean> list;

	public HzcxAdapter(List<ShouYiBean> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
	    this.list=list;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v=mInflater.inflate(R.layout.layout_item_hzcx, null);
		TextView tv_shouyiid=(TextView) v.findViewById(R.id.tv_shouyiid);
		TextView tv_jine=(TextView) v.findViewById(R.id.tv_jine);
		TextView tv_yinghuanshijian=(TextView) v.findViewById(R.id.tv_yinghuanshijian);
		ShouYiBean bean=list.get(position);
		tv_shouyiid.setText(bean.getShouyiid());
		tv_jine.setText(bean.getJine()+" 元");
		String time=StringUtils.getFormatDate(bean.getYinghuanshijian());
		tv_yinghuanshijian.setText(time);
		return v;
	}

}
