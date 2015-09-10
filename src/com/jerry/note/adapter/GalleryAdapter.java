package com.jerry.note.adapter;

import java.util.List;

import com.jerry.note.R;
import com.jerry.note.bean.Note;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class GalleryAdapter extends BaseAdapter {

	private LayoutInflater mInflater; 
	private Context mContext;
	private List<Note> mList;
	
	public GalleryAdapter(Context context, List<Note> list) {
		//super();
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

	private class ViewHolder
	{
		public TextView text;
		public TextView time;
		public TextView changeTime;
		public TextView contents;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null)
		{
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.activity_conent, group,false);
			convertView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			holder.text=(TextView)convertView.findViewById(R.id.textContent);
			holder.time=(TextView)convertView.findViewById(R.id.textTime);
			holder.changeTime=(TextView)convertView.findViewById(R.id.textChangeTime);
			holder.contents=(TextView)convertView.findViewById(R.id.contents);
			convertView.setTag(holder);
		}
		else
		{
			holder=(ViewHolder)convertView.getTag();
		}
		
		Note note=mList.get(position);
		
		holder.text.setText(note.getTitle());
		holder.time.setText("´´½¨£º"+note.getCreateTime());
		holder.changeTime.setText("ÐÞ¸Ä£º"+note.getLastModifyTime());
		holder.contents.setText(note.getContent());
		
		return  convertView;
	}
}
