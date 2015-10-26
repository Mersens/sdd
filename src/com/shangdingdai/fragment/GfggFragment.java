package com.shangdingdai.fragment;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.shangdingdai.activity.GfggDetailsActivity;
import com.shangdingdai.adapter.GfggAdapter;
import com.shangdingdai.applcation.Constants;
import com.shangdingdai.bean.GfggBean;
import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.JsonUtils;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class GfggFragment extends FragmentBase implements OnClickListener,
		IXListViewListener, EventListener {
	private XListView listView;
	private LinearLayout layout_no_msg;
	private ProgressBar progressBar;
	private GfggAdapter adapter;
	private List<GfggBean> list;
	private List<GfggBean> lists=new ArrayList<GfggBean>();
	public static final int SUCCESS = 0X01;
	public static final int REFRESH = 0X00;
	public static final int LOADMORE = 0X10;
	public boolean isRefresh = false;
	public boolean isLoadMore = false;
	public boolean isFirst = false;
	public static int nowpage = 1;
	public static int pagesize = 10;
	private MyAnsyTask mt;
	private static final String URL = Constants.SERVICE_ADDRESS
			+ "systemMessage/loadingGonggaoList";


	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.layout_gfgg, null);
		initViews(v);
		if (!NetworkUtils.isNetworkAvailable(getActivity()
				.getApplicationContext())) {
			ShowToast(R.string.tv_network_available);
			layout_no_msg.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
		} else {
			progressBar.setVisibility(View.VISIBLE);
			String params[] = { nowpage + "", pagesize + "" };
			mt = new MyAnsyTask();
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
	class MyAnsyTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			if(isCancelled()){
				return null;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("nowpage", params[0]);
			map.put("pagesize", params[1]);
			String result = HttpPostUtils.doPost(URL, map);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			progressBar.setVisibility(View.GONE);
			if(isCancelled()){
				return ;
			}
			try {
				list = JsonUtils.getGfggFromJson(result);
				if (list.size() == 10 && isFirst) {
					listView.setPullLoadEnable(true);
				}
				if (list.size() == 0 && isFirst) {
					layout_no_msg.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
					listView.setPullLoadEnable(false);
					listView.setPullRefreshEnable(false);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	private void initViews(View v) {
		// TODO Auto-generated method stub
		isFirst = true;//
		listView = (XListView) v.findViewById(R.id.listView);
		layout_no_msg = (LinearLayout) v.findViewById(R.id.layout_no_msg);
		progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
		listView.setPullLoadEnable(false);
		// 允许下拉
		listView.setPullRefreshEnable(false);
		listView.setXListViewListener(this);
	}

	private void initEvents() {
		lists.addAll(list);
		adapter = new GfggAdapter(list, getActivity().getApplicationContext());
		listView.setAdapter(adapter);
		progressBar.setVisibility(View.GONE);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String item_id=lists.get(position-1).getId();
				Intent intent=new Intent(getActivity(),GfggDetailsActivity.class);
				intent.putExtra("item_id", item_id);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
			}
			
		});
	}

	private void refresh() {
		lists.clear();
		lists.addAll(list);
		adapter.setList(list);
		adapter.notifyDataSetChanged();
		listView.stopRefresh();
	}

	private void loadMore() {
		listView.setPullRefreshEnable(true);
		lists.addAll(list);
		adapter.addAll(list);
		adapter.notifyDataSetChanged();
		listView.stopLoadMore();
	}

	@Override
	public void onRefresh() {
		listView.setEnabled(false);
		isRefresh = true;
		nowpage = 1;
		String params[] = { nowpage + "", pagesize + "" };
		mt = new MyAnsyTask();
		mt.execute(params);
	}

	@Override
	public void onLoadMore() {
		listView.setEnabled(false);
		isLoadMore = true;
		nowpage = nowpage + 1;
		mt = new MyAnsyTask();
		String params[] = { nowpage + "", pagesize + "" };
		mt.execute(params);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
