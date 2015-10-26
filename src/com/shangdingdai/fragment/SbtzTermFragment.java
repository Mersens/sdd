package com.shangdingdai.fragment;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.shangdingdai.activity.BiaoDetailedActivity;
import com.shangdingdai.adapter.XszxAdapter;
import com.shangdingdai.applcation.Constants;
import com.shangdingdai.bean.BiaoBean;
import com.shangdingdai.bean.XszxBean;
import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.view.R;
import com.shangdingdai.view.XListView;
import com.shangdingdai.view.XListView.IXListViewListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;

public class SbtzTermFragment extends FragmentBase implements OnClickListener,
		IXListViewListener, EventListener {
	private ProgressBar progressBar;
	private XListView mListView;
	private XszxAdapter adapter;
	private List<BiaoBean> list;
	private List<BiaoBean> lists=new ArrayList<BiaoBean>();
	private XszxBean bean;
	public static String GETINFO_URL = Constants.SERVICE_ADDRESS+"biao/getBiaoList";
	public static final int SUCCESS = 0X01;
	public static final int REFRESH = 0X00;
	public static final int LOADMORE = 0X10;
	public static int page = 1;
	public static int pagesize = 10;
	public boolean isRefresh = false;
	public boolean isLoadMore = false;
	public boolean isFirst = false;
	private MyAnsyTask mt;
	private LinearLayout layout_no_msg;


	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.layout_sbtz_qx, null);
		initViews(v);
		if (!NetworkUtils.isNetworkAvailable(getActivity()
				.getApplicationContext())) {
			ShowToast(R.string.tv_network_available);
			layout_no_msg.setVisibility(View.VISIBLE);

		} else {
			progressBar.setVisibility(View.VISIBLE);
			 mt = new MyAnsyTask();
			String params[] = { page + "", pagesize + "", "dkqx", "0" };
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
	private void initEvents() {
		lists.addAll(list);
		adapter = new XszxAdapter(list, getActivity().getApplicationContext());
		mListView.setAdapter(adapter);
		progressBar.setVisibility(View.GONE);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BiaoBean bean= lists.get(position-1);
				String biaoid=bean.getBiaoid();
				Intent intent=new Intent(getActivity(),BiaoDetailedActivity.class);
				intent.putExtra("biaoid", biaoid);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
		});
	}

	private void initViews(View v) {
		isFirst = true;
		mListView = (XListView) v.findViewById(R.id.listView);
		progressBar=(ProgressBar) v.findViewById(R.id.progressBar);
		mListView.setPullLoadEnable(true);
		// 允许下拉
		mListView.setPullRefreshEnable(false);
		mListView.setXListViewListener(this);
		layout_no_msg=(LinearLayout) v.findViewById(R.id.layout_no_msg);

	}

	class MyAnsyTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			if(isCancelled()){
				return null;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("page", params[0]);
			map.put("pagesize", params[1]);
			map.put("ordertype", params[2]);
			map.put("isnewer", params[3]);
			String result = HttpPostUtils.doPost(GETINFO_URL, map);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(isCancelled()){
				return ;
			}
			Gson gson = new Gson();
			bean = gson.fromJson(result, XszxBean.class);
			if (bean != null) {
				list = bean.getBiaolist();
			} else {
				list = new ArrayList<BiaoBean>();
			}
			if (isFirst) {
				isFirst = false;
				initEvents();
			} else if (isRefresh) {
				isRefresh = false;
				refresh();
				mListView.setEnabled(true);
			} else if (isLoadMore) {
				isLoadMore = false;
				loadMore();
				mListView.setEnabled(true);
			}

		}
	}

	private void loadMore() {
		mListView.setPullRefreshEnable(true);
		lists.addAll(list);
		adapter.addAll(list);
		adapter.notifyDataSetChanged();
		mListView.stopLoadMore();
	}

	private void refresh() {
		lists.clear();
		adapter.setList(list);
		lists.addAll(list);
		adapter.notifyDataSetChanged();
		mListView.stopRefresh();
	}
	@Override
	public void onRefresh() {
		mListView.setEnabled(false);
		isRefresh = true;
		page = 1;
		 mt = new MyAnsyTask();
		String params[] = { page + "", pagesize + "", "dkqx", "0" };
		mt.execute(params);
	}

	@Override
	public void onLoadMore() {
		mListView.setEnabled(false);
		isLoadMore = true;
		page = page + 1;
		mt = new MyAnsyTask();
		String params[] = { page + "", pagesize + "", "dkqx", "0" };
		mt.execute(params);
	}

	@Override
	public void onClick(View v) {

	}
}
