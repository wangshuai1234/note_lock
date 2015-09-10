package com.jerry.note.bean;

import java.io.Serializable;

import android.text.format.Time;

public class Note implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -683619959726158358L;
	
	private String title;
	private String createTime;
	private int id;
	private String lastModifyTime;
	private String monthOfTime ;
	private String content;
	private String type;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public String getMonthOfTime () {
		return monthOfTime ;
	}
	public void setMonthOfTime (String monthOfTime ) {
		this.monthOfTime  = monthOfTime ;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Note()
	{
		
	}
	public Note (String title,Time createTime,Time lastModifyTime,String content,String type)
	{
		this.title=title;
		this.createTime=createTime.year+"年"+createTime.month+"月"+createTime.monthDay+"日"+"   "+createTime.hour+":"+createTime.minute+":"+createTime.second;
		this.lastModifyTime=lastModifyTime.year+"年"+lastModifyTime.month+"月"+lastModifyTime.monthDay+"日"+"   "+lastModifyTime.hour+":"+lastModifyTime.minute+":"+lastModifyTime.second;
		this.monthOfTime =createTime.year+"年"+createTime.month+"月";
		this.content=content;
		this.type=type;
	}
	public Note(String title, String createTime,
			String lastModifyTime, String monthOfTime , String content,String type,int id) {
		super();
		this.id=id;
		this.type=type;
		this.title = title;
		this.createTime = createTime;
		this.lastModifyTime = lastModifyTime;
		this.monthOfTime  = monthOfTime ;
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
