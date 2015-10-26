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

public class NewTelActivity extends BaseActivity implements OnClickListener {
	public ImageView mImageView;
	public TextView mTextView;
	private EditText et_newtel;
	private EditText et_newtel_code;
	private Button btn_newtel_send;
	private Button btn_newtel_ok;
	private Intent mIntent;
	private String newtel;
	private String num;
	private SendAsyncTask sat;
	private MyAsyncTask mt;
	private Map<String, String> datas;
	private static final String sendurl = Constants.SERVICE_ADDRESS+"send_sms/sendSmsUntil";
	private static final String URL = Constants.SERVICE_ADDRESS+"myinfo/changeUserPhone";
	/**
	 * 倒计时Handler
	 */
	@SuppressLint("HandlerLeak")
	Handler mCodeHandler = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.what == RegisterCodeTimer.IN_RUNNING) {// 正在倒计时
				btn_newtel_send.setText(msg.obj.toString());
				btn_newtel_send.setClickable(false);
				btn_newtel_send.setBackgroundResource(R.color.next_color);
			} else if (msg.what == RegisterCodeTimer.END_RUNNING) {// 完成倒计时
				btn_newtel_send.setClickable(true);
				btn_newtel_send.setText(msg.obj.toString());
				btn_newtel_send
						.setBackgroundResource(R.drawable.btn_login_selector);
			}
			switch (msg.arg1) {
			case 6:
				// 未找到用户信息
				ShowToast("未找到用户信息!");
				break;
			case 1:
				// 手机号码修改成功
				ShowToast("手机号码修改成功!");
				doComplete();
				break;
			case 2:
				// 未找到用户绑定的手机号码
				ShowToast("未找到用户绑定的手机号码!");
				break;
			case 3:
				// 手机号码不能为空
				ShowToast("手机号码不能为空!");
				break;
			case 4:
				// 该手机号码已被绑定
				ShowToast("该手机号码已被绑定!");
				break;
			case 5:
				// 手机号码修改失败
				ShowToast("手机号码修改失败!");
				break;
			}

		}
	};
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(mt!=null && mt.getStatus()==AsyncTask.Status.RUNNING){
			mt.cancel(true);
		}
		if(sat!=null && sat.getStatus()==AsyncTask.Status.RUNNING){
			sat.cancel(true);
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newtel);
		RegisterCodeTimerService.setHandler(mCodeHandler);
		mIntent = new Intent(NewTelActivity.this,
				RegisterCodeTimerService.class);
		initViews();
		initEvents();
	}

	private void initViews() {
		mImageView = (ImageView) findViewById(R.id.img_back);
		mTextView = (TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.bdxsj);
		mImageView.setVisibility(View.VISIBLE);
		et_newtel = (EditText) findViewById(R.id.et_newtel);
		et_newtel_code = (EditText) findViewById(R.id.et_newtel_code);
		btn_newtel_send = (Button) findViewById(R.id.btn_newtel_send);
		btn_newtel_ok = (Button) findViewById(R.id.btn_newtel_ok);
	}

	private void initEvents() {
		btn_newtel_send.setOnClickListener(this);
		btn_newtel_ok.setOnClickListener(this);
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finishActivity();
			}
		});
	}

	public void doComplete() {
		SharePreferenceUtil.getInstance(getApplicationContext()).setUserTel(
				datas.get("tel"));
		SharePreferenceUtil.getInstance(getApplicationContext()).setPhone(true);
		intentAction(NewTelActivity.this, CertificateActivity.class);
		finish();
	}

	public void doSend() {
		newtel = et_newtel.getText().toString().trim();
		if (!StringUtils.isMobileNum(newtel)) {
			ShowToast("手机格式不正确！");
			return;
		}
		if (TextUtils.isEmpty(newtel)) {
			ShowToast("手机号码不能为空！");
			return;
		}
		if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
			ShowToast("没有可用网络，请检查网络设置!");
			return;
		}
		startService(mIntent);
		datas = new HashMap<String, String>();
		num = StringUtils.getRandom();// 验证码
		datas.put("code", num);
		datas.put("tel", newtel);
		String str1 = "尊敬的用户，您好！您的验证码是：" + num;
		String str2 = ",欢迎使用商鼎贷。如非本人操作请致电：400-168-3966。" + "【商鼎贷】";
		String center = str1 + str2;// 发送短信内容
		sat = new SendAsyncTask();
		sat.execute(newtel, num, center);
	}

	public void doSubmit() {
		String strtel = et_newtel.getText().toString().trim();
		String strcode = et_newtel_code.getText().toString().trim();
		if (!StringUtils.isMobileNum(strtel)) {
			ShowToast("手机格式不正确！");
			return;
		}
		if (TextUtils.isEmpty(strtel)) {
			ShowToast("手机号码不能为空！");
			return;
		}
		if (TextUtils.isEmpty(strcode)) {
			ShowToast("验证码不能为空！");
			return;
		}

		if (!strcode.equals(datas.get("code"))) {
			ShowToast("验证码填写有误！");
			return;
		}
		if (!strtel.equals(datas.get("tel"))) {
			ShowToast("手机号码不一致！");
			return;
		}
		if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
			ShowToast("没有可用网络，请检查网络设置!");
			return;
		}

		doBunding();

	}

	public void doBunding() {
		String oldtel = SharePreferenceUtil
				.getInstance(getApplicationContext()).getUserTel();
		String newtel = datas.get("tel");
		String userid = SharePreferenceUtil
				.getInstance(getApplicationContext()).getUseId();

		 mt = new MyAsyncTask();
		mt.execute(userid, oldtel, newtel);

	}

	class MyAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", params[0]);
			map.put("oldphone", params[1]);
			map.put("newphone", params[2]);
			String str = HttpPostUtils.doPost(URL, map);
			return JsonUtils.getCode(str);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Message msg = new Message();
			if ("0".equals(result)) {
				msg.arg1 = 6;
			} else if ("1".equals(result)) {
				msg.arg1 = 1;
			} else if ("2".equals(result)) {
				msg.arg1 = 2;
			} else if ("3".equals(result)) {
				msg.arg1 = 3;
			} else if ("4".equals(result)) {
				msg.arg1 = 4;
			} else if ("5".equals(result)) {
				msg.arg1 = 5;
			}
			mCodeHandler.sendMessage(msg);
		}
	}

	class SendAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userphone", params[0]);
			map.put("phonecode", params[1]);
			map.put("center", params[2]);
			map.put("typeid", "bangdingshouji");
			String str = HttpPostUtils.doPost(sendurl, map);
			return JsonUtils.getCode(str);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(mIntent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_newtel_send:
			doSend();
			break;
		case R.id.btn_newtel_ok:
			doSubmit();
			break;

		default:
			break;
		}
	}
}
