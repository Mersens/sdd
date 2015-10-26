package com.shangdingdai.activity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shangdingdai.applcation.Constants;
import com.shangdingdai.bean.ZcglBean;
import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.JsonUtils;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.view.DialogTips;
import com.shangdingdai.view.R;

public class ZcglActivity extends BaseActivity implements OnClickListener {
	public ImageView mImageView;
	public TextView mTextView;
	private Button btn_cz;
	private Button btn_tx;
	private LinearLayout layout_no_msg;
	private LinearLayout layout_progressBar;
	private String userid = null;
	private ZcglBean bean = null;
	private TextView tv_jzc;
	private TextView tv_kyye;
	private TextView tv_djje;
	private TextView tv_zhye;
	private TextView tv_tzzc;
	private TextView tv_dsbx;
	private MyAnsyTask mt;
	public static final String URL = Constants.SERVICE_ADDRESS
			+ "myinfo/getMyZicanData";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zcgl);
		userid = SharePreferenceUtil.getInstance(getApplicationContext())
				.getUseId();
		initViews();
		initEvents();
		if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
			ShowToast(R.string.tv_network_available);
			layout_no_msg.setVisibility(View.VISIBLE);
		} else {
			layout_progressBar.setVisibility(View.VISIBLE);
			mt = new MyAnsyTask();
			mt.execute(userid);
		}
	}

	private void initViews() {
		mImageView = (ImageView) findViewById(R.id.img_back);
		mImageView.setVisibility(View.VISIBLE);
		mTextView = (TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.zcgl);
		btn_cz = (Button) findViewById(R.id.btn_cz);
		btn_tx = (Button) findViewById(R.id.btn_tx);
		layout_no_msg = (LinearLayout) findViewById(R.id.layout_no_msg);
		layout_progressBar = (LinearLayout) findViewById(R.id.layout_progressBar);
		tv_jzc = (TextView) findViewById(R.id.tv_jzc);
		tv_kyye = (TextView) findViewById(R.id.tv_kyye);
		tv_djje = (TextView) findViewById(R.id.tv_djje);
		tv_zhye = (TextView) findViewById(R.id.tv_zhye);
		tv_tzzc = (TextView) findViewById(R.id.tv_tzzc);
		tv_dsbx = (TextView) findViewById(R.id.tv_dsbx);
	}

	private void initEvents() {
		btn_cz.setOnClickListener(this);
		btn_tx.setOnClickListener(this);
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
			if (isCancelled()) {
				return null;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", params[0]);
			String result = HttpPostUtils.doPost(URL, map);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			if (isCancelled()) {
				return;
			}
			layout_progressBar.setVisibility(View.GONE);
			String code = null;
			if (!TextUtils.isEmpty(result)) {
				code = JsonUtils.getCode(result);
			}
			if (code != null) {
				if ("0".equals(code)) {
					layout_no_msg.setVisibility(View.VISIBLE);
					ShowToast(R.string.no_user_msg);
				} else if ("1".equals(code)) {
					try {
						bean = JsonUtils.getZcglFromJson(result);
						initDatas();

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
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

	public void initDatas() {
		tv_jzc.setText(getPrecisionMoney(bean.getJingzichan()));
		tv_kyye.setText(getPrecisionMoney(bean.getKeyongyue()));
		tv_djje.setText(getPrecisionMoney(bean.getDongjiejine()));
		tv_zhye.setText(getPrecisionMoney(bean.getZhanghuyue()) + "元");
		tv_tzzc.setText(getPrecisionMoney(bean.getTouzijine()) + "元");
		tv_dsbx.setText(getPrecisionMoney(bean.getDaishoubenxi()) + "元");
	}

	public static String getPrecisionMoney(String money) {
		String result = null;
		if (TextUtils.isEmpty(money)) {
			result = "0.0";
			return result;
		}
		double x = Double.valueOf(money);
		BigDecimal bd = new BigDecimal(x);
		double d = bd.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		return String.valueOf(d);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_cz:
			// 充值
			intentAction(ZcglActivity.this, RechargeActivity.class);
			break;
		case R.id.btn_tx:
			// 提现
			doIntent();
			break;
		}
	}

	private void doIntent() {
		String userbank = SharePreferenceUtil.getInstance(
				getApplicationContext()).getUserBank();
		boolean isSmrz = SharePreferenceUtil.getInstance(
				getApplicationContext()).isSmrz();
		if (!isSmrz) {
			String msg = "你未进行实名认证，请在[账户安全]或https://p2p.shangdingdai.com上完成实名认证";
			this.showDialog(msg);
		}
		if (!TextUtils.isEmpty(userbank)) {
			if ("0".equals(userbank)) {
				String msg = "未绑定银行卡,请在https://p2p.shangdingdai.com绑定银行卡";
				this.showDialog(msg);
			} else if ("1".equals(userbank)) {
				intentAction(ZcglActivity.this, DrawingActivity.class);
			}
		}

	}

	public void showDialog(String msg) {
		DialogTips dialog = new DialogTips(ZcglActivity.this, msg, "确定");
		dialog.show();
		dialog = null;
	}
}
