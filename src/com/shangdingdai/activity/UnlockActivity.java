package com.shangdingdai.activity;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangdingdai.applcation.CustomApplcation;
import com.shangdingdai.bean.ImageBean;
import com.shangdingdai.db.ImageDao;
import com.shangdingdai.db.ImageDaoImpl;
import com.shangdingdai.utils.ImageLoadOptions;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.view.CircleImageView;
import com.shangdingdai.view.DialogTips;
import com.shangdingdai.view.Lock9View;
import com.shangdingdai.view.R;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class UnlockActivity extends BaseActivity implements OnClickListener {
	private Lock9View lock9View;
	private CircleImageView gesture_user_img;
	private TextView draw_gesture_psd;
	private TextView login_again;
	private TextView forget_psd;
	private ImageDao dao;
	private int inputNum = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_unlock_view);
		dao = new ImageDaoImpl(this);
		initViews();
		initDatas();
		initEvents();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		lock9View = (Lock9View) findViewById(R.id.lock_9_view);
		gesture_user_img = (CircleImageView) findViewById(R.id.gesture_user_img);
		draw_gesture_psd = (TextView) findViewById(R.id.draw_gesture_psd);
		login_again = (TextView) findViewById(R.id.login_again);
		forget_psd = (TextView) findViewById(R.id.forget_psd);
	}

	private void initDatas() {
		// TODO Auto-generated method stub
		String id = SharePreferenceUtil.getInstance(getApplicationContext())
				.getUseId();
		List<ImageBean> bean = dao.select(id);
		if (bean != null && bean.size() > 0) {
			ImageLoader.getInstance().displayImage(bean.get(0).getUrl(),
					gesture_user_img, ImageLoadOptions.getOptions());
		} else {
			gesture_user_img.setImageResource(R.drawable.icon_user_img);
		}

		lock9View.setCallBack(new Lock9View.CallBack() {
			@SuppressLint({ "ClickableViewAccessibility", "ResourceAsColor" })
			@Override
			public void onFinish(String password) {
				if (password.length() <= 3) {
					ShowToast(R.string.gesture_psd_hint_psd_short);
					return;
				}

				String psd = SharePreferenceUtil.getInstance(
						getApplicationContext()).getGesturePsd();
				if (psd.equals(password)) {
					ShowToast(R.string.unlock_success);
					SharePreferenceUtil.getInstance(getApplicationContext())
					.setIsFirstGesturePsd(false);
					finishActivity();
				} else {
					inputNum = inputNum + 1;
					ShowToast(R.string.unlock_error);
					draw_gesture_psd.setText("密码错误！您还有" + (3 - inputNum)
							+ "次输入机会");
					if (inputNum == 3) {
						draw_gesture_psd.setText(R.string.psd_error);
						lock9View.setOnTouchListener(new OnTouchListener() {
							@Override
							public boolean onTouch(View v, MotionEvent event) {
								// TODO Auto-generated method stub
								return true;
							}
						});
						return;
					}
				}
			}
		});
	}

	private void initEvents() {
		// TODO Auto-generated method stub
		login_again.setOnClickListener(this);
		forget_psd.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			confirmExit();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void confirmExit() {
		DialogTips dialog = new DialogTips(UnlockActivity.this,"退出","是否退出软件？", "确定",true,true);
		dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int userId) {
				SharePreferenceUtil.getInstance(getApplicationContext()).setIsFirstGesturePsd(true);
				SharePreferenceUtil.getInstance(getApplicationContext())
				.setUnLock(false);
				CustomApplcation.getInstance().exit();
				finish();
			}
		});
		dialog.show();
		dialog = null;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_again:
			SharePreferenceUtil.getInstance(getApplicationContext()).clearData();
			intentAction(UnlockActivity.this,LoginActivity.class);
			finish();
			break;
		case R.id.forget_psd:
			SharePreferenceUtil.getInstance(getApplicationContext()).clearData();
			intentAction(UnlockActivity.this,LoginActivity.class);
			finish();
			break;
		}

	}
}
