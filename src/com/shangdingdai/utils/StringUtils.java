package com.shangdingdai.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.shangdingdai.bean.BiaoRenZhengBean;

public class StringUtils {
	public static final int ICON_YJJ = 6;// 已拒绝
	public static final int ICON_TBZ = 2;// 投标中
	public static final int ICON_YMB = 3;// 已满标
	public static final int ICON_HKZ = 4;// 还款中
	public static final int ICON_SHZ = 0;// 0,1 审核中
	public static final int ICON_YJS = 5;// 已结束

	/**
	 * @author Mersens 验证是否为手机号格式
	 * @param num
	 * @return
	 */
	public static boolean isMobileNum(String num) {
		Pattern pattern = Pattern.compile("1[0-9]{10}");
		Matcher matcher = pattern.matcher(num);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author Mersens 验证是否为邮箱格式
	 * @param num
	 * @return
	 */
	public static boolean isEmail(String strEmail) {
		String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author Mersens 验证是否包含空格
	 * @param num
	 * @return
	 */
	public static boolean isContainsSpace(String str) {
		return str.contains(" ");
	}

	/**
	 * @author Mersens 验证密碼長度是否大於6
	 * @param num
	 * @return
	 */
	public static boolean isLeanth(String str) {
		if (str.trim().length() < 6) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @author Mersens 随机生成6位验证码
	 * @return
	 */
	public static String getRandom() {
		return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));

	}

	/**
	 * @author Mersens 判断需要显示的图片
	 * @param str
	 * @return
	 */
	public static int showIcon(String str) {
		if ("6".equals(str)) {
			return ICON_YJJ;
		} else if ("3".equals(str)) {

			return ICON_YMB;
		} else if ("5".equals(str)) {

			return ICON_YJS;
		} else if ("4".equals(str)) {

			return ICON_HKZ;
		} else if ("1".equals(str)) {

			return ICON_SHZ;
		} else if ("0".equals(str)) {
			return ICON_SHZ;
		} else if ("2".equals(str)) {
			return ICON_TBZ;
		}

		return 201;
	}

	public static String replaceBr(String str) {
		String str1 = str.replaceAll("<br/>", " ");
		StringBuffer sbf = new StringBuffer(str1);
		return sbf.toString();

	}

	public static String getFkshStr() {
		String str = "本借款项目已严格做好风险控制，多重措施最大程度保障投资人利益:\n措施一\n合作机构风险控制部已对该借款项目做好风控审核，对本借款承担无限连带责任保证担保;\n措施二\n商鼎贷风险储备金账户对本借款项目提供本息保障，借款人出现逾期的情况，商鼎贷会启动风险储备金账户提供等额的资金垫付给投资人";
		String str1 = str.replaceAll("<br/>", " ");
		StringBuffer sbf = new StringBuffer(str1);
		return sbf.toString();

	}

	public static Map<String, String> getImages(BiaoRenZhengBean bean) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("身份证", bean.getShenfenzheng());
		map.put("户口本", bean.getHukouben());
		map.put("结婚证", bean.getShenfenzheng());
		map.put("工作证明", bean.getGongzuozhengming());
		map.put("工资卡流水", bean.getGongzikaliushui());
		map.put("收入证明", bean.getShouruzhengming());
		map.put("征信报告", bean.getZhengxinbaogao());
		map.put("亲属调查", bean.getQinshudiaocha());
		map.put("行驶证", bean.getXingshizheng());
		map.put("车辆登记证", bean.getCheliangdengjizheng());
		map.put("车辆登记发票", bean.getCheliangdengjifapiao());
		map.put("车辆交强险", bean.getCheliangjiaoqiangxian());
		map.put("车辆商业保险", bean.getCheliangshangyebaoxian());
		map.put("国土证", bean.getGuotuzheng());
		map.put("房屋实地认证", bean.getFangwushidirenzheng());
		map.put("车辆评估认证", bean.getCheliangpinggurenzheng());
		map.put("房产证", bean.getFangchanzheng());
		return map;

	}

	public static String getProportion(String str) {
		double db = Double.valueOf(str);
		int i = (int) db;
		return String.valueOf(i);

	}

	public static int getIntProportion(String str) {
		double db = Double.valueOf(str);
		int n = (int) db;
		double progress = 360.0 * (n / 100.0);
		int i = (int) progress;
		return i;

	}

	public static String getTelNum(String tel) {
		String string = tel.substring(3, tel.length() - 4);
		return tel.replace(string, "****");

	}

