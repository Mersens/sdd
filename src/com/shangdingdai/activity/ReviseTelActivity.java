package com.shangdingdai.activity;

import java.util.HashMap;
import java.util.Map;

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
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.R;

public class ReviseTelActivity extends BaseActivity implements OnClickListener {
	public ImageView mImageView;
	public TextView mTextView;
	private TextView tv_telnum;
	private EditText et_code;
	private Button btn_send;
	private Button btn_ok;
	private String strcode;
	private Intent mIntent;
	private String num;
	private SendAsyncTask sat;
	private static final String sendurl = Constants.SERVICE_ADDRESS
			+ "send_sms/sendSmsUntil";
	/**
	 * 倒计时Handler
	 */
	@SuppressLint("HandlerLeak")
	Handler mCodeHandler = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.what == RegisterCodeTimer.IN_RUNNING) {// 正在倒计时
				btn_send.setText(msg.obj.toString());
				btn_send.setClickable(false);
				btn_send.setBackgroundResource(R.color.next_color);
			} else if (msg.what == RegisterCodeTimer.END_RUNNING) {// 完成倒计时
				btn_send.setClickable(true);
				btn_send.setText(msg.obj.toString());
				btn_send.setBackgroundResource(R.drawable.btn_login_selector);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revisetel);
		RegisterCodeTimerService.setHandler(mCodeHandler);
		mIntent = new Intent(ReviseTelActivity.this,
				RegisterCodeTimerService.class);
		initViews();
		initEvents();
	}

	private void initViews() {
		mImageView = (ImageView) findViewById(R.id.img_back);
		mTextView = (TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.xgbdsj);
		mImageView.setVisibility(View.VISIBLE);
		tv_telnum = (TextView) findViewById(R.id.tv_telnum);
		String tel = SharePreferenceUtil.getInstance(getApplicationContext())
				.getUserTel();
		String telNum = StringUtils.getTelNum(tel);
		tv_telnum.setText(telNum);
		et_code = (EditText) findViewById(R.id.et_code);
		btn_send = (Button) findViewById(R.id.btn_send);
		btn_send.setEnabled(true);
		btn_ok = (Button) findViewById(R.id.btn_ok);
	}

	private void initEvents() {
		btn_send.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
		mImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});
	}

	public void doNext() {
		strcode = et_code.getText().toString().trim();
		if (TextUtils.isEmpty(strcode)) {
			ShowToast("验证码不能为空！");
			return;
		}

		if (!strcode.equals(num)) {
			ShowToast("验证码填写有误！");
			return;
		}

		Intent intent = new Intent(ReviseTelActivity.this, NewTelActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		finish();

	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(sat!=null && sat.getStatus()==AsyncTask.Status.RUNNING){
			sat.cancel(true);
		}
	}
	public void doSend() {
		if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
			ShowToast("没有可用网络，请检查网络设置!");
			return;
		}
		startService(mIntent);
		num = StringUtils.getRandom();// 验证码
		String str1 = "尊敬的用户，您好！您的验证码是：" + num;
		String str2 = ",欢迎使用商鼎贷。如非本人操作请致电：400-168-3966。" + "【商鼎贷】";
		String center = str1 + str2;// 发送短信内容
		String tel = SharePreferenceUtil.getInstance(getApplicationContext())
				.getUserTel();
		sat = new SendAsyncTask();
		sat.execute(tel, num, center);
	}

	class SendAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userphone", params[0]);
			map.put("phonecode", params[1]);
			map.put("center", params[2]);
			map.put("typeid", "jiebangshouji");
			String str = HttpPostUtils.doPost(sendurl, map);
			return JsonUtils.getCode(str);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:
			doSend();
			break;
		case R.id.btn_ok:
			doNext();
			break;

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(mIntent);
	}

}
