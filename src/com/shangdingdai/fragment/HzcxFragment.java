package com.shangdingdai.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shangdingdai.adapter.HzcxAdapter;
import com.shangdingdai.applcation.Constants;
import com.shangdingdai.bean.HzcxBean;
import com.shangdingdai.bean.ShouYiBean;
import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.JsonUtils;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.view.R;

public class HzcxFragment  extends FragmentBase{
	private TextView tv_skze;
	private TextView tv_skze_msg;
	private TextView tv_msg;
	private ListView listview;
	private LinearLayout layout_no_msg;
	private LinearLayout linearlayout_main;
	public static int page = 1;
	public static int pagesize = 10;
	private ProgressBar progressBar;
	private HzcxBean bean;
	private List<ShouYiBean> list;
	private HzcxAdapter adapter;
	private static final String URL = Constants.SERVICE_ADDRESS+"backmoney/findMyBackMoney";
	public static final int SUCCESS = 0X01;
	private MyAnsyTask mt;
	@SuppressLint("HandlerLeak")
	Handler mCodeHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.arg1) {
			case SUCCESS:
				initDatas();
				break;
			}
		}
	};
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.layout_hzcx, null);
		String userid = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getUseId();
		initViews(v);
		if (!NetworkUtils.isNetworkAvailable(getActivity()
				.getApplicationContext())) {
			ShowToast(R.string.tv_network_available);
			layout_no_msg.setVisibility(View.VISIBLE);
			linearlayout_main.setVisibility(View.GONE);
		} else {
			progressBar.setVisibility(View.VISIBLE);
			String params[] = { userid, page + "", pagesize + "",};
			mt=new MyAnsyTask();
			mt.execute(params);
		}
		return v;
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(mt!=null && mt.getStatus()==AsyncTask.Status.RUNNING){
			mt.cancel(true);
		}
		
	}
	public void initDatas(){
		tv_skze_msg.setText(bean.getOnemonthshouyijine()+" 元");
		String date=bean.getToday()+"到"+bean.getMonthlaterdate()+"当日24:00未还款视为逾期";
		tv_msg.setText(date);
		if(list.size()>0){
			adapter=new HzcxAdapter(list,getActivity().getApplicationContext());
			listview.setAdapter(adapter);
			listview.setVisibility(View.VISIBLE);
			layout_no_msg.setVisibility(View.GONE);
			progressBar.setVisibility(View.GONE);
		}
	}
	
	private void initViews(View v) {
		// TODO Auto-generated method stub
		tv_skze=(TextView) v.findViewById(R.id.tv_skze);
		tv_skze_msg=(TextView) v.findViewById(R.id.tv_skze_msg);
		tv_msg=(TextView) v.findViewById(R.id.tv_msg);
		listview=(ListView) v.findViewById(R.id.lv_hzcx);
		layout_no_msg=(LinearLayout) v.findViewById(R.id.layout_no_msg);
		progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
		linearlayout_main=(LinearLayout) v.findViewById(R.id.linearlayout_main);
	}
	
	class MyAnsyTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			if(isCancelled()){
				return null;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", params[0]);
			map.put("page", params[1]);
			map.put("pagesize", params[2]);
			String result = HttpPostUtils.doPost(URL, map);
			return result;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(isCancelled()){
				return;
			}
			try {
				bean=JsonUtils.getHzcx(result);
				list=bean.getList();
			} catch (JSONException e) {
				e.printStackTrace();
			}finally{
				progressBar.setVisibility(View.GONE);
			}
			Message msg = new Message();
			msg.arg1=SUCCESS;
			mCodeHandler.sendMessage(msg);
		}
	}
}
