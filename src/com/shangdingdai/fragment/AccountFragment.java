package com.shangdingdai.fragment;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangdingdai.activity.CertificateActivity;
import com.shangdingdai.activity.GesturePsdActivity;
import com.shangdingdai.activity.JlcxActivity;
import com.shangdingdai.activity.MyBankCardActivity;
import com.shangdingdai.activity.MyMessageActivity;
import com.shangdingdai.activity.MyRedPagerActivity;
import com.shangdingdai.activity.SetInofActivity;
import com.shangdingdai.activity.TzglActivity;
import com.shangdingdai.activity.ZcglActivity;
import com.shangdingdai.applcation.Constants;
import com.shangdingdai.bean.ImageBean;
import com.shangdingdai.db.ImageDao;
import com.shangdingdai.db.ImageDaoImpl;
import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.ImageLoadOptions;
import com.shangdingdai.utils.JsonUtils;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.R;

public class AccountFragment extends FragmentBase implements OnClickListener {
	private ImageView user_head_img;
	private TextView tv_account;
	private TextView tv_tel;
	private ImageDao dao;
	private TextView txt_zcgl;
	private TextView txt_tzgl;
	private TextView txt_jlcx;
	private TextView txt_wdyhk;
	private TextView txt_wdxx;
	private TextView txt_myredpager;
	private RelativeLayout layout_zhaq;
	private RelativeLayout layout_ssmm;
	private TextView tv_safelevel;
	private TextView tv_zhye;
	private EditText editText;
	private TextView tv_phone;
	private LinearLayout linearlayout;
	private TextView tv_ssmm;
	private String userid;
	private MyAnsyTask mt;

