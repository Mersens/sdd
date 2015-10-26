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
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.R;

public class RegisterNewActivity extends BaseActivity implements OnClickListener{
	public ImageView mImageView;
	public TextView mTextView;
	private EditText et_newemail;
	private Button btn_newemail_send;
	private EditText et_newemail_code;
	private Button btn_newemail_ok;
	private Intent mIntent;
	private static final String SENDURL = Constants.SERVICE_ADDRESS+"myinfo/sendYanzhengmaToBangdingEmail";
	private static final String URL = Constants.SERVICE_ADDRESS+"myinfo/changeUserEmail";
	private String stremail;
	private Map<String,String> datas;
	private String strcode;
	private MyAsyncTask mt;
	private SendAsyncTask st;
	/**
	 * 倒计时Handler
	 */
	@SuppressLint("HandlerLeak")
	Handler mCodeHandler = new Handler() {
		public void handleMessage(Message msg) {

			if (msg.what == RegisterCodeTimer.IN_RUNNING) {// 正在倒计时
				btn_newemail_send.setText(msg.obj.toString());
				btn_newemail_send.setClickable(false);
				btn_newemail_send.setBackgroundResource(R.color.next_color);
			} else if (msg.what == RegisterCodeTimer.END_RUNNING) {// 完成倒计时
				btn_newemail_send.setClickable(true);
				btn_newemail_send.setText(msg.obj.toString());
				btn_newemail_send.setBackgroundResource(R.drawable.btn_login_selector);
			}
			switch (msg.arg1) {
			case 1:
				//邮件发送成功 
				ShowToast("邮件发送成功 !");
				break;
			case 2:
				//邮件发送失败
				ShowToast("邮件发送失败!");
				break;
			case 3:
				//找不到用户信息
				ShowToast("找不到用户信息!");
				break;
			case 4:
				//找不到用户信息
				ShowToast("找不到用户信息!");
				break;
			case 5:
				//修改绑定邮箱成功 
				ShowToast("修改绑定邮箱成功 !");
				SharePreferenceUtil.getInstance(getApplicationContext()).setRzyx(true);
				SharePreferenceUtil.getInstance(getApplicationContext()).setUserEmail(datas.get("email"));
				intentAction(RegisterNewActivity.this, CertificateActivity.class);
				finish();
				break;
			case 6:
				//未找到用户绑定的邮箱 
				ShowToast("未找到用户绑定的邮箱 !");
				break;
			case 7:
				//邮箱不能为空
				ShowToast("邮箱不能为空!");
				break;
			case 8:
				//邮箱已被绑定
				ShowToast("邮箱已被绑定!");
				break;
			case 9:
				//邮箱输入不正确
				ShowToast("邮箱输入不正确!");
				break;
				
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_new_email);
		RegisterCodeTimerService.setHandler(mCodeHandler);
		mIntent = new Intent(RegisterNewActivity.this,
				RegisterCodeTimerService.class);
		initViews();
		initEventws();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mt != null && mt.getStatus() == AsyncTask.Status.RUNNING) {
			mt.cancel(true);
		}
		if (st != null && st.getStatus() == AsyncTask.Status.RUNNING) {
			st.cancel(true);
		}
	}
	private void initViews() {
		mImageView=(ImageView) findViewById(R.id.img_back);
		mTextView=(TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.bdxyx);
		mImageView.setVisibility(View.VISIBLE);
		et_newemail=(EditText) findViewById(R.id.et_newemail);
		btn_newemail_send=(Button) findViewById(R.id.btn_newemail_send);
		et_newemail_code=(EditText) findViewById(R.id.et_newemail_code);
		btn_newemail_ok=(Button) findViewById(R.id.btn_newemail_ok);
		
		
		
	}

	private void initEventws() {
		btn_newemail_send.setOnClickListener(this);
		btn_newemail_ok.setOnClickListener(this);
		mImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finishActivity();
			}
		});
		
	}

	
	public void doSend(){
		stremail=et_newemail.getText().toString().trim();
		if (!StringUtils.isEmail(stremail)) {
			ShowToast("邮箱格式不正确！");
			return;
		}
		if (TextUtils.isEmpty(stremail)) {
			ShowToast("邮箱不能为空！");
			return;
		}
		if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
			ShowToast("没有可用网络，请检查网络设置!");
			return;
		}
		datas=new HashMap<String, String>();
		datas.put("email", stremail);
		st=new SendAsyncTask();
		String userid=SharePreferenceUtil.getInstance(getApplicationContext()).getUseId();
		st.execute(userid,stremail);
	}
	
	public void doSubmit(){
		String code=et_newemail_code.getText().toString().trim();
		String str=et_newemail.getText().toString().trim();
		if (TextUtils.isEmpty(code)) {
			ShowToast("验证码不能为空！");
			return;
		}
		if(!code.equals(datas.get("code"))){
			ShowToast("验证码输入错误！");
			return;
		}
		if(!str.equals(datas.get("email"))){
			ShowToast("邮箱不一致！");
			return;
		}
		if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
			ShowToast("没有可用网络，请检查网络设置!");
		}
		String userid=SharePreferenceUtil.getInstance(getApplicationContext()).getUseId();
		mt=new MyAsyncTask();
		mt.execute(userid,datas.get("email"));
		
	}
	
	class MyAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", params[0]);
			map.put("newemail", params[1]);
			String str = HttpPostUtils.doPost(URL, map);
			return JsonUtils.getCode(str);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Message msg = new Message();
			if ("0".equals(result)) {
				msg.arg1 = 4;
			} else if ("1".equals(result)) {
				msg.arg1 = 5;
			} else if ("2".equals(result)) {
				msg.arg1 = 6;
			} else if ("3".equals(result)) {
				msg.arg1 = 7;
			} else if ("4".equals(result)) {
				msg.arg1 = 8;
			} else if ("5".equals(result)) {
				msg.arg1 = 9;
			}
			mCodeHandler.sendMessage(msg);
		}
	}

	class SendAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", params[0]);
			map.put("email", params[1]);
			String str = HttpPostUtils.doPost(SENDURL, map);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(str);
				strcode=jsonObject.getString("yanzhengma");
				datas.put("code", strcode);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return JsonUtils.getCode(str);
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Message msg = new Message();
			if ("0".equals(result)) {
				msg.arg1 = 3;
			} else if ("1".equals(result)) {
				msg.arg1 = 1;
			} else if("2".equals(result)) {
				msg.arg1 = 2;
			}
			mCodeHandler.sendMessage(msg);
			
		}
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
		case R.id.btn_newemail_send:
			doSend();
			break;
		case R.id.btn_newemail_ok:
			doSubmit();
			break;

		}
		
	}
	
}
