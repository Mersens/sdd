package com.shangdingdai.activity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
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
import com.shangdingdai.view.DialogTips;
import com.shangdingdai.view.R;

/**
 * @author Mersens 提现
 */
public class DrawingActivity extends BaseActivity {
	public ImageView mImageView;
	public TextView mTextView;
	private LinearLayout layout_no_msg;
	private LinearLayout layout_progressBar;
	private MyBankCardBean bean;

	private ImageView image_bank;
	private TextView tv_bank;
	private TextView tv_kh;
	private EditText et_draw_money;
	private TextView tv_kyje;
	private RadioGroup txfs_radioGroup;
	private RadioButton pttx_radio;
	private RadioButton jjtx_radio;
	private TextView tv_txfy;
	private TextView tv_sjdz;
	private Button btn_ok;
	private String userid;
	private double kyye;
	private double sjdz;
	private double fy_money = 2;
	private boolean isBankType = false;
	private String type="0";
	private MyAnsyTask mt;
	public static final String BANK_URL = Constants.SERVICE_ADDRESS
			+ "myinfo/findYinhangkaByUserid";
	public static final String INFO_URL = Constants.SERVICE_ADDRESS
			+ "myinfo/getUserInfo";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawing);
		userid = SharePreferenceUtil.getInstance(getApplicationContext())
				.getUseId();
		initViews();
		initEvents();
		if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
			ShowToast(R.string.tv_network_available);
			layout_no_msg.setVisibility(View.VISIBLE);
		} else {
			layout_progressBar.setVisibility(View.VISIBLE);
			UserBankAnsyTask ubat = new UserBankAnsyTask();
			ubat.execute(userid);
			mt = new MyAnsyTask();
			mt.execute(userid);
		}
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mt != null && mt.getStatus() == AsyncTask.Status.RUNNING) {
			mt.cancel(true);
		}

	}

	private void initViews() {
		mImageView = (ImageView) findViewById(R.id.img_back);
		mTextView = (TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.tx);
		mImageView.setVisibility(View.VISIBLE);
		layout_no_msg = (LinearLayout) findViewById(R.id.layout_no_msg);
		layout_progressBar = (LinearLayout) findViewById(R.id.layout_progressBar);
		image_bank = (ImageView) findViewById(R.id.image_bank);
		tv_bank = (TextView) findViewById(R.id.tv_bank);
		tv_kh = (TextView) findViewById(R.id.tv_kh);
		et_draw_money = (EditText) findViewById(R.id.et_draw_money);
		tv_kyje = (TextView) findViewById(R.id.tv_kyje);
		txfs_radioGroup = (RadioGroup) findViewById(R.id.txfs_radioGroup);
		pttx_radio = (RadioButton) findViewById(R.id.pttx_radio);
		jjtx_radio = (RadioButton) findViewById(R.id.jjtx_radio);
		tv_txfy = (TextView) findViewById(R.id.tv_txfy);
		tv_txfy.setText(fy_money + " 元");
		tv_sjdz = (TextView) findViewById(R.id.tv_sjdz);
		tv_sjdz.setText("0 元");
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setClickable(false);
		btn_ok.setBackgroundResource(R.color.next_color);
	}

	private void initEvents() {

		et_draw_money
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						// TODO Auto-generated method stub
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							String money = et_draw_money.getText().toString()
									.trim();
							if (!TextUtils.isEmpty(money)) {
								double input_money = Double.valueOf(money);
								if (input_money >= 100) {
									if (input_money <= kyye) {
										if(jjtx_radio.isChecked()){
											double x=(input_money/1.0005)*0.005;
											if(x<=2){
											  tv_sjdz.setText(fy_money+ " 元");
											}else{
											  BigDecimal bd = new  BigDecimal(x);  
											  double d = bd.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();  
											  tv_sjdz.setText(d + " 元");
											}
										}else{
											sjdz = input_money - fy_money;
											tv_sjdz.setText(sjdz + " 元");
										}
										btn_ok.setClickable(true);
										btn_ok.setBackgroundResource(R.drawable.btn_login_selector);
									} else {
										ShowToast(R.string.tx_input_error);
										btn_ok.setClickable(false);
										btn_ok.setBackgroundResource(R.color.next_color);
									}
								}
							} else {
								ShowToast(R.string.tx_null_msg);
								btn_ok.setClickable(false);
								btn_ok.setBackgroundResource(R.color.next_color);
							}
							return false;
						}
						return false;
					}

				});

		txfs_radioGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (checkedId == jjtx_radio.getId()) {
							if (isBankType) {
								jjtx_radio.setChecked(true);
								type = "1";
								String money = et_draw_money.getText().toString()
										.trim();
								if(TextUtils.isEmpty(money)){
									money="0";
								}
								double dmoney = Double.valueOf(money);
								double x=(dmoney/1.0005)*0.005;
								if(x<=2){
								  tv_sjdz.setText(fy_money+ " 元");
								}else{
								  BigDecimal bd = new  BigDecimal(x);  
								  double d = bd.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();  
								  tv_sjdz.setText(d + " 元");
								}
							} else {
								pttx_radio.setChecked(true);
								type = "0";
								String msg = "加急提现仅支持：工商，农业，建设，交通，招商五个银行，请选择【普通提现】后继续。";
								showDialog(msg);
							}
						}
						if (pttx_radio.isChecked()) {
							fy_money = 2;
							tv_txfy.setText(fy_money + " 元");
						}
					}
				});

		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String money=et_draw_money.getText().toString().toString();
				Intent intent=new Intent(DrawingActivity.this,RechargeWebViewActivity.class);
				intent.putExtra("money",money);
				intent.putExtra("type",type);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);

			}
		});
		mImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finishActivity();
			}
		});
	}

	class UserBankAnsyTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", params[0]);
			String result = HttpPostUtils.doPost(BANK_URL, map);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				bean = JsonUtils.getMyBankCard(result);
				if (bean != null) {
					initBankDatas();
					layout_progressBar.setVisibility(View.GONE);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class MyAnsyTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", params[0]);
			String result = HttpPostUtils.doPost(INFO_URL, map);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			layout_progressBar.setVisibility(View.GONE);
			String zhye = null;
			try {
				zhye = JsonUtils.getKyjeFromJson(result);
				tv_kyje.setText(zhye + " 元");
				if (zhye == null) {
					zhye = "0";
				}
				kyye = Double.valueOf(zhye);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void initBankDatas() {
		// TODO Auto-generated method stub
		String bankNane = bean.getYinhang();
		isBankType = StringUtils.isBankType(bankNane);
		String icon_bank_url = Constants.ICON_BANK + bankNane + ".png";
		ImageLoader.getInstance().displayImage(icon_bank_url, image_bank,
				ImageLoadOptions.getOptions());
		tv_bank.setText(StringUtils.showBankName(bankNane));
		tv_kh.setText(bean.getKahao());
	}

	public void showDialog(String msg) {
		DialogTips dialog = new DialogTips(DrawingActivity.this, msg, "确定");
		dialog.show();
		dialog = null;
	}

}
