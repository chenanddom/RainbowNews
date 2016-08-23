package com.dom.rainbownews.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * activity的基类
 */
public abstract class BaseActivity extends Activity {
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setView();
	initData();
	initView();
	setListener();
}
	/**
	 * 设置布局文件
	 */
	public abstract void setView();
	/**
	 * 初始化数据
	 */
	public abstract void initData();
	/**
	 * 初始化布局文件中的控件
	 */
	public abstract void initView();

	/**
	 * 设置控件的监听
	 */
	public abstract void setListener();
}
