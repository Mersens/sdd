package com.shangdingdai.activity;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import com.shangdingdai.applcation.CustomApplcation;
import com.shangdingdai.fragment.WsyFragment;
import com.shangdingdai.fragment.YgqFragment;
import com.shangdingdai.fragment.YshFragment;
import com.shangdingdai.view.R;

import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MyRedPagerActivity extends FragmentActivity {
	private ViewPager viewPager;// 页卡内容
	private ImageView imageView;// 动画图片
	private TextView tv_mr, tv_lv, tv_je;// 选项名称
	private List<Fragment> fragments;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private int selectedColor, unSelectedColor;
	/** 页卡总数 **/
	private static final int pageSize = 3;
	public ImageView mImageView;
	public TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_redpager);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		CustomApplcation.getInstance().addActivity(this);
		initView();
	}

	
	private void initView() {
		selectedColor = getResources()
				.getColor(R.color.tab_title_pressed_color);
		unSelectedColor = getResources().getColor(
				R.color.tab_title_normal_color);
		mImageView=(ImageView) findViewById(R.id.img_back);
		mTextView=(TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.my_red_pager);
		mImageView.setVisibility(View.VISIBLE);
		InitImageView();
		InitTextView();
		InitViewPager();
	}
	
	/**
	 * 初始化Viewpager页
	 */
	private void InitViewPager() {
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		fragments = new ArrayList<Fragment>();
		fragments.add(new WsyFragment());
		fragments.add(new YshFragment());
		fragments.add(new YgqFragment());
		viewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager(),fragments));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	/**
	 * 初始化头标
	 * 
	 */
	private void InitTextView() {
		tv_mr = (TextView) findViewById(R.id.tab_1);
		tv_lv = (TextView) findViewById(R.id.tab_2);
		tv_je = (TextView) findViewById(R.id.tab_3);
		tv_mr.setTextColor(selectedColor);
		tv_lv.setTextColor(unSelectedColor);
		tv_je.setTextColor(unSelectedColor);
		tv_mr.setText(R.string.wsy);
		tv_lv.setText(R.string.ysy);
		tv_je.setText(R.string.ygq);
		tv_mr.setOnClickListener(new MyOnClickListener(0));
		tv_lv.setOnClickListener(new MyOnClickListener(1));
		tv_je.setOnClickListener(new MyOnClickListener(2));
		mImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		});
	}

	/**
	 * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
	 */
	private void InitImageView() {
		imageView = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.tab_selected_bg).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / pageSize - bmpW) / 2;// 计算偏移量--(屏幕宽度/页卡总数-图片实际宽度)/2													// = 偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}
	
	/**
	 * 头标点击监听
	 */
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}		
		public void onClick(View v) {
			switch (index) {
			case 0:
				tv_mr.setTextColor(selectedColor);
				tv_lv.setTextColor(unSelectedColor);
				tv_je.setTextColor(unSelectedColor);
				break;
			case 1:
				tv_mr.setTextColor(unSelectedColor);
				tv_lv.setTextColor(selectedColor);
				tv_je.setTextColor(unSelectedColor);
				break;
			case 2:
				tv_mr.setTextColor(unSelectedColor);
				tv_lv.setTextColor(unSelectedColor);
				tv_je.setTextColor(selectedColor);
				break;
			}
			viewPager.setCurrentItem(index);
		}
	}

	/**
	 * 为选项卡绑定监听器
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		public void onPageScrollStateChanged(int index) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int index) {
			Animation animation = new TranslateAnimation(one * currIndex, one
					* index, 0, 0);// 显然这个比较简洁，只有一行代码。
			currIndex = index;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);

			switch (index) {
			case 0:
				tv_mr.setTextColor(selectedColor);
				tv_lv.setTextColor(unSelectedColor);
				tv_je.setTextColor(unSelectedColor);
				break;
			case 1:
				tv_mr.setTextColor(unSelectedColor);
				tv_lv.setTextColor(selectedColor);
				tv_je.setTextColor(unSelectedColor);

				break;
			case 2:
				tv_mr.setTextColor(unSelectedColor);
				tv_lv.setTextColor(unSelectedColor);
				tv_je.setTextColor(selectedColor);
				break;
			}
		}
	}

	/**
	 * 定义适配器
	 */
	class myPagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragmentList;
		public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
			super(fm);
			this.fragmentList = fragmentList;
		}
		/**
		 * 得到每个页面
		 */
		@Override
		public Fragment getItem(int arg0) {
			return (fragmentList == null || fragmentList.size() == 0) ? null
					: fragmentList.get(arg0);
		}
		
		/**
		 * 每个页面的title
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return null;
		}
		
		/**
		 * 页面的总个数
		 */
		@Override
		public int getCount() {
			return fragmentList == null ? 0 : fragmentList.size();
		}
	}
	@Override
	protected void onResume() {
		JPushInterface.onResume(getApplicationContext());
		super.onResume();
	}


	@Override
	protected void onPause() {
		JPushInterface.onPause(getApplicationContext());
		super.onPause();
	}
}
