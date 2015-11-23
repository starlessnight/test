package com.time_set;

import java.util.Calendar;



import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class reviseActivity extends Activity {

	private int MYDIALOG;
	private EditText title;
	private TextView et,et2;
	private Button bt1,bt2,start,end;
	private RadioButton Shock, Quiet;
	private Calendar calendar=null;
	private String hr1=null,min1=null,hr2=null,min2=null,sun="0",mon="0",tue="0",wed="0",thu="0",fri="0",sta="0",
			day, lt, ft, nm,wh_id,mode;
	private String[] weekdays, ft2, lt2;
	private int nhr, nmin, weekday, sun2, mon2, tue2, wed2, thu2, fri2, sta2, control,rs_id;
	private AudioManager audioManager;
	private Bundle bundle=new Bundle();
	private CheckBox CBsun,CBmon,CBtue,CBwed,CBthu,CBfri,CBsta;
	private mySQLiteOpenHelper mydb= new mySQLiteOpenHelper(reviseActivity.this);
	private SQLiteDatabase db;
	private Cursor myCursor;
	protected final static int MENU_ADD = Menu.FIRST;
	protected final static int MENU_EDIT = Menu.FIRST + 1;
	protected final static int MENU_DELETE = Menu.FIRST + 2;
	private process ps=new process();
	private Bundle b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		findView ();
		b=this.getIntent().getExtras();
		
		rs_id=b.getInt("id");
		wh_id=String.valueOf(b.getInt("id"));
		db = mydb.getReadableDatabase();
		myCursor = db.rawQuery(
						"select process_name,PROCESS_ftTimetext,PROCESS_lstTimetext,weekday,mode from process where _id=?;",
						new String[]{wh_id});
		myCursor.moveToFirst();

		if(myCursor.getCount()>0)
		{
			myCursor.moveToFirst();
			nm = myCursor.getString(0);
			ft = myCursor.getString(1);
			lt = myCursor.getString(2);
			day = myCursor.getString(3);
			mode=myCursor.getString(4);
			
			if(mode.equals("震動"))
				Shock.setChecked(true);
			else
				Quiet.setChecked(true);
			
			weekdays = day.split(",");
			sun2 = Integer.valueOf(weekdays[0]);
			mon2 = Integer.valueOf(weekdays[1]);
			tue2 = Integer.valueOf(weekdays[2]);
			wed2 = Integer.valueOf(weekdays[3]);
			thu2 = Integer.valueOf(weekdays[4]);
			fri2 = Integer.valueOf(weekdays[5]);
			sta2 = Integer.valueOf(weekdays[6]);
			ft2 = ft.split(":");
			lt2 = lt.split(":");
		}
		if (nm != null) {
			title.setText(nm);
			et.setText(ft2[0] + "點" + ft2[1] + "分");
			et2.setText(lt2[0] + "點" + lt2[1] + "分");
			
		} else {
			et.setText("開始時間");
			et2.setText("結束時間");
		}

		if (sun2 != 0)
			CBsun.setChecked(true);
		if (mon2 != 0)
			CBmon.setChecked(true);
		if (tue2 != 0)
			CBtue.setChecked(true);
		if (wed2 != 0)
			CBwed.setChecked(true);
		if (thu2 != 0)
			CBthu.setChecked(true);
		if (fri2 != 0)
			CBfri.setChecked(true);
		if (sta2 != 0)
			CBsta.setChecked(true);
		
		db.close();
		start.setText("修改");
		end.setText("取消");
		title.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				title.setText("");
			}
		});
		
		
		et.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MYDIALOG=1;
				showDialog(MYDIALOG);
			}
			
		});
		
		et2.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MYDIALOG=2;
				showDialog(MYDIALOG);
			}
			
		});
		
		start.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				if(title.getText().toString()!=null)
				if(hr1!=null){
					//System.out.println("@@@@@@@@@"+hr1);
					if(hr2!=null){
						
						
						
						
						//修改資料
						reviseTodo();
						//更新gridView跟開啓Service
						intent.setClass(reviseActivity.this, process.class);
						
						process.process.finish();
			            startActivity(intent);
			            
			            Toast.makeText(reviseActivity.this, "時間設置完成", Toast.LENGTH_SHORT)
						.show();
			            reviseActivity.this.finish();
					}else
						Toast.makeText(reviseActivity.this, "結束時間未選擇", Toast.LENGTH_SHORT)
						.show();
				}
				else
					Toast.makeText(reviseActivity.this, "開始時間未選擇", Toast.LENGTH_SHORT)
					.show();
					
			}
			
		});
		
		end.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
               		
               		Toast.makeText(reviseActivity.this, "取消", Toast.LENGTH_SHORT)
					.show();
               		reviseActivity.this.finish();
               	 
	            
			}
			
		});
	}
	
	
	private void findView (){
		et=(TextView)findViewById(R.id.Text1);
		et2=(TextView)findViewById(R.id.Text2);
		start=(Button)findViewById(R.id.start);
		end=(Button)findViewById(R.id.end);
		title=(EditText)findViewById(R.id.title);
		Shock = (RadioButton) findViewById(R.id.Shock);
		Quiet = (RadioButton) findViewById(R.id.Quiet);
		
		CBsun=(CheckBox)findViewById(R.id.sun);
		CBmon=(CheckBox)findViewById(R.id.mon);
		CBtue=(CheckBox)findViewById(R.id.tue);
		CBwed=(CheckBox)findViewById(R.id.wed);
		CBthu=(CheckBox)findViewById(R.id.thu);
		CBfri=(CheckBox)findViewById(R.id.fri);
		CBsta=(CheckBox)findViewById(R.id.sta);
		
		CBsun.setOnCheckedChangeListener(chklistener);
		CBmon.setOnCheckedChangeListener(chklistener);
		CBtue.setOnCheckedChangeListener(chklistener);
		CBwed.setOnCheckedChangeListener(chklistener);
		CBthu.setOnCheckedChangeListener(chklistener);
		CBfri.setOnCheckedChangeListener(chklistener);
		CBsta.setOnCheckedChangeListener(chklistener);
	};
	
	private CheckBox.OnCheckedChangeListener chklistener=new CheckBox.OnCheckedChangeListener()
	{

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			
			if(CBsun.isChecked())
				sun="1";
			else
				sun="0";
			if(CBmon.isChecked())
				mon="2";
			else
				mon="0";
			if(CBtue.isChecked())
				tue="3";
			else
				tue="0";
			if(CBwed.isChecked())
				wed="4";
			else
				wed="0";
			if(CBthu.isChecked())
				thu="5";
			else
				thu="0";
			if(CBfri.isChecked())
				fri="6";
			else
				fri="0";
			if(CBsta.isChecked())
				sta="7";
			else
				sta="0";
		}
		
	};
	@Override
	protected Dialog onCreateDialog(int id){
		Dialog dialog=null;
		switch(id)
		{
		case 1:
			calendar=Calendar.getInstance();
			//Builder builder = new AlertDialog.Builder(this);
			//builder.setTitle("選擇時間");
			dialog=new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub
					et.setText(hourOfDay+"點"+minute+"分");
					hr1=String.valueOf(hourOfDay);
					min1=String.valueOf(minute);
					
				}
			},calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false);
			
			
			
			
			//dialog=builder.create();
			break;
			
		case 2:
			calendar=Calendar.getInstance();
			dialog=new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub
					et2.setText(hourOfDay+"點"+minute+"分");
					hr2=String.valueOf(hourOfDay);
					min2=String.valueOf(minute);
				}
			},calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false);
			
			
			break;
		default:
			System.out.println("boom");
			break;
		}
		return dialog;
	}
	

	 
	 
	 private void reviseTodo()
	  {
		 if(Shock.isChecked())
				mode="震動";
			else
				mode="靜音";
		 String weekdays=sun+","+mon+","+tue+","+wed+","+thu+","+fri+","+sta;
		 db = mydb.getWritableDatabase();
		 if(rs_id!=0)
		 mydb.update(rs_id, title.getText().toString(), hr1 + ":" + min1 + ":00",
					hr2 + ":" + min2 + ":00", weekdays,mode);
		 
		 
		 
		 
	  }

	  

	  
}
