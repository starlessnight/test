package com.example.lab2;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        creatMap();
    }

private void creatMap()
{
	MapFragment mf =(MapFragment)getFragmentManager().findFragmentById(R.id.map);
	GoogleMap map = mf.getMap();
	
	LatLng Location1=new LatLng(22.763284,120.376330);
	LatLng Location2=new LatLng(22.763026,120.375357);
	
	map.addMarker(new MarkerOptions()
			.position(Location1)
			.title("99113244")
			.snippet("陳沅旻"));
	map.addMarker(new MarkerOptions()
			.position(Location2)
			.rotation(180f)
			.title("99113244")
			.snippet("陳沅旻"));
	map.animateCamera(CameraUpdateFactory.newLatLngZoom(Location1, 15.5f),2000,null);
	
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
