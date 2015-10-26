package com.shangdingdai.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangdingdai.bean.ImageBean;
import com.shangdingdai.db.ImageDao;
import com.shangdingdai.db.ImageDaoImpl;
import com.shangdingdai.utils.ImageLoadOptions;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.view.CircleImageView;
import com.shangdingdai.view.Lock9View;
import com.shangdingdai.view.R;

public class GesturePsdActivity extends BaseActivity {
	public ImageView mImageView;
	public TextView mTextView;
	private Lock9View lock9View;
	private CircleImageView user_img;
	private ImageDao dao;
	private Map<String, String> map = null;
	private boolean isFirst = true;
	private boolean isSecond = false;
	private TextView tv_hint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesture_psd);
		dao = new ImageDaoImpl(this);
		initViews();
		initDatas();
		initEvents();
	}

	private void initViews() {
		mImageView = (ImageView) findViewById(R.id.img_back);
		mTextView = (TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.gesture_psd);
		mImageView.setVisibility(View.VISIBLE);
		lock9View = (Lock9View) findViewById(R.id.lock_9_view);
		user_img = (CircleImageView) findViewById(R.id.user_img);
		tv_hint = (TextView) findViewById(R.id.tv_hint);
	}

	private void initEvents() {
		// TODO Auto-generated method stub
		mImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharePreferenceUtil.getInstance(getApplicationContext()).setIsFirstLogin(false);
				finishActivity();
			}
		});
		lock9View.setCallBack(new Lock9View.CallBack() {
			@Override
			public void onFinish(String password) {
				doDealWithPsd(password);
			}

		});
	}

	private void initDatas() {
		String id = SharePreferenceUtil.getInstance(getApplicationContext())
				.getUseId();
		List<ImageBean> bean = dao.select(id);
		if (bean != null && bean.size() > 0) {
			ImageLoader.getInstance().displayImage(bean.get(0).getUrl(),
					user_img, ImageLoadOptions.getOptions());
		} else {
			user_img.setImageResource(R.drawable.icon_user_img);
		}
	}

	public void doDealWithPsd(String psd) {
		if (psd.length() <= 3) {
			ShowToast(R.string.gesture_psd_hint_psd_short);
			return;
		}
		if (isFirst && !isSecond) {
			isFirst = false;
			isSecond = true;
			map = new HashMap<String, String>();
			map.put("password", psd);
			tv_hint.setText(R.string.gesture_psd_hint_psd_again);
			ShowToast(R.string.gesture_psd_hint_psd_again);
			return;
		}
		if (isSecond && !isFirst) {
			if (psd.equals(map.get("password"))) {
				tv_hint.setText(R.string.gesture_psd_hint_psd_success);
				SharePreferenceUtil.getInstance(getApplicationContext()).setHasGesturePsd(true);
				SharePreferenceUtil.getInstance(getApplicationContext()).setGesturePsd(psd);
				SharePreferenceUtil.getInstance(getApplicationContext()).setIsFirstLogin(false);
				SharePreferenceUtil.getInstance(getApplicationContext()).setUnLock(true);
				ShowToast(R.string.gesture_psd_hint_psd_success);
				finishActivity();
			} else {
				ShowToast(R.string.gesture_psd_hint_psd_difference);
				tv_hint.setText(R.string.gesture_psd_hint_psd_difference);
			}
		}
	}
}
