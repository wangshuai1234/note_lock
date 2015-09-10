package com.jerry.note;

import com.jerry.note.db.DBManager;

import android.app.Application;
import android.content.SharedPreferences;

public class MyApp extends Application {

	DBManager dbManager;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		dbManager=new DBManager(this);
	}
	
	public DBManager getDBManager()
	{
		return dbManager;
	}
	
	
	public void setIsUsed(boolean isUsed)
	{
		SharedPreferences settings = this.getSharedPreferences(
				"settings", 0);
		settings.edit().putBoolean("isUsed", isUsed).commit();
	}
	
	public void setPassword()
	{
		SharedPreferences settings = this.getSharedPreferences(
				"settings", 0);
		settings.edit().putString("password", "").commit();
	}
	
	public boolean isLockUsed()
	{
		SharedPreferences settings = this.getSharedPreferences(
				"settings", 0);
		return settings.getBoolean("isUsed", false); // , "0,1,2,3,4,5,6,7,8"
	}
}
