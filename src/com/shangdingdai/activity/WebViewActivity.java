package com.shangdingdai.activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.shangdingdai.view.R;
public class WebViewActivity extends BaseActivity {
	public TextView mTextView;
	public ImageView mImageView;
	private WebView mWebView;
	private ProgressBar progress;
	private static final String PATH="https://m.p2p.shangdingdai.com/regxieyi.html?codeid=1";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_webview);
		initViews();
		initEvents();
	}
	private void initViews() {
		mImageView=(ImageView) findViewById(R.id.img_back);
		mImageView.setVisibility(View.VISIBLE);
		mTextView=(TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.zcxy);
		mWebView=(WebView) findViewById(R.id.webView);
		progress=(ProgressBar) findViewById(R.id.progressBar);
		progress.setBackgroundResource(R.color.pro_bg);
	}
	
	private void initEvents() {
		
        WebSettings settings = mWebView.getSettings();  
        settings.setSupportZoom(true);       
        settings.setUseWideViewPort(true); 
        settings.setLoadWithOverviewMode(true); 
        mWebView.loadUrl(PATH);
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
