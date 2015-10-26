package com.shangdingdai.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shangdingdai.bean.BiaoHuanKuanBean;
import com.shangdingdai.view.R;

public class HklbAdapter extends BaseListAdapter<BiaoHuanKuanBean> {
	private List<BiaoHuanKuanBean> list;
	public HklbAdapter(List<BiaoHuanKuanBean> list, Context context) {
		super(list, context);
		this.list=list;
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.layout_hkjl_item, null);
			holder.tv_hklb_hkrq=(TextView) convertView.findViewById(R.id.tv_hklb_hkrq);
			holder.tv_hklb_qs=(TextView) convertView.findViewById(R.id.tv_hklb_qs);
			holder.tv_hklb_yhje=(TextView) convertView.findViewById(R.id.tv_hklb_yhje);
			holder.tv_hklb_hkzt=(TextView) convertView.findViewById(R.id.tv_hklb_hkzt);
			convertView.setTag(holder);
			
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		BiaoHuanKuanBean bean=list.get(position);
		holder.tv_hklb_hkrq.setText(bean.getHuankuandate());
		holder.tv_hklb_qs.setText(bean.getHuankuanqishu());
		holder.tv_hklb_yhje.setText(bean.getHuankuanjine());
		holder.tv_hklb_hkzt.setText(bean.getHuankuanstatic());
		
		return convertView;
	}

	private class ViewHolder{
		public TextView tv_hklb_hkrq;
		public TextView tv_hklb_qs;
		public TextView tv_hklb_yhje;
		public TextView tv_hklb_hkzt;
		
	}
	
}
