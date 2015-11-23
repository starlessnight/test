package com.gps_location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;
 
public class GPS_L extends Activity implements LocationListener {
	/** Called when the activity is first created. */
	@Override
	public void onLocationChanged(Location location) {	//��a�I���ܮ�
		// TODO Auto-generated method stub
		getLocation(location);
	}
 
	@Override
	public void onProviderDisabled(String arg0) {	//��GPS�κ����w��\��������
		// TODO Auto-generated method stub
 
	}
 
	@Override
	public void onProviderEnabled(String arg0) {	//��GPS�κ����w��\��}��
		// TODO Auto-generated method stub
 
	}
 
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {	//�w�쪬�A����
		// TODO Auto-generated method stub
 
	}
	
	
	private boolean getService = false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//���o�t�Ωw��A��
				LocationManager status = (LocationManager) (this.getSystemService(Context.LOCATION_SERVICE));
				if (status.isProviderEnabled(LocationManager.GPS_PROVIDER) || status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
					//�p�GGPS�κ����w��}�ҡA�I�slocationServiceInitial()��s��m
					getService = true;	//�T�{�}�ҩw��A��
					locationServiceInitial();
				} else {
					Toast.makeText(this, "�ж}�ҩw��A��", Toast.LENGTH_LONG).show();
					
					startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));	//�}�ҳ]�w����
				}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(getService) {
			lms.requestLocationUpdates(bestProvider, 1000, 1, this);
			//�A�ȴ��Ѫ̡B��s�W�v60000�@��=1�����B�̵u�Z���B�a�I���ܮɩI�s����
		}
	}
 
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(getService) {
			lms.removeUpdates(this);	//���}�����ɰ����s
		}
	}
	
	
	
	
	
	private LocationManager lms;
	private String bestProvider = LocationManager.GPS_PROVIDER;	//�̨θ�T���Ѫ�
	private void locationServiceInitial() {
		lms = (LocationManager) getSystemService(LOCATION_SERVICE);	//���o�t�Ωw��A��
		Criteria criteria = new Criteria();	//��T���Ѫ̿���з�
		bestProvider = lms.getBestProvider(criteria, true);	//��ܺ�ǫ׳̰������Ѫ�
		Location location = lms.getLastKnownLocation(bestProvider);
		getLocation(location);
	}
	private void getLocation(Location location) {	//�N�w���T��ܦb�e����
		if(location != null) {
			TextView longitude_txt = (TextView) findViewById(R.id.longitude);
			TextView latitude_txt = (TextView) findViewById(R.id.latitude);
 
			Double longitude = location.getLongitude();	//���o�g��
			Double latitude = location.getLatitude();	//���o�n��
			System.out.println(Double.toString(longitude));
			longitude_txt.setText(String.valueOf(longitude));
			latitude_txt.setText(String.valueOf(latitude));
		}
		else {
			Toast.makeText(this, "�L�k�w��y��", Toast.LENGTH_LONG).show();
		}
	}
	
	
	
}
