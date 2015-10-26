package com.shangdingdai.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import com.shangdingdai.applcation.CustomApplcation;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.view.R;

public class BaseActivity extends Activity {
	public View mView;
	public ImageView mImageView;
	public TextView mTextView;
	public Toast mToast;
	protected Activity context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		CustomApplcation.getInstance().addActivity(this);
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
			mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}

	public void ShowToast(int text) {
		if (mToast == null) {
			mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}
	
	/**
	 * 判断是否有网络连接
	 */
	public boolean isNetworkAvailable(Context context) {
		return NetworkUtils.isNetworkAvailable(context);
	}
	
	public <T> void intentAction(Activity context, Class<T> cls) {
		Intent intent = new Intent(context, cls);
		startActivity(intent);
		context.overridePendingTransition(R.anim.push_left_in,
				R.anim.push_left_out);

	}
	
	public void finishActivity(){
		finish();
		overridePendingTransition(R.anim.push_right_in,
				R.anim.push_right_out);
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
