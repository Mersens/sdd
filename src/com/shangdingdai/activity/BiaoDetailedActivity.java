package com.shangdingdai.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangdingdai.adapter.GridAdapter;
import com.shangdingdai.adapter.HklbAdapter;
import com.shangdingdai.adapter.TzjlAdapter;
import com.shangdingdai.applcation.Constants;
import com.shangdingdai.bean.BiaoFileBean;
import com.shangdingdai.bean.BiaoHuanKuanBean;
import com.shangdingdai.bean.BiaoRenZhengBean;
import com.shangdingdai.bean.BiaoTouZiBean;
import com.shangdingdai.bean.DetailedBiaoBean;
import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.ImageLoadOptions;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.ProgressWheel;
import com.shangdingdai.view.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BiaoDetailedActivity extends BaseActivity implements
		OnClickListener {
	private List<String> listTemp;
	private ImageView icon_title;
	private TextView tv_title;
	private TextView tv_yqlv_msg;
	private TextView tv_syqx_msg;
	private TextView tv_zrjg_msg;
	private ImageView icon_zt;
	private TextView tv_xmid;
	private TextView tv_jkje;
	private TextView tv_hkfs;
	private TextView tv_fbrq;

	private TextView tv_xmjs;
	private TextView tv_shxx;
	private TextView tv_fksh;
	private TextView tv_xgwj;
	private TextView tv_tbjl;
	private TextView tv_hklb;
	public ImageView mImageView;
	public TextView mTextView;
	private DetailedBiaoBean detailedBiaoBean;
	private BiaoRenZhengBean biaoRenZhengBean;
	private List<BiaoFileBean> biaoFileBeanList;
	private List<BiaoTouZiBean> biaoTouZiBeanList;
	private List<BiaoHuanKuanBean> biaoHuanKuanBeanList;
	private TextView tv_xmjs_msg;
	private TextView tv_not_msg1;
	private TextView tv_not_msg2;
	private TextView tv_fksh_msg;

	private GridView linearLayout_shxx;
	private LinearLayout linearLayout_tbjl;
	private LinearLayout layout_hklb;
	private ViewPager viewPager;
	private ListView listView_tzjl;
	private ListView listView_hklb;

	private LinearLayout layout_progressBar;
	private LinearLayout layout_no_msg;
	private RelativeLayout layout_num;
	private TextView tv_mqtzze;// 目前投资总额
	private TextView tv_sytzje;// 剩余投资金额
	private ArrayList<String> list;
	private Button btn_isTB;
	private TextView tv_sumnum;
	private TextView tv_count;
	private ProgressWheel pwOne;
	boolean login;
	private TextView tv_center;
	public static final String BIAODATA_URL = Constants.SERVICE_ADDRESS
			+ "biao/getBiaoData";
	private String biaoid;
	public static final String TYPE="type";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_details_item);
		String type = getIntent().getStringExtra("type");
		if (TYPE.equals(type)) {
			Bundle bundle = getIntent().getExtras();
			biaoid = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
		} else {
			biaoid = getIntent().getStringExtra("biaoid");
		}
		initViews();
		MyAnsyTask mt = new MyAnsyTask();
		mt.execute(biaoid);

	}

	private void initViews() {
		btn_isTB = (Button) findViewById(R.id.btn_isTB);
		listView_hklb = (ListView) findViewById(R.id.listView_hklb);
		listView_tzjl = (ListView) findViewById(R.id.listView_tzjl);
		tv_xmjs_msg = (TextView) findViewById(R.id.tv_xmjs_msg);
		mImageView = (ImageView) findViewById(R.id.img_back);
		mTextView = (TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.xq);
		mImageView.setVisibility(View.VISIBLE);
		icon_title = (ImageView) findViewById(R.id.icon_title);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_yqlv_msg = (TextView) findViewById(R.id.tv_yqlv_msg);
		tv_syqx_msg = (TextView) findViewById(R.id.tv_syqx_msg);
		tv_zrjg_msg = (TextView) findViewById(R.id.tv_zrjg_msg);
		icon_zt = (ImageView) findViewById(R.id.icon_zt);
		tv_xmid = (TextView) findViewById(R.id.tv_xmid);
		tv_jkje = (TextView) findViewById(R.id.tv_jkje);
		tv_hkfs = (TextView) findViewById(R.id.tv_hkfs);
		tv_fbrq = (TextView) findViewById(R.id.tv_fbrq);
		tv_center = (TextView) findViewById(R.id.tv_center);
		tv_xmjs = (TextView) findViewById(R.id.tv_xmjs);
		tv_xmjs.setBackgroundResource(R.drawable.btn_login_n);
		tv_shxx = (TextView) findViewById(R.id.tv_shxx);
		tv_fksh = (TextView) findViewById(R.id.tv_fksh);
		tv_xgwj = (TextView) findViewById(R.id.tv_xgwj);
		tv_tbjl = (TextView) findViewById(R.id.tv_tbjl);
		tv_tbjl.setBackgroundResource(R.drawable.btn_login_n);
		tv_hklb = (TextView) findViewById(R.id.tv_hklb);

		tv_not_msg1 = (TextView) findViewById(R.id.tv_not_msg1);
		tv_not_msg1.setVisibility(View.GONE);
		tv_not_msg2 = (TextView) findViewById(R.id.tv_not_msg2);
		tv_not_msg2.setVisibility(View.GONE);
		tv_fksh_msg = (TextView) findViewById(R.id.tv_fksh_msg);
		String fksh_msg = StringUtils.getFkshStr();
		tv_fksh_msg.setText(fksh_msg);
		tv_fksh_msg.setVisibility(View.GONE);
		tv_mqtzze = (TextView) findViewById(R.id.tv_mqtzze);
		// ------------------------------------
		tv_sytzje = (TextView) findViewById(R.id.tv_sytzje);

		linearLayout_shxx = (GridView) findViewById(R.id.linearLayout_shxx);
		pwOne = (ProgressWheel) findViewById(R.id.rogressWheel);

		viewPager = (ViewPager) findViewById(R.id.viewpager_xgwj);
		viewPager.setVisibility(View.GONE);
		linearLayout_tbjl = (LinearLayout) findViewById(R.id.linearLayout_tbjl);
		linearLayout_tbjl.setVisibility(View.GONE);
		layout_hklb = (LinearLayout) findViewById(R.id.layout_hklb);
		layout_hklb.setVisibility(View.GONE);
		layout_progressBar = (LinearLayout) findViewById(R.id.layout_progressBar);
		layout_no_msg = (LinearLayout) findViewById(R.id.layout_no_msg);
		layout_num = (RelativeLayout) findViewById(R.id.layout_num);
		tv_sumnum = (TextView) findViewById(R.id.tv_sumnum);
		tv_count = (TextView) findViewById(R.id.tv_count);
		tv_count.setText("1");
	}

	@SuppressLint("InflateParams")
	private void initDatas() {
		ImageLoader.getInstance().displayImage(
				Constants.IC_USER_URL + detailedBiaoBean.getImgsrc(),
				icon_title, ImageLoadOptions.getOptions());
		int i = StringUtils.showIcon(detailedBiaoBean.getDatastatic());
		switch (i) {
		case StringUtils.ICON_HKZ:
			icon_zt.setImageResource(R.drawable.icon_hkz);
			btn_isTB.setClickable(false);
			btn_isTB.setText(R.string.hkz);
			btn_isTB.setBackgroundResource(R.color.next_color);
			break;
		case StringUtils.ICON_SHZ:
			icon_zt.setImageResource(R.drawable.icon_shz);
			btn_isTB.setClickable(false);
			btn_isTB.setText(R.string.shz);
			btn_isTB.setBackgroundResource(R.color.next_color);
			break;
		case StringUtils.ICON_YJJ:
			icon_zt.setImageResource(R.drawable.icon_hjj);
			btn_isTB.setClickable(false);
			btn_isTB.setText(R.string.yjj);
			btn_isTB.setBackgroundResource(R.color.next_color);
			break;
		case StringUtils.ICON_YJS:
			icon_zt.setImageResource(R.drawable.icon_yjs);
			btn_isTB.setClickable(false);
			btn_isTB.setText(R.string.yjs);
			btn_isTB.setBackgroundResource(R.color.next_color);
			break;
		case StringUtils.ICON_YMB:
			icon_zt.setImageResource(R.drawable.icon_ymb);
			btn_isTB.setClickable(false);
			btn_isTB.setText(R.string.ymb);
			btn_isTB.setBackgroundResource(R.color.next_color);
		case StringUtils.ICON_TBZ:
			icon_zt.setVisibility(View.GONE);
			pwOne.setVisibility(View.VISIBLE);
			tv_center.setVisibility(View.VISIBLE);
			// icon_zt.setImageResource(R.drawable.icon_tzz);
			btn_isTB.setClickable(true);
			btn_isTB.setText(R.string.mstb);
			btn_isTB.setBackgroundResource(R.drawable.btn_login_selector);
			break;
		}
		tv_title.setText(detailedBiaoBean.getTitle());
		tv_yqlv_msg.setText(detailedBiaoBean.getLilv() + "%");
		tv_syqx_msg.setText(detailedBiaoBean.getDkqx());
		tv_zrjg_msg.setText(detailedBiaoBean.getShengyujine() + "元");
		tv_xmid.setText(detailedBiaoBean.getBiaoid());
		tv_jkje.setText(detailedBiaoBean.getLoanjine() + "元");
		tv_hkfs.setText(detailedBiaoBean.getHuankuanval());
		tv_fbrq.setText(detailedBiaoBean.getAddtime());

		String strProportion = StringUtils.getProportion(detailedBiaoBean
				.getYitoubili());

		pwOne.setText(strProportion + "%");
		int proportion = StringUtils.getIntProportion(detailedBiaoBean
				.getYitoubili());
		pwOne.setProgress(proportion);

		String str = StringUtils
				.replaceBr(detailedBiaoBean.getJieshao() == null ? ""
						: detailedBiaoBean.getJieshao());
		if (str == null && "".equals(str)) {
			tv_xmjs_msg.setVisibility(View.GONE);
			tv_not_msg1.setVisibility(View.VISIBLE);
		} else {
			tv_xmjs_msg.setVisibility(View.VISIBLE);
			tv_xmjs_msg.setText(str);
		}
		if (biaoFileBeanList.size() > 0) {
			int len = biaoFileBeanList.size();
			View view = null;
			ImageView imageview;
			LayoutInflater mInflater = LayoutInflater
					.from(getApplicationContext());
			List<View> lists = new ArrayList<View>();
			list = new ArrayList<String>();
			for (int i1 = 0; i1 < len; i1++) {
				view = mInflater.inflate(R.layout.imageview_layout, null);
				imageview = (ImageView) view
						.findViewById(R.id.viewpager_imageview);
				list.add(Constants.IC_USER_URL
						+ biaoFileBeanList.get(i1).getImgsrc());
				ImageLoader.getInstance().displayImage(
						Constants.IC_USER_URL
								+ biaoFileBeanList.get(i1).getImgsrc(),
						imageview, ImageLoadOptions.getOptions());
				lists.add(view);
			}
			tv_sumnum.setText(lists.size() + "");
			viewPager.setAdapter(new MyAdapter(getApplicationContext(), lists,
					list));
		}

		Map<String, String> map = StringUtils.getImages(biaoRenZhengBean);
		listTemp = new ArrayList<String>();
		List<String> names = new ArrayList<String>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String str1 = entry.getValue();
			if (str1 != null && !"".equals(str1)) {
				listTemp.add(Constants.IC_USER_URL + str1);
				names.add(entry.getKey());

			}
		}
		linearLayout_shxx.setAdapter(new GridAdapter(getApplicationContext(),
				listTemp, names));
		if (biaoTouZiBeanList.size() > 0) {
			linearLayout_tbjl.setVisibility(View.VISIBLE);
			tv_not_msg2.setVisibility(View.GONE);
			listView_tzjl.setAdapter(new TzjlAdapter(biaoTouZiBeanList,
					getApplicationContext()));
			listView_tzjl.setVisibility(View.VISIBLE);
			tv_mqtzze.setText(detailedBiaoBean.getYitoujine() + "元");
			tv_sytzje.setText(detailedBiaoBean.getShengyujine() + "元");
			// 设置数据
		} else {
			linearLayout_tbjl.setVisibility(View.GONE);
			tv_not_msg2.setVisibility(View.VISIBLE);
		}

		if (biaoHuanKuanBeanList.size() > 0) {
			layout_hklb.setVisibility(View.GONE);
			tv_not_msg2.setVisibility(View.GONE);
			// 设置数据
			listView_hklb.setAdapter(new HklbAdapter(biaoHuanKuanBeanList,
					getApplicationContext()));
			listView_hklb.setVisibility(View.GONE);
		}
	}

	private void initEvent() {
		tv_xmjs.setOnClickListener(this);
		tv_shxx.setOnClickListener(this);
		tv_fksh.setOnClickListener(this);
		tv_xgwj.setOnClickListener(this);
		tv_tbjl.setOnClickListener(this);
		tv_hklb.setOnClickListener(this);
		mImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});
		login = SharePreferenceUtil.getInstance(getApplicationContext())
				.isLogin();
		btn_isTB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (login) {
					boolean isSmrz = SharePreferenceUtil.getInstance(
							getApplicationContext()).isSmrz();
					boolean isPhone = SharePreferenceUtil.getInstance(
							getApplicationContext()).isphone();
					boolean isRzyx = SharePreferenceUtil.getInstance(
							getApplicationContext()).isRzyx();
					if (isSmrz && isPhone && isRzyx) {
						Intent intent = new Intent(BiaoDetailedActivity.this,
								TouBiaoActivity.class);
						intent.putExtra("title", detailedBiaoBean.getTitle());
						intent.putExtra("biaoid", detailedBiaoBean.getBiaoid());
						startActivity(intent);
						overridePendingTransition(R.anim.push_left_in,
								R.anim.push_left_out);
					} else {
						intentAction(BiaoDetailedActivity.this,
								CertificateActivity.class);
					}
				} else {
					intentAction(BiaoDetailedActivity.this, LoginActivity.class);
				}
			}
		});

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				tv_count.setText((arg0 + 1) + "");

			}

		});
	}

	class MyAdapter extends PagerAdapter {
		private List<View> list;
		private ArrayList<String> paths;

		public MyAdapter(Context context, List<View> list,
				ArrayList<String> paths) {
			this.list = list;
			this.paths = paths;

		}

		@Override
		public void destroyItem(View view, int position, Object arg2) {
			ViewPager pViewPager = ((ViewPager) view);
			pViewPager.removeView(list.get(position));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object instantiateItem(View view, final int position) {

			list.get(position).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(),
							CheckImgActivity.class);
					intent.putStringArrayListExtra("path", paths);
					intent.putExtra("position", position);
					startActivity(intent);
				}
			});
			ViewPager pViewPager = ((ViewPager) view);
			pViewPager.addView(list.get(position));
			return list.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	class MyAnsyTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			layout_progressBar.setVisibility(View.VISIBLE);
			Map<String, String> map = new HashMap<String, String>();
			map.put("biaoid", params[0]);
			String result = HttpPostUtils.doPost(BIAODATA_URL, map);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			jsonToEntity(result);
			initEvent();
			initDatas();
			layout_progressBar.setVisibility(View.GONE);
		}
	}

	public void jsonToEntity(String result) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(result);
			String strbiao = jsonObject.getString("biao");
			String strbiaorenzheng = jsonObject.getString("biaorenzheng");
			String strbiaoFile = jsonObject.getString("biaofile");
			String strbiaotouzi = jsonObject.getString("biaotouzi");
			String strbiaohuankuan = jsonObject.getString("biaohuankuan");
			Gson gson = new Gson();
			detailedBiaoBean = gson.fromJson(strbiao, DetailedBiaoBean.class);
			biaoRenZhengBean = gson.fromJson(strbiaorenzheng,
					BiaoRenZhengBean.class);
			biaoFileBeanList = gson.fromJson(strbiaoFile,
					new TypeToken<List<BiaoFileBean>>() {
					}.getType());
			biaoTouZiBeanList = gson.fromJson(strbiaotouzi,
					new TypeToken<List<BiaoTouZiBean>>() {
					}.getType());
			biaoHuanKuanBeanList = gson.fromJson(strbiaohuankuan,
					new TypeToken<List<BiaoHuanKuanBean>>() {
					}.getType());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			layout_no_msg.setVisibility(View.VISIBLE);
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_xmjs:
			tv_xmjs.setBackgroundResource(R.drawable.btn_login_n);
			tv_shxx.setBackgroundResource(R.color.white);
			tv_fksh.setBackgroundResource(R.color.white);
			tv_xgwj.setBackgroundResource(R.color.white);
			setXmjs();
			break;
		case R.id.tv_shxx:
			tv_xmjs.setBackgroundResource(R.color.white);
			tv_shxx.setBackgroundResource(R.drawable.btn_login_n);
			tv_fksh.setBackgroundResource(R.color.white);
			tv_xgwj.setBackgroundResource(R.color.white);
			setShxx();
			break;
		case R.id.tv_fksh:
			tv_xmjs.setBackgroundResource(R.color.white);
			tv_shxx.setBackgroundResource(R.color.white);
			tv_fksh.setBackgroundResource(R.drawable.btn_login_n);
			tv_xgwj.setBackgroundResource(R.color.white);
			setFksh();
			break;
		case R.id.tv_xgwj:
			tv_xmjs.setBackgroundResource(R.color.white);
			tv_shxx.setBackgroundResource(R.color.white);
			tv_fksh.setBackgroundResource(R.color.white);
			tv_xgwj.setBackgroundResource(R.drawable.btn_login_n);
			setXgwj();
			break;

		case R.id.tv_tbjl:
			tv_hklb.setBackgroundResource(R.color.white);
			tv_tbjl.setBackgroundResource(R.drawable.btn_login_n);
			setTbjl();
			break;
		case R.id.tv_hklb:
			tv_hklb.setBackgroundResource(R.drawable.btn_login_n);
			tv_tbjl.setBackgroundResource(R.color.white);
			setHklb();
			break;
		}
	}

	private void setHklb() {
		if (biaoHuanKuanBeanList.size() > 0) {
			linearLayout_tbjl.setVisibility(View.GONE);
			listView_tzjl.setVisibility(View.GONE);
			layout_hklb.setVisibility(View.VISIBLE);
			listView_hklb.setVisibility(View.VISIBLE);
			tv_not_msg2.setVisibility(View.GONE);
		} else {
			linearLayout_tbjl.setVisibility(View.GONE);
			layout_hklb.setVisibility(View.GONE);
			tv_not_msg2.setVisibility(View.VISIBLE);
			listView_hklb.setVisibility(View.GONE);
			listView_tzjl.setVisibility(View.GONE);
		}

	}

	private void setTbjl() {
		if (biaoTouZiBeanList.size() > 0) {
			linearLayout_tbjl.setVisibility(View.VISIBLE);
			listView_tzjl.setVisibility(View.VISIBLE);
			layout_hklb.setVisibility(View.GONE);
			listView_hklb.setVisibility(View.GONE);
			tv_not_msg2.setVisibility(View.GONE);
		} else {
			linearLayout_tbjl.setVisibility(View.GONE);
			layout_hklb.setVisibility(View.GONE);
			tv_not_msg2.setVisibility(View.VISIBLE);
			listView_tzjl.setVisibility(View.GONE);
			listView_hklb.setVisibility(View.GONE);
		}

	}

	private void setXgwj() {
		tv_xmjs_msg.setVisibility(View.GONE);
		linearLayout_shxx.setVisibility(View.GONE);
		tv_fksh_msg.setVisibility(View.GONE);
		if (biaoFileBeanList.size() > 0) {
			viewPager.setVisibility(View.VISIBLE);
			layout_num.setVisibility(View.VISIBLE);
			tv_not_msg1.setVisibility(View.GONE);
		} else {
			viewPager.setVisibility(View.GONE);
			layout_num.setVisibility(View.GONE);
			tv_not_msg1.setVisibility(View.VISIBLE);
		}
	}

	private void setFksh() {
		tv_xmjs_msg.setVisibility(View.GONE);
		linearLayout_shxx.setVisibility(View.GONE);
		tv_fksh_msg.setVisibility(View.VISIBLE);
		viewPager.setVisibility(View.GONE);
		layout_num.setVisibility(View.GONE);
		tv_not_msg1.setVisibility(View.GONE);
	}

	private void setShxx() {
		tv_xmjs_msg.setVisibility(View.GONE);
		if (listTemp.size() <= 0) {
			linearLayout_shxx.setVisibility(View.GONE);
			tv_not_msg1.setVisibility(View.VISIBLE);
		} else {
			linearLayout_shxx.setVisibility(View.VISIBLE);
			tv_not_msg1.setVisibility(View.GONE);
		}
		tv_fksh_msg.setVisibility(View.GONE);
		viewPager.setVisibility(View.GONE);
		layout_num.setVisibility(View.GONE);
	}

	private void setXmjs() {
		String str = StringUtils
				.replaceBr(detailedBiaoBean.getJieshao() == null ? ""
						: detailedBiaoBean.getJieshao());
		if (str == null && "".equals(str)) {
			tv_xmjs_msg.setVisibility(View.GONE);
			tv_not_msg1.setVisibility(View.VISIBLE);
		} else {
			tv_xmjs_msg.setVisibility(View.VISIBLE);
			tv_xmjs_msg.setText(str);
			tv_not_msg1.setVisibility(View.GONE);
		}
		linearLayout_shxx.setVisibility(View.GONE);
		tv_fksh_msg.setVisibility(View.GONE);
		viewPager.setVisibility(View.GONE);
		layout_num.setVisibility(View.GONE);
	}
}
