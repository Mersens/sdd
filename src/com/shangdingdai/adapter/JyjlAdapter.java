package com.shangdingdai.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shangdingdai.bean.JyjlBean;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.R;

public class JyjlAdapter extends BaseListAdapter<JyjlBean>{
	private List<JyjlBean> list;

	public JyjlAdapter(List<JyjlBean> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
		this.list=list;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		View v=mInflater.inflate(R.layout.layout_item_jlcx, null);
		TextView tv_type=(TextView) v.findViewById(R.id.tv_type);
		TextView tv_time=(TextView) v.findViewById(R.id.tv_time);
		TextView tv_money=(TextView) v.findViewById(R.id.tv_money);
		JyjlBean bean=list.get(position);
		int n=StringUtils.showType(bean.getTypeid());
		switch (n) {
		case 1:
			tv_type.setText(JyjlBean.JYJL_CZ);
			break;
		case 2:
			tv_type.setText(JyjlBean.JYJL_TB);
			break;
		case 3:
			tv_type.setText(JyjlBean.JYJL_TX);
			break;
		case 4:
			tv_type.setText(JyjlBean.JYJL_JK);
			break;
		case 5:
			tv_type.setText(JyjlBean.JYJL_HK);
			break;
		case 6:
			tv_type.setText(JyjlBean.JYJL_ZR);
			break;
		case 7:
			tv_type.setText(JyjlBean.JYJL_SY);
			break;
		}
		tv_time.setText(bean.getJiaoyitime());
		tv_money.setText(bean.getJiaoyijine()+" 元");
		return v;
	}

}
