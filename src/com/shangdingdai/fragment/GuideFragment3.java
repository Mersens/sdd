package com.shangdingdai.fragment;

import com.shangdingdai.utils.GuidedUtil;
import com.shangdingdai.view.MainActivity;
import com.shangdingdai.view.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class GuideFragment3 extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_guide3, container, false);
		TextView tv=(TextView) v.findViewById(R.id.tvInNew);
		tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setGuided();
				Intent intent=new Intent(getActivity(),MainActivity.class);
				getActivity().startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
				getActivity().finish();
			}
		});
		
		return v;
	}
	public void setGuided() {
		GuidedUtil.getInstance(getActivity().getApplicationContext()).setIsFirst(false);

	}
}
