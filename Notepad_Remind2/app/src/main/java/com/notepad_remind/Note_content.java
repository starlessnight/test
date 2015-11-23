package com.notepad_remind;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;


public class Note_content extends Activity{
	private TextView modeTitle,ViewTitle,ViewContent,ViewRemind;
	private SQLiteDatabase db;
	private SQLite_set mydb;
	private Cursor thisCursor;
	private String [] mode,title,content,time;
	private Bundle b;
	private int id;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		findView();
		b=this.getIntent().getExtras();
		id=b.getInt("id");
		
		
		mydb=new SQLite_set(Note_content.this, "table_name", null, 0);
		db=mydb.getReadableDatabase();
		thisCursor=db.rawQuery("select * from table_name;", null);
		
		mode=new String[thisCursor.getCount()];
		title=new String[thisCursor.getCount()];
		content=new String[thisCursor.getCount()];
		time=new String[thisCursor.getCount()];
		thisCursor.moveToFirst();
		
		
		
		for(int i=0;i<thisCursor.getCount();i++){
			title[i]=thisCursor.getString(1);
			content[i]=thisCursor.getString(2);
			time[i]=thisCursor.getString(3)+"\n"+thisCursor.getString(4);
			mode[i]=thisCursor.getString(5);
			thisCursor.moveToNext();
		}
		ViewTitle.setText(title[id]);
		ViewContent.setText("內容:"+content[id]);
		ViewRemind.setText("閙鐘時間:\n"+time[id]);
		modeTitle.setText(mode[id]);
		db.close();
		
	}
	
	private void findView(){
		modeTitle=(TextView)findViewById(R.id.View_mode);
		ViewTitle=(TextView)findViewById(R.id.View_title);
		ViewContent=(TextView)findViewById(R.id.View_content);
		ViewRemind=(TextView)findViewById(R.id.View_remindDate);
	}

}
