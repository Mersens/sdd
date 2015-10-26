package com.shangdingdai.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shangdingdai.bean.BiaoTouZiBean;
import com.shangdingdai.view.R;

public class TzjlAdapter extends BaseListAdapter<BiaoTouZiBean>{
	private List<BiaoTouZiBean> list;

	public TzjlAdapter(List<BiaoTouZiBean> list, Context context) {
		super(list, context);
		this.list=list;
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.layout_list_item, null);
			holder.tv_tzje=(TextView) convertView.findViewById(R.id.tv_tzje);
			holder.tv_tzr=(TextView) convertView.findViewById(R.id.tv_tzr);
			holder.tv_tzsj=(TextView) convertView.findViewById(R.id.tv_tzsj);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}

		BiaoTouZiBean bean=list.get(position);
		holder.tv_tzr.setText(bean.getTouziuser());
		holder.tv_tzje.setText(bean.getTouzijine());
		holder.tv_tzsj.setText(bean.getTouziaddtime());
		return convertView;
	}

	static class ViewHolder{
		public TextView tv_tzr;
		public TextView tv_tzje;
		public TextView tv_tzsj;
	}
	
}
