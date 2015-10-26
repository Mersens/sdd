package com.shangdingdai.activity;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import com.shangdingdai.applcation.Constants;
import com.shangdingdai.bean.XtxxItemBean;
import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.JsonUtils;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.R;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class XtxxDetailsActivity extends BaseActivity{
	public ImageView mImageView;
	public TextView mTextView;
	private XtxxItemBean bean=null;
	private WebView webView;
	private TextView tv_title;
	private TextView tv_ly;
	private TextView tv_time;
	private LinearLayout layout_progressBar;
	private LinearLayout layout_no_msg;
	private static final String URL = Constants.SERVICE_ADDRESS
			+ "systemMessage/loadingMessageById";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_xtxx_details);
		initViews();
		initEvents();
		if (!NetworkUtils.isNetworkAvailable(
				getApplicationContext())) {
			ShowToast(R.string.tv_network_available);
			layout_no_msg.setVisibility(View.VISIBLE);
		} else {
			layout_progressBar.setVisibility(View.VISIBLE);
			layout_no_msg.setVisibility(View.GONE);
			MyAnsyTask mt=new MyAnsyTask();
			String params=getIntent().getStringExtra("item_id");
			mt.execute(params);
		}
	}
	private void initViews() {
		// TODO Auto-generated method stub
		mImageView=(ImageView)findViewById(R.id.img_back);
		mTextView=(TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.xq);
		mImageView.setVisibility(View.VISIBLE);
		webView=(WebView) findViewById(R.id.webView1);
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_ly=(TextView) findViewById(R.id.tv_ly);
		tv_time=(TextView) findViewById(R.id.tv_time);
		layout_progressBar=(LinearLayout) findViewById(R.id.layout_progressBar);
		layout_no_msg=(LinearLayout) findViewById(R.id.layout_no_msg);
		
	}
	@SuppressLint("SetJavaScriptEnabled")
	private void initEvents() {
		// TODO Auto-generated method stub	

        WebSettings settings = webView.getSettings();       
        settings.setUseWideViewPort(true); 
        //自适应屏幕
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true); 
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);  
        settings.setSupportZoom(true); 
        settings.setUseWideViewPort(true);
        settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        settings.setDefaultFontSize(36);
		mImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finishActivity();
				
			}
		});
	}
	
	class MyAnsyTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", params[0]);
			String result = HttpPostUtils.doPost(URL, map);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {

			try {
				bean=JsonUtils.getXtxxItemFromJson(result);
				if(bean==null){
					layout_no_msg.setVisibility(View.VISIBLE);
					layout_progressBar.setVisibility(View.GONE);
				}else{
					initDatas();
					layout_no_msg.setVisibility(View.GONE);
					layout_progressBar.setVisibility(View.GONE);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@SuppressLint("NewApi")
	private void initDatas() {
		// TODO Auto-generated method stub
		tv_title.setText(bean.getName());
		tv_ly.setText(R.string.app_name);
		tv_time.setText(StringUtils.getFormatDate(bean.getTime()));
		webView.setInitialScale(100);
		 webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webView.loadDataWithBaseURL(null,bean.getCenter()==null?"":bean.getCenter(),"text/html", "utf-8",null);
        webView.setWebViewClient(new WebViewClient() {  
            @Override  
            public boolean shouldOverrideUrlLoading(WebView view, String url) {  
                view.loadUrl(url); 
                return true;    
            } 
        }); 
	}
}
