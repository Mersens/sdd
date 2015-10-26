package com.shangdingdai.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangdingdai.applcation.Constants;
import com.shangdingdai.service.RegisterCodeTimerService;
import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.JsonUtils;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.utils.RegisterCodeTimer;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.view.R;

public class UpdateEmailActivity extends BaseActivity implements
		OnClickListener {
	public ImageView mImageView;
	public TextView mTextView;
	private TextView tv_old_email;
	private EditText et_email_code;
	private Button btn_email_send;
	private Button btn_email_ok;
	private Intent mIntent;
	private String yzm_num;
	private MyAsyncTask mt;
	private static final String SENDURL = Constants.SERVICE_ADDRESS
			+ "myinfo/sendYanzhengmaToJiebangEmail";
	/**
	 * 倒计时Handler
	 */
	@SuppressLint("HandlerLeak")
	Handler mCodeHandler = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.what == RegisterCodeTimer.IN_RUNNING) {// 正在倒计时
				btn_email_send.setText(msg.obj.toString());
				btn_email_send.setClickable(false);
				btn_email_send.setBackgroundResource(R.color.next_color);
			} else if (msg.what == RegisterCodeTimer.END_RUNNING) {// 完成倒计时
				btn_email_send.setClickable(true);
				btn_email_send.setText(msg.obj.toString());
				btn_email_send
						.setBackgroundResource(R.drawable.btn_login_selector);
			}

			switch (msg.arg1) {
			case 1:
				// 邮件发送成功
				ShowToast("邮件发送成功 !");
				break;
			case 2:
				// 邮件发送失败
				ShowToast("邮件发送失败!");
				break;
			case 3:
				// 找不到用户信息
				ShowToast("找不到用户信息!");
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_email);
		RegisterCodeTimerService.setHandler(mCodeHandler);
		mIntent = new Intent(UpdateEmailActivity.this,
				RegisterCodeTimerService.class);
		initViews();
		initEvents();
	}

	private void initViews() {
		mImageView = (ImageView) findViewById(R.id.img_back);
		mTextView = (TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.xgbdyx);
		mImageView.setVisibility(View.VISIBLE);
		tv_old_email = (TextView) findViewById(R.id.tv_old_email);
		String email = SharePreferenceUtil.getInstance(getApplicationContext())
				.getUserEmail();
		tv_old_email.setText(email);
		et_email_code = (EditText) findViewById(R.id.et_email_code);
		btn_email_send = (Button) findViewById(R.id.btn_email_send);
		btn_email_ok = (Button) findViewById(R.id.btn_email_ok);

	}

	private void initEvents() {
		btn_email_send.setOnClickListener(this);
		btn_email_ok.setOnClickListener(this);
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mt != null && mt.getStatus() == AsyncTask.Status.RUNNING) {
			mt.cancel(true);
		}
	}

	public void doSend() {
		if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
			ShowToast("没有可用网络，请检查网络设置!");
			return;
		}
		startService(mIntent);
		String useremail = SharePreferenceUtil.getInstance(
				getApplicationContext()).getUserEmail();
		String userid = SharePreferenceUtil
				.getInstance(getApplicationContext()).getUseId();
		mt = new MyAsyncTask();
		mt.execute(userid, useremail);
	}

	class MyAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", params[0]);
			map.put("email", params[1]);
			String str = HttpPostUtils.doPost(SENDURL, map);
			try {
				JSONObject jsonObject = new JSONObject(str);
				yzm_num = jsonObject.getString("yanzhengma");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return JsonUtils.getCode(str);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Message msg = new Message();
			if ("0".equals(result)) {
				msg.arg1 = 3;
			} else if ("1".equals(result)) {
				msg.arg1 = 1;
			} else if ("2".equals(result)) {
				msg.arg1 = 2;
			}
			mCodeHandler.sendMessage(msg);
		}
	}

	public void doNext() {
		String str = et_email_code.getText().toString().trim();
		if (TextUtils.isEmpty(str)) {
			ShowToast("验证码不能为空！");
			return;
		}

		if (!str.equals(yzm_num)) {
			ShowToast("验证码填写有误！");
			return;
		}

		Intent intent = new Intent(UpdateEmailActivity.this,
				RegisterNewActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(mIntent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_email_send:
			doSend();
			break;
		case R.id.btn_email_ok:
			doNext();
			break;
		}

	}

}
