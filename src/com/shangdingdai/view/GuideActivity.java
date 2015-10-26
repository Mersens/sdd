package com.shangdingdai.view;

import java.util.ArrayList;
import java.util.List;

import com.shangdingdai.adapter.GuideViewPagerAdapter;
import com.shangdingdai.fragment.GuideFragment1;
import com.shangdingdai.fragment.GuideFragment2;
import com.shangdingdai.fragment.GuideFragment3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class GuideActivity extends FragmentActivity{
	private ViewPager viewPage;
	private GuideFragment1 mFragment1;
	private GuideFragment2 mFragment2;
	private GuideFragment3 mFragment3;
	private PagerAdapter mPgAdapter;
	private RadioGroup dotLayout;
	private List<Fragment> mListFragment = new ArrayList<Fragment>();
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_guide);
		initView();
		initEvent();
		
	}

	private void initEvent() {
		mPgAdapter = new GuideViewPagerAdapter(getApplicationContext(),getSupportFragmentManager(),
				mListFragment);
		viewPage.setAdapter(mPgAdapter);
		viewPage.setOnPageChangeListener(new MyPagerChangeListener());
	}

	private void initView() {
		dotLayout = (RadioGroup) findViewById(R.id.advertise_point_group);
		viewPage = (ViewPager) findViewById(R.id.viewpager);
		mFragment1=new GuideFragment1();
		mFragment2=new GuideFragment2();
		mFragment3=new GuideFragment3();
		mListFragment.add(mFragment1);
		mListFragment.add(mFragment2);
		mListFragment.add(mFragment3);

	}
	
	public class MyPagerChangeListener implements OnPageChangeListener {

		public void onPageSelected(int position) {
			
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			((RadioButton) dotLayout.getChildAt(position)).setChecked(true);
		}

	}
}
