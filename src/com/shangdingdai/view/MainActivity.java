package com.shangdingdai.view;

import com.shangdingdai.activity.GesturePsdActivity;
import com.shangdingdai.activity.LoginActivity;
import com.shangdingdai.activity.UnlockActivity;
import com.shangdingdai.applcation.CustomApplcation;
import com.shangdingdai.fragment.AccountFragment;
import com.shangdingdai.fragment.HomeFragment;
import com.shangdingdai.fragment.MoreFragment;
import com.shangdingdai.utils.ExampleUtil;
import com.shangdingdai.utils.SharePreferenceUtil;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements OnClickListener {
	private AccountFragment accountFragment;
	private HomeFragment homeFragment;
	private MoreFragment moreFragment;
	private Fragment[] fragments;

	private ImageView img_home;
	private ImageView img_account;
	private ImageView img_more;

	private RelativeLayout layout_home;
	private RelativeLayout layout_account;
	private RelativeLayout layout_more;
	private RelativeLayout[] mTabs;

	private int index;
	private int currentTabIndex;
	boolean isLogin;
	boolean login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		CustomApplcation.getInstance().addActivity(this);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		registerMessageReceiver(); 
		initViews();
		initEvents();

	}

	@Override
	protected void onResume() {
		isForeground = true;
		super.onResume();
	}


	@Override
	protected void onPause() {
		isForeground = false;
		super.onPause();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		SharePreferenceUtil.getInstance(getApplicationContext())
				.setIsFirstGesturePsd(true);

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		boolean isScreenOn = pm.isScreenOn();
		if (isScreenOn) {
			SharePreferenceUtil.getInstance(getApplicationContext()).setUnLock(
					true);
		} else {
			SharePreferenceUtil.getInstance(getApplicationContext()).setUnLock(
					false);
		}
	
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		doUnlock();
	}

	boolean isHasGesturePsd;
	boolean isFirstGesturePsd;
	boolean isFirstLogin;
	boolean isUnlock ;

	private void doUnlock() {
		isHasGesturePsd = SharePreferenceUtil.getInstance(
				getApplicationContext()).isHasGesturePsd();
		isFirstGesturePsd = SharePreferenceUtil.getInstance(
				getApplicationContext()).isFirstGesturePsd();
		isFirstLogin = SharePreferenceUtil.getInstance(getApplicationContext())
				.isFirstLogin();
		isUnlock = SharePreferenceUtil.getInstance(getApplicationContext())
				.isUnLock();
		if (isHasGesturePsd && isFirstGesturePsd && !isUnlock) {
			intentAction(MainActivity.this, UnlockActivity.class);
		} else if (!isHasGesturePsd && isFirstLogin) {
			intentAction(MainActivity.this, GesturePsdActivity.class);
		}
	}

	private void initViews() {
		isLogin = getIntent().getBooleanExtra("isLogin", false);
		login = SharePreferenceUtil.getInstance(getApplicationContext())
				.isLogin();
		homeFragment = new HomeFragment();
		accountFragment = new AccountFragment();
		moreFragment = new MoreFragment();
		fragments = new Fragment[] { homeFragment, accountFragment,
				moreFragment };

		mTabs = new RelativeLayout[3];
		layout_home = (RelativeLayout) findViewById(R.id.layout_home);
		layout_account = (RelativeLayout) findViewById(R.id.layout_account);
		layout_more = (RelativeLayout) findViewById(R.id.layout_more);
		img_home = (ImageView) findViewById(R.id.img_home);
		img_account = (ImageView) findViewById(R.id.img_account);
		img_more = (ImageView) findViewById(R.id.img_more);

		mTabs[0] = layout_home;
		mTabs[1] = layout_account;
		mTabs[2] = layout_more;
		if (isLogin) {
			index = 1;
			currentTabIndex = 1;
			setTab(1);
			mTabs[1].setSelected(true);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.fragment_container, accountFragment)
					.show(homeFragment).commit();
		} else {
			mTabs[0].setSelected(true);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.fragment_container, homeFragment)
					.show(homeFragment).commit();
		}
	}

	private void initEvents() {
		layout_home.setOnClickListener(this);
		layout_account.setOnClickListener(this);
		layout_more.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_home:
			index = 0;
			setTab(0);
			break;
		case R.id.layout_account:
			if (login) {
				index = 1;
				setTab(1);
			} else {
				Intent intent = new Intent(MainActivity.this,
						LoginActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
			break;
		case R.id.layout_more:
			index = 2;
			setTab(2);
			break;
		}

		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.remove(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		mTabs[currentTabIndex].setSelected(false);
		mTabs[index].setSelected(true);
		currentTabIndex = index;
	}

	private void setTab(int i) {
		resetImgs();
		switch (index) {
		case 0:
			img_home.setImageResource(R.drawable.icon_home_pressed);
			break;
		case 1:
			img_account.setImageResource(R.drawable.icon_account_pressed);
			break;
		case 2:
			img_more.setImageResource(R.drawable.icon_more_pressd);
			break;
		}
	}

	private void resetImgs() {
		img_home.setImageResource(R.drawable.icon_home_normal);
		img_account.setImageResource(R.drawable.icon_account_normal);
		img_more.setImageResource(R.drawable.icon_more_normal);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			this.confirmExit();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void confirmExit() {

		DialogTips dialog = new DialogTips(MainActivity.this, "退出", "是否退出软件？",
				"确定", true, true);
		dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int userId) {
				SharePreferenceUtil.getInstance(getApplicationContext())
						.setIsFirstGesturePsd(true);
				SharePreferenceUtil.getInstance(getApplicationContext())
				.setUnLock(false);
				CustomApplcation.getInstance().exit();
				finish();
			}
		});
		dialog.show();
		dialog = null;
	}

	public <T> void intentAction(Activity context, Class<T> cls) {
		Intent intent = new Intent(context, cls);
		startActivity(intent);
		context.overridePendingTransition(R.anim.push_left_in,
				R.anim.push_left_out);
	}
	
	
	//for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	public static boolean isForeground = false;
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
              String messge = intent.getStringExtra(KEY_MESSAGE);
              String extras = intent.getStringExtra(KEY_EXTRAS);
              StringBuilder showMsg = new StringBuilder();
              showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
              if (!ExampleUtil.isEmpty(extras)) {
            	  showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
              }

			}
		}
	}
}
