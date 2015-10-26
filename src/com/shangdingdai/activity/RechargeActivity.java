package com.shangdingdai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangdingdai.view.R;

/**
 * 
 * @author Administrator 充值
 */
public class RechargeActivity extends BaseActivity {
	public ImageView mImageView;
	public TextView mTextView;
	private EditText et_cz_money;
	private TextView sj_dz_money;
	private Button btn_zf_ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
		initViews();
		initEvents();
	}

	private void initViews() {
		mImageView = (ImageView) findViewById(R.id.img_back);
		mTextView = (TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.cz);
		mImageView.setVisibility(View.VISIBLE);
		et_cz_money = (EditText) findViewById(R.id.et_cz_money);
		sj_dz_money = (TextView) findViewById(R.id.sj_dz_money);
		btn_zf_ok = (Button) findViewById(R.id.btn_zf_ok);
		btn_zf_ok.setClickable(false);
		btn_zf_ok.setBackgroundResource(R.color.next_color);
	}

	private void initEvents() {
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finishActivity();
			}
		});

		et_cz_money.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				sj_dz_money.setText(et_cz_money.getText().toString().toString()
						+ " 元");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String money = et_cz_money.getText().toString().toString();
				if (!TextUtils.isEmpty(money)) {
					String str = money.substring(0, 1);
					if ("0".equals(str)) {
						ShowToast(R.string.tx_input_error);
						et_cz_money.setText("");
						sj_dz_money.setText("0 元");
						return;
					}
					btn_zf_ok.setClickable(true);
					btn_zf_ok
							.setBackgroundResource(R.drawable.btn_login_selector);
				} else {
					sj_dz_money.setText("0 元");
					btn_zf_ok.setClickable(false);
					btn_zf_ok.setBackgroundResource(R.color.next_color);
				}
			}
		});

		btn_zf_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String money = et_cz_money.getText().toString().toString();
				Intent intent = new Intent(RechargeActivity.this,
						RechargeWebViewActivity.class);
				intent.putExtra("money", money);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
		});
	}

}
