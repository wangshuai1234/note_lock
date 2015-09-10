package com.jerry.note.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jerry.note.bean.Note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
	DBHelper mHelper;
	SQLiteDatabase mDatabase;
	public DBManager(Context context)
	{
		mHelper=new DBHelper(context);
		mDatabase=mHelper.getWritableDatabase();
	}
	
	/**
	 * 
	 * @param note
	 * @return
	 */
	public boolean insertNote(Note note)
	{
		ContentValues values=new ContentValues();
		values.put("title", note.getTitle());
		values.put("content", note.getContent());
		values.put("create_time", note.getCreateTime());
		values.put("last_edit_time", note.getLastModifyTime());
		values.put("month_of_time", note.getMonthOfTime());
		values.put("type", note.getType());
		long id=mDatabase.insert("tbl_note", null, values);
		if(id!=-1)
			return true;
		return false;
	}
	/**
	 * 
	 * @param note
	 * @return
	 */
	public boolean updateNote(Note note)
	{
		ContentValues values=new ContentValues();
		values.put("title", note.getTitle());
		values.put("content", note.getContent());
		values.put("last_edit_time", note.getLastModifyTime());
		values.put("type", note.getType());
		values.put("month_of_time", note.getMonthOfTime());
		String whereClause="id=?";
		String[] whereArgs={String.valueOf(note.getId())};
		int affect=mDatabase.update("tbl_note", values, whereClause, whereArgs);
		if(affect>0)
			return true;
		return false;
	}
	
	public  List<Note>getAllNotes()
	{
		List<Note>notes=new ArrayList<Note>();
		String sql="select * from tbl_note order by month_of_time desc;";
		Cursor cursor = mDatabase.rawQuery(sql, null);
		while(cursor.moveToNext())
		{
			Note note=new Note();
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String title = cursor.getString(cursor.getColumnIndex("title"));
			String content = cursor.getString(cursor.getColumnIndex("content"));
			String createTime = cursor.getString(cursor.getColumnIndex("create_time"));
			String lastEditTime = cursor.getString(cursor.getColumnIndex("last_edit_time"));
			String type = cursor.getString(cursor.getColumnIndex("type"));
			String monthOfTime = cursor.getString(cursor.getColumnIndex("month_of_time"));
			note.setId(id);
			note.setContent(content);
			note.setTitle(title);
			note.setType(type);
			note.setCreateTime(createTime);
			note.setLastModifyTime(lastEditTime);
			note.setMonthOfTime(monthOfTime);
			notes.add(note);
		}
		return notes;
	}

	public List<Map<String,String>>findNoteCount()
	{
		List<Map<String,String>> info=null;
		String sql="select month_of_time,count(*) as notecount from tbl_note  group by month_of_time order by month_of_time desc;";
		Cursor cursor=mDatabase.rawQuery(sql,null);
		info =new ArrayList<Map<String,String>>();
		while(cursor.moveToNext())
		{
			Map<String,String> node=new HashMap<String, String>();
			node.put("monthoftime", cursor.getString(cursor.getColumnIndex("month_of_time")));
			node.put("notecount", cursor.getString(cursor.getColumnIndex("notecount")));
			info.add(node);
		}
		return info;
	}
	public boolean deleteNotes(int id)
	{
		int i=mDatabase.delete("tbl_note", "id=?", new String[]{String.valueOf(id)});
		if(i>=0)
			return true;
		return false;
	}
	
	public List<Note> findNotesByTime(String monthoftime) {
		// TODO Auto-generated method stub
		List<Note>  notes=null;
		Cursor cursor=mDatabase.query("tbl_note", null, "month_of_time =? ", new String[]{monthoftime}, null, null, "create_time desc");
		notes =new ArrayList<Note>();
		while(cursor.moveToNext())
		{
			Note note=new Note();
			note.setId(cursor.getInt(cursor.getColumnIndex("id")));
			note.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			note.setCreateTime(cursor.getString(cursor.getColumnIndex("create_time")));
			note.setLastModifyTime(cursor.getString(cursor.getColumnIndex("last_edit_time")));
			note.setMonthOfTime(cursor.getString(cursor.getColumnIndex("month_of_time")));
			note.setContent(cursor.getString(cursor.getColumnIndex("content")));
			note.setType(cursor.getString(cursor.getColumnIndex("type")));
			notes.add(note);
		}
		return notes;
	}
}
