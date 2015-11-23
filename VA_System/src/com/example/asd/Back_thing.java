package com.example.asd;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

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

public class Back_thing extends  Activity{
	
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //setContentView(R.layout.activity_test);
            LocationManager locationManager;
            String serviceName = Context.LOCATION_SERVICE;
            locationManager = (LocationManager) getSystemService(serviceName);
//            String provider = LocationManager.GPS_PROVIDER;
//            String provider = "gps";

            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false); 
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            String provider = locationManager.getBestProvider(criteria, true);

            Location location = locationManager.getLastKnownLocation(provider);
            updateWithNewLocation(location);
            locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);

    }

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
            String latLongString;
            TextView myLocationText;
            //myLocationText = (TextView) findViewById(R.id.myLocationText);
//            try {
//                    Thread.sleep(0);//因为真机获取gps数据需要一定的时间，为了保证获取到，采取系统休眠的延迟方法
//            } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    throw new RuntimeException(e);
//            }
            if (location != null) {
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();
                    
                    Geocoder geocoder=new Geocoder(this); 
//                    Geocoder geocoder = new Geocoder(this, Locale.CHINA);
                    List places = null;
                    
                    try {
                            //Thread.sleep(2000);
                            places = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
                            //Thread.sleep(2000);
                            Toast.makeText(Back_thing.this, places.size()+"", Toast.LENGTH_LONG).show();
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

                    latLongString = "緯度:" + lat + "\n經度:" + lng;
                    Toast.makeText(Back_thing.this, latLongString, Toast.LENGTH_LONG).show();
            } else {
                    latLongString = "無法取得經緯度";
            }
           // myLocationText.setText("您当前的位置是:\n" + latLongString);
    }
}
