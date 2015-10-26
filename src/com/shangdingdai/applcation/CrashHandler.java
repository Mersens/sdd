package com.shangdingdai.applcation;

import java.lang.Thread.UncaughtExceptionHandler;
import com.shangdingdai.utils.SharePreferenceUtil;
import android.content.Context;


public class CrashHandler implements UncaughtExceptionHandler {

	// 系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	// CrashHandler实例
	private static CrashHandler INSTANCE = new CrashHandler();
	// 程序的Context对象
	private Context mContext;

	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (mDefaultHandler != null) {
			SharePreferenceUtil.getInstance(mContext)
					.setIsFirstGesturePsd(true);
			SharePreferenceUtil.getInstance(mContext).setUnLock(false);
			CustomApplcation.getInstance().exit();
			 mDefaultHandler.uncaughtException(thread, ex); 
		}
	}

}