	public static String getGetUrl(String url, Map<String, String> map) {
		StringBuffer sbf = new StringBuffer(url);
		sbf.append("?");
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				sbf.append(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		String path = sbf.toString();
		return path.substring(0, path.length() - 1);

	}

	@SuppressLint("SimpleDateFormat")
	public static String getFormatDate(String str) {
		String strtime = null;
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = time.parse(str);
			strtime = time.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strtime;
	}

	public static int showType(String str) {
		if ("1".equals(str)) {
			return 1;
		} else if ("2".equals(str)) {
			return 2;
		} else if ("3".equals(str)) {
			return 3;
		} else if ("4".equals(str)) {
			return 4;
		} else if ("5".equals(str)) {
			return 5;
		} else if ("6".equals(str)) {
			return 6;
		} else if ("7".equals(str)) {
			return 7;
		} else {
			return 0;
		}
	}

	public static String showBankName(String name) {
		if ("BOCO".equals(name)) {
			return BankConstant.BOCO;
		} else if ("CEB".equals(name)) {
			return BankConstant.CEB;
		} else if ("SPDB".equals(name)) {
			return BankConstant.SPDB;
		} else if ("ABC".equals(name)) {
			return BankConstant.ABC;
		} else if ("ECITIC".equals(name)) {
			return BankConstant.ECITIC;
		} else if ("CCB".equals(name)) {
			return BankConstant.CCB;
		} else if ("CMBC".equals(name)) {
			return BankConstant.CMBC;
		} else if ("SDB".equals(name)) {
			return BankConstant.SDB;
		} else if ("POST".equals(name)) {
			return BankConstant.POST;
		} else if ("CMBCHINA".equals(name)) {
			return BankConstant.CMBCHINA;
		} else if ("CIB".equals(name)) {
			return BankConstant.CIB;
		} else if ("ICBC".equals(name)) {
			return BankConstant.ICBC;
		} else if ("BOC".equals(name)) {
			return BankConstant.BOC;
		} else if ("BCCB".equals(name)) {
			return BankConstant.BCCB;
		} else if ("GDB".equals(name)) {
			return BankConstant.GDB;
		} else if ("HXB".equals(name)) {
			return BankConstant.HXB;
		} else if ("XAYHGFYHGS".equals(name)) {
			return BankConstant.XAYHGFYHGS;
		} else if ("SHYH".equals(name)) {
			return BankConstant.SHYH;
		} else if ("TJYH".equals(name)) {
			return BankConstant.TJYH;
		} else if ("SZNCSYYHGFYHGS".equals(name)) {
			return BankConstant.SZNCSYYHGFYHGS;
		} else if ("BJNCSYYHGFYHGS".equals(name)) {
			return BankConstant.BJNCSYYHGFYHGS;
		} else if ("HZYHGFYHGS".equals(name)) {
			return BankConstant.HZYHGFYHGS;
		} else if ("KLYHGFYHGS".equals(name)) {
			return BankConstant.KLYHGFYHGS;
		} else if ("ZZYH".equals(name)) {
			return BankConstant.ZZYH;
		} else if ("WZYH".equals(name)) {
			return BankConstant.WZYH;
		} else if ("HKYH".equals(name)) {
			return BankConstant.HKYH;
		} else if ("NJYHGFYHGS".equals(name)) {
			return BankConstant.NJYHGFYHGS;
		} else if ("XMYHGFYHGS".equals(name)) {
			return BankConstant.XMYHGFYHGS;
		} else if ("NCYH".equals(name)) {
			return BankConstant.NCYH;
		} else if ("HKBEA".equals(name)) {
			return BankConstant.HKBEA;
		} else if ("JISYHGFYHGS".equals(name)) {
			return BankConstant.JISYHGFYHGS;
		} else if ("CDYH".equals(name)) {
			return BankConstant.CDYH;
		} else if ("NBYHGFYHGS".equals(name)) {
			return BankConstant.NBYHGFYHGS;
		} else if ("CSYHGFYHGS".equals(name)) {
			return BankConstant.CSYHGFYHGS;
		} else if ("HBYHGFYHGS".equals(name)) {
			return BankConstant.HBYHGFYHGS;
		} else if ("GUAZYHGFYHGS".equals(name)) {
			return BankConstant.GUAZYHGFYHGS;
		} else {
			return BankConstant.UNDEFINITION;
		}
	}

	public static String getJianjie(String str) {
		if (TextUtils.isEmpty(str)) {
			return "暂无简介！";
		}
		StringBuffer sbf = new StringBuffer(str);
		return sbf.toString();
	}

	
	public static boolean isBankType(String bankName) {
		boolean flag = false;
		if (bankName.equals("ICBC")) {
			flag = true;

		} else if (bankName.equals("ABC")) {
			flag = true;

		} else if (bankName.equals("CCB")) {
			flag = true;

		} else if (bankName.equals("BOCO")) {
			flag = true;

		} else if (bankName.equals("CMBCHINA")) {
			flag = true;
		}
		return flag;
	}

}
