package com.shangdingdai.fragment;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangdingdai.activity.LoginActivity;
import com.shangdingdai.activity.SbtzAxtivity;
import com.shangdingdai.activity.XszxAxtivity;
import com.shangdingdai.adapter.ViewPagerAdapter;
import com.shangdingdai.applcation.Constants;
import com.shangdingdai.bean.BannerMsgBean;
import com.shangdingdai.bean.BannersBean;
import com.shangdingdai.bean.ImageBean;
import com.shangdingdai.db.ImageDao;
import com.shangdingdai.db.ImageDaoImpl;
import com.shangdingdai.utils.GuidedUtil;
import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.ImageLoadOptions;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.view.MainActivity;
import com.shangdingdai.view.R;

@SuppressLint("InflateParams")
public class HomeFragment extends FragmentBase implements OnClickListener {
	private ViewPager viewpager;
	private int[] pics = { R.drawable.banner1, R.drawable.banner2, R.drawable.banner3,
			R.drawable.banner4};
	private Runnable viewpagerRunnable;
	private static Handler handler;
	private RadioGroup dotLayout;
	private RelativeLayout layout;
	public static final String BANNERS_PATH="https://appservice.shangdingdai.com/index/getBanners";
	public  static final int SUCCESS=0X10;
	public static final int ERROR=0X01;
	private BannersBean bean;
	private boolean isFirstLoaderBanners=false;
	private  ImageDao dao;
	private RelativeLayout layout_xszx;
	private RelativeLayout layout_sbtz;
	private ImageView user_image;
	private MyAnsyTask mt;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.main,container , false);
		dao=new ImageDaoImpl(getActivity().getApplicationContext());
		initViews(v);
		handler = new Handler();
		viewpager = (ViewPager)v.findViewById(R.id.viewpager);
		dotLayout = (RadioGroup)v.findViewById(R.id.advertise_point_group);
		isFirstLoaderBanners=GuidedUtil.getInstance(getActivity().getApplicationContext()).isFirstLoaderBanners();
		if(!NetworkUtils.isNetworkAvailable(getActivity().getApplicationContext())){
			ShowToast(R.string.tv_network_available);
			setLocalBannersNotNet();
			return v;
		}
		if(isFirstLoaderBanners){
		    mt=new MyAnsyTask();
			mt.execute(BANNERS_PATH);
			GuidedUtil.getInstance(getActivity().getApplicationContext()).setFirstLoaderBanners(false);
		}else{
			setLocalBanners();
		}

		return v;
	}

	public void setLocalBannersNotNet(){
		initListener();
		int len = pics.length;
		View view = null;
		ImageView imageview;
		LayoutInflater mInflater = LayoutInflater.from(getActivity());
		List<View> lists = new ArrayList<View>();
		for (int i = 0; i < len; i++)
		{
			view = mInflater.inflate(R.layout.imageview_layout, null);
			imageview = (ImageView) view.findViewById(R.id.viewpager_imageview);
			imageview.setBackgroundResource(pics[i]);
			lists.add(view);
		}
		viewpager.setAdapter(new ViewPagerAdapter(getActivity(),lists));
		initRunnable();
	}
	
	public void setLocalBanners(){
		List<ImageBean>imglist=dao.select(Constants.BANNERS_PATH_ID);
		initListener();
		int len = imglist.size();
		View view = null;
		ImageView imageview;
		LayoutInflater mInflater = LayoutInflater.from(getActivity());
		List<View> lists = new ArrayList<View>();
		for (int i = 0; i < len; i++)
		{
			view = mInflater.inflate(R.layout.imageview_layout, null);
			imageview = (ImageView) view.findViewById(R.id.viewpager_imageview);
			ImageLoader.getInstance().displayImage(imglist.get(i).getUrl(), imageview,
					ImageLoadOptions.getOptions());
			lists.add(view);
		}
		viewpager.setAdapter(new ViewPagerAdapter(getActivity(),lists));
		initRunnable();
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(mt!=null && mt.getStatus()==AsyncTask.Status.RUNNING){
			mt.cancel(true);
		}
		
		
	}

	public void setBanners(){
		boolean isSave=false;
		
		List<ImageBean>imglist=dao.select(Constants.BANNERS_PATH_ID);
		if(imglist!=null && imglist.size()>0){
			isSave=true;
		}
		initListener();
		List<BannerMsgBean> list=bean.getBanners();
		int len = list.size();
		View view = null;
		ImageView imageview;
		LayoutInflater mInflater = LayoutInflater.from(getActivity());
		List<View> lists = new ArrayList<View>();
		for (int i = 0; i < len; i++)
		{
			view = mInflater.inflate(R.layout.imageview_layout, null);
			imageview = (ImageView) view.findViewById(R.id.viewpager_imageview);
			if(isSave){
				dao.update(Constants.BANNERS_PATH_ID, list.get(i).getImgsrc());
			}else{
				
				dao.add(Constants.BANNERS_PATH_ID, list.get(i).getImgsrc());
			}
			ImageLoader.getInstance().displayImage(list.get(i).getImgsrc(), imageview,
					ImageLoadOptions.getOptions());
			lists.add(view);
		}
		viewpager.setAdapter(new ViewPagerAdapter(getActivity(),lists));
		initRunnable();
	}
	
	public void initViews(View v) {
		user_image=(ImageView) v.findViewById(R.id.user_image);
		user_image.setImageResource(R.drawable.icon_user_img);
		String id=SharePreferenceUtil.getInstance(getActivity().getApplicationContext()).getUseId();
		List<ImageBean> bean=dao.select(id);
		if(bean!=null && bean.size()>0){
			ImageLoader.getInstance().displayImage(bean.get(0).getUrl(), user_image,
					ImageLoadOptions.getOptions());
		}else{
			user_image.setImageResource(R.drawable.icon_user_img);
		}
		layout_xszx=(RelativeLayout) v.findViewById(R.id.layout_xszx);
		layout_sbtz=(RelativeLayout) v.findViewById(R.id.layout_sbtz);
		layout_xszx.setOnClickListener(this);
		layout_sbtz.setOnClickListener(this);
		final boolean isLogin=SharePreferenceUtil.getInstance(getActivity().getApplicationContext()).isLogin();
		if(isLogin){
			TextView title=(TextView) v.findViewById(R.id.tv_dl);
			title.setText(R.string.ydl);
			TextView msg=(TextView) v.findViewById(R.id.tv_dl_msg);
			msg.setText(R.string.ydl_msg);
		}
		layout=(RelativeLayout) v.findViewById(R.id.layout_login);
		
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isLogin){
					Intent intent=new Intent(getActivity(),MainActivity.class);
					intent.putExtra("isLogin", isLogin);
					startActivity(intent);
					getActivity().overridePendingTransition(R.anim.push_left_in,
							R.anim.push_left_out);
					getActivity().finish();
				}else{
					 intentAction(getActivity(), LoginActivity.class);
				}
			}
		});
	}

	private void initListener()
	{
		viewpager.setOnPageChangeListener(new OnPageChangeListener()
		{
			boolean isScrolled = false;

			@Override
			public void onPageScrollStateChanged(int status)
			{
				switch (status)
				{
				case 1:
					isScrolled = false;
					break;
				case 2:
					isScrolled = true;
					break;
				case 0:

					if (viewpager.getCurrentItem() == viewpager.getAdapter()
							.getCount() - 1 && !isScrolled)
					{
						viewpager.setCurrentItem(0);
					}
					else if (viewpager.getCurrentItem() == 0 && !isScrolled)
					{
						viewpager.setCurrentItem(viewpager.getAdapter()
								.getCount() - 1);
					}
					break;
				}
			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2)
			{
				((RadioButton) dotLayout.getChildAt(position)).setChecked(true);
			}

			@Override
			public void onPageSelected(int index)
			{
				
			}
		});
	}

	private static final int TIME = 3000;

	protected void initRunnable()
	{
		viewpagerRunnable = new Runnable()
		{

			@Override
			public void run()
			{
				int nowIndex = viewpager.getCurrentItem();
				int count = viewpager.getAdapter().getCount();
				if (nowIndex + 1 >= count)
				{
					viewpager.setCurrentItem(0);
				} else
				{
					viewpager.setCurrentItem(nowIndex + 1);
				}
				handler.postDelayed(viewpagerRunnable, TIME);
			}
		};
		handler.postDelayed(viewpagerRunnable, TIME);
		
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		handler.removeCallbacks(viewpagerRunnable);
	}
	
	
	class MyAnsyTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			if(isCancelled()){
				return null;
			}
			String str=HttpPostUtils.doGetBanners(params[0]);
			return str;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(isCancelled()){
				return ;
			}
			Gson gson=new Gson();
			bean=gson.fromJson(result, BannersBean.class);
			if(bean!=null){
				setBanners();
			}
		}
	}

	public <T> void intentAction(Activity context, Class<T> cls) {
		Intent intent = new Intent(context, cls);
		startActivity(intent);
		context.overridePendingTransition(R.anim.push_left_in,
				R.anim.push_left_out);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_xszx:
			intentAction(getActivity(),XszxAxtivity.class);
			break;
		case R.id.layout_sbtz:
			intentAction(getActivity(),SbtzAxtivity.class);
			break;
		}
	}
	
}
