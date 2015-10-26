package com.shangdingdai.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangdingdai.adapter.ViewPagerAdapter;
import com.shangdingdai.utils.ImageLoadOptions;
import com.shangdingdai.view.R;

public class CheckImgActivity extends BaseActivity {
	private ViewPager viewpager;
	private ArrayList<String> list;
	private int index;
	private TextView tv_sumnum;
	private TextView tv_count;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_check_img);
		Intent intent=getIntent();
		list=intent.getStringArrayListExtra("path");
		index=intent.getIntExtra("position",0);
		initViews();
		initEvents();
	}
	private void initEvents() {
		viewpager.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				tv_count.setText((arg0+1)+"");
				
			}
			
		});
		
		
	}
	@SuppressLint("InflateParams")
	private void initViews() {
		viewpager=(ViewPager) findViewById(R.id.check_viewpager);
		tv_sumnum=(TextView) findViewById(R.id.tv_sumnum);
		tv_sumnum.setText(list.size()+"");
		tv_count=(TextView) findViewById(R.id.tv_count);
		tv_count.setText((index+1)+"");
		int len = list.size();
		View view = null;
		ImageView imageview;
		LayoutInflater mInflater = LayoutInflater.from(getApplicationContext());
		List<View> lists = new ArrayList<View>();
		for (int i1 = 0; i1 < len; i1++)
		{
			view = mInflater.inflate(R.layout.imageview_layout_check, null);
			imageview = (ImageView) view.findViewById(R.id.viewpager_imageview_check);
			imageview.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finishActivity();
				}
			});
			ImageLoader.getInstance().displayImage(list.get(i1), imageview,
					ImageLoadOptions.getOptions());
			lists.add(view);
		}
		viewpager.setAdapter(new ViewPagerAdapter(getApplicationContext(),lists));
		viewpager.setCurrentItem(index);
	}
}
