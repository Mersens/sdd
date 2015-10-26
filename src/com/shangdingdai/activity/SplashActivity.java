package com.shangdingdai.activity;

import cn.jpush.android.api.JPushInterface;

import com.shangdingdai.utils.GuidedUtil;
import com.shangdingdai.view.GuideActivity;
import com.shangdingdai.view.MainActivity;
import com.shangdingdai.view.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

@SuppressLint("HandlerLeak")
public class SplashActivity extends BaseActivity {
    boolean isFirst = false;
    private static final int GO_HOME = 0X00;
    private static final int GO_GUIDE = 0X01;
    // 延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 3000;
    public static  String SHAREDPREFERENCES_NAME = "first_pref";
    /**
     * Handler:跳转到不同界面
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case GO_HOME:
            	intentAction(SplashActivity.this,MainActivity.class);
            	finish();
            	break;
            case GO_GUIDE:
            	intentAction(SplashActivity.this,GuideActivity.class);
            	finish();
                break;
            }
            super.handleMessage(msg);
        }
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		ImageView mImageView=(ImageView) findViewById(R.id.image);
		mImageView.setBackgroundResource(R.drawable.icon_start);
		init();
	}

	private void init() {
		isFirst=GuidedUtil.getInstance(getApplicationContext()).isFirst();
		if(isFirst){
			 mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
		}else{
			 mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
	}


	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}
}
