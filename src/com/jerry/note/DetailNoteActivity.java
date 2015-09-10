package com.jerry.note;


import java.util.ArrayList;
import java.util.List;

import com.jerry.note.adapter.GalleryAdapter;
import com.jerry.note.bean.Note;
import com.jerry.note.db.DBManager;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Gallery;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class DetailNoteActivity extends Activity {

	private Gallery gallery;
	private List<Note> data;
	private Note note;
	private MyApp myApp;
	private DBManager dbManager;
	private GalleryAdapter adapter;
	private View back;
	private View edit;
	private TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detial_activity);
		initData();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		gallery=(Gallery)findViewById(R.id.id_gallery);
		back=findViewById(R.id.id_back);
		edit=findViewById(R.id.id_edit);
		title=(TextView) findViewById(R.id.id_title);
		title.setText("详细内容");
		edit.setVisibility(View.VISIBLE);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		edit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(DetailNoteActivity.this,NewNoteActivity.class);
				Bundle bundle=new Bundle();
				note=(Note) gallery.getSelectedItem();
				bundle.putSerializable("note", note);
				intent.putExtra("edit", true);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		adapter=new GalleryAdapter(this, data);
		gallery.setAdapter(adapter);
		
	}

	private void initData() {
		// TODO Auto-generated method stub
		myApp=(MyApp)getApplication();
		dbManager=myApp.getDBManager();
		Intent intent=getIntent();
		Bundle bundle = intent.getExtras();
		note=(Note)bundle.get("note");
		data=new ArrayList<Note>();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		data.clear();
		data.addAll(dbManager.getAllNotes());
		adapter.notifyDataSetChanged();
		int position=0;
		for(position=0;position<data.size();position++)
		{
			Note temp=data.get(position);
			if(temp.getId()==note.getId())
				break;
		}
		gallery.setSelection(position);
	}
}
