package com.shangdingdai.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * 
 * @author Mersens
 * @param <E>
 */
public abstract class BaseListAdapter<E> extends BaseAdapter {
  public List<E> list;
  public Context context;
  public LayoutInflater mInflater;
  public BaseListAdapter(List<E> list,Context context){
	  this.list=list;
	  this.context=context;
	  mInflater=LayoutInflater.from(context);
  }

  //添加元素
  public void add(E e){
	  list.add(e);
	  notifyDataSetChanged();
  }
  
  //添加元素
  public void setList(List<E> list){
	  this.list=list;
	  notifyDataSetChanged();
  }
  //添加集合
  public void addAll(List<E> list){
	  this.list.addAll(list);
	  notifyDataSetChanged();
  }
   
  //移除元素
  public void remove(int position){
	  this.list.remove(position);
	  notifyDataSetChanged();
  }
  
  
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getContentView(position,convertView,parent);
	}
	
	public abstract View getContentView(int position, View convertView, ViewGroup parent);
}
