package com.shangdingdai.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.shangdingdai.bean.GfggBean;
import com.shangdingdai.bean.GfggItemBean;
import com.shangdingdai.bean.HongBaoBean;
import com.shangdingdai.bean.HzcxBean;
import com.shangdingdai.bean.JyjlBean;
import com.shangdingdai.bean.MyBankCardBean;
import com.shangdingdai.bean.MyRedPagerBean;
import com.shangdingdai.bean.ShouYiBean;
import com.shangdingdai.bean.TouZiBean;
import com.shangdingdai.bean.XmtzBean;
import com.shangdingdai.bean.XtxxItemBean;
import com.shangdingdai.bean.ZcglBean;

/**
 * 
 * @author Mersens 解析Json工具类
 */
public class JsonUtils {
	/**
	 * @author Mersens 得到返回码判断是否成功获取数据
	 * @param str
	 * @return
	 */
	public static String getCode(String str) {
		String result = null;
		try {
			JSONObject jsonObject = new JSONObject(str);
			result = jsonObject.getString("code");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}
	/**
	 * @author Mersens 解析Json数据，获取Userid
	 * @param str
	 * @return
	 */
	public static String getUserId(String str) {
		String result = null;
		try {
			JSONObject jsonObject = new JSONObject(str);
			result = jsonObject.getString("userid");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}
	/**
	 * @author Mersens 解析Json数据，获取用户头像地址imgSrc
	 * @param str
	 * @return
	 */
	public static String getImgSrc(String str) {
		String result = null;
		try {
			JSONObject jsonObject = new JSONObject(str);
			result = jsonObject.getString("imgsrc");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	public static List<XmtzBean> getXmtz(String result) throws JSONException {
		List<XmtzBean> list = new ArrayList<XmtzBean>();
		if (!TextUtils.isEmpty(result)) {
			JSONObject jsonObject = new JSONObject(result);
			JSONArray array = new JSONArray(jsonObject.getString("touzilist"));
			for (int i = 0; i < array.length(); i++) {
				XmtzBean bean = new XmtzBean();
				JSONObject obj = (JSONObject) array.get(i);
				JSONObject biaojson = new JSONObject(obj.getString("biao"));
				bean.setImgsrc(biaojson.getString("imgsrc"));
				bean.setTitle(biaojson.getString("title"));
				bean.setLilv(biaojson.getString("lilv"));
				bean.setDkqy(biaojson.getString("dkqy"));
				bean.setDatastatic(biaojson.getString("datastatic"));
				JSONObject touzijson = new JSONObject(obj.getString("touzi"));
				bean.setJine(touzijson.getString("jine"));
				bean.setYujishouyi(touzijson.getString("yujishouyi"));
				list.add(bean);
			}
		}
		return list;
	}

	public static HzcxBean getHzcx(String result) throws JSONException {
		List<ShouYiBean> list = new ArrayList<ShouYiBean>();
		HzcxBean bean = new HzcxBean();
		if (!TextUtils.isEmpty(result)) {
			JSONObject jsonObject = new JSONObject(result);
			bean.setToday(jsonObject.getString("today"));
			bean.setMonthlaterdate(jsonObject.getString("monthlaterdate"));
			bean.setOnemonthshouyijine(jsonObject
					.getString("onemonthshouyijine"));
			JSONArray array = new JSONArray(jsonObject.getString("shouyilist"));
			for (int i = 0; i < array.length(); i++) {
				ShouYiBean sybean = new ShouYiBean();
				JSONObject obj = (JSONObject) array.get(i);
				sybean.setJine(obj.getString("jine"));
				sybean.setShouyiid(obj.getString("shouyiid"));
				sybean.setYinghuanshijian(obj.getString("yinghuanshijian"));
				list.add(sybean);
			}
			bean.setList(list);
		}
		return bean;
	}

	public static List<JyjlBean> getJykl(String str) throws JSONException {
		List<JyjlBean> list = new ArrayList<JyjlBean>();
		if (!TextUtils.isEmpty(str)) {
			JSONObject jsonObject = new JSONObject(str);
			JSONArray array = new JSONArray(jsonObject.getString("liushuilist"));
			for (int i = 0; i < array.length(); i++) {
				JyjlBean bean = new JyjlBean();
				JSONObject obj = (JSONObject) array.get(i);
				bean.setTypeid(obj.getString("statictype"));
				bean.setJiaoyijine(obj.getString("jiaoyijine"));
				bean.setJiaoyitime(obj.getString("jieshutime"));
				list.add(bean);
			}
		}
		return list;
	}

	public static MyBankCardBean getMyBankCard(String str) throws JSONException {
		if (!TextUtils.isEmpty(str)) {
			JSONObject jsonObject = new JSONObject(str);
			String code = jsonObject.getString("code");
			if ("1".equals(code)) {
				MyBankCardBean bean = new MyBankCardBean();
				bean.setKahao(jsonObject.getString("kahao"));
				bean.setYinhang(jsonObject.getString("yinhang"));
				return bean;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static List<MyRedPagerBean> getMyRedPagerFromJson(String str)
			throws JSONException {
		List<MyRedPagerBean> list = new ArrayList<MyRedPagerBean>();
		if (!TextUtils.isEmpty(str)) {
			JSONObject jsonObject = new JSONObject(str);
			JSONArray array = new JSONArray(jsonObject.getString("hongbao"));
			for (int i = 0; i < array.length(); i++) {
				MyRedPagerBean bean = new MyRedPagerBean();
				JSONObject obj = (JSONObject) array.get(i);
				bean.setDatastatic(obj.getString("datastatic"));
				bean.setJine(obj.getString("jine"));
				bean.setTitle(obj.getString("title"));
				bean.setYouxiaoqi(obj.getString("youxiaoqi"));
				list.add(bean);
			}
		}
		return list;
	}
	
	public static List<GfggBean> getGfggFromJson(String str) throws JSONException{
		List<GfggBean> list=new ArrayList<GfggBean>();
		if(!TextUtils.isEmpty(str)){
			JSONObject jsonObject = new JSONObject(str);
			JSONArray array = new JSONArray(jsonObject.getString("datas"));
			for (int i = 0; i < array.length(); i++) {
				GfggBean bean=new GfggBean();
				JSONObject obj = (JSONObject) array.get(i);
				bean.setId(obj.getString("id"));
				bean.setJianjie(obj.getString("jianjie"));
				bean.setTime(obj.getString("time"));
				bean.setTitle(obj.getString("title"));
				list.add(bean);
			}
		}
		return list;
		
	}
	
	public static GfggItemBean getGfggItemFromJson(String str) throws JSONException{
		if(TextUtils.isEmpty(str)){
			return null;
		}
		GfggItemBean bean=new GfggItemBean();
		JSONObject jsonObject = new JSONObject(str);
		bean.setCenter(jsonObject.getString("center"));
		bean.setId(jsonObject.getString("id"));
		bean.setLaiyuan(jsonObject.getString("laiyuan"));
		bean.setName(jsonObject.getString("name"));
		bean.setTime(jsonObject.getString("time"));
		return bean;
	}
	
	public static XtxxItemBean getXtxxItemFromJson(String str) throws JSONException{
		if(TextUtils.isEmpty(str)){
			return null;
		}
		XtxxItemBean bean=new XtxxItemBean();
		JSONObject jsonObject = new JSONObject(str);
		bean.setCenter(jsonObject.getString("center"));
		bean.setId(jsonObject.getString("id"));
		bean.setName(jsonObject.getString("name"));
		bean.setTime(jsonObject.getString("time"));
		return bean;
	}
	
	public static ZcglBean getZcglFromJson(String str) throws JSONException{
		if(TextUtils.isEmpty(str)){
			return null;
		}
		ZcglBean bean=new ZcglBean();
		JSONObject jsonObject = new JSONObject(str);
		bean.setDaishoubenxi(jsonObject.getString("daishoubenxi"));
		bean.setDongjiejine(jsonObject.getString("dongjiejine"));
		bean.setJiekuanjine(jsonObject.getString("jiekuanjine"));
		bean.setJingzichan(jsonObject.getString("jingzichan"));
		bean.setKeyongyue(jsonObject.getString("keyongyue"));
		bean.setTouzijine(jsonObject.getString("touzijine"));
		bean.setZhanghuyue(jsonObject.getString("zhanghuyue"));
		return bean;
	}
	
	
	
	public static TouZiBean getTouZiFromJson(String str) throws JSONException{
		if(TextUtils.isEmpty(str)){
			return null;
		}
		TouZiBean bean=new TouZiBean();
		JSONObject jsonObject = new JSONObject(str);
		bean.setBiaoid(jsonObject.getString("biaoid"));
		bean.setMintouzi(jsonObject.getString("mintouzi"));
		bean.setShengyujine(jsonObject.getString("shengyujine"));
		bean.setTitle(jsonObject.getString("title"));
		bean.setUseryue(jsonObject.getString("useryue"));
		return bean;
	}
	
	public static List<HongBaoBean> getHongBao(String str) throws JSONException{
		List<HongBaoBean> list=new ArrayList<HongBaoBean>();
		if(TextUtils.isEmpty(str)){
			return list;
		}
		JSONObject jsonObject = new JSONObject(str);
		JSONArray array = new JSONArray(jsonObject.getString("hongbao"));
		for(int i=0;i<array.length();i++){
			JSONObject obj = (JSONObject) array.get(i);
			HongBaoBean bean=new HongBaoBean();
			bean.setHongbaojine(obj.getString("hongbaojine"));
			bean.setHongbaoname(obj.getString("hongbaoname"));
			bean.setUserhongbaoid(obj.getString("userhongbaoid"));
			list.add(bean);
		}
		return list;
	}
	
	public static String getKyjeFromJson(String str)throws JSONException{
		if(TextUtils.isEmpty(str)){
			return null;
		}
		JSONObject jsonObject = new JSONObject(str);
		JSONObject obj=new JSONObject(jsonObject.getString("zijin"));
		return obj.getString("zhanghuyue");
		
	}
}
