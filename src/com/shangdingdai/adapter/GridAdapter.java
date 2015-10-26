package com.shangdingdai.adapter;

import java.util.List;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangdingdai.utils.ImageLoadOptions;
import com.shangdingdai.view.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter{
	private List<String> images;
	private List<String> names;
	private LayoutInflater mInflater;
	public GridAdapter(Context context,List<String> images,List<String> names){
		this.images=images;
		this.names=names;
		mInflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return images.size();
	}

	@Override
	public Object getItem(int position) {
		return images.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v=mInflater.inflate(R.layout.layout_zhengjian, null);
		ImageView imageView = (ImageView) v.findViewById(R.id.icon_zj); 
		TextView tv=(TextView) v.findViewById(R.id.tv_zj);
		tv.setText(names.get(position));
		ImageLoader.getInstance().displayImage(images.get(position), imageView,
				ImageLoadOptions.getOptions());
		return v;
	}

}
