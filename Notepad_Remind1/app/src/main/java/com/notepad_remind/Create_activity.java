package com.notepad_remind;

import java.text.SimpleDateFormat;
import java.util.Calendar;



import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.SyncStateContract.Helpers;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Create_activity extends Activity{
	
	private RadioGroup select_time;
	private int select_witch=1,year,month,day,hr,min;
	private EditText title,content,reciprocal;
	private Button select_button,cancel,checked;
	private TextView timeShow,rp_title;
	private LinearLayout reciprocal_ln;
	private RadioButton rd_btn1,rd_btn2;
	private SimpleDateFormat date=new SimpleDateFormat("yyyy年MM月dd日 HH:mm"); // 初始化开始时间  
	private SimpleDateFormat today=new SimpleDateFormat("yyyy年MM月dd日");
	private String initEndDateTime ,hr2,id; // 初始化结束时间  
	private SQLite_set date_db=new SQLite_set(Create_activity.this),helper;
	private SQLiteDatabase db;
	private Cursor myCursor;
	private NoteActivity aa;
	private String url;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_layout);
		find_View();
		
		helper=new SQLite_set(Create_activity.this,"table_name", null, 0);
		db=helper.getReadableDatabase();
		myCursor=db.rawQuery("select note_id from table_name ;", null);
		//id=new int [myCursor.getCount()];
		myCursor.moveToLast();
		id=myCursor.getString(0);
		Log.d("showId", String.valueOf(id));
		rd_btn1.setChecked(true);
		select_time.setOnCheckedChangeListener(select_timeOnCheckedChange);
		select_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				//日期跟時間
				
				initEndDateTime = date.format(new java.util.Date());
				System.out.println("@@@@@@"+initEndDateTime);
				DateTimePick dateTimePicKDialog = new DateTimePick(  
				Create_activity.this, initEndDateTime); 
		        dateTimePicKDialog.dateTimePicKDialog(timeShow);
		        timeShow.setTextColor(0xffaa0000);
		       
		        
				
			}
		});
		
		
		
						
	        
		
		//取消的按鈕
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Create_activity.this.finish();
				
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
								
								AddJob();
								
								 
							}else
								show_info("時間未填寫");
							
						}else 
							if(timeShow.getText().toString()!=null){
								
								AddJob();
							}else
								show_info("時間未選擇");
					}else
						show_info("內容未填寫");
				}else
					show_info("標題未填寫");
				 
				
				
				
			}
		});
		
		
		
		
	}
	
	private void find_View(){
		
		title=(EditText)findViewById(R.id.ct_title);
		content=(EditText)findViewById(R.id.ct_content);
		select_time=(RadioGroup)findViewById(R.id.select_time);
		select_button=(Button)findViewById(R.id.select_butoon);
		timeShow=(TextView)findViewById(R.id.timeShow);
		rd_btn1=(RadioButton)findViewById(R.id.choosed_time);
		rd_btn2=(RadioButton)findViewById(R.id.choosed_date);
		reciprocal_ln=(LinearLayout)findViewById(R.id.ln);
		reciprocal=(EditText)findViewById(R.id.reciprocal);
		rp_title=(TextView)findViewById(R.id.reciprocal_title);
		cancel=(Button)findViewById(R.id.cancel);
		checked=(Button)findViewById(R.id.checked);
		
		
	}
	
	private RadioGroup.OnCheckedChangeListener select_timeOnCheckedChange=
			new RadioGroup.OnCheckedChangeListener(){
		
	
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			
			switch(checkedId){
			case R.id.choosed_time:
				select_button.setVisibility(View.GONE);
				timeShow.setVisibility(View.GONE);
				reciprocal_ln.setVisibility(View.VISIBLE);
				rp_title.setVisibility(View.VISIBLE);
				select_witch=1;
				break;
			case R.id.choosed_date:
				select_button.setVisibility(View.VISIBLE);
				timeShow.setVisibility(View.VISIBLE);
				reciprocal_ln.setVisibility(View.GONE);
				rp_title.setVisibility(View.GONE);
				select_witch=2;
				break;
			
			}
			
		}
		
	};
	
	private void show_info(String text){
		Toast.makeText(this,text, Toast.LENGTH_LONG).show();
		
	}
	private void AddJob(){
		String now_date;
		 String now_time;
		if(select_witch!=1){
		 now_date = spliteString(timeShow.getText().toString(), "日", "index", "front"); // 日期  
		 
         now_time = spliteString(timeShow.getText().toString(), "日", "index", "back"); // 时间  
		}else{
			now_date=today.format(new java.util.Date());
			      
			now_time=reciprocal.getText().toString();
			System.out.println("@22222@"+now_time);
		}
        	
        	long check=date_db.insert( title.getText().toString(), content.getText().toString(), now_date, now_time, "1", "");
        
        
		if(check>0){
			
			
			hr2 = spliteString(timeShow.getText().toString(), "日", "index", "back");
	        year=Integer.parseInt(spliteString(timeShow.getText().toString(), "年", "index", "front"));
	        month= Integer.parseInt(timeShow.getText().toString().substring(5,7));    
	        day= Integer.parseInt(timeShow.getText().toString().substring(8,10));
	        hr=Integer.parseInt(hr2.substring(1, hr2.indexOf(":")));
	        min=Integer.parseInt(spliteString(hr2, ":", "index", "back"));
	        
	        Calendar  c=Calendar.getInstance();
					c.setTimeInMillis(System.currentTimeMillis());
					c.set(year, month-1, day);
					if(hr<=12){
					c.set(Calendar.AM_PM, Calendar.AM);
					c.set(Calendar.HOUR,hr);
					}
		              //c.set(year, month, day, hr, min, 0);
		            //c.set(Calendar.YEAR,year);
		            /*if(month==1)
		            	c.set(Calendar.MONTH,Calendar.JANUARY);
		            if(month==2)
			            c.set(Calendar.MONTH,Calendar.FEBRUARY);
		            if(month==3)
			            c.set(Calendar.MONTH,Calendar.MARCH);
		            if(month==4)
			            c.set(Calendar.MONTH,Calendar.APRIL);
		            if(month==5)
			            c.set(Calendar.MONTH,Calendar.MAY);
		            if(month==6)
			            c.set(Calendar.MONTH,Calendar.JUNE);
		            if(month==7)
			            c.set(Calendar.MONTH,Calendar.JULY);
		            if(month==8)
			            c.set(Calendar.MONTH,Calendar.AUGUST);
		            if(month==9)
			            c.set(Calendar.MONTH,Calendar.SEPTEMBER);
		            if(month==10)
			            c.set(Calendar.MONTH,Calendar.OCTOBER);
		            if(month==11)
			            c.set(Calendar.MONTH,Calendar.NOVEMBER);
		            if(month==12)
			            c.set(Calendar.MONTH,Calendar.DECEMBER);*/
			        
			        
		            //c.set(Calendar.DAY_OF_MONTH,day);
			        if(hr>12){
			        	c.set(Calendar.AM_PM, Calendar.PM);
			        	c.set(Calendar.HOUR,hr-12);
			        }
					
		            c.set(Calendar.MINUTE,min);
		            c.set(Calendar.SECOND,0);
		              Intent intent = new Intent(Create_activity.this, CallAlarmText.class);
		              intent.putExtra("msg", "Get up!Get up!");
		              SharedPreferences send=getSharedPreferences("PhoneRing",MODE_WORLD_READABLE);
		              url=send.getString("url", null);
		              intent.putExtra("ringURI", url);
		              PendingIntent sender=PendingIntent.getBroadcast(Create_activity.this,Integer.valueOf(id)+1, intent, 0);
		              
		              AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		              am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),sender );
		              Log.d("GGGGGGG", id+"+1");
		              db.close();
			
			
		              
	        
	        
	        
	        
			
			Toast.makeText(this, "新增成功", Toast.LENGTH_LONG).show();
			intent.setClass(Create_activity.this, NoteActivity.class);
			NoteActivity.NoteActivity.finish();
			startActivity(intent);
			Create_activity.this.finish();
		}else
		{
			Toast.makeText(this, "新增失敗", Toast.LENGTH_LONG).show();
			
		}
		date_db.close();
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
