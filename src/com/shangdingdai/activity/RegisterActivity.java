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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangdingdai.service.RegisterCodeTimerService;
import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.JsonUtils;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.utils.RegisterCodeTimer;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.R;

public class RegisterActivity extends BaseActivity implements OnClickListener {
	private TextView mTextView;
	private ImageView mImageView;
	private TextView tv_link;
	private EditText tel_num;
	private Button btn_next;
	private Button btn_send;
	private Intent mIntent;
	private EditText et_code;
	private CheckBox cb;
	private static final String url = "https://appservice.shangdingdai.com/register/existUserphone";
	private static final String sendurl = "https://appservice.shangdingdai.com/send_sms/sendSmsUntil";
	private String num;
	private Map<String, String> datamap;
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
			if (msg.arg1 == 1) {
				// 手机号码可用
				startService(mIntent);
				doSendMSG();
			} else if (msg.arg1 == 2) {
				// 手机号码是空值
				ShowToast("手机号码是空值!");
			} else if (msg.arg1 == 3) {
				// 手机号码已被占用
				ShowToast("手机号码已被占用");
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		RegisterCodeTimerService.setHandler(mCodeHandler);
		mIntent = new Intent(RegisterActivity.this,
				RegisterCodeTimerService.class);
		initViews();
		initEvents();
	}

	public void doSendMSG() {
		datamap = new HashMap<String, String>();
		num = StringUtils.getRandom();
		String str1 = "尊敬的用户，您好！您的验证码是：" + num;
		String str2 = ",欢迎使用商鼎贷。如非本人操作请致电：400-168-3966。" + "【商鼎贷】";
		String center = str1 + str2;
		String userphone = tel_num.getText().toString().trim();
		datamap.put("phonecode", num);
		datamap.put("userphone", userphone);
		SendAsyncTask sat = new SendAsyncTask();
		sat.execute(userphone, num, center);
	}

	class SendAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userphone", params[0]);
			map.put("phonecode", params[1]);
			map.put("center", params[2]);
			map.put("typeid", "zhucexinxi");
			String str = HttpPostUtils.doPost(sendurl, map);
			return JsonUtils.getCode(str);
		}

	}

	private void initViews() {
		mImageView = (ImageView) findViewById(R.id.img_back);
		mImageView.setVisibility(View.VISIBLE);
		mTextView = (TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.register);
		tv_link = (TextView) findViewById(R.id.tv_link);
		tel_num = (EditText) findViewById(R.id.tel_num);
		btn_next = (Button) findViewById(R.id.btn_next);
		btn_send = (Button) findViewById(R.id.btn_send);
		btn_send.setEnabled(true);
		et_code = (EditText) findViewById(R.id.et_code);
		cb = (CheckBox) findViewById(R.id.checkbox);

	}

	private void initEvents() {
		mTextView.setOnClickListener(this);
		mImageView.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		btn_send.setOnClickListener(this);
		tv_link.setOnClickListener(this);
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (!isChecked) {
					btn_next.setBackgroundResource(R.color.next_color);
					btn_next.setClickable(false);
				} else {
					btn_next.setBackgroundResource(R.drawable.btn_login_selector);
					btn_next.setClickable(true);
				}
			}
		});
	}

	public void doSend() {
		String str = tel_num.getText().toString().trim();
		if (!StringUtils.isMobileNum(str)) {
			ShowToast("手机格式不正确！");
			return;
		}
		if (TextUtils.isEmpty(str)) {
			ShowToast("手机号码不能为空！");
			return;
		}
		if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
			ShowToast("没有可用网络，请检查网络设置!");
			return;
		}
		MyAsyncTask m = new MyAsyncTask();
		m.execute(str);
	}

	private void doNext() {
		String str = tel_num.getText().toString().trim();
		String code = et_code.getText().toString().trim();
		if (!StringUtils.isMobileNum(str)) {
			ShowToast("手机格式不正确！");
			return;
		}
		if (TextUtils.isEmpty(str)) {
			ShowToast("手机号码不能为空！");
			return;
		}
		if (TextUtils.isEmpty(code)) {
			ShowToast("验证码不能为空！");
			return;
		}

		if (!code.equals(datamap.get("phonecode"))) {
			ShowToast("验证码填写有误！");
			return;
		}
		if (!str.equals(datamap.get("userphone"))) {
			ShowToast("手机号码不一致！");
			return;
		}
		if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
			ShowToast("没有可用网络，请检查网络设置!");
			return;
		}
		Intent intent = new Intent(RegisterActivity.this,
				SetAccountActivity.class);
		intent.putExtra("tel", str);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		finish();

	}

	class MyAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userphone", params[0]);
			String str = HttpPostUtils.doPost(url, map);
			return JsonUtils.getCode(str);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Message msg = new Message();
			if ("1".equals(result)) {
				msg.arg1 = 1;
			} else if ("2".equals(result)) {
				msg.arg1 = 2;
			} else {
				msg.arg1 = 3;
			}
			mCodeHandler.sendMessage(msg);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finishActivity();
			break;
		case R.id.btn_next:
			doNext();
			break;
		case R.id.btn_send:
			doSend();
			break;
		case R.id.tv_link:
			intentAction(RegisterActivity.this, WebViewActivity.class);
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(mIntent);
	}

}
