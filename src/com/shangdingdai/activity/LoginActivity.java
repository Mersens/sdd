package com.shangdingdai.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.shangdingdai.bean.ImageBean;
import com.shangdingdai.db.ImageDao;
import com.shangdingdai.db.ImageDaoImpl;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.MainActivity;
import com.shangdingdai.view.R;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private ProgressDialog progress;
	private EditText et_username;
	private EditText et_password;
	private Button btn_login;
	private TextView tv_register;
	public TextView mTextView;
	public ImageView mImageView;
	private  ImageDao dao;
	public static final String URL = "https://appservice.shangdingdai.com/login/UserLogin";
	public static final String USETINFO_URL = "https://appservice.shangdingdai.com/myinfo/getUserInfo";
	@SuppressLint("HandlerLeak")
	Handler mCodeHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.arg1) {
			case 1:
				ShowToast("登陆成功！");
				intentAction(LoginActivity.this, MainActivity.class);
				SharePreferenceUtil.getInstance(getApplicationContext()).setIsFirstLogin(true);
				finish();
				break;
			case 2:
				ShowToast("用户名或密码为空！");
				break;
			case 3:
				ShowToast("同一个ip地址多次尝试数据!");
				break;
			case 4:
				ShowToast("您输入的用户名或密码错误!");
				break;
			}
		}
	};

	private void save(String id, String psd) {
		SharePreferenceUtil.getInstance(getApplicationContext()).setUserId(id);
		SharePreferenceUtil.getInstance(getApplicationContext())
				.setUserPsd(psd);
		SharePreferenceUtil.getInstance(getApplicationContext()).setLogin(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		dao=new ImageDaoImpl(getApplicationContext());
		initViews();
		initEvents();
	}

	private void initEvents() {
		btn_login.setOnClickListener(this);
		tv_register.setOnClickListener(this);
		mImageView.setOnClickListener(this);

	}

	private void initViews() {
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		tv_register = (TextView) findViewById(R.id.tv_register);
		mImageView = (ImageView) findViewById(R.id.img_back);
		mImageView.setVisibility(View.VISIBLE);
		mTextView = (TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.login);
		progress = new ProgressDialog(this);
		progress.setMessage("正在登陆...");
		progress.setCanceledOnTouchOutside(false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_register:
			intentAction(LoginActivity.this, RegisterActivity.class);
			break;
		case R.id.img_back:
			finishActivity();
			break;
		case R.id.btn_login:
			// 执行登陆
			doLogin();
			break;
		}
	}

	private void doLogin() {
		String name = et_username.getText().toString().trim();
		String psd = et_password.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			ShowToast("账号不能为空！");
			return;
		}
		if (StringUtils.isContainsSpace(name)) {
			ShowToast("账号包含非法字符！");
			return;
		}
		if (TextUtils.isEmpty(psd)) {
			ShowToast("密码不能为空！");
			return;
		}
		if (StringUtils.isContainsSpace(psd)) {
			ShowToast("密码包含非法字符！");
			return;
		}
		if (!StringUtils.isLeanth(psd)) {
			ShowToast("密码长度至少6位！");
			return;
		}
		
		if (!StringUtils.isLeanth(name)) {
			ShowToast("账号长度至少6位！");
			return;
		}
		if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
			ShowToast("没有可用网络，请检查网络设置!");
			return;
		}
		login(name, psd);
	}

	public void getUserInfo(String user_id) {
		HttpEntity httpentity = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userid", user_id));
		try {
			httpentity = new UrlEncodedFormEntity(params, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpPost httpPost = new HttpPost(USETINFO_URL);
		httpPost.setEntity(httpentity);
		HttpResponse httpResponse = null;
		JSONObject jsonObject;
		try {
			httpResponse = new DefaultHttpClient().execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			try {
				String result = EntityUtils.toString(httpResponse.getEntity());
				jsonObject = new JSONObject(result);
				String safelevel=jsonObject.getString("safelevel");
				SharePreferenceUtil.getInstance(getApplicationContext()).setSafelevel(safelevel);
				String tel = jsonObject.getString("userphone");
				String email=jsonObject.getString("usermail");
				String userbank=jsonObject.getString("yinhangkanum");
				SharePreferenceUtil.getInstance(getApplicationContext()).setUserBank(userbank);
				if(email!=null && !email.equals("")){
					SharePreferenceUtil.getInstance(getApplicationContext()).setRzyx(true);
					SharePreferenceUtil.getInstance(getApplicationContext()).setUserEmail(email);
				}else{
					SharePreferenceUtil.getInstance(getApplicationContext()).setRzyx(false);
				}
				String username=jsonObject.getString("username");
				SharePreferenceUtil.getInstance(getApplicationContext()).setUserName(
						username);
				String userphoneStatic = jsonObject.getString("userphoneStatic");
				if("1".equals(userphoneStatic)){
					SharePreferenceUtil.getInstance(getApplicationContext()).setPhone(true);
				}else{
					SharePreferenceUtil.getInstance(getApplicationContext()).setPhone(false);
				}
				String usermailStatic = jsonObject.getString("usermailStatic");
				if("1".equals(usermailStatic)){
					SharePreferenceUtil.getInstance(getApplicationContext()).setRzyx(true);
				}else{
					SharePreferenceUtil.getInstance(getApplicationContext()).setRzyx(false);
				}
				String strheadimg = jsonObject.getString("info");
				JSONObject obj = new JSONObject(strheadimg);
				String headimg=obj.getString("headimg");
				SharePreferenceUtil.getInstance(getApplicationContext()).setSmrz(obj.has("shenfenzheng"));
			  List<ImageBean>imglist=dao.select(user_id);
			  
			  JSONObject jn=new JSONObject(jsonObject.getString("zijin"));
			  String zhanghuyue = jn.getString("zhanghuyue");
			  SharePreferenceUtil.getInstance(getApplicationContext()).setUserZhye(zhanghuyue);
				if(imglist!=null && imglist.size()>0){
					dao.update(user_id, Constants.IC_USER_URL+headimg);
				}else{
					dao.add(user_id, Constants.IC_USER_URL+headimg);
				}
				SharePreferenceUtil.getInstance(getApplicationContext())
						.setUserTel(tel == null ? "" : tel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void login(final String username, final String password) {
		progress.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = null;
				HttpEntity httpentity = null;
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("password", password));
				try {
					httpentity = new UrlEncodedFormEntity(params, "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				HttpPost httpPost = new HttpPost(URL);
				httpPost.setEntity(httpentity);
				HttpResponse httpResponse = null;
				try {
					httpResponse = new DefaultHttpClient().execute(httpPost);
					if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						progress.dismiss();
						String result = EntityUtils.toString(httpResponse
								.getEntity());
						JSONObject jsonObject;
						msg = new Message();
						try {
							jsonObject = new JSONObject(result);
							String name = jsonObject.getString("code");
							if ("1".equals(name)) {
								String userid = jsonObject.getString("userid");
								getUserInfo(userid);
								save(userid,  password);
								msg.arg1 = 1;
								mCodeHandler.sendMessage(msg);
							} else if ("2".equals(name)) {
								msg.arg1 = 2;
								mCodeHandler.sendMessage(msg);

							} else if ("3".equals(name)) {
								msg.arg1 = 3;
								mCodeHandler.sendMessage(msg);

							} else if ("4".equals(name)) {
								msg.arg1 = 4;
								mCodeHandler.sendMessage(msg);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					progress.dismiss();
				}
			}
		}).start();
	}

}
