package com.shangdingdai.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.shangdingdai.applcation.Constants;
import com.shangdingdai.bean.HongBaoBean;
import com.shangdingdai.bean.TouZiBean;
import com.shangdingdai.utils.Code;
import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.JsonUtils;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.view.R;

public class TouBiaoActivity extends BaseActivity implements OnClickListener {
	public ImageView mImageView;
	public TextView mTextView;
	private Button btn_tz_ok;
	private Spinner spinner;
	private List<String> spinnerlist;
	private ArrayAdapter<String> adapter;
	private ImageView img_code;
	private String title;
	private TextView tv_syktje_msg;
	private TextView tv_zhkyye_msg;
	private EditText et_money;
	private EditText et_input_yzm;
	private String userid;
	private String biaoid;
	private TouZiBean bean;
	private LinearLayout layout_no_msg;
	private LinearLayout layout_progress;
	private int TAG;
	private int index;
	private String yz_code;
	MyAnsyTask mt;
	MyHongBaoAnsyTask mb;
	private List<HongBaoBean> list;
	private static final String URL = Constants.SERVICE_ADDRESS
			+ "biao/gotoTouziBiao";
	private static final String HBURL = Constants.SERVICE_ADDRESS
			+ "biao/getHongBaoForUser";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_toubiao);
		title = getIntent().getStringExtra("title");
		biaoid = getIntent().getStringExtra("biaoid");
		userid = SharePreferenceUtil.getInstance(getApplicationContext())
				.getUseId();
		initViews();
		initEvents();
		if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
			layout_no_msg.setVisibility(View.VISIBLE);
			ShowToast(R.string.tv_network_available);
		} else {
			layout_progress.setVisibility(View.VISIBLE);
			 mt = new MyAnsyTask();
			mt.execute(userid, biaoid);
		}

	}

	private void initViews() {
		// TODO Auto-generated method stub
		mImageView = (ImageView) findViewById(R.id.img_back);
		mTextView = (TextView) findViewById(R.id.txt_title);
		mTextView.setText(title);
		mImageView.setVisibility(View.VISIBLE);
		spinner = (Spinner) findViewById(R.id.spinner);
		spinner.setClickable(false);
		btn_tz_ok = (Button) findViewById(R.id.btn_tz_ok);
		img_code = (ImageView) findViewById(R.id.img_code);
		img_code.setImageBitmap(Code.getInstance().createBitmap());
		yz_code = Code.getInstance().getCode();
		tv_syktje_msg = (TextView) findViewById(R.id.tv_syktje_msg);
		tv_zhkyye_msg = (TextView) findViewById(R.id.tv_zhkyye_msg);
		et_money = (EditText) findViewById(R.id.et_money);
		et_input_yzm = (EditText) findViewById(R.id.et_input_yzm);
		layout_no_msg = (LinearLayout) findViewById(R.id.layout_no_msg);
		layout_progress = (LinearLayout) findViewById(R.id.layout_progress);

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(mt!=null && mt.getStatus()==AsyncTask.Status.RUNNING){
			mt.cancel(true);
		}
		if(mb!=null && mb.getStatus()==AsyncTask.Status.RUNNING){
			mb.cancel(true);
		}
	}
	
	
	private void initDatas() {
		// TODO Auto-generated method stub
		spinnerlist = new ArrayList<String>();

		if (list.size() > 0 && list != null) {
			for (int i = 0; i < list.size(); i++) {
				spinnerlist.add(list.get(i).getHongbaoname() + " "
						+ list.get(i).getHongbaojine() + "元");
			}

			spinner.setClickable(true);
		}
		// 适配器
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spinnerlist);
		// 设置样式
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 加载适配器
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				index = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initEvents() {
		// TODO Auto-generated method stub
		img_code.setOnClickListener(this);
		btn_tz_ok.setOnClickListener(this);
		mImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finishActivity();
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_tz_ok:
			doTouZi();
			break;
		case R.id.img_code:
			img_code.setImageBitmap(Code.getInstance().createBitmap());
			yz_code = Code.getInstance().getCode();
			break;
		}
	}

	private void doTouZi() {
		String input_money = et_money.getText().toString().trim();
		if(TextUtils.isEmpty(input_money)){
			ShowToast("投资金额不能为空！");
		}
		String userhongbaoid = null;
		if (TAG == 2) {
			HongBaoBean bean = list.get(index);
			userhongbaoid = bean.getUserhongbaoid();

		} else {
			userhongbaoid = null;
		}
		String yzm = et_input_yzm.getText().toString().trim();
		if (yz_code.equals(yzm)) {

			Intent intent = new Intent(TouBiaoActivity.this,
					TouBiaoWebActivity.class);
			intent.putExtra("userhongbaoid", userhongbaoid == null ? "123"
					: userhongbaoid);
			intent.putExtra("money", input_money);
			intent.putExtra("biaoid", biaoid);
			startActivity(intent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		} else {
			ShowToast("验证码输入有误！");
			return;
		}

	}

	class MyHongBaoAnsyTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", params[0]);
			map.put("biaoid", params[1]);
			map.put("money", params[2]);
			String result = HttpPostUtils.doPost(HBURL, map);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			String code = JsonUtils.getCode(result);
			if ("1".equals(code)) {
				TAG = 2;
				try {
					list = JsonUtils.getHongBao(result);
					initDatas();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				TAG = 1;
				List<String> nolist = new ArrayList<String>();
				nolist.add("暂无红包！");
				// 适配器
				adapter = new ArrayAdapter<String>(TouBiaoActivity.this,
						android.R.layout.simple_spinner_item, nolist);
				// 设置样式
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// 加载适配器
				spinner.setAdapter(adapter);
			}

		}
	}

	class MyAnsyTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", params[0]);
			map.put("biaoid", params[1]);
			String result = HttpPostUtils.doHttpGet(URL, map);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				bean = JsonUtils.getTouZiFromJson(result);
				if (bean != null) {
					setDatas();
				} else {
					layout_no_msg.setVisibility(View.VISIBLE);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void setDatas() {
		tv_syktje_msg.setText(bean.getShengyujine() + " 元");
		tv_zhkyye_msg.setText(bean.getUseryue() + " 元");
		et_money.setHint("本标最低投资金额" + bean.getMintouzi() + " 元");
		layout_progress.setVisibility(View.GONE);
		et_money.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_NEXT) {
					String money = et_money.getText().toString().trim();
					double inputmoney = Double.valueOf(money);
					double minmoney = Double.valueOf(bean.getMintouzi());
					if (inputmoney < minmoney) {
						ShowToast("输入投资金额有误！");
						return false;
					}
					mb = new MyHongBaoAnsyTask();
					mb.execute(userid, biaoid, money);
					return false;
				}
				return false;
			}
		});

	}

}
