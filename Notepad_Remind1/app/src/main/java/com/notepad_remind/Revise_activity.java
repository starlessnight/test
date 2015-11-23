package com.notepad_remind;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Revise_activity extends Activity{
	private int select_witch=1,id,year,month,day,hr,min;
	private String wh_id,title_text,content_text,reciprocal_text,timeView_text,now_date,now_time;
	private Button checked,cancel,select_button;
	private EditText title,content,reciprocal;
	private LinearLayout reciprocal_ln;
	private RadioGroup select_time;
	private RadioButton rd_btn1,rd_btn2;
	private TextView timeView,rp_title,minute_txt;
	private SimpleDateFormat date=new SimpleDateFormat("yyyy年MM月dd日 HH:mm"); // 初始化开始时间  
	private SimpleDateFormat today=new SimpleDateFormat("yyyy年MM月dd日");
	private String initEndDateTime,title_txt,content_txt,time_txt,date_txt ,hr2; // 初始化结束时间 
	private SQLite_set date_db=new SQLite_set(Revise_activity.this),helper;
	private SQLite_set mydb;
	private SQLiteDatabase db;
	private Bundle b;
	private Cursor SQLSelect;
	private String url;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_layout);
		setTitle("修改");
		findView();
		mydb=new SQLite_set(this, "table_name", null, 0);
		db=mydb.getReadableDatabase();
		b=this.getIntent().getExtras();
		id=b.getInt("id");
		wh_id=String.valueOf(b.getInt("id"));
		SQLSelect=db.rawQuery("select * from table_name where note_id=?;", new String[]{wh_id});
		
		
		SQLSelect.moveToFirst();

		if(SQLSelect.getCount()>0){
			title_txt=SQLSelect.getString(1);
			content_txt=SQLSelect.getString(2);
			time_txt=SQLSelect.getString(4);
			date_txt=SQLSelect.getString(3);
			System.out.println("@22@@@@@"+SQLSelect.getString(4));
		}
		if(time_txt.contains(":")){
			rd_btn2.setChecked(true);
			select_button.setVisibility(View.VISIBLE);
			timeView.setVisibility(View.VISIBLE);
			reciprocal_ln.setVisibility(View.GONE);
			rp_title.setVisibility(View.GONE);
			timeView.setText(date_txt+time_txt);
			select_witch=2;
		}
		else{
			rd_btn1.setChecked(true);
			select_button.setVisibility(View.GONE);
			timeView.setVisibility(View.GONE);
			reciprocal_ln.setVisibility(View.VISIBLE);
			rp_title.setVisibility(View.VISIBLE);
			reciprocal.setText(time_txt);
		}
			
		title.setText(title_txt);
		content.setText(content_txt);
		
		
		select_time.setOnCheckedChangeListener(select_timeOnCheckedChange);
		select_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				//日期跟時間
						
				initEndDateTime = date.format(new java.util.Date());
				System.out.println("@@@@@@"+initEndDateTime);
				DateTimePick dateTimePicKDialog = new DateTimePick(  
				Revise_activity.this, initEndDateTime);  
		        dateTimePicKDialog.dateTimePicKDialog(timeView);
		        timeView.setTextColor(0xffaa0000);
		                
							
				
		        
				
			}
		});
		
		//取消的按鈕
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Revise_activity.this.finish();
				
			}
		});
		
		//確定的按鈕
		checked.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(title.getText().toString()!=null){
					if(content.getText().toString()!=null){
						if(select_witch==1){
							if(reciprocal.getText().toString()!=null){
								
								title_txt=title.getText().toString();
								content_text=content.getText().toString();
								
								Revise();
								
							}else
								show_info("時間未填寫");
							
						}else 
							if(timeView.getText().toString()!=null){
								title_txt=title.getText().toString();
								content_text=content.getText().toString();
								System.out.println("@title@"+timeView.getText().toString());
								Revise();
								
							}else
								show_info("時間未選擇");
					}else
						show_info("內容未填寫");
				}else
					show_info("標題未填寫");
				 
				
				
				
			}
		});
		db.close();
	}

	private void findView(){
		title=(EditText)findViewById(R.id.ct_title);
		content=(EditText)findViewById(R.id.ct_content);
		reciprocal=(EditText)findViewById(R.id.reciprocal);
		timeView=(TextView)findViewById(R.id.timeShow);
		reciprocal_ln=(LinearLayout)findViewById(R.id.ln);
		rp_title=(TextView)findViewById(R.id.reciprocal_title);
		select_time=(RadioGroup)findViewById(R.id.select_time);
		select_button=(Button)findViewById(R.id.select_butoon);
		checked=(Button)findViewById(R.id.checked);
		cancel=(Button)findViewById(R.id.cancel);
		rd_btn1=(RadioButton)findViewById(R.id.choosed_time);
		rd_btn2=(RadioButton)findViewById(R.id.choosed_date);
	}
	private RadioGroup.OnCheckedChangeListener select_timeOnCheckedChange=
			new RadioGroup.OnCheckedChangeListener(){
		
	
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			
			switch(checkedId){
			case R.id.choosed_time:
				select_button.setVisibility(View.GONE);
				timeView.setVisibility(View.GONE);
				reciprocal_ln.setVisibility(View.VISIBLE);
				rp_title.setVisibility(View.VISIBLE);
				select_witch=1;
				break;
			case R.id.choosed_date:
				select_button.setVisibility(View.VISIBLE);
				timeView.setVisibility(View.VISIBLE);
				reciprocal_ln.setVisibility(View.GONE);
				rp_title.setVisibility(View.GONE);
				select_witch=2;
				break;
			
			}
			
		}
		
	};
	private void show_info(String text){
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
	
	
	private void Revise(){
		Intent intent=new Intent();
		mydb=new SQLite_set(this, "table_name", null, 0);
		if(select_witch!=1){
			 now_date = spliteString(timeView.getText().toString(), "日", "index", "front"); // 日期  
			 
	         now_time = spliteString(timeView.getText().toString(), "日", "index", "back"); // 时间  
			}else{
				now_date=today.format(new java.util.Date());
				      
				now_time=reciprocal.getText().toString();
				
			}
		
		long check=date_db.update(id, title_txt, content_text, now_date, now_time, "");
		if(check>0){
			
			hr2 = spliteString(timeView.getText().toString(), "日", "index", "back");
	        year=Integer.parseInt(spliteString(timeView.getText().toString(), "年", "index", "front"));
	        month= Integer.parseInt(timeView.getText().toString().substring(5,7));    
	        day= Integer.parseInt(timeView.getText().toString().substring(8,10));
	        hr=Integer.parseInt(hr2.substring(1, hr2.indexOf(":")));
	        min=Integer.parseInt(spliteString(hr2, ":", "index", "back"));
	        
	        Calendar  c=Calendar.getInstance();
						c.setTimeInMillis(System.currentTimeMillis());
						c.set(year, month-1, day);
						if(hr<=12){
						c.set(Calendar.AM_PM, Calendar.AM);
						c.set(Calendar.HOUR,hr);
					}
					
						if(hr>12){
				        	c.set(Calendar.AM_PM, Calendar.PM);
				        	c.set(Calendar.HOUR,hr-12);
				        }
						
			            c.set(Calendar.MINUTE,min);
			            c.set(Calendar.SECOND,0);
			            
			             intent = new Intent(Revise_activity.this, CallAlarmText.class);
			              intent.putExtra("msg", "Get up!Get up!");
			              SharedPreferences send=getSharedPreferences("PhoneRing",MODE_WORLD_READABLE);
			              url=send.getString("url", null);
			              intent.putExtra("ringURI", url);
			              PendingIntent sender=PendingIntent.getBroadcast(Revise_activity.this,id, intent, 0);
			              
			              AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
			              am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),sender );
			              Log.d("GGGGGGG22", String.valueOf(id));
			              db.close();
					
			Toast.makeText(this, "修改成功", Toast.LENGTH_LONG).show();
			intent.setClass(Revise_activity.this, NoteActivity.class);
			date_db.close();
			NoteActivity.NoteActivity.finish();
			startActivity(intent);
			Revise_activity.this.finish();
		}else{
			Toast.makeText(this, "修改失敗", Toast.LENGTH_LONG).show();
		}
		
	}
	
	public static String spliteString(String srcStr, String pattern,String indexOrLast, String frontOrBack) {  
        String result = "";  
        int loc = -1;  
        if (indexOrLast.equalsIgnoreCase("index")) {  
            loc = srcStr.indexOf(pattern); // 取得字符串第一次出现的位置  
        } else {  
            loc = srcStr.lastIndexOf(pattern); // 最后一个匹配串的位置  
        }  
        if (frontOrBack.equalsIgnoreCase("front")) {  
            if (loc != -1)  
                result = srcStr.substring(0, loc); // 截取子串  
        } else {  
            if (loc != -1)  
                result = srcStr.substring(loc + 1, srcStr.length()); // 截取子串  
        }  
        return result;  
    } 
	
}
