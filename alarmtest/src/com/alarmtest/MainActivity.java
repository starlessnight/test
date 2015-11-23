package com.alarmtest;

import java.util.Calendar;

import com.alarmtest.CallAlarmText;

import android.support.v7.app.ActionBarActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private Button test;
	private Calendar  c=Calendar.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findView();
		
		
		test.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				c.setTimeInMillis(System.currentTimeMillis());
		        //c.set(year, month, day, hr, min, 0);
				c.set(Calendar.YEAR,2000);
				c.set(Calendar.MONTH,4);
				c.set(Calendar.DATE,7);
				c.set(Calendar.HOUR_OF_DAY,9);
				c.set(Calendar.MINUTE,10);
				c.set(Calendar.SECOND,0);
				c.set(Calendar.MILLISECOND,0);
		        Intent intent = new Intent(getBaseContext(), CallAlarmText.class);
		        PendingIntent sender=PendingIntent.getBroadcast(
		      		  getBaseContext(),0, intent, 0);
		        AlarmManager am;
		        am = (AlarmManager)getSystemService(ALARM_SERVICE);
		        am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
		               c.getTimeInMillis(),
		               sender 
		              );
		        
		        Toast.makeText(MainActivity.this, "@@@@@@@",Toast.LENGTH_SHORT ).show();
				}
			});
		
		
	}
	
	
	private void findView(){
		
		test=(Button)findViewById(R.id.test);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
