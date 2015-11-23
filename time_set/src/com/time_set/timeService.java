package com.time_set;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class timeService extends Service {

	private Notification baseNF;
	private NotificationManager nm;
	private Handler handler=new Handler();
	private int hr1,min1,hr2,min2;
	Time t=new Time("GMT+8");
	AudioManager audioManager;
	Calendar c = Calendar.getInstance();
	SimpleDateFormat hr = new SimpleDateFormat("HH"),min = new SimpleDateFormat("mm");
	SimpleDateFormat setTime=new SimpleDateFormat("HH:mm:ss");
	Date ftTime,lsTime,nowTime;
	
	int nhr,nmin,weekday,sun,mon,tue,wed,thu,fri,sta,control,Notification_ID_BASE = 110; ; 
	private SQLiteDatabase db;
	private mySQLiteOpenHelper helper;
	private Cursor myCursor;
	private String _id,day,lt,ft,pc_name,now_time,mode;
	private String[] weekdays;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//當Service開始時執行
	@Override
    public void onStart(Intent intent, int startId) { 
        //以下為狀態欄
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		baseNF = new Notification();
		baseNF.icon = R.drawable.clock;
		baseNF.tickerText = "time_set正在執行";
		baseNF.flags |= Notification.FLAG_NO_CLEAR;
		baseNF.setLatestEventInfo(timeService.this, "time_set", "time_set正在執行", null);
		nm.notify(Notification_ID_BASE, baseNF); 
		//_id=intent.getStringExtra("id");
		
		//接收傳
		SharedPreferences send=getSharedPreferences("transport",MODE_WORLD_READABLE);
		_id=send.getString("id",null);
		System.out.println("ididid"+_id);
		/*資料庫呼叫
		 * 
		 */
		helper = new mySQLiteOpenHelper(timeService.this,"process");
		db = helper.getReadableDatabase();
		myCursor = db.rawQuery("select process_name,PROCESS_ftTimetext,PROCESS_lstTimetext,weekday,mode from process where _id=?;", new String[]{_id});
		while (myCursor.moveToNext()) {
			pc_name=myCursor.getString(0);
		    ft = myCursor.getString(1);
		    lt = myCursor.getString(2);
		    day = myCursor.getString(3);
		    mode=myCursor.getString(4);
		    }
			System.out.println(pc_name+"\n"+ft+"\n"+lt+"\n"+day);
		    weekdays=day.split(",");
		    sun=Integer.valueOf(weekdays[0]);
			mon=Integer.valueOf(weekdays[1]);
			tue=Integer.valueOf(weekdays[2]);
			wed=Integer.valueOf(weekdays[3]);
			thu=Integer.valueOf(weekdays[4]);
			fri=Integer.valueOf(weekdays[5]);
			sta=Integer.valueOf(weekdays[6]);
		    try {
		    	ftTime= setTime.parse(ft);
		    	lsTime =setTime.parse(lt);
				System.out.println("PROCESS_ftTimetext:,"+ ftTime + ",PROCESS_lstTimetext " + lsTime+"@@"+mode);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
			 weekday = c.get(Calendar.DAY_OF_WEEK);
			 
			 myCursor.close();
			handler.postDelayed(showTime, 1000);
	        super.onStart(intent, startId);
		
	        db.close();
	
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(showTime);
        super.onDestroy();
        nm.cancel(Notification_ID_BASE);
    }
    
    private Runnable showTime = new Runnable() {
        public void run() {
            //log目前時間
            //Log.i("time:", new Date().toString());
        	 
        	//nhr = c.get(Calendar.HOUR_OF_DAY); 
        	//nmin = t.MINUTE;
        	nhr = Integer.valueOf(hr.format(new java.util.Date()));
        	nmin= Integer.valueOf(min.format(new java.util.Date()));
        	now_time=nhr+":"+nmin+":00";
        	try {
				nowTime= setTime.parse(now_time);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	System.out.println("nowTime:,"+ nowTime);
        	if(mode.equals("震動")){
	        	if(sun+mon+tue+wed+thu+fri+sta==0)
	        		compareTime();
	        	else if(weekday==sun)
	        			compareTime();
	        	else if(weekday==mon)
	        			compareTime();
	        	else if(weekday==tue)
	        			compareTime();
	        	else if(weekday==wed)
	    				compareTime();
	        	else if(weekday==thu)
	    				compareTime();
	        	else if(weekday==fri)
	    				compareTime();
	        	else if(weekday==sta)
	    				compareTime();
	        	else 
	        	{
	        		try{
	               	 audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
	               	 if(audioManager!=null)
	               	 {
	               		 audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	               	 }
			        	}catch(Exception e){
			    			
			        			}
	        	}
        	}else
        		
	        	if(sun+mon+tue+wed+thu+fri+sta==0)
	        		compareTime();
	        	else if(weekday==sun)
	        			compareTime2();
	        	else if(weekday==mon)
	        			compareTime2();
	        	else if(weekday==tue)
	        			compareTime2();
	        	else if(weekday==wed)
	    				compareTime2();
	        	else if(weekday==thu)
	    				compareTime2();
	        	else if(weekday==fri)
	    				compareTime2();
	        	else if(weekday==sta)
	    				compareTime2();
	        	
	        	else 
	        	{
	        		try{
	               	 audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
	               	 if(audioManager!=null)
	               	 {
	               		 audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	               	 }
			        	}catch(Exception e){
			    			
			        			}
	        	}
        	
        	
        		
        	//System.out.println(Integer.valueOf(nmin)+"@ft@"+min1+"@ls@"+min2+"@week@"+weekday);
        	//System.out.println(hr1+"點"+min1+"分到\n"+nhr1+"點"+min2+"分");
        	
            handler.postDelayed(this, 1000);
        }
    };
    
    
    private void compareTime()
    {
    	if(lsTime.after(ftTime))
    	{
        	if(nowTime.after(ftTime)&&nowTime.before(lsTime))
            {
        		
	        		try
	    			{
		        		audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
		        		if(audioManager!=null)
		        		{
		        			if(audioManager.getRingerMode()!=1)
		        			{
			        			audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
			        			System.out.println("震動"+audioManager.getRingerMode());
			        			control=0;
		        			}
		        		}
	        		}catch(Exception e){
	    			
	        			}
        		
                          
             }else 
             {
            	 if(control==0)
            	 {
	                 try{
	                	 audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
	                	 if(audioManager!=null)
	                	 {
	                		 audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	                		 System.out.println("一般"+audioManager.getRingerMode());
	                		 control=1;
	                	 }
			        	}catch(Exception e){
			    			
			        			}
            	 }
             }
    	}else
    	{
    		if(nowTime.before(ftTime)&&nowTime.after(lsTime))
            {
    			if(control==0)
    			{
	        		try{
	                	 audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
	                	 if(audioManager!=null)
	                	 {
	                		 audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	                		 control=1;
	                	 }
			        	}catch(Exception e){
			    			
			        			}
    			}
             }else 
             {
                 try
    			{
	        		audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
	        		if(audioManager!=null)
	        		{
	        			audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	        			control=0;
	        		}
        		}catch(Exception e){
    			
        			}
             }
    	}
    }
   
    
    private void compareTime2()
    {
    	if(lsTime.after(ftTime))
    	{
        	if(nowTime.after(ftTime)&&nowTime.before(lsTime))
            {
        		
	        		try
	    			{
		        		audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
		        		if(audioManager!=null)
		        		{
		        			if(audioManager.getRingerMode()!=1)
		        			{
			        			audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			        			System.out.println("靜音"+audioManager.getRingerMode());
			        			control=0;
		        			}
		        		}
	        		}catch(Exception e){
	    			
	        			}
        		
                          
             }else 
             {
            	 if(control==0)
            	 {
	                 try{
	                	 audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
	                	 if(audioManager!=null)
	                	 {
	                		 audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	                		 System.out.println("一般"+audioManager.getRingerMode());
	                		 control=1;
	                	 }
			        	}catch(Exception e){
			    			
			        			}
            	 }
             }
    	}else
    	{
    		if(nowTime.before(ftTime)&&nowTime.after(lsTime))
            {
    			if(control==0)
    			{
	        		try{
	                	 audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
	                	 if(audioManager!=null)
	                	 {
	                		 audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	                		 control=1;
	                	 }
			        	}catch(Exception e){
			    			
			        			}
    			}
             }else 
             {
                 try
    			{
	        		audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
	        		if(audioManager!=null)
	        		{
	        			audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	        			control=0;
	        		}
        		}catch(Exception e){
    			
        			}
             }
    	}
    }
    
    
}