	public static final String INFO_URL = Constants.SERVICE_ADDRESS
			+ "myinfo/getUserInfo";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_account, container, false);
		dao = new ImageDaoImpl(getActivity());
		userid = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getUseId();
		initTopBarTitle("我的账户", v);
		initViews(v);
		initEvents();
		if (!NetworkUtils.isNetworkAvailable(getActivity()
				.getApplicationContext())) {
			ShowToast(R.string.tv_network_available);
			String zhye = "0.0";
			tv_zhye.setText("¥ " + zhye + " 元");
		} else {
			mt = new MyAnsyTask();
			mt.execute(userid);
		}
		return v;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mt != null && mt.getStatus() == AsyncTask.Status.RUNNING) {
			mt.cancel(true);
		}
	}

	private void initViews(View v) {
		// TODO Auto-generated method stub
		user_head_img = (ImageView) v.findViewById(R.id.user_head_img);
		user_head_img.setImageResource(R.drawable.icon_user_img);
		String id = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getUseId();
		List<ImageBean> bean = dao.select(id);
		if (bean != null && bean.size() > 0) {
			ImageLoader.getInstance().displayImage(bean.get(0).getUrl(),
					user_head_img, ImageLoadOptions.getOptions());
		} else {
			user_head_img.setImageResource(R.drawable.icon_user_img);
		}
		tv_account = (TextView) v.findViewById(R.id.tv_account);
		String account = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getUserName();
		tv_account.setText(account);
		tv_tel = (TextView) v.findViewById(R.id.tv_tel);
		String tel = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getUserTel();
		tv_tel.setText(StringUtils.getTelNum(tel));
		txt_zcgl = (TextView) v.findViewById(R.id.txt_zcgl);
		txt_tzgl = (TextView) v.findViewById(R.id.txt_tzgl);
		txt_jlcx = (TextView) v.findViewById(R.id.txt_jlcx);
		txt_wdyhk = (TextView) v.findViewById(R.id.txt_wdyhk);
		txt_wdxx = (TextView) v.findViewById(R.id.txt_wdxx);
		txt_myredpager = (TextView) v.findViewById(R.id.txt_myredpager);
		layout_zhaq = (RelativeLayout) v.findViewById(R.id.layout_zhaq);
		layout_ssmm = (RelativeLayout) v.findViewById(R.id.layout_ssmm);
		tv_safelevel = (TextView) v.findViewById(R.id.tv_safelevel);
		String safelevel = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getSafelevel();
		tv_safelevel.setText(safelevel);
		tv_zhye = (TextView) v.findViewById(R.id.tv_zhye);
		linearlayout = (LinearLayout) v.findViewById(R.id.linearlayout);
		tv_ssmm = (TextView) v.findViewById(R.id.tv_ssmm);
		boolean isHasGesturePsd = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).isHasGesturePsd();
		if (isHasGesturePsd) {
			tv_ssmm.setText(R.string.ssmm_msg);
		} else {
			tv_ssmm.setText(R.string.no_setting);
		}
	}

	private void initEvents() {
		// TODO Auto-generated method stub
		user_head_img.setOnClickListener(this);
		txt_zcgl.setOnClickListener(this);
		txt_tzgl.setOnClickListener(this);
		txt_jlcx.setOnClickListener(this);
		txt_wdyhk.setOnClickListener(this);
		txt_wdxx.setOnClickListener(this);
		layout_zhaq.setOnClickListener(this);
		layout_ssmm.setOnClickListener(this);
		txt_myredpager.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_head_img:
			intentAction(getActivity(), SetInofActivity.class);
			break;
		case R.id.txt_zcgl:
			intentAction(getActivity(), ZcglActivity.class);
			break;
		case R.id.txt_tzgl:
			intentAction(getActivity(), TzglActivity.class);
			break;
		case R.id.txt_jlcx:
			intentAction(getActivity(), JlcxActivity.class);
			break;
		case R.id.txt_wdyhk:
			intentAction(getActivity(), MyBankCardActivity.class);
			break;
		case R.id.txt_wdxx:
			intentAction(getActivity(), MyMessageActivity.class);
			break;
		case R.id.txt_myredpager:
			intentAction(getActivity(), MyRedPagerActivity.class);
			break;
		case R.id.layout_zhaq:
			intentAction(getActivity(), CertificateActivity.class);
			break;
		case R.id.layout_ssmm:
			boolean isHasGesturePsd = SharePreferenceUtil.getInstance(
					getActivity().getApplicationContext()).isHasGesturePsd();
			if (isHasGesturePsd) {
				showDialog();
			} else {
				intentAction(getActivity(), GesturePsdActivity.class);
			}

			break;
		}
	}

	Button bt_setting, bt_cancel;
	View viewAddEmployee;
	PopupWindow avatorPop;

	@SuppressLint("InflateParams")
	private void showDialog() {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		viewAddEmployee = layoutInflater
				.inflate(R.layout.activity_dialog, null);
		editText = (EditText) viewAddEmployee.findViewById(R.id.editText1);
		tv_phone = (TextView) viewAddEmployee.findViewById(R.id.tv_phone);
		bt_setting = (Button) viewAddEmployee.findViewById(R.id.bt_setting);
		bt_cancel = (Button) viewAddEmployee.findViewById(R.id.bt_cancel);
		String tel = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getUserTel();
		tv_phone.setText(StringUtils.getTelNum(tel));

		showPop();

		bt_setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String psd = SharePreferenceUtil.getInstance(
						getActivity().getApplicationContext()).getUserPsd();
				String password = editText.getText().toString().trim();
				if (TextUtils.isEmpty(password)) {
					ShowToast("密码不能为空！");
					return;
				}
				if (password.equals(psd)) {
					avatorPop.dismiss();
					intentAction(getActivity(), GesturePsdActivity.class);

				} else {
					ShowToast("输入密码错误！");
				}
			}
		});

		bt_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				avatorPop.dismiss();
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
			String result = HttpPostUtils.doPost(INFO_URL, map);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				String zhye = JsonUtils.getKyjeFromJson(result);
				JSONObject jsonObject = new JSONObject(result);
				String userbank = jsonObject.getString("yinhangkanum");
				SharePreferenceUtil.getInstance(
						getActivity().getApplicationContext()).setUserBank(
						userbank);

				String money = getPrecisionMoney(zhye);
				SharePreferenceUtil.getInstance(
						getActivity().getApplicationContext()).setUserZhye(
						money);
				tv_zhye.setText("¥ " + money + " 元");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

	@SuppressLint("ClickableViewAccessibility")
	@SuppressWarnings("deprecation")
	public void showPop() {
		avatorPop = new PopupWindow(viewAddEmployee);
		avatorPop.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					avatorPop.dismiss();
					return true;
				}
				return false;
			}
		});
		avatorPop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		avatorPop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		avatorPop.setTouchable(true);
		avatorPop.setFocusable(true);
		avatorPop.setOutsideTouchable(true);
		avatorPop.setBackgroundDrawable(new BitmapDrawable());
		// 动画效果 从底部弹起
		avatorPop.setAnimationStyle(R.style.Animations_GrowFromBottom);
		avatorPop.showAtLocation(linearlayout, Gravity.CENTER, 0, 0);
	}
}
