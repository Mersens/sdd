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
import com.shangdingdai.bean.MyBankCardBean;
import com.shangdingdai.utils.ImageLoadOptions;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.R;

public class MyBankCardAdapter extends BaseListAdapter<MyBankCardBean>{

	private List<MyBankCardBean> list;
	public MyBankCardAdapter(List<MyBankCardBean> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
		this.list=list;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		View v=mInflater.inflate(R.layout.layout_item_wdyhk, null);
		ImageView image_bank=(ImageView) v.findViewById(R.id.image_bank);
		TextView tv_bank=(TextView) v.findViewById(R.id.tv_bank);
		TextView tv_kh=(TextView) v.findViewById(R.id.tv_kh);
		MyBankCardBean bean=list.get(position);
		String bankNane=bean.getYinhang();
		String icon_bank_url=Constants.ICON_BANK+bankNane+".png";
		ImageLoader.getInstance().displayImage(icon_bank_url, image_bank,
				ImageLoadOptions.getOptions());
		tv_bank.setText(StringUtils.showBankName(bankNane));
		tv_kh.setText(bean.getKahao());
		return v;
	}

}
