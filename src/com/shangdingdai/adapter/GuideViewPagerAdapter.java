package com.shangdingdai.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.view.MainActivity;
import com.shangdingdai.view.R;

public class GuideViewPagerAdapter extends FragmentPagerAdapter{

	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private Context context;

	public GuideViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	public GuideViewPagerAdapter(Context context,FragmentManager fragmentManager,
			List<Fragment> arrayList) {
		super(fragmentManager);
		this.fragmentList = arrayList;
		this.context=context;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}
	@Override
	public Object instantiateItem(View container, int position) {

		if(position==fragmentList.size()-1){
			TextView tv=(TextView) container.findViewById(R.id.tvInNew);
			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					setGuided();
					Intent intent=new Intent(context,MainActivity.class);
					context.startActivity(intent);
					((Activity) context).overridePendingTransition(R.anim.push_left_in,
							R.anim.push_left_out);
				}

			});
			
		}
		return fragmentList.get(position);
	}
	
	public void setGuided() {
		SharePreferenceUtil.getInstance(context).setIsFirst(false);
	}
}
