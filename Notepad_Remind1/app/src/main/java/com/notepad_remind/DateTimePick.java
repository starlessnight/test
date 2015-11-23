package com.notepad_remind;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class DateTimePick extends Activity implements OnDateChangedListener ,OnTimeChangedListener{

	 private String initDateTime;  
	 private Activity activity;  
	 private AlertDialog ad;
	 private DatePicker datePicker;  
	 private TimePicker timePicker;  
	 private String dateTime; 
	public DateTimePick(Activity activity,String initDateTime){
		this.activity=activity;
		this.initDateTime=initDateTime;
	}
	
	
	private void init(DatePicker dataTimePick,TimePicker timePick){
		Calendar calendar=Calendar.getInstance();
		if(!(null==initDateTime ||"".equals(initDateTime))){
			calendar=this.getCalendarByInintData(initDateTime);
			
		}else {  
            initDateTime = calendar.get(Calendar.YEAR) + "年"  
	                    + calendar.get(Calendar.MONTH) + "月"  
	                    + calendar.get(Calendar.DAY_OF_MONTH) + "日 "  
	                    + calendar.get(Calendar.HOUR_OF_DAY) + ":"  
	                    + calendar.get(Calendar.MINUTE);  
        }  
		dataTimePick.init(calendar.get(Calendar.YEAR),  
	                		calendar.get(Calendar.MONTH),  
	                		calendar.get(Calendar.DAY_OF_MONTH), this);  
		timePick.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));  
		timePick.setCurrentMinute(calendar.get(Calendar.MINUTE));  
	}
	
	
	public AlertDialog dateTimePicKDialog(final TextView timeShow) {  
        LinearLayout dateTimeLayout = (LinearLayout) activity  
                .getLayoutInflater().inflate(R.layout.calender_item, null);  
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datePicker);  
        timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timePicker);  
        init(datePicker, timePicker);  
        timePicker.setIs24HourView(false);  
        timePicker.setOnTimeChangedListener(this);  
  
        ad = new AlertDialog.Builder(activity)  
                .setTitle(initDateTime)  
                .setView(dateTimeLayout)  
                .setPositiveButton("设置", new DialogInterface.OnClickListener() { 
                	@Override
                    public void onClick(DialogInterface dialog, int whichButton) {  
                		timeShow.setText(dateTime);
                        
                       
                    }  
                })  
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
                        //inputDate.setText(""); 
                    	
                    	
                    }  
                }).show();  
  
        onDateChanged(null, 0, 0, 0);  
        return ad;  
    }  
	
	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		
		Calendar calendar = Calendar.getInstance();  
		  
        calendar.set(datePicker.getYear(), datePicker.getMonth(),  
                datePicker.getDayOfMonth(), timePicker.getCurrentHour(),  
                timePicker.getCurrentMinute());  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");  
  
        dateTime = sdf.format(calendar.getTime());  
		
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		onDateChanged(null, 0, 0, 0);  
	}
	
	
	
	
	/** 
     * 实现将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒,并赋值给calendar 
     *  
     * @param initDateTime 
     *            初始日期时间值 字符串型 
     * @return Calendar 
     */  
    private Calendar getCalendarByInintData(String initDateTime) {  
        Calendar calendar = Calendar.getInstance();  
  
        // 将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒  
        String date = spliteString(initDateTime, "日", "index", "front"); // 日期  
        String time = spliteString(initDateTime, "日", "index", "back"); // 时间  
  
        String yearStr = spliteString(date, "年", "index", "front"); // 年份  
        String monthAndDay = spliteString(date, "年", "index", "back"); // 月日  
  
        String monthStr = spliteString(monthAndDay, "月", "index", "front"); // 月  
        String dayStr = spliteString(monthAndDay, "月", "index", "back"); // 日  
  
        String hourStr = spliteString(time, ":", "index", "front"); // 时  
        String minuteStr = spliteString(time, ":", "index", "back"); // 分  
  
        int currentYear = Integer.valueOf(yearStr.trim()).intValue();  
        int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;  
        int currentDay = Integer.valueOf(dayStr.trim()).intValue();  
        int currentHour = Integer.valueOf(hourStr.trim()).intValue();  
        int currentMinute = Integer.valueOf(minuteStr.trim()).intValue();  
  
        calendar.set(currentYear, currentMonth, currentDay, currentHour,  
                currentMinute);  
        return calendar;  
    }  
    
    
    /** 
     * 截取子串 
     *  
     * @param srcStr 
     *            源串 
     * @param pattern 
     *            匹配模式 
     * @param indexOrLast 
     * @param frontOrBack 
     * @return 
     */  
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
