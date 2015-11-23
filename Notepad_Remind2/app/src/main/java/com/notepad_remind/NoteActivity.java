package com.notepad_remind;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;


public class NoteActivity extends Activity {

	protected final static int MENU_ADD = Menu.FIRST;
	protected final static int MENU_SET = Menu.FIRST + 1;
	public SimpleAdapter adapter;
	public GridView menu_gv;
	private SQLiteDatabase db;
	static  Activity NoteActivity;
	private String[] id,title,content,date,time,kind,address;
	private Cursor myCursor;
	public SimpleAdapter sa ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		find_View();
		NoteActivity=this;
		
		SQLite_set helper=new SQLite_set(NoteActivity.this,"table_name", null, 0);
		db=helper.getReadableDatabase();
		myCursor=db.rawQuery("select * from table_name ;", null);
		String[] colNames = myCursor.getColumnNames();
		
		/*
		String [] a=new String [myCursor.getColumnCount()];
		for(int i=0;i<myCursor.getColumnCount();i++){
			a[i]=myCursor.getColumnName(i);
			Log.d("testaaaaa", a[i]);
		}*/
		id=new String[myCursor.getCount()];
		title=new String[myCursor.getCount()];
		content=new String[myCursor.getCount()];
		date=new String[myCursor.getCount()];
		time=new String[myCursor.getCount()];
		kind=new String[myCursor.getCount()];
		myCursor.moveToFirst();
		
		for(int i=0;i<myCursor.getCount();i++){
			id[i] = myCursor.getString(myCursor.getColumnIndex(colNames[0]));
			title[i] = myCursor.getString(1)  ; // 第2欄位
			date[i]= myCursor.getString(3) ;
			time[i]=myCursor.getString(4) ;
			kind[i]=myCursor.getString(5) ;
			// 第3欄位
			// .............如果還要顯示其它欄位，就要再複製上面一行
			myCursor.moveToNext(); // 下一筆
			//System.out.println("@@CCCCC@@"+time[i]);
		}
		
		ArrayList<HashMap<String, Object>> list=new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> data=null;
		if (myCursor.getCount() > 0) {
			for(int i=0;i<myCursor.getCount();i++){
				data = new HashMap<String, Object>();
				data.put("title",title[i]);
				String Sumtime="提醒日期:"+date[i]+"\n提醒時間:"+time[i]; 
				data.put("time", Sumtime);
				data.put("kind", "類別:" +kind[i]);
				list.add(data);
			}
			
			
			
			// 將資料放入對應的位子
						adapter = new SimpleAdapter(this, list, R.layout.note_item,new String[] { "title", "time","kind" }, new int[] { R.id.item_title,
										R.id.item_time,R.id.item_kind });
						
						menu_gv.setOnItemLongClickListener(new OnItemLongClickListener() {

							@Override
							public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
									int arg2, long arg3) {

								Intent intent=new Intent();
								Bundle bundle=new Bundle();
								//wharg=arg2;
								System.out.println("@111111@"+Integer.parseInt(id[arg2]));
								bundle.putInt("which", Integer.parseInt(id[arg2]));
								intent.putExtras(bundle);
								intent.setClass(NoteActivity.this, choose.class);
								startActivity(intent);
								
								return false;
							}

							
						});
						
						menu_gv.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1,
									int arg2, long arg3) {
								// TODO Auto-generated method stub
								//myCursor = db.rawQuery("select PROCESS_ftTimetext,PROCESS_lstTimetext from process where _id=?;", new String[]{id[arg2]});
								Intent intent=new Intent();
								Bundle b=new Bundle();
								b.putInt("id", arg2);
								intent.putExtras(b);
								intent.setClass(NoteActivity.this,Note_content.class );
								startActivity(intent);
							}
						});
						
						db.close();
						menu_gv.setAdapter(adapter);
						sa=(SimpleAdapter)menu_gv.getAdapter();
						 
			
		}
		
		
		
		
		
	}
	
	
	private void find_View(){
		
		menu_gv=(GridView)findViewById(R.id.dataView); 
	}
	
	
	
       
	  

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		super.onOptionsItemSelected(item);
		Intent intent=new Intent();
		switch(item.getItemId()){
		case MENU_ADD:
			intent.setClass(NoteActivity.this, Create_activity.class);
			startActivity(intent);
			break;
		case MENU_SET:
			intent.setClass(NoteActivity.this, Set_upActivity.class);
			startActivity(intent);
			break;
		}
		return true;
	}
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(Menu.NONE, MENU_ADD, 0, "新增事件");
		menu.add(Menu.NONE, MENU_SET, 0, "設定");
		getMenuInflater().inflate(R.menu.note, menu);
		return true;
	}

}
