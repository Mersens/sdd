package com.shangdingdai.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import com.shangdingdai.view.R;

public class FragmentBase extends Fragment {
	public LayoutInflater mInflater;
	public View mView;
	public ImageView mImageView;
	public TextView mTextView;
	public Toast mToast;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		mInflater = LayoutInflater.from(getActivity());
	}
	
	public void initTopBarBack(View v){
		mImageView=(ImageView) v.findViewById(R.id.img_back);
		mImageView.setVisibility(View.VISIBLE);
	}
	
	public void initTopBarTitle(String title,View v){
		mTextView=(TextView) v.findViewById(R.id.txt_title);
		mTextView.setText(title);
	}
	
	public void initTopBarBackAndText(String title,View v){
		mImageView=(ImageView) v.findViewById(R.id.img_back);
		mTextView=(TextView) v.findViewById(R.id.txt_title);
		mTextView.setText(title);
		mImageView.setVisibility(View.VISIBLE);
	}
	
	public void ShowToast(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}

	public void ShowToast(int text) {
		if (mToast == null) {
			mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_LONG);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}
	
	public <T> void intentAction(Activity context, Class<T> cls) {
		Intent intent = new Intent(context, cls);
		startActivity(intent);
		context.overridePendingTransition(R.anim.push_left_in,
				R.anim.push_left_out);
	}
	@Override
	public void onResume() {
		JPushInterface.onResume(getActivity().getApplicationContext());
		super.onResume();
	}


	@Override
	public void onPause() {
		JPushInterface.onPause(getActivity().getApplicationContext());
		super.onPause();
	}
	

}
