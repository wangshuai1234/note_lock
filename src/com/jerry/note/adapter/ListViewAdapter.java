package com.jerry.note.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jerry.note.DetailNoteActivity;
import com.jerry.note.R;
import com.jerry.note.bean.Note;
import com.jerry.note.db.DBManager;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import android.widget.TextView;


public class ListViewAdapter extends BaseAdapter {

	public interface RefreshCallBack{
		void refresh();
	}
	private LayoutInflater mInflater; 
	private Context mContext;
	private List<Map<String, String>> mList;
	private List<List<Note>>mNotes;
	private DBManager dbManager;
	private String []items={"删除"};
	private RefreshCallBack callBack;
	
	public ListViewAdapter(Context context, List<Map<String, String>> list,DBManager dbManager,RefreshCallBack callBack )
	{
		this.mContext = context;
		this.mList = list;
		this.mInflater = LayoutInflater.from(mContext); 
		this.dbManager=dbManager;
		this.callBack=callBack;
		mNotes=new ArrayList<List<Note>>();
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
			convertView = mInflater.inflate(R.layout.innerlistview, group,false); 
			holder.txtTime=(TextView)convertView.findViewById(R.id.txtTime);
			holder.txtCount=(TextView)convertView.findViewById(R.id.txtCount);
			holder.gridView=(GridView) convertView.findViewById(R.id.id_grid_note);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		Map<String, String> item=mList.get(position);

		holder.txtTime.setText((String)item.get("monthoftime"));
		holder.txtCount.setText((String)item.get("notecount"));

		holder.gridView.setNumColumns(3);// 设置每行列数
		holder.gridView.setGravity(Gravity.CENTER);// 位置居中
		holder.gridView.setHorizontalSpacing(10);// 水平间隔
		holder.gridView.setVerticalSpacing(10);
		
		List<Note> gridViewList=dbManager.findNotesByTime((String)item.get("monthoftime"));
		mNotes.add(gridViewList);
		GridViewAdapter adapter=new GridViewAdapter(mContext, gridViewList);
		holder.gridView.setAdapter(adapter);
		holder.gridView.setOnItemClickListener(new MyOnItemClickListener(position) );

		holder.gridView.setOnItemLongClickListener(new MyLonClickListener(position));	
		return convertView;
	}

	private class MyLonClickListener implements AdapterView.OnItemLongClickListener
	{

		private int dataPosition;
		public MyLonClickListener(int dataPosition)
		{
			this.dataPosition=dataPosition;
		}
		@Override
		public boolean onItemLongClick(AdapterView<?> adapterView, View view,
				final int position, long id) {
			Log.i("onItemLongClick", "dataPosition="+dataPosition+";postion"+position);
			AlertDialog.Builder builder=new Builder(mContext);
			builder.setItems(items, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Note note=mNotes.get(dataPosition).get(position);
					boolean result = dbManager.deleteNotes(note.getId());
					if(result)
					{
						Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
						callBack.refresh();
					}
					else
					{
						Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
					}
				}
			}).show();
			return true;
		}
	}
	private class MyOnItemClickListener implements AdapterView.OnItemClickListener
	{
		private int dataPosition;
		public MyOnItemClickListener(int dataPosition)
		{
			this.dataPosition=dataPosition;
		}
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Log.i("onItemClick", "dataPosition="+dataPosition+";postion"+position);
			Intent intent=new Intent(mContext, DetailNoteActivity.class);
			Bundle bundle=new Bundle();
			Note note=mNotes.get(dataPosition).get(position);
			bundle.putSerializable("note", note);
			intent.putExtras(bundle);
			mContext.startActivity(intent);
		}

	}
	private static class ViewHolder
	{
		public ViewHolder(){}
		public TextView txtTime;
		public TextView txtCount;
		public GridView gridView;
	}
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
		mNotes.clear();
	}
}
