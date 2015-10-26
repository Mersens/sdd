package com.shangdingdai.fragment;

import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.shangdingdai.adapter.TzglAdapter;
import com.shangdingdai.bean.XmtzBean;
import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.JsonUtils;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.view.R;
import com.shangdingdai.view.XListView;
import com.shangdingdai.view.XListView.IXListViewListener;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class TbzFragment extends FragmentBase implements OnClickListener,
IXListViewListener, EventListener{
	private XListView mListView;
	private LinearLayout layout_no_msg;
	public static final int SUCCESS = 0X01;
	public static final int REFRESH = 0X00;
	public static final int LOADMORE = 0X10;
	public boolean isRefresh = false;
	public boolean isLoadMore = false;
	public boolean isFirst = false;
	private static final String URL = "https://appservice.shangdingdai.com/mytouzi/getTouziData";
	public static int page = 1;
	public static int pagesize = 10;
    String userid;
	private List<XmtzBean> list;
	private TzglAdapter adapter;
    private ProgressBar progressBar;
    private MyAnsyTask mt;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.layout_tbz, null);
		 userid = SharePreferenceUtil.getInstance(
					getActivity().getApplicationContext()).getUseId();
		initViews(v);
		if (!NetworkUtils.isNetworkAvailable(getActivity()
				.getApplicationContext())) {
			ShowToast(R.string.tv_network_available);
			layout_no_msg.setVisibility(View.VISIBLE);
		} else {
			progressBar.setVisibility(View.VISIBLE);
			mt = new MyAnsyTask();
			String params[] = { userid,page + "", pagesize + "", "touzizhong" };
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
		layout_no_msg = (LinearLayout) v.findViewById(R.id.layout_no_msg);
		mListView = (XListView) v.findViewById(R.id.listView);
		progressBar=(ProgressBar) v.findViewById(R.id.progressBar);
		mListView.setPullLoadEnable(false);
		// 允许下拉
		mListView.setPullRefreshEnable(false);
		mListView.setXListViewListener(this);

		
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
			map.put("pagesize",params[2]);
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
					list = JsonUtils.getXmtz(result);
					if(list.size()==10 && isFirst){
						mListView.setPullLoadEnable(true);
					}
					if(list.size()==0 && isFirst){
						layout_no_msg.setVisibility(View.VISIBLE);
						mListView.setVisibility(View.GONE);
						mListView.setPullLoadEnable(false);
						mListView.setPullRefreshEnable(false);	
						progressBar.setVisibility(View.GONE);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					progressBar.setVisibility(View.GONE);
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

	private void initEvents() {
		adapter = new TzglAdapter(list, getActivity().getApplicationContext());
		mListView.setAdapter(adapter);
		progressBar.setVisibility(View.GONE);
	}
	
	private void refresh() {
		adapter.setList(list);
		adapter.notifyDataSetChanged();
		mListView.stopRefresh();
	}
	private void loadMore() {
		mListView.setPullRefreshEnable(true);
		adapter.addAll(list);
		adapter.notifyDataSetChanged();
		mListView.stopLoadMore();
	}
	
	@Override
	public void onRefresh() {
		mListView.setEnabled(false);
		isRefresh = true;
		page = 1;
		mt = new MyAnsyTask();
		String params[] = { userid,page + "", pagesize + "", "touzizhong"  };
		mt.execute(params);
	}

	@Override
	public void onLoadMore() {
		mListView.setEnabled(false);
		isLoadMore = true;
		page = page + 1;
		mt = new MyAnsyTask();
		String params[] = { userid,page + "", pagesize + "", "touzizhong" };
		mt.execute(params);
	}

	@Override
	public void onClick(View v) {

	}
}
