package com.jerry.note.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME="note.db";
	private static final String TBL_NOTE="tbl_note";
	private static final String SQL1="create table if not exists "+TBL_NOTE+" (" +
			"id integer primary key autoincrement," +
			"title text not null," +
			"content text not null," +
			"create_time text not null," +
			"last_edit_time text not null," +
			"type text, " +
			"month_of_time text not null);";
	public DBHelper(Context context)
	{
		super(context, DB_NAME, null, 1);
	}
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(SQL1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
