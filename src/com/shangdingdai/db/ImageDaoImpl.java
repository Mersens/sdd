package com.shangdingdai.db;

import java.util.ArrayList;
import java.util.List;

import com.shangdingdai.bean.ImageBean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ImageDaoImpl implements ImageDao {
	private DBHelper helper = null;
	public ImageDaoImpl(Context context){
		helper = new DBHelper(context);
	}

	@Override
	public void add(String id,String url) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(
				"insert into imgtb(img_id,img_url) values(?,?)",
				 new Object[] {id,url});
		db.close();
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("delete from imgtb where img_id=?",
				new Object[] {id});
		db.close();
	}

	@Override
	public void update(String id, String url) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(
				"update imgtb set img_url=? where img_id=?",
				new Object[] {url,id });
		db.close();
	}

	@Override
	public List<ImageBean> select(String id) {
		List<ImageBean> list = new ArrayList<ImageBean>();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from imgtb where img_id=? ",
				new String[] {id+""});
		while (cursor.moveToNext()) {
			ImageBean bean = new ImageBean();
			bean.setId(cursor.getString(cursor.getColumnIndex("img_id")));
			bean.setUrl(cursor.getString(cursor.getColumnIndex("img_url")));
			list.add(bean);
		}
		cursor.close();
		db.close();
		return list;
	}

}
