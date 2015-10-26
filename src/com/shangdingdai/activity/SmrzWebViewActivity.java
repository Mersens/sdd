package com.shangdingdai.activity;

import com.shangdingdai.view.R;
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

public class SmrzWebViewActivity extends BaseActivity{
	public TextView mTextView;
	public ImageView mImageView;
	private WebView mWebView;
	private ProgressBar progress;
	private String path;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_smrz_webview);
		path=getIntent().getStringExtra("path");
		initViews();
		initEvents();
	}
	private void initViews() {
		mImageView=(ImageView) findViewById(R.id.img_back);
		mImageView.setVisibility(View.VISIBLE);
		mTextView=(TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.smrz);
		mWebView=(WebView) findViewById(R.id.webView);
		progress=(ProgressBar) findViewById(R.id.progressBar);
		progress.setBackgroundResource(R.color.pro_bg);
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void initEvents() {
        WebSettings settings = mWebView.getSettings();  
        settings.setSupportZoom(true);       
        settings.setUseWideViewPort(true); 
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true); 
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);  
        mWebView.loadUrl(path);
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
