package com.cn.daming;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {
		  TextView setTime1;
		  TextView setTime2;
		  TextView setTime3;
		  Button mButton1;
		  Button mButton2;
		  Button mButton3;
		  Button mButton4;
		  Button mButton5;
		  Button mButton6;
		  
		  String time1String = null;
		  String time2String = null;
		  String time3String = null;
		  String defalutString = "目前无设置";
		  
		  AlertDialog builder = null;
		  Calendar c=Calendar.getInstance();

		  @Override
		  public void onCreate(Bundle savedInstanceState)
		  {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.main);
            
		    //取得活动的Preferences对象
		    SharedPreferences settings = getPreferences(Activity.MODE_PRIVATE);
		    time1String = settings.getString("TIME1", defalutString);
		    time2String = settings.getString("TIME2", defalutString);
		    time3String = settings.getString("TIME3", defalutString);
		    
		    InitButton1();
		    InitButton2();
		    InitButton3();
		    InitButton4();
		    InitButton5();
		    InitButton6();	
		    
		    setTime1.setText(time1String);
		    setTime3.setText(time2String);
		    setTime2.setText(time3String);
		  }
		  
		  public void InitButton1()
		  {
			    setTime1=(TextView) findViewById(R.id.setTime1);
			    mButton1=(Button)findViewById(R.id.mButton1);
			    mButton1.setOnClickListener(new View.OnClickListener()
			    {
			      public void onClick(View v)
			      {
			        c.setTimeInMillis(System.currentTimeMillis());
			        int mHour=c.get(Calendar.HOUR_OF_DAY);
			        int mMinute=c.get(Calendar.MINUTE);
			        
			       
			        new TimePickerDialog(MainActivity.this,
			          new TimePickerDialog.OnTimeSetListener()
			          {                
			            public void onTimeSet(TimePicker view,int hourOfDay,
			                                  int minute)
			            {
			              c.setTimeInMillis(System.currentTimeMillis());
			              c.set(Calendar.HOUR_OF_DAY,hourOfDay);
			              c.set(Calendar.MINUTE,minute);
			              c.set(Calendar.SECOND,0);
			              c.set(Calendar.MILLISECOND,0);
			              Log.d("showhr", String.valueOf(minute));
			              Intent intent = new Intent(MainActivity.this, CallAlarm.class);
			              PendingIntent sender=PendingIntent.getBroadcast(
			            		  MainActivity.this,0, intent, 0);
			              AlarmManager am;
			              am = (AlarmManager)getSystemService(ALARM_SERVICE);
			              am.set(AlarmManager.RTC_WAKEUP,
			                     c.getTimeInMillis(),
			                     sender
			                    );
			              String tmpS=format(hourOfDay)+"："+format(minute);
			              setTime1.setText(tmpS);
			              
			              //SharedPreferences保存数据，并提交
			              SharedPreferences time1Share = getPreferences(0);
			              SharedPreferences.Editor editor = time1Share.edit();
			              editor.putString("TIME1", tmpS);
			              editor.commit();
			              
			              Toast.makeText(MainActivity.this,"设置大明闹钟时间为"+tmpS,
			                Toast.LENGTH_SHORT)
			                .show();
			            }          
			          },mHour,mMinute,true).show();
			      }
			    });
		  }

		  public void InitButton2()
		  {
			    mButton2=(Button) findViewById(R.id.mButton2);
			    mButton2.setOnClickListener(new View.OnClickListener()
			    {
			      public void onClick(View v)
			      {
			        Intent intent = new Intent(MainActivity.this, CallAlarm.class);
			        PendingIntent sender=PendingIntent.getBroadcast(
			        		MainActivity.this,0, intent, 0);
			        AlarmManager am;
			        am =(AlarmManager)getSystemService(ALARM_SERVICE);
			        am.cancel(sender);
			        Toast.makeText(MainActivity.this,"大明闹钟时间删除",
			                       Toast.LENGTH_SHORT).show();
			        setTime1.setText("目前无设置");
			        
			        SharedPreferences time1Share = getPreferences(0);
		            SharedPreferences.Editor editor = time1Share.edit();
		            editor.putString("TIME1", "目前无设置");
		            editor.commit();
			      }
			    });
		  }
		  
		  public void InitButton3()
		  {
			    setTime3=(TextView) findViewById(R.id.setTime5);
			    mButton3=(Button)findViewById(R.id.mButton5);
			    mButton3.setOnClickListener(new View.OnClickListener()
			    {
			      public void onClick(View v)
			      {
			        c.setTimeInMillis(System.currentTimeMillis());
			        int mHour=c.get(Calendar.HOUR_OF_DAY);
			        int mMinute=c.get(Calendar.MINUTE);
			        
			       
			        new TimePickerDialog(MainActivity.this,
			          new TimePickerDialog.OnTimeSetListener()
			          {                
			            public void onTimeSet(TimePicker view,int hourOfDay,
			                                  int minute)
			            {
			              c.setTimeInMillis(System.currentTimeMillis());
			              c.set(Calendar.HOUR_OF_DAY,hourOfDay);
			              c.set(Calendar.MINUTE,minute);
			              c.set(Calendar.SECOND,0);
			              c.set(Calendar.MILLISECOND,0);
			              
			              Intent intent = new Intent(MainActivity.this, CallAlarm.class);
			              PendingIntent sender=PendingIntent.getBroadcast(
			            		  MainActivity.this,1, intent, 0);
			              AlarmManager am;
			              am = (AlarmManager)getSystemService(ALARM_SERVICE);
			              am.set(AlarmManager.RTC_WAKEUP,
			                     c.getTimeInMillis(),
			                     sender
			                    );
			              String tmpS=format(hourOfDay)+"："+format(minute);
			              setTime3.setText(tmpS);
			              
			              //SharedPreferences保存数据，并提交
			              SharedPreferences time2Share = getPreferences(1);
			              SharedPreferences.Editor editor = time2Share.edit();
			              editor.putString("TIME2", tmpS);
			              editor.commit();
			              
			              Toast.makeText(MainActivity.this,"设置大明闹钟时间为"+tmpS,
			                Toast.LENGTH_SHORT)
			                .show();
			            }          
			          },mHour,mMinute,true).show();
			      }
			    });
		  }
		  
		  public void InitButton4()
		  {
			    mButton4=(Button) findViewById(R.id.mButton6);
			    mButton4.setOnClickListener(new View.OnClickListener()
			    {
			      public void onClick(View v)
			      {
			        Intent intent = new Intent(MainActivity.this, CallAlarm.class);
			        PendingIntent sender=PendingIntent.getBroadcast(
			        		MainActivity.this,0, intent, 0);
			        AlarmManager am;
			        am =(AlarmManager)getSystemService(ALARM_SERVICE);
			        am.cancel(sender);
			        Toast.makeText(MainActivity.this,"大明闹钟时间删除",
			                       Toast.LENGTH_SHORT).show();
			        setTime3.setText("目前无设置");
			        
			        //SharedPreferences保存数据，并提交
		            SharedPreferences time2Share = getPreferences(1);
		            SharedPreferences.Editor editor = time2Share.edit();
		            editor.putString("TIME2", "目前无设置");
		            editor.commit();
			      }
			    });
		  }
		  
		  public void InitButton5()
		  {
			    setTime2=(TextView) findViewById(R.id.setTime2);
			    LayoutInflater factory = LayoutInflater.from(this);
			    final View setView = factory.inflate(R.layout.timeset,null);
			    final TimePicker tPicker=(TimePicker)setView
			                               .findViewById(R.id.tPicker);
			    tPicker.setIs24HourView(true);

			    final AlertDialog di=new AlertDialog.Builder(MainActivity.this)
			          .setIcon(R.drawable.clock)
			          .setTitle("设置")
			          .setView(setView)
			          .setPositiveButton("确定",
			            new DialogInterface.OnClickListener()
			           {
			             public void onClick(DialogInterface dialog, int which)
			             {
			               EditText ed=(EditText)setView.findViewById(R.id.mEdit);
			               int times=Integer.parseInt(ed.getText().toString())
			                          *1000;
			               c.setTimeInMillis(System.currentTimeMillis());
			               c.set(Calendar.HOUR_OF_DAY,tPicker.getCurrentHour());
			               c.set(Calendar.MINUTE,tPicker.getCurrentMinute());
			               c.set(Calendar.SECOND,0);
			               c.set(Calendar.MILLISECOND,0);

			               Intent intent = new Intent(MainActivity.this,
			                                          CallAlarm.class);
			               PendingIntent sender = PendingIntent.getBroadcast(
			            		   MainActivity.this,1, intent, 0);
			               AlarmManager am;
			               am = (AlarmManager)getSystemService(ALARM_SERVICE);
			               am.setRepeating(AlarmManager.RTC_WAKEUP,
			                         c.getTimeInMillis(),times,sender);
			               String tmpS=format(tPicker.getCurrentHour())+"："+
			                           format(tPicker.getCurrentMinute());
			               String subStr = "设置大明闹钟时间为"+tmpS+
                           "开始，重复间隔为"+times/1000+"秒";
			               setTime2.setText("设置大明闹钟时间为"+tmpS+
			                                "开始，重复间隔为"+times/1000+"秒");
			               
			               //SharedPreferences保存数据，并提交  
			               SharedPreferences time3Share = getPreferences(2);
				           SharedPreferences.Editor editor = time3Share.edit();
				           editor.putString("TIME3", subStr);
				           editor.commit();
			               
			               Toast.makeText(MainActivity.this,"设置大明闹钟为"+tmpS+
			                              "开始，重复间隔为"+times/1000+"秒",
			                              Toast.LENGTH_SHORT).show();
			             }
			           })
			          .setNegativeButton("取消",
			            new DialogInterface.OnClickListener()
			           {
			             public void onClick(DialogInterface dialog, int which)
			             {
			             }
			           }).create();

			    mButton5=(Button) findViewById(R.id.mButton3);
			    mButton5.setOnClickListener(new View.OnClickListener()
			    {
			      public void onClick(View v)
			      {
			        c.setTimeInMillis(System.currentTimeMillis());
			        tPicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
			        tPicker.setCurrentMinute(c.get(Calendar.MINUTE));
			        di.show();
			      }
			    });
		  }
		  
		  public void InitButton6()
		  {
			    mButton6=(Button) findViewById(R.id.mButton4);
			    mButton6.setOnClickListener(new View.OnClickListener()
			    {
			      public void onClick(View v)
			      {
			        Intent intent = new Intent(MainActivity.this, CallAlarm.class);
			        PendingIntent sender = PendingIntent.getBroadcast(
			        		MainActivity.this,1, intent, 0);
			        AlarmManager am;
			        am = (AlarmManager)getSystemService(ALARM_SERVICE);
			        am.cancel(sender);
			        Toast.makeText(MainActivity.this,"闹钟时间删除",
			                       Toast.LENGTH_SHORT).show();
			        setTime2.setText("目前无设置");
			        //SharedPreferences保存数据，并提交  
		            SharedPreferences time3Share = getPreferences(2);
			        SharedPreferences.Editor editor = time3Share.edit();
			        editor.putString("TIME3", "目前无设置");
			        editor.commit();
			      }
			    });
		  }
        
		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event) {
			
            if(keyCode == KeyEvent.KEYCODE_BACK){
			builder = new AlertDialog.Builder(MainActivity.this)
					.setIcon(R.drawable.clock)
					.setTitle("温馨提示：")
					.setMessage("您是否要退出大明闹钟程序!!!")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									MainActivity.this.finish();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									builder.dismiss();
								}
							}).show();
            }
            return true;
		}

		private String format(int x)
		  {
		    String s=""+x;
		    if(s.length()==1) s="0"+s;
		    return s;
		  }
}