package com.shangdingdai.fragment;

import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.shangdingdai.adapter.JyjlAdapter;
import com.shangdingdai.applcation.Constants;
import com.shangdingdai.bean.JyjlBean;
import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.JsonUtils;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.view.R;
import com.shangdingdai.view.XListView;
import com.shangdingdai.view.XListView.IXListViewListener;

public class JyjlFragment  extends FragmentBase implements OnClickListener,
IXListViewListener, EventListener{
	private XListView listView;
	private JyjlAdapter adapter;
	private LinearLayout layout_no_msg;
	private ProgressBar progressBar;//
	public static int page = 1;
	public static int pagesize = 10;
	public static final int SUCCESS = 0X01;
	public static final int REFRESH = 0X00;
	public static final int LOADMORE = 0X10;
	public boolean isRefresh = false;
	public boolean isLoadMore = false;
	public boolean isFirst = false;
	private List<JyjlBean> list;
	String userid ;
	private MyAnsyTask mt;
	public static final String URL=Constants.SERVICE_ADDRESS+"myliushui/getMyliushuiData";

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.layout_jyjl, null);
		userid = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getUseId();
		initViews(v);
		if (!NetworkUtils.isNetworkAvailable(getActivity()
				.getApplicationContext())) {
			ShowToast(R.string.tv_network_available);
			layout_no_msg.setVisibility(View.VISIBLE);
		}else{
			progressBar.setVisibility(View.VISIBLE);
			String params[] = { userid, page + "", pagesize + "","0"};
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
	private void initViews(View v) {
		// TODO Auto-generated method stub
		isFirst = true;
		listView=(XListView) v.findViewById(R.id.listView);
		layout_no_msg=(LinearLayout) v.findViewById(R.id.layout_no_msg);
		progressBar=(ProgressBar) v.findViewById(R.id.progressBar);
		listView.setPullLoadEnable(false);
		// 允许下拉
		listView.setPullRefreshEnable(false);
		listView.setXListViewListener(this);
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
			map.put("typeid", params[3]);
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
				list=JsonUtils.getJykl(result);
				if (list.size() == 10 && isFirst) {
					listView.setPullLoadEnable(true);
				}
				if (list.size() == 0 && isFirst) {
					layout_no_msg.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
					listView.setPullLoadEnable(false);
					listView.setPullRefreshEnable(false);
					progressBar.setVisibility(View.GONE);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				progressBar.setVisibility(View.GONE);
			}
			if (isFirst) {
				isFirst = false;
				initEvents();
			} else if (isRefresh) {
				isRefresh = false;
				refresh();
				listView.setEnabled(true);
			} else if (isLoadMore) {
				isLoadMore = false;
				loadMore();
				listView.setEnabled(true);
			}
		}
	}

	@Override
	public void onRefresh() {
		listView.setEnabled(false);
		isRefresh = true;
		page = 1;
		mt = new MyAnsyTask();
		String params[] = { userid, page + "", pagesize + "", "0" };
		mt.execute(params);
	}

	@Override
	public void onLoadMore() {
		listView.setEnabled(false);
		isLoadMore = true;
		page = page + 1;
		mt = new MyAnsyTask();
		String params[] = { userid, page + "", pagesize + "", "0" };
		mt.execute(params);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	private void initEvents() {
		adapter = new JyjlAdapter(list, getActivity().getApplicationContext());
		listView.setAdapter(adapter);
		progressBar.setVisibility(View.GONE);
	}

	private void refresh() {
		adapter.setList(list);
		adapter.notifyDataSetChanged();
		listView.stopRefresh();
	}

	private void loadMore() {
		listView.setPullRefreshEnable(true);
		adapter.addAll(list);
		adapter.notifyDataSetChanged();
		listView.stopLoadMore();
	}
}
