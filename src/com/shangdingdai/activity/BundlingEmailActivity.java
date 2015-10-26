package com.shangdingdai.activity;

import java.util.HashMap;
import java.util.Map;

import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.JsonUtils;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.R;

import android.annotation.SuppressLint;
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

public class BundlingEmailActivity extends BaseActivity {
	public ImageView mImageView;
	public TextView mTextView;
	private EditText et_email;
	private Button btn_ok;
	private String stremail;
    public  static final String URL="https://appservice.shangdingdai.com/myinfo/bangdingUserEmail";
    @SuppressLint("HandlerLeak")
	Handler mCodeHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.arg1) {
			case 0:
				// 未找到用户信息
				ShowToast("未找到用户信息!");
				break;
			case 1:
				// 绑定成功
				ShowToast("绑定成功!");
				doComplete();
				break;
			case 2:
				//该邮箱已被绑定
				ShowToast("该邮箱已被绑定!");
				break;
			case 3:
				//邮箱输入不正确
				ShowToast("邮箱输入不正确!");
				break;
		}
	}
};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bundlingemail);
		initViews();
		initEvents();
	}

	public void doComplete(){
		SharePreferenceUtil.getInstance(getApplicationContext()).setRzyx(true);
		intentAction(BundlingEmailActivity.this,CertificateActivity.class);
		finish();
	}
	
	private void initViews() {
		mImageView=(ImageView)findViewById(R.id.img_back);
		mTextView=(TextView)findViewById(R.id.txt_title);
		mTextView.setText(R.string.rzyx);
		mImageView.setVisibility(View.VISIBLE);
		et_email=(EditText) findViewById(R.id.et_email);
		btn_ok=(Button) findViewById(R.id.btn_ok);
	}


	private void initEvents() {
		mImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});
		
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doBundlind();
				
			}
		});
	}
	
	
	public void doBundlind(){
		stremail=et_email.getText().toString().trim();
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
		String userid=SharePreferenceUtil.getInstance(getApplicationContext()).getUseId();
		MyAsyncTask mt=new MyAsyncTask();
		mt.execute(userid,stremail);
	}
	
	class MyAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", params[0]);
			map.put("email", params[1]);
			String str = HttpPostUtils.doPost(URL, map);
			return JsonUtils.getCode(str);
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Message msg = new Message();
			if ("0".equals(result)) {
				msg.arg1 = 0;
			} else if ("1".equals(result)) {
				msg.arg1 = 1;
			} else if("2".equals(result)){
				msg.arg1 = 2;
			}
			else if("3".equals(result)){
				msg.arg1 = 3;
			}

			mCodeHandler.sendMessage(msg);
		}
	}

    
}
