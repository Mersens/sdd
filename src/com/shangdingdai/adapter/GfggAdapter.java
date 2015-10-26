package com.shangdingdai.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shangdingdai.bean.GfggBean;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.R;

public class GfggAdapter extends BaseListAdapter<GfggBean>{
	private List<GfggBean> list;

	public GfggAdapter(List<GfggBean> list, Context context) {
		super(list, context);
		this.list=list;
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("InflateParams")
	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.layout_item_mymsg_gfgg, parent,false);
			holder.tv_title=(TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_jianjie =(TextView) convertView.findViewById(R.id.tv_jianjie);
			holder.tv_time=(TextView) convertView.findViewById(R.id.tv_time);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		GfggBean bean=list.get(position);
		holder.tv_title.setText(bean.getTitle());
		holder.tv_time.setText(bean.getTime());
		holder.tv_jianjie.setText(StringUtils.getJianjie(bean.getJianjie()));
		return convertView;
	}
	
	private class ViewHolder{
		public TextView tv_title;
		public TextView tv_jianjie;
		public TextView tv_time;
	}

}
