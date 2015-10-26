package com.shangdingdai.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {
	private static SharePreferenceUtil sp;
	private static SharedPreferences mSharedPreferences;
	private static SharedPreferences.Editor editor;
	public static final String SHARED_KEY_NOTIFY = "shared_key_notify";
	public static final String SHARED_KEY_ISFIRST = "shared_key_isfirst";
	public static final String PREFERENCE_NAME = "_sharedinfo";
	public static final String USER_ID="user_id";
	public static final String USER_NAME="user_name";
	public static final String USER_PSD="user_psd";
	public static final String ISLOGIN="isLogin";
	public static final String USER_TEL="user_tel";
	public static final String USER_SMRZ="user_smrz";
	public static final String USER_BDDH="user_bddh";
	public static final String USER_RZYX="user_rzyx";
	public static final String USER_EMAIL="user_email";
	public static final String USER_SAFELEVEL="user_safelevel";
	public static final String USER_ZHANGHUYUE="user_zhanghuyue";
	public static final String GESTUREPSD="gesturepsd";
	public static final String ISGESTUREPSD="isgesturepsd";
	public static final String ISFIRST_GESTUREPSD="isfirst_gesturepsd";
	public static final String FIRSTLOGIN="firstlogin";
	public static final String USERBANK="userbank";
	public static final String ISUNLOCK="isunlock";
	private SharePreferenceUtil(){
		
	}
	
	/**
	 * @author Mersens
	 * 线程安全的单例模式
	 * @param context
	 * @return
	 */
	public static synchronized SharePreferenceUtil getInstance(Context context){
		if(sp==null){
		sp=new SharePreferenceUtil();
		mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		editor = mSharedPreferences.edit();
		}
		return sp;
	}
	// 是否允许推送通知
	public  boolean isAllowPushNotify() {
		return mSharedPreferences.getBoolean(SHARED_KEY_NOTIFY, true);
	}

	public  void setPushNotifyEnable(boolean isChecked) {
		editor.putBoolean(SHARED_KEY_NOTIFY, isChecked);
		editor.commit();
	}
	

	public boolean isHasGesturePsd(){
		return mSharedPreferences.getBoolean(ISGESTUREPSD, false);
	}
	
	public  void setHasGesturePsd(boolean isGesturePsd){
		editor.putBoolean(ISGESTUREPSD, isGesturePsd);
		editor.commit();
	}
	
	public boolean isFirstGesturePsd(){
		return mSharedPreferences.getBoolean(ISFIRST_GESTUREPSD, true);
	}
	
	public  void setIsFirstGesturePsd(boolean isfirst_gesturepsd){
		editor.putBoolean(ISFIRST_GESTUREPSD, isfirst_gesturepsd);
		editor.commit();
	}
	
	public boolean isFirst(){
		return mSharedPreferences.getBoolean(SHARED_KEY_ISFIRST, true);
	}
	
	public  void setIsFirst(boolean isFist){
		editor.putBoolean(SHARED_KEY_ISFIRST, isFist);
		editor.commit();
	}	
	
	public boolean isFirstLogin(){
		return mSharedPreferences.getBoolean(FIRSTLOGIN, false);
	}
	
	public  void setIsFirstLogin(boolean isFirstLogin){
		editor.putBoolean(FIRSTLOGIN, isFirstLogin);
		editor.commit();
	}	
	
	public boolean isUnLock(){
		return mSharedPreferences.getBoolean(ISUNLOCK, false);
	}
	
	public  void setUnLock(boolean isUnlock){
		editor.putBoolean(ISUNLOCK, isUnlock);
		editor.commit();
	}
	
	public  String getUseId(){
		return mSharedPreferences.getString(USER_ID, "");
	}
	public void setUserId(String user_id){
		editor.putString(USER_ID, user_id);
		editor.commit();
	}
	
	public  String getUserBank(){
		return mSharedPreferences.getString(USERBANK, null);
	}
	public void setUserBank(String userbank){
		editor.putString(USERBANK, userbank);
		editor.commit();
	}
	
	public  String getGesturePsd(){
		return mSharedPreferences.getString(GESTUREPSD, "");
	}
	public void setGesturePsd(String psd){
		editor.putString(GESTUREPSD, psd);
		editor.commit();
	}
	
	public String getUserName(){
		return mSharedPreferences.getString(USER_NAME, "");
	}
	public void setUserName(String user_name){
		editor.putString(USER_NAME, user_name);
		editor.commit();
	}
	
	public String getSafelevel(){
		return mSharedPreferences.getString(USER_SAFELEVEL, "");
	}
	public void setSafelevel(String str){
		editor.putString(USER_SAFELEVEL, str);
		editor.commit();
	}
	
	public String getUserEmail(){
		return mSharedPreferences.getString(USER_EMAIL, "");
	}
	public void setUserEmail(String user_email){
		editor.putString(USER_EMAIL, user_email);
		editor.commit();
	}
	
	public String getUserPsd(){
		return mSharedPreferences.getString(USER_PSD, "");
	}
	public void setUserPsd(String user_psd){
		editor.putString(USER_PSD, user_psd);
		editor.commit();
	}
	
	public String getUserZhye(){
		return mSharedPreferences.getString(USER_ZHANGHUYUE, "");
	}
	public void setUserZhye(String zhye){
		editor.putString(USER_ZHANGHUYUE, zhye);
		editor.commit();
	}
	
	public String getUserTel(){
		return mSharedPreferences.getString(USER_TEL, "");
	}
	public void setUserTel(String tel){
		editor.putString(USER_TEL, tel);
		editor.commit();
	}
	
	public boolean isphone(){
		return mSharedPreferences.getBoolean(USER_BDDH, false);
	}
	
	public void setPhone(boolean islogin){
		editor.putBoolean(USER_BDDH, islogin);
		editor.commit();
	}
	
	public boolean isLogin(){
		return mSharedPreferences.getBoolean(ISLOGIN, false);
	}
	
	public void setLogin(boolean islogin){
		editor.putBoolean(ISLOGIN, islogin);
		editor.commit();
	}
	
	public boolean isSmrz(){
		return mSharedPreferences.getBoolean(USER_SMRZ, false);
	}
	
	public void setSmrz(boolean islogin){
		editor.putBoolean(USER_SMRZ, islogin);
		editor.commit();
	}
	
	public boolean isRzyx(){
		return mSharedPreferences.getBoolean(USER_RZYX, false);
	}
	
	public void setRzyx(boolean islogin){
		editor.putBoolean(USER_RZYX, islogin);
		editor.commit();
	}
	
	public void clearData(){
		editor.clear().commit();
	}
}
