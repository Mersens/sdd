package com.shangdingdai.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangdingdai.applcation.Constants;
import com.shangdingdai.bean.ImageBean;
import com.shangdingdai.db.ImageDao;
import com.shangdingdai.db.ImageDaoImpl;
import com.shangdingdai.utils.HttpPostUtils;
import com.shangdingdai.utils.ImageLoadOptions;
import com.shangdingdai.utils.JsonUtils;
import com.shangdingdai.utils.NetworkUtils;
import com.shangdingdai.utils.PhotoUtil;
import com.shangdingdai.utils.SharePreferenceUtil;
import com.shangdingdai.view.MainActivity;
import com.shangdingdai.view.R;

public class SetInofActivity extends BaseActivity implements OnClickListener {
	public TextView mTextView;
	public ImageView mImageView;
	private ImageView user_img;
	private Button btn_back;
	private TextView tv_nick;
	private RelativeLayout layout_user_img;
	public static final int FROM_XC=0X00;
	public static final int FROM_CJ=0X01;
	public 	String path;
	private ImageDao dao;
	private static final String UPLOADIMG_URL="https://appservice.shangdingdai.com/myinfo/headup";
	@SuppressLint("HandlerLeak")
	Handler mCodeHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.arg1) {
			case 1:
                //上传成功
				ShowToast("更新成功！!");
				break;
			case 2:
				//图片资源过大
				ShowToast("图片资源过大!");
				break;
			case 3:
				//找不到用户信息
				ShowToast("找不到用户信息!");
				break;

			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		dao=new ImageDaoImpl(this);
		initViews();
		initEvents();
	}
	private void initViews() {
		// TODO Auto-generated method stub
		layout_user_img=(RelativeLayout) findViewById(R.id.layout_user_img);
		user_img=(ImageView) findViewById(R.id.user_img);
		mImageView=(ImageView) findViewById(R.id.img_back);
		mImageView.setVisibility(View.VISIBLE);
		mTextView=(TextView) findViewById(R.id.txt_title);
		mTextView.setText(R.string.yhxx);
		btn_back=(Button) findViewById(R.id.btn_back);
		tv_nick=(TextView) findViewById(R.id.tv_nick);
		String id=SharePreferenceUtil.getInstance(getApplicationContext()).getUseId();
		List<ImageBean> bean=dao.select(id);
		if(bean!=null && bean.size()>0){
			ImageLoader.getInstance().displayImage(bean.get(0).getUrl(), user_img,
					ImageLoadOptions.getOptions());
		}else{
			user_img.setImageResource(R.drawable.icon_user_img);
		}
		String nick=SharePreferenceUtil.getInstance(getApplicationContext()).getUserName();
		tv_nick.setText(nick);
	}
	private void initEvents() {
		// TODO Auto-generated method stub
		mImageView.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		layout_user_img.setOnClickListener(this);
	}
	
	public void showAvatarPop(){
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(intent,FROM_XC);
				
	}
	
	private void startImageAction(Uri uri, int outputX, int outputY,
			int requestCode, boolean isCrop) {
		Intent intent = null;
		if (isCrop) {
			intent = new Intent("com.android.camera.action.CROP");
		} else {
			intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		}
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, requestCode);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case FROM_XC:
			Uri uri = null;
			if (data == null) {
				return;
			}
			if (resultCode == RESULT_OK) {
				if (!Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					ShowToast("SD不可用");
					return;
				}
				uri = data.getData();
				startImageAction(uri, 200, 200,
						FROM_CJ, true);
			} else {
				ShowToast("照片获取失败");
			}
			break;
			case FROM_CJ:
				if (data == null) {
					// Toast.makeText(this, "取消选择", Toast.LENGTH_SHORT).show();
					return;
				} else {
					saveCropAvator(data);
				}
			break;

		}
	}
	
	/**
	 * 保存裁剪的头像
	 * 
	 * @param data
	 */
	@SuppressLint("SimpleDateFormat")
	private void saveCropAvator(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap bitmap = extras.getParcelable("data");
			if (bitmap != null) {
				bitmap = PhotoUtil.toRoundCorner(bitmap, 10);
				user_img.setImageBitmap(bitmap);
				// 保存图片
				String filename = new SimpleDateFormat("yyMMddHHmmss")
						.format(new Date())+".png";
				path = Constants.MyAvatarDir + filename;
				String img_id=SharePreferenceUtil.getInstance(getApplicationContext()).getUseId();
				dao.add(img_id, path);
				PhotoUtil.saveBitmap(Constants.MyAvatarDir, filename,
						bitmap, true);
				// 上传头像
				uploadAvatar(path);
				if (bitmap != null && bitmap.isRecycled()) {
					bitmap.recycle();
				}
			}
		}
	}
	
	public void uploadAvatar(String path) {
		if(!NetworkUtils.isNetworkAvailable(getApplicationContext())){
			ShowToast("没有可用网络，请检查网络设置!");
			return;
		}
		String img_id=SharePreferenceUtil.getInstance(getApplicationContext()).getUseId();
		MyAnsyTask mAnsyTask=new MyAnsyTask();
		mAnsyTask.execute(img_id,path,UPLOADIMG_URL);
	}
	
	class MyAnsyTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			Map<String,String> map=new HashMap<String, String>();
			map.put("userid", params[0]);
			File file=new File(params[1]);
			String str=HttpPostUtils.doPostImg(params[2], map, file); 
			return str;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(result==null || "".equals(result)){
				ShowToast("头像上传失败！");
				return;
			}
			String str=JsonUtils.getCode(result);
			Message msg=new Message();
			if("1".equals(str)){
				String img_id=SharePreferenceUtil.getInstance(getApplicationContext()).getUseId();
				String imgsrc=JsonUtils.getImgSrc(result);
				dao.update(img_id, imgsrc);
				msg.arg1=1;
			}else if("2".equals(str)){
				msg.arg1=2;
			}else if("3".equals(str)){
				msg.arg1=3;
			}
			mCodeHandler.sendMessage(msg);			
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_back:
			finishActivity();
			break;
		case R.id.btn_back:
			SharePreferenceUtil.getInstance(getApplicationContext()).clearData();
			intentAction(SetInofActivity.this,MainActivity.class);
			finish();
			break;
		case R.id.layout_user_img:
			showAvatarPop();
			break;
		}
		
	}

}
