package com.time_set_small;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
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

	private Handler handler=new Handler();
	private int hr1,min1,hr2,min2;
	Time t=new Time("GMT+8");
	AudioManager audioManager;
	Calendar c = Calendar.getInstance();
	SimpleDateFormat hr = new SimpleDateFormat("HH"),min = new SimpleDateFormat("mm");
	SimpleDateFormat setTime=new SimpleDateFormat("HH:mm:ss");
	Date ftTime,lsTime,nowTime;
	
	int nhr,nmin,weekday,sun,mon,tue,wed,thu,fri,sta,control ; 
	private SQLiteDatabase db;
	private mySQLiteOpenHelper helper;
	private Cursor myCursor;
	private String _id,day,lt,ft,nt,mode;
	private String[] weekdays;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
    public void onStart(Intent intent, int startId) {
		
		
		
		helper = new mySQLiteOpenHelper(timeService.this,"process");
		db = helper.getReadableDatabase();
		myCursor = db.rawQuery("select PROCESS_ftTimetext,PROCESS_lstTimetext,weekday from process ;", null);
		myCursor.moveToFirst();
		
		    ft = myCursor.getString(0);
		    lt = myCursor.getString(1);
		    day = myCursor.getString(2);
		    
		    
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
				System.out.println("PROCESS_ftTimetext:,"+ ftTime + ",PROCESS_lstTimetext " + lsTime+"@@"+mon);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
			 weekday = c.get(Calendar.DAY_OF_WEEK);
			
			
			handler.postDelayed(showTime, 1000);
	        super.onStart(intent, startId);
		
		
		
		/*if(intent!=null){
		sun=Integer.valueOf(intent.getStringExtra("sun"));
		mon=Integer.valueOf(intent.getStringExtra("mon"));
		tue=Integer.valueOf(intent.getStringExtra("tue"));
		wed=Integer.valueOf(intent.getStringExtra("wed"));
		thu=Integer.valueOf(intent.getStringExtra("thu"));
		fri=Integer.valueOf(intent.getStringExtra("fri"));
		sta=Integer.valueOf(intent.getStringExtra("sta"));
		System.out.println(sun);
		hr1=Integer.valueOf(intent.getStringExtra("hr1"));
		min1=Integer.valueOf(intent.getStringExtra("min1"));
		//System.out.println("hrhrhrhrhr"+min1);
		hr2=Integer.valueOf(intent.getStringExtra("hr2"));
		min2=Integer.valueOf(intent.getStringExtra("min2"));
		ftTime=new Date(hr1,min1,00);
		lsTime=new Date(hr2,min2,00);
		if(lsTime.after(ftTime))
		{
			min1=min1-1;
			ftTime=new Date(hr1,min1,00);
			lsTime=new Date(hr2,min2,00);
		}else
		{
			min2=min2-1;
			ftTime=new Date(hr1,min1,00);
			lsTime=new Date(hr2,min2,00);
		}
		 weekday = c.get(Calendar.DAY_OF_WEEK);
		
		
		handler.postDelayed(showTime, 5000);
        super.onStart(intent, startId);
		}else
			System.out.println("intent=null");
			*/
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(showTime);
        super.onDestroy();
    }
    
    private Runnable showTime = new Runnable() {
        public void run() {
            //log目前時間
            //Log.i("time:", new Date().toString());
        	 
        	//nhr = c.get(Calendar.HOUR_OF_DAY); 
        	//nmin = t.MINUTE;
        	nhr = Integer.valueOf(hr.format(new java.util.Date()));
        	nmin= Integer.valueOf(min.format(new java.util.Date()));
        	nt=nhr+":"+nmin+":00";
        	try {
				nowTime= setTime.parse(nt);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	System.out.println("nowTime:,"+ nowTime);
        	
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
