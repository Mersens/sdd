package com.shangdingdai.service;

import com.shangdingdai.utils.RegisterCodeTimer;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

/**
 * 注册验证码计时服务
 * 
 */
public class RegisterCodeTimerService extends Service {

	private static Handler mHandler;
	private static RegisterCodeTimer mCodeTimer;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		mCodeTimer = new RegisterCodeTimer(60000, 1000, mHandler);
		mCodeTimer.start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mCodeTimer.cancel();
	}

	/**
	 * 设置Handler
	 */
	public static void setHandler(Handler handler) {
		mHandler = handler;
	}

}
