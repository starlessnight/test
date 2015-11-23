package com.example.asd;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.example.asd.R;


import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
public class AllMaina extends Activity{
	private Button btnEnter, btncontrol, btnSupervise, btnSos, ClBack, SeBack,
	SsBack, sosSet, btnMap,MapBack,close,goLook;
	private EditText SosMessage, CellPhone;
	int i=0;
	private String NO="nULL",getED,getNmb,Mg,Nb,Send,s119,ssg,getX,getY,ms;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		jumpToLayout2();
		
		
		
		LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(serviceName);
//        String provider = LocationManager.GPS_PROVIDER;
//        String provider = "gps";

        Criteria criteria = new Criteria();
        
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false); 
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);

        Location location = locationManager.getLastKnownLocation(provider);
        //updateWithNewLocation(location);
        locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
        Button mBtnLoadResult=(Button)findViewById(R.id.btnSupervise);
        mBtnLoadResult.setOnClickListener(btnLoadResultLis);
        
	}


//主控介面
	public void jumpToLayout2() {
		// 將layout改成saved.xml
				setContentView(R.layout.main);
				btncontrol = (Button) findViewById(R.id.btncontrol);
				btncontrol.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						jumpTocontrol();
					}
				});

				btnSupervise = (Button) findViewById(R.id.btnSupervise);
				btnSupervise.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						jumpToSupervise();
					}
				});

				btnSos = (Button) findViewById(R.id.btnSos);
				btnSos.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

			             //new 一個intent物件，並指定要啟動的class

			             Intent intent = new Intent();

			             intent.setClass(AllMaina.this, SosMain.class);

			             //呼叫一個新的activity

			             startActivity(intent);

			                          

						
					}
				});
				
				btnMap = (Button) findViewById(R.id.btnMap);
				btnMap.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						LocationManager lManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
						
						//GPS設定畫面
						Intent mapGo=new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						if(!lManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||
								!lManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
						{
							Toast.makeText(AllMaina.this, "GPS:未啓用",Toast.LENGTH_SHORT).show();
							
							//導向GPS設定畫面
							startActivityForResult(mapGo,0);
						}else
						{
						
						// TODO Auto-generated method stub

		             //new 一個intent物件，並指定要啟動的class

		             Intent intent = new Intent();

		             intent.setClass(AllMaina.this, MapMain.class);

		             //呼叫一個新的activity

		             startActivity(intent);

		             
						
						
						}
						
						
					             

					}
				});
				close= (Button) findViewById(R.id.close);
				close.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						

		                          

					}
				});
	}
	
	private Button.OnClickListener btnLoadResultLis=new Button.OnClickListener(){
		public void onClick(View v){
						
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");
			if(	Send!=null){
			SmsManager smsManager=SmsManager.getDefault();
			try{
				
				
				//�إ�PendingIntent �@�� sentIntent�Ѽ�
				PendingIntent mPI=PendingIntent.getBroadcast(
						AllMaina.this,0,new Intent(),0);
				
				//�����ǰe²�T
				smsManager.sendTextMessage(Mg,null,Nb,mPI,null);
				Toast.makeText(AllMaina.this,
						getResources().getText(R.string.str_cmd_sending).toString()+Mg,Toast.LENGTH_SHORT
						).show();
				
				
			}catch(Exception e){
				
			}
			
			}
			
			SharedPreferences MsgResultData=getSharedPreferences("MSG_RESULT",MODE_WORLD_READABLE);
			getED=MsgResultData.getString("text", "我出車禍了");
			//view1.setText(getED);
			getNmb=MsgResultData.getString("nmb", "119");
			//view2.setText(getNmb);
			getX=MsgResultData.getString("gpsX", "找不到X");
			getY=MsgResultData.getString("gpsY", "找不到Y");
			ms=getED+"\n"+getX+"\n"+getY;
			Toast.makeText(AllMaina.this, "現在位置\n"+getX+"\n"+getY,Toast.LENGTH_SHORT).show();
			
			
			
			
			String mobile = getNmb;

			
			// ���T�����e

			

			// �q�H�~�̤��\�C���ǰe���r�Ʀ����A�ڭ̥i�H�ϥ�Android���ڭ̴��Ѫ�²�T�u��C

			if (ms != null) {

			SmsManager sms = SmsManager.getDefault();

			// �p�G²�T�S���W�L�����סA�h�Ǧ^�@�Ӫ�ת�List�C

			List<String> texts = sms.divideMessage(ms);
			System.out.println(texts);
			for (String text : texts) {

			sms.sendTextMessage(mobile, null, text, null, null);

			}

			}
			
		}
	};
//跳回主控介面
	public void jumpToactivity() {
		setContentView(R.layout.activity_main);
	}

	// 控制介面
	public void jumpTocontrol() {
		Intent intent =new Intent();
		intent.setClass(AllMaina.this, control.class);
		startActivity(intent);
		
		
		
	}

	// 帳戶管控介面
	public void jumpToSupervise() {
		setContentView(R.layout.supervise);
		SeBack = (Button) findViewById(R.id.SeBack);
		SeBack.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				jumpToLayout2();
			}
		});
	}
	
	
	//GPS
	
	
	private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
                updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider) {
                updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
};

private void updateWithNewLocation(Location location) {
        String latLongString,gpX,gpY;
        TextView myLocationText;
        
        //myLocationText = (TextView) findViewById(R.id.myLocationText);
//        try {
//                Thread.sleep(0);//因为真机获取gps数据需要一定的时间，为了保证获取到，采取系统休眠的延迟方法
//        } catch (InterruptedException e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//        }
        
        if (location != null) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                
                Geocoder geocoder=new Geocoder(this,Locale.TAIWAN); 
//                Geocoder geocoder = new Geocoder(this, Locale.CHINA);
                List places = null;
                
                try {
                        Thread.sleep(2000);
                        places = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
                        Thread.sleep(2000);
                        Toast.makeText(AllMaina.this, places.size()+"", Toast.LENGTH_LONG).show();
                        System.out.println(places.size()+""); 
                } catch (Exception e) {  
                        e.printStackTrace();
                }
                
                String placename = "";
                if (places != null && places.size() > 0) {
                        placename=((Address)places.get(0)).getLocality();
                        //一下的信息将会具体到某条街
                        //其中getAddressLine(0)表示国家，getAddressLine(1)表示精确到某个区，getAddressLine(2)表示精确到具体的街
                        placename = ((Address) places.get(0)).getAddressLine(0) + ", " + System.getProperty("line.separator")
                                        + ((Address) places.get(0)).getAddressLine(1) + ", "
                                        + ((Address) places.get(0)).getAddressLine(2);
                }
                	gpX="緯度:" + Double.toString(lat);
                	gpY="經度" + Double.toString(lng);;
                	SharedPreferences MsgResultData=getSharedPreferences("MSG_RESULT",MODE_WORLD_READABLE);
					
					MsgResultData.edit()
					.putString("gpsX",gpX)
					.putString("gpsY",gpY)
					.commit();
					
                latLongString = "緯度:" + lat + "\n經度:" + lng;
                Toast.makeText(AllMaina.this, latLongString, Toast.LENGTH_LONG).show();
        } else {
                latLongString = "無法取得經緯度";
        }
        //myLocationText.setText("您当前的位置是:\n" + latLongString);
}


	
	


	
	
}