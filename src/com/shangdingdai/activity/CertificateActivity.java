package com.shangdingdai.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.R;

public class CertificateActivity  extends BaseActivity implements OnClickListener{
	public ImageView mImageView;
	public TextView mTextView;
	private RelativeLayout layout_smrz;
	private RelativeLayout layout_bdsj;
	private RelativeLayout layout_rzyx;
	private boolean isSmrz;
	private boolean isPhone;
	private boolean isRzyx;
	private ImageView image_smrz;
	private ImageView image_bdsj;
	private ImageView image_rzyx;
	private TextView tv_smrz_msg;
	private TextView tv_bdsj_msg;
	private TextView tv_rzyx_msg;
	private MyAsyncTask mt;
	public static final String USETINFO_URL = "https://appservice.shangdingdai.com/myinfo/getUserInfo";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		setContentView(R.layout.activity_certificate);
 		String userid=SharePreferenceUtil.getInstance(getApplicationContext()).getUseId();
 		mt=new MyAsyncTask();
 		mt.execute(userid);
	}
	
	private void initViews() {
		mImageView=(ImageView)findViewById(R.id.img_back);
		mTextView=(TextView)findViewById(R.id.txt_title);
		mTextView.setText(R.string.zhaq);
		mImageView.setVisibility(View.VISIBLE);
		layout_smrz=(RelativeLayout) findViewById(R.id.layout_smrz);
		layout_bdsj=(RelativeLayout) findViewById(R.id.layout_bdsj);
		layout_rzyx=(RelativeLayout) findViewById(R.id.layout_rzyx);
		image_smrz=(ImageView) findViewById(R.id.image_smrz);
		image_bdsj=(ImageView) findViewById(R.id.image_bdsj);
		image_rzyx=(ImageView) findViewById(R.id.image_rzyx);
	    tv_smrz_msg=(TextView) findViewById(R.id.tv_smrz_msg);
		tv_bdsj_msg=(TextView) findViewById(R.id.tv_bdsj_msg);
		tv_rzyx_msg=(TextView) findViewById(R.id.tv_rzyx_msg);
		
		initDatas();
	}
	
	private void initDatas() {
		// TODO Auto-generated method stub
		isSmrz=SharePreferenceUtil.getInstance(getApplicationContext()).isSmrz();
		isPhone=SharePreferenceUtil.getInstance(getApplicationContext()).isphone();
		isRzyx=SharePreferenceUtil.getInstance(getApplicationContext()).isRzyx();
		if(isSmrz){
			image_smrz.setImageResource(R.drawable.icon_yrz);
			tv_smrz_msg.setText(R.string.yrz_msg);
		}else{
			image_smrz.setImageResource(R.drawable.icon_wrz);
			tv_smrz_msg.setText(R.string.wrz_msg);
		}
		if(isPhone){
			image_bdsj.setImageResource(R.drawable.icon_yrz);
			String tel=SharePreferenceUtil.getInstance(getApplicationContext()).getUserTel();
			tv_bdsj_msg.setText(StringUtils.getTelNum(tel));
		}else{
			image_bdsj.setImageResource(R.drawable.icon_wrz);
			tv_bdsj_msg.setText(R.string.wrz_msg);
		}
		if(isRzyx){
			image_rzyx.setImageResource(R.drawable.icon_yrz);
			String email=SharePreferenceUtil.getInstance(getApplicationContext()).getUserEmail();
			tv_rzyx_msg.setText(email);
		}else{
			image_rzyx.setImageResource(R.drawable.icon_wrz);
			tv_rzyx_msg.setText(R.string.wrz_msg);
		}
	}
	private void initEvents() {
		layout_smrz.setOnClickListener(this);
		layout_bdsj.setOnClickListener(this);
		layout_rzyx.setOnClickListener(this);
		mImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});
	}
	
	class MyAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", params[0]);
			String str = HttpPostUtils.doPost(USETINFO_URL, map);
			return str;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			saveUserStatic(result);
	 		initViews();
	 		initEvents();
		}
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(mt!=null && mt.getStatus()==AsyncTask.Status.RUNNING){
			mt.cancel(true);
		}
	}
	public void saveUserStatic(String str) {
		try {
			JSONObject jsonObject = new JSONObject(str);
			String email=jsonObject.getString("usermail");
			if(!TextUtils.isEmpty(email)){
				SharePreferenceUtil.getInstance(getApplicationContext()).setRzyx(true);
				SharePreferenceUtil.getInstance(getApplicationContext()).setUserEmail(email);
			}
			String tel = jsonObject.getString("userphone");
			if(!TextUtils.isEmpty(tel)){
				SharePreferenceUtil.getInstance(getApplicationContext()).setPhone(true);
				SharePreferenceUtil.getInstance(getApplicationContext()).setUserTel(tel);
			}
			
			JSONObject obj = new JSONObject(jsonObject.getString("info"));
			if(obj.has("realname") && obj.has("shenfenzheng")){
				String shenfenzheng=obj.getString("shenfenzheng");
				if(shenfenzheng!=null && !"".equals(shenfenzheng)){
					SharePreferenceUtil.getInstance(getApplicationContext()).setSmrz(true);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_smrz:
			boolean isSmrz=SharePreferenceUtil.getInstance(getApplicationContext()).isSmrz();
			if(isSmrz){
				ShowToast("你已经实名认证!");
			}else{
				intentAction(CertificateActivity.this, SmrzActivity.class);
				finish();	
			}
			break;
		case R.id.layout_bdsj:
			intentAction(CertificateActivity.this, ReviseTelActivity.class);
			finish();
			break;
		case R.id.layout_rzyx:
			if(isRzyx){
				intentAction(CertificateActivity.this, UpdateEmailActivity.class);
				finish();
			}else{
			    intentAction(CertificateActivity.this, BundlingEmailActivity.class);
			    finish();
			}
			break;
		}
	}

	
}
