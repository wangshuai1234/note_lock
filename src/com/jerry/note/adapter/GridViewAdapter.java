package com.jerry.note.adapter;

import java.util.List;

import com.jerry.note.R;
import com.jerry.note.bean.Note;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;


public class GridViewAdapter extends BaseAdapter {

	private LayoutInflater mInflater; 
	private Context mContext;
	private List<Note> mList;
	
	public GridViewAdapter(Context context, List<Note> list) {
		this.mContext = context;
		this.mList = list;
		this.mInflater = LayoutInflater.from(mContext); 
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mList==null)
			return 0;
		else
			return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(mList==null)
			return null;
		else
			return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null)
		{
			holder=new ViewHolder();
			convertView = mInflater.inflate(R.layout.gridviewitem, group,false); 
			holder.txtTitle=(TextView)convertView.findViewById(R.id.txtTitle);
			holder.txtContent=(TextView)convertView.findViewById(R.id.txtContent);
			holder.txtDate=(TextView)convertView.findViewById(R.id.txtDetialTime);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		 Note note = mList.get(position);
		holder.txtTitle.setText(note.getTitle());
		holder.txtDate.setText(note.getCreateTime());
		holder.txtContent.setText(note.getContent());
		return convertView;
	}
	
	private static  class ViewHolder
	{
		public ViewHolder(){}
		public TextView txtTitle;
		public TextView txtDate;
		public TextView txtContent;
	}

}
