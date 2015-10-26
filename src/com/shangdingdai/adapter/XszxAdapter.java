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
import com.shangdingdai.bean.BiaoBean;
import com.shangdingdai.utils.ImageLoadOptions;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.ProgressWheel;
import com.shangdingdai.view.R;

public class XszxAdapter extends BaseListAdapter<BiaoBean> {
	private List<BiaoBean> list;
	public XszxAdapter(List<BiaoBean> list, Context context) {
		super(list, context);
		this.list=list;
	}
	
	@SuppressLint("InflateParams")
	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {

			convertView=mInflater.inflate(R.layout.layout_item,null);
			ImageView icon_title=(ImageView) convertView.findViewById(R.id.icon_title);
			TextView tv_title=(TextView) convertView.findViewById(R.id.tv_title);
			TextView tv_je_msg=(TextView) convertView.findViewById(R.id.tv_je_msg);
			TextView tv_nlv_msg=(TextView) convertView.findViewById(R.id.tv_nlv_msg);
			TextView tv_qx_msg=(TextView) convertView.findViewById(R.id.tv_qx_msg);
			TextView tv_zt=(TextView) convertView.findViewById(R.id.tv_zt);
			ImageView icon_zt=(ImageView) convertView.findViewById(R.id.icon_zt);
			ProgressWheel pwOne=(ProgressWheel) convertView.findViewById(R.id.rogressWheel);
			TextView tv_center=(TextView) convertView.findViewById(R.id.tv_center);

		    BiaoBean bean=list.get(position);
		
		if(null!=bean){
			ImageLoader.getInstance().displayImage(Constants.IC_USER_URL+bean.getImgsrc(), icon_title,
					ImageLoadOptions.getOptions());
			tv_title.setText(bean.getTitle());
			tv_je_msg.setText(bean.getLoanjine()+"元");
			tv_nlv_msg.setText(bean.getLilv()+"%");
			tv_qx_msg.setText(bean.getDkqx());
			tv_zt.setText(bean.getTimeval());
			
			int i=StringUtils.showIcon(bean.getDatastatic());
			switch (i) {
			case StringUtils.ICON_HKZ:
				icon_zt.setImageResource(R.drawable.icon_hkz);
				break;
			case StringUtils.ICON_SHZ:
				icon_zt.setImageResource(R.drawable.icon_shz);
				break;
			case StringUtils.ICON_YJJ:
				icon_zt.setImageResource(R.drawable.icon_hjj);
				break;
			case StringUtils.ICON_YJS:
				icon_zt.setImageResource(R.drawable.icon_yjs);
				break;
			case StringUtils.ICON_YMB:
				icon_zt.setImageResource(R.drawable.icon_ymb);
			case StringUtils.ICON_TBZ:
				icon_zt.setVisibility(View.GONE);
				String str=StringUtils.getProportion(bean.getYitoubili());
				pwOne.setText(str+"%");
				int proportion=StringUtils.getIntProportion(bean.getYitoubili());
				pwOne.setProgress(proportion);
				pwOne.setVisibility(View.VISIBLE);
				tv_center.setVisibility(View.VISIBLE);
				break;
			}
		}
		return convertView;
	}
	


}
