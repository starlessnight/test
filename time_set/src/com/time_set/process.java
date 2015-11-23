package com.time_set;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class process extends Activity {

	
	private GridView gridView;
	AudioManager audioManager;
	private int clickTemp = -1;
	private SimpleAdapter adapter;
	static Activity process;
	private TextView process_name;
	protected final static int MENU_ADD = Menu.FIRST;
	protected final static int MENU_CANCEL = Menu.FIRST + 1;
	private SQLiteDatabase db;
	private Cursor myCursor;
	private String start_id;
	private String[] id, name,weekday,SpWeekday,mode;
	private int[] id2;
	private int name_string,wharg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.process);
		process = this;

		findView();
		
		
		mySQLiteOpenHelper helper = new mySQLiteOpenHelper(process.this,"process");
		db = helper.getReadableDatabase();
		myCursor = db.rawQuery("select * from process ;", null);
		String[] colNames = myCursor.getColumnNames();

		id = new String[myCursor.getCount()];
		name = new String[myCursor.getCount()];
		weekday= new String[myCursor.getCount()];
		mode= new String[myCursor.getCount()];
		id2=new int[myCursor.getCount()];
		myCursor.moveToFirst();
		
		for (int i = 0; i < myCursor.getCount(); i++) {
			id2[i]=myCursor.getInt(myCursor.getColumnIndex(colNames[0]));
			id[i] = myCursor.getString(myCursor.getColumnIndex(colNames[0]))
					+ "\t\t";
			name[i] = myCursor.getString(1) + "\n"; // 第2欄位
			weekday[i]= myCursor.getString(4) ;
			mode[i]=myCursor.getString(5) ;
			// 第3欄位
			// .............如果還要顯示其它欄位，就要再複製上面一行
			myCursor.moveToNext(); // 下一筆
		}
		//設定title
		SharedPreferences send=getSharedPreferences("transport",MODE_WORLD_READABLE);
		start_id=send.getString("name","time_set");
		if(!start_id.equals("time_set")){
				setTitle("執行: "+start_id);
				
		}else
			setTitle("time_set");
			
		
		
		
		for(int i=0;i< myCursor.getCount();i++){
			
			SpWeekday=weekday[i].split(",");
			if(!SpWeekday[0].equals("0"))
				SpWeekday[0]="日 ";
			else
				SpWeekday[0]="";
			if(!SpWeekday[1].equals("0"))
				SpWeekday[1]="一 ";
			else
				SpWeekday[1]="";
			if(!SpWeekday[2].equals("0"))
				SpWeekday[2]="二 ";
			else
				SpWeekday[2]="";
			if(!SpWeekday[3].equals("0"))
				SpWeekday[3]="三 ";
			else
				SpWeekday[3]="";
			if(!SpWeekday[4].equals("0"))
				SpWeekday[4]="四 ";
			else
				SpWeekday[4]="";
			if(!SpWeekday[5].equals("0"))
				SpWeekday[5]="五 ";
			else
				SpWeekday[5]="";
			if(!SpWeekday[6].equals("0"))
				SpWeekday[6]="六";
			else
				SpWeekday[6]="";
			
			weekday[i]=SpWeekday[0]+SpWeekday[1]+SpWeekday[2]+SpWeekday[3]+SpWeekday[4]+SpWeekday[5]+SpWeekday[6];
			if(!weekday[i].equals(""))
				weekday[i]="限定: "+weekday[i];
			else
				weekday[i]="重複每天";
		}
		
		
		//System.out.println(name[2]);
		ArrayList<HashMap<String, Object >> list = new ArrayList<HashMap<String, Object >>();
		HashMap<String, Object> data = null;
		// 將資料從字串取出
		if (myCursor.getCount() > 0) {
			for (int i = 0; i < myCursor.getCount(); i++) {
				data = new HashMap<String, Object>();
				data.put("mode",mode[i]);
				data.put("name", name[i]);
				data.put("weekday", weekday[i]);
				System.out.println(weekday[i]);
				list.add(data);
			}
			
			// 將資料放入對應的位子
			adapter = new SimpleAdapter(this, list, R.layout.process_item,new String[] { "mode", "name","weekday" }, new int[] { R.id.process_id,
							R.id.process_name,R.id.weekdays });

			gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent intent=new Intent();
					Bundle bundle=new Bundle();
					//System.out.println(id[arg2]);
					wharg=arg2;
					bundle.putInt("which", id2[arg2]);
					intent.putExtras(bundle);
					intent.setClass(process.this, choose.class);
					startActivity(intent);
					
					return true;
				}

				
			});
			
			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					//myCursor = db.rawQuery("select PROCESS_ftTimetext,PROCESS_lstTimetext from process where _id=?;", new String[]{id[arg2]});
					Intent intent=new Intent();
					
					SharedPreferences send=getSharedPreferences("transport",MODE_WORLD_READABLE);
					send.edit()
					.putString("name",name[arg2])
					.commit();
					send.edit()
					.putString("id",id[arg2])
					.commit();
					setTitle("執行: "+name[arg2]);
					intent.putExtra("id", id[arg2]);
					intent.setClass(process.this,timeService.class );
					startService(intent);
					Toast.makeText(process.this, "執行"+name[arg2], Toast.LENGTH_SHORT)
					.show();
				}
			});
			
			db.close();
			gridView.setAdapter(adapter);
		}

	}

	private void findView() {
		gridView = (GridView)findViewById(R.id.dataView);

	}
	public void setSeclection(int position) { clickTemp = position; }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case MENU_ADD:
			
			intent.setClass(process.this, addActivity.class);
			System.out.println("新增");
			startActivity(intent);
			break;
		case MENU_CANCEL:
			intent.setClass(process.this, timeService.class);
			System.out.println("行程取消");
			Toast.makeText(process.this, "已取消所有行程", Toast.LENGTH_SHORT).show();
			try
			{
        		audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        		if(audioManager!=null)
        		{
        			if(audioManager.getRingerMode()!=1)
        			{
	        			audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	        			
        			}
        		}
    		}catch(Exception e){
			
    			}
			setTitle("time_set");
			stopService(intent);
			break;
		
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, MENU_ADD, 0, R.string.strAddButton);
		menu.add(Menu.NONE, MENU_CANCEL, 0, R.string.cancel);

		return true;
	}

	 @Override
	 protected void onDestroy() {
	  super.onDestroy();
	 }
	
}
