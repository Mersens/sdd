package com.shangdingdai.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangdingdai.applcation.Constants;
import com.shangdingdai.bean.XmtzBean;
import com.shangdingdai.utils.ImageLoadOptions;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.R;

public class TzglAdapter extends BaseListAdapter<XmtzBean>{
	private List<XmtzBean> list;

	public TzglAdapter(List<XmtzBean> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
		this.list=list;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		View v=mInflater.inflate(R.layout.layout_item_tzgl, null);
		TextView tv_title=(TextView) v.findViewById(R.id.tv_title);
		TextView tv_static=(TextView) v.findViewById(R.id.tv_static);
		TextView tv_je_msg=(TextView) v.findViewById(R.id.tv_je_msg);
		TextView tv_nlv_msg=(TextView) v.findViewById(R.id.tv_nlv_msg);
		TextView tv_qx_msg=(TextView) v.findViewById(R.id.tv_qx_msg);
		TextView tv_qq_msg=(TextView) v.findViewById(R.id.tv_qq_msg);
		ImageView icon_title=(ImageView) v.findViewById(R.id.icon_title);
		XmtzBean bean=list.get(position);
		ImageLoader.getInstance().displayImage(Constants.IC_USER_URL+bean.getImgsrc(), icon_title,
				ImageLoadOptions.getOptions());
		tv_title.setText(bean.getTitle());
		tv_je_msg.setText(bean.getJine()+"元");
		tv_nlv_msg.setText(bean.getLilv()+"%");
		tv_qx_msg.setText(bean.getYujishouyi()+"元");
		tv_qq_msg.setText(bean.getDkqy()+"月");
		int i=StringUtils.showIcon(bean.getDatastatic());
		switch (i) {
		case StringUtils.ICON_HKZ:
			tv_static.setText("还款中");
			break;
		case StringUtils.ICON_SHZ:
			tv_static.setText("审核中");
			break;
		case StringUtils.ICON_YJJ:
			tv_static.setText("已拒绝");
			break;
		case StringUtils.ICON_YJS:
			tv_static.setText("已结束");
			break;
		case StringUtils.ICON_YMB:
			tv_static.setText("已满标");
		case StringUtils.ICON_TBZ:
			tv_static.setText("已结束");
			break;
		}
		return v;
	}

}
