package com.shangdingdai.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.view.R;
import com.shangdingdai.view.SlideSwitch;
import com.shangdingdai.view.SlideSwitch.SlideListener;

public class MoreFragment extends FragmentBase implements SlideListener {
	private TextView tv_kfdh_msg;
	private RelativeLayout layout_tel;
	private SlideSwitch switch_btn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_more, container, false);
		initTopBarTitle("更多", v);
		initViews(v);
		initEvents();
		return v;
	}

	private void initViews(View v) {
		tv_kfdh_msg = (TextView) v.findViewById(R.id.tv_kfdh_msg);
		layout_tel = (RelativeLayout) v.findViewById(R.id.layout_tel);
		switch_btn = (SlideSwitch) v.findViewById(R.id.switch_btn);
		boolean isNotify = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).isAllowPushNotify();
		if (isNotify) {
			switch_btn.setState(true);
		} else {
			switch_btn.setState(false);
		}
	}

	private void initEvents() {
		switch_btn.setSlideListener(this);
		layout_tel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String tel = tv_kfdh_msg.getText().toString().toString();
				String num = tel.replaceAll("-", "");
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ num));
				startActivity(intent);
			}
		});
	}

	@Override
	public void open() {
		SharePreferenceUtil.getInstance(getActivity().getApplicationContext())
				.setPushNotifyEnable(true);
		JPushInterface.resumePush(getActivity().getApplicationContext());
		
	}

	@Override
	public void close() {
		SharePreferenceUtil.getInstance(getActivity().getApplicationContext())
				.setPushNotifyEnable(false);
		JPushInterface.stopPush(getActivity().getApplicationContext());
	
	}
}
