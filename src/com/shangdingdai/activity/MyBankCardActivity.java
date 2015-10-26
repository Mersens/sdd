package com.shangdingdai.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangdingdai.applcation.Constants;
import com.shangdingdai.bean.MyBankCardBean;
import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.ImageLoadOptions;
import com.shangdingdai.utils.JsonUtils;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.R;
public class MyBankCardActivity extends BaseActivity {
	public ImageView mImageView;
	public TextView mTextView;
	private LinearLayout layout_no_msg;
	private LinearLayout linear_main;
	private ProgressBar progressBar;
	private String userid;
	private MyBankCardBean bean;
	private ImageView image_bank;
	private TextView tv_bank;
	private TextView tv_kh;
	private static final String URL = Constants.SERVICE_ADDRESS+"myinfo/findYinhangkaByUserid";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mybankcard);
		userid = SharePreferenceUtil.getInstance(getApplicationContext()).getUseId();
		initViews();
		if (!NetworkUtils.isNetworkAvailable(
				getApplicationContext())) {
			ShowToast(R.string.tv_network_available);
			layout_no_msg.setVisibility(View.VISIBLE);
			linear_main.setVisibility(View.GONE);
		} else {
			progressBar.setVisibility(View.VISIBLE);
			MyAnsyTask mt=new MyAnsyTask();
			mt.execute(userid);
		}
		initEvents();
	}

	private void initViews() {
		mImageView = (ImageView) findViewById(R.id.img_back);
		mImageView.setVisibility(View.VISIBLE);
		mTextView = (TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.wdyhk);
		image_bank=(ImageView)findViewById(R.id.image_bank);
		tv_bank=(TextView) findViewById(R.id.tv_bank);
		tv_kh=(TextView) findViewById(R.id.tv_kh);
		linear_main=(LinearLayout) findViewById(R.id.linear_main);
		layout_no_msg = (LinearLayout) findViewById(R.id.layout_no_msg);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		
	}

	private void initEvents() {
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});
	}
	
	class MyAnsyTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", params[0]);
			String result = HttpPostUtils.doPost(URL, map);
			return result;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progressBar.setVisibility(View.GONE);
			try {
				bean=JsonUtils.getMyBankCard(result);
				if(bean!=null){
					initDatas();
				}else{
					layout_no_msg.setVisibility(View.VISIBLE);
					linear_main.setVisibility(View.GONE);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void initDatas() {
		// TODO Auto-generated method stub
		String bankNane=bean.getYinhang();
		String icon_bank_url=Constants.ICON_BANK+bankNane+".png";
		ImageLoader.getInstance().displayImage(icon_bank_url, image_bank,
				ImageLoadOptions.getOptions());
		tv_bank.setText(StringUtils.showBankName(bankNane));
		tv_kh.setText(bean.getKahao());
	}
}
