package com.shangdingdai.activity;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shangdingdai.applcation.Constants;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.utils.StringUtils;
import com.shangdingdai.view.R;

public class TouBiaoWebActivity extends BaseActivity {
	public TextView mTextView;
	public ImageView mImageView;
	private WebView mWebView;
	private ProgressBar progress;
	private String userid;
	private String money;
	private String  userhongbaoid;
	private String biaoid;
	public static final String PATH=Constants.SERVICE_ADDRESS
			+ "biao/touziBiao";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_toubiao_web);
		userid = SharePreferenceUtil.getInstance(getApplicationContext())
				.getUseId();
		money=getIntent().getStringExtra("money");
		biaoid=getIntent().getStringExtra("biaoid");
		 userhongbaoid=getIntent().getStringExtra("userhongbaoid");
		System.out.println("userid==="+userid+"====money=="+money+"+hongbiaoid===="+userhongbaoid);
		initViews();
		initEvents();
	}
	private void initViews() {
		mImageView=(ImageView) findViewById(R.id.img_back);
		mImageView.setVisibility(View.VISIBLE);
		mTextView=(TextView) findViewById(R.id.txt_title);
		mTextView.setText("投资");
		mWebView=(WebView) findViewById(R.id.webView);
		progress=(ProgressBar) findViewById(R.id.progressBar);
		progress.setBackgroundResource(R.color.pro_bg);
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void initEvents() {
        WebSettings settings = mWebView.getSettings();  
        settings.setSupportZoom(true);       
        settings.setUseWideViewPort(true); 
        settings.setLoadWithOverviewMode(true); 
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);  
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		Map<String,String> map=new HashMap<String, String>();
		map.put("userid",userid);
		map.put("money",money);
		map.put("userhongbaoid",userhongbaoid);
		map.put("jiaoyipassword","123");
		map.put("biaoid",biaoid);
		String url=StringUtils.getGetUrl(PATH, map);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {  
            @Override  
            public boolean shouldOverrideUrlLoading(WebView view, String url) {  
                view.loadUrl(url); 
                return true;    
            } 
        }); 
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    progress.setProgress(newProgress);
                    if (newProgress == 100) {
                     progress.setVisibility(View.GONE);
             }
            }
    });

		mImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});
		
	}

}
