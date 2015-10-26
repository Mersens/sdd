package com.shangdingdai.activity;

import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.JsonUtils;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.MainActivity;
import com.shangdingdai.view.R;

public class SetAccountActivity extends BaseActivity implements OnClickListener {
	private ProgressDialog progress;
	private ProgressDialog progress_login;
	private  TextView mTextView;
	private ImageView mImageView;
	private EditText et_nick;
	private EditText et_psd;
	private EditText et_psd_again;
	private Button btn_register;
	private String temp;
	private String  user_id;
	private String psd;
	private String name;
	private String userphone;
	private static String url="https://appservice.shangdingdai.com/register/userRegister";
	@SuppressLint("HandlerLeak")
	Handler mCodeHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.arg1) {
			case 1:
                //注册成功 userid 用户id,执行登陆
				ShowToast("注册成功 !");
				login();
				break;
			case 2:
				//输入的用户名 密码 手机号码 有空值
				ShowToast("输入的用户名 密码 手机号码 有空值!");
				break;
			case 3:
				//手机号码已被注册
				ShowToast("手机号码已被注册!");
				break;
			case 4:
				//用户名已被占用了
				ShowToast("用户名已被占用了!");
				break;
			case 5:
				//账户名和密码相同
				ShowToast("账户名和密码相同!");
			case 6:
				//登陆成功！
				progress_login.dismiss();
				ShowToast("登陆成功!");
				save(user_id, name, psd,userphone);
				intentAction(SetAccountActivity.this,MainActivity.class);
				finish();
				break;
			case 7:
				//用户名或密码为空！
				ShowToast("用户名或密码为空！");
			case 8:
				//同一个ip地址多次尝试数据
				ShowToast("同一个ip地址多次尝试数据!");
			case 9:
				//您输入的用户名或密码错误
				ShowToast("您输入的用户名或密码错误!");
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setaccount);
		userphone=getIntent().getStringExtra("tel");
		initViews();
		initEvents();
	}

	
	public void login(){
		progress_login.show();
		 name=et_nick.getText().toString().trim();
		 psd=et_psd.getText().toString().trim();	
		LoginAsyncTask lat=new LoginAsyncTask();
		lat.execute(name,psd);
		
	}
	
	public void save(String id,String name,String psd,String userphone) {
		SharePreferenceUtil.getInstance(getApplicationContext()).setUserId(id);
		SharePreferenceUtil.getInstance(getApplicationContext()).setUserName(name);
		SharePreferenceUtil.getInstance(getApplicationContext()).setUserPsd(psd);
		SharePreferenceUtil.getInstance(getApplicationContext()).setUserTel(userphone);
		SharePreferenceUtil.getInstance(getApplicationContext()).setLogin(true);
	}
	
	private void initViews() {
		mImageView=(ImageView) findViewById(R.id.img_back);
		mImageView.setVisibility(View.VISIBLE);
		mTextView=(TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.set_account);	
		et_nick=(EditText) findViewById(R.id.et_nick);
		et_psd=(EditText) findViewById(R.id.et_psd);
		et_psd_again=(EditText) findViewById(R.id.et_psd_again);
		btn_register=(Button) findViewById(R.id.btn_register);
		progress=new ProgressDialog(this);
		progress.setMessage("正在保存...");
		progress.setCanceledOnTouchOutside(false);
		progress_login=new ProgressDialog(this);
		progress_login.setMessage("正在登陆...");
		progress_login.setCanceledOnTouchOutside(false);
		
	}

	private void initEvents() {	
		mTextView.setOnClickListener(this);
		mImageView.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finishActivity();
			break;
		case R.id.btn_register:
			doComplete();
			break;
		}
	}

	class LoginAsyncTask extends AsyncTask<String, Void, String>{
		@Override
		protected String doInBackground(String... params) {
			Map<String,String> map=new HashMap<String, String>();
			map.put("username", params[0]);
			map.put("password", params[1]);
			String str= HttpPostUtils.doPost(LoginActivity.URL, map);
			return str;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			String str=JsonUtils.getCode(result);
			Message msg=new Message();
			if("1".equals(str)){
				msg.arg1=6;
			}else if("2".equals(str)){
				msg.arg1=7;
			}else if("3".equals(str)){
				msg.arg1=8;
			}else if("4".equals(str)){
				msg.arg1=9;
			}

			mCodeHandler.sendMessage(msg);
		}
	}
	
	class MyAsyncTask extends AsyncTask<String, Void, String>{
		@Override
		protected String doInBackground(String... params) {
			Map<String,String> map=new HashMap<String, String>();
			map.put("userphone", params[0]);
			map.put("username", params[1]);
			map.put("userpwd", params[2]);
			String str= HttpPostUtils.doPost(url, map);
			return str;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			String str=JsonUtils.getCode(result);
			user_id=JsonUtils.getUserId(result);
			Message msg=new Message();
			if("1".equals(str)){
				msg.arg1=1;
			}else if("2".equals(str)){
				msg.arg1=2;
			}else if("3".equals(str)){
				msg.arg1=3;
			}else if("4".equals(str)){
				msg.arg1=4;
			}else{
				msg.arg1=5;
			}
			progress.dismiss();
			mCodeHandler.sendMessage(msg);
		}
	}
	
	private void doComplete() {
		String str=et_nick.getText().toString().trim();
		if(StringUtils.isContainsSpace(str)){
			ShowToast("昵称包含非法字符！");
			return;
		}
		if(TextUtils.isEmpty(str)){
			ShowToast("昵称不能为空！");
			return;
		}
		if (!StringUtils.isLeanth(str)) {
			ShowToast("昵称长度至少6位！");
			return;
		}
		String psd=et_psd.getText().toString().trim();
		if(StringUtils.isContainsSpace(psd)){
			ShowToast("密码包含非法字符！");
			return;
		}
		if(TextUtils.isEmpty(psd)){
			ShowToast("密码不能为空！");
			return;
		}
		if(!StringUtils.isLeanth(psd)){
			ShowToast("密码长度至少6位！");
			return;
		}
		if(!NetworkUtils.isNetworkAvailable(getApplicationContext())){
			ShowToast("没有可用网络，请检查网络设置!");
			return;
		}
		temp=psd;
		String psd_again=et_psd_again.getText().toString().trim();
		if(!temp.equals(psd_again)){
			ShowToast("确认密码不一致！");
			return;
		}
		progress.show();
		MyAsyncTask m=new MyAsyncTask();
		m.execute(userphone,str,psd);
	}
}
