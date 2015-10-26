package com.shangdingdai.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.R;

public class SmrzActivity extends BaseActivity{
	public ImageView mImageView;
	public TextView mTextView;
	private Button btn_submit;
	private EditText et_real_name;
	private EditText et_real_cardId;
	private String username;
	private String cardid;
	private static final String URL="https://appservice.shangdingdai.com/goto_Yibao/gotoRenzheng";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smrz);
		initViews();
		initEvents();
	}

	private void initViews() {
		
		mImageView=(ImageView)findViewById(R.id.img_back);
		mTextView=(TextView)findViewById(R.id.txt_title);
		mTextView.setText(R.string.smrz);
		mImageView.setVisibility(View.VISIBLE);
		btn_submit=(Button) findViewById(R.id.btn_submit);
		et_real_name=(EditText) findViewById(R.id.et_real_name);
		et_real_cardId=(EditText) findViewById(R.id.et_real_cardId);
	}

	private void initEvents() {
		mImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});
		
		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doLogin();
				
			}
		});
		
	}
	

	
	
	public void doLogin(){
		username=et_real_name.getText().toString().trim();
		cardid=et_real_cardId.getText().toString().trim();
		if (TextUtils.isEmpty(username)) {
			ShowToast("姓名不能为空！");
			return;
		}
		if (StringUtils.isContainsSpace(username)) {
			ShowToast("姓名包含非法字符！");
			return;
		}
		if (TextUtils.isEmpty(cardid)) {
			ShowToast("身份证不能为空！");
			return;
		}
		if (StringUtils.isContainsSpace(cardid)) {
			ShowToast("身份证包含非法字符！");
			return;
		}
		if(cardid.length()<18){
		    ShowToast("身份证长度不够！");
			return;
		}

		if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
			ShowToast("没有可用网络，请检查网络设置!");
			return;
		}
		String userid=SharePreferenceUtil.getInstance(getApplicationContext()).getUseId();
		login(username,cardid,userid);
		
	}
	
	public void login(String name,String cardid,String userid){
		Map<String,String> map=new HashMap<String, String>();
		map.put("xingming",name);
		map.put("shenfenzheng", cardid);
		map.put("userid",userid);
		String path=StringUtils.getGetUrl(URL, map);
		System.out.println(path);
		Intent intent=new Intent(SmrzActivity.this,SmrzWebViewActivity.class);
		intent.putExtra("path",path);
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in,
				R.anim.push_left_out);
	}
	
}
