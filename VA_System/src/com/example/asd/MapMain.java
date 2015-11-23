package com.example.asd;
import com.example.asd.R;
import com.example.asd.MapMain;

import java.util.List;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapMain extends  MapActivity{
	private Button MapBack;
	
	public ProgressDialog myDialog = null;
	public int intZoomLevel = 14, sum = 0;
	private EditText SosMessage, CellPhone;
	private LocationManager mLocationManager01;
	private String strLocationPrivider = "",address=null;
	private Location mLocation01 = null;
	private TextView mTextView01, mTextView02;
	private MapView mMapView01;
	private GeoPoint currentGeoPoint;
	
	@Override
	  protected void onCreate(Bundle icicle)
	  {
	    // TODO Auto-generated method stub
	    super.onCreate(icicle);
	    setContentView(R.layout.maps);
	    
	    mTextView01 = (TextView)findViewById(R.id.MapText);
	    mTextView02 = (TextView)findViewById(R.id.MapAddress);
	    /* 建立MapView物件 */
	    //mMapView01 = (MapView)findViewById(R.id.map);
	    
	    /* 建立LocationManager物件取得系統LOCATION服務 */
	    mLocationManager01 = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    
	    /* 第一次執行向Location Provider取得Location */
	    mLocation01 = getLocationPrivider(mLocationManager01);
	    
	    if(mLocation01!=null)
	    {
	      processLocationUpdated(mLocation01);
	    }
	    else
	    {
	    	System.out.println("@@@@@@@@@"+(getResources().getText(R.string.str_err_address).toString()));
	    	mTextView01.setText (getResources().getText(R.string.str_err_address).toString());
	    	
	    }
	    /* 建立LocationManager物件，監聽Location變更時事件，更新MapView 
	    mLocationManager01.requestLocationUpdates(strLocationPrivider, 2000, 10, mLocationListener01);*/
	    MapBack=(Button)findViewById(R.id.MapBack);
		MapBack.setOnClickListener(new Button.OnClickListener() {
			 
			@Override
			public void onClick(View v) {
				
				mLocationManager01.removeUpdates(mLocationListener01 );
				
				// TODO Auto-generated method stub

                //new 一個intent物件，並指定要啟動的class

                

                MapMain.this.finish();     
				
			}
		});
	  }
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	private void creatMap(String latlng1 ,String latlang2)
	{
		MapFragment mf =(MapFragment)getFragmentManager().findFragmentById(R.id.map);
		GoogleMap map = mf.getMap();
		
		LatLng Location1=new LatLng(Double.parseDouble(latlang2),Double.parseDouble(latlng1));
		LatLng Location2=new LatLng(22.763026,120.375357);
		
		map.addMarker(new MarkerOptions()
				.position(Location1)
				.title("目前所在地")
				.snippet(address));
		/*map.addMarker(new MarkerOptions()
				.position(Location2)
				.rotation(180f)
				.title("99113259")
				.snippet("陳弘啓"));*/
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(Location1, 15.5f),2000,null);
		
	}
	  
	  public final LocationListener mLocationListener01 = new LocationListener()
	  {
	    @Override
	    public void onLocationChanged(Location location)
	    {
	      // TODO Auto-generated method stub
	      
	      /* 當手機收到位置變更時，將location傳入取得地理座標 */
	      processLocationUpdated(location);
	    }
	    
	    @Override
	    public void onProviderDisabled(String provider)
	    {
	      // TODO Auto-generated method stub
	      /* 當Provider已離開服務範圍時 */
	    }
	    
	    @Override
	    public void onProviderEnabled(String provider)
	    {
	      // TODO Auto-generated method stub
	    }
	    
	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras)
	    {
	      // TODO Auto-generated method stub
	      
	    }
	  };
	  
	  public String getAddressbyGeoPoint(GeoPoint gp)
	  {
	    String strReturn = "";
	    try
	    {
	      /* 當GeoPoint不等於null */
	      if (gp != null)
	      {
	        /* 建立Geocoder物件 */
	        Geocoder gc = new Geocoder(MapMain.this, Locale.getDefault());
	        
	        /* 取出地理座標經緯度 */
	        double geoLatitude = gp.getLatitudeE6()/1E6;
	        double geoLongitude = gp.getLongitudeE6()/1E6;
	        
	        /* 自經緯度取得地址（可能有多行地址） */
	        List<Address> lstAddress = gc.getFromLocation(geoLatitude, geoLongitude, 1);
	        StringBuilder sb = new StringBuilder();
	        
	        /* 判斷地址是否為多行 */
	        if (lstAddress.size() > 0)
	        {
	          Address adsLocation = lstAddress.get(0);

	          for (int i = 0; i < adsLocation.getMaxAddressLineIndex(); i++)
	          {
	            sb.append(adsLocation.getAddressLine(i)).append("\n");
	          }
	          sb.append(adsLocation.getLocality()).append("\n");
	          sb.append(adsLocation.getPostalCode()).append("\n");
	          sb.append(adsLocation.getCountryName());
	        }
	        
	        /* 將擷取到的地址，組合後放在StringBuilder物件中輸出用 */
	        address=sb.toString();
	        strReturn = lstAddress.get(0).getAddressLine(0);;
	        System.out.println("8888888888888"+address);
	      }
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return strReturn;
	  }
	  
	  public Location getLocationPrivider(LocationManager lm)
	  {
	    Location retLocation = null;
	    try
	    {
	      Criteria mCriteria01 = new Criteria();
	      mCriteria01.setAccuracy(Criteria.ACCURACY_FINE);
	      mCriteria01.setAltitudeRequired(false);
	      mCriteria01.setBearingRequired(false);
	      mCriteria01.setCostAllowed(true);
	      mCriteria01.setPowerRequirement(Criteria.POWER_LOW);
	      strLocationPrivider = lm.getBestProvider(mCriteria01, true);
	      retLocation = lm.getLastKnownLocation(strLocationPrivider);
	    }
	    catch(Exception e)
	    {
	      mTextView01.setText(e.toString());
	      e.printStackTrace();
	    }
	    return retLocation;
	  }
	  
	  private GeoPoint getGeoByLocation(Location location)
	  {
	    GeoPoint gp = null;
	    try
	    {
	      /* 當Location存在 */
	      if (location != null)
	      {
	        double geoLatitude = location.getLatitude()*1E6;
	        double geoLongitude = location.getLongitude()*1E6;
	        gp = new GeoPoint((int) geoLatitude, (int) geoLongitude);
	      }
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return gp;
	  }
	  
	  public static void refreshMapViewByGeoPoint(GeoPoint gp, MapView mv, int zoomLevel, boolean bIfSatellite)
	  {
	    try
	    {
	      mv.displayZoomControls(true);
	      /* 取得MapView的MapController */
	      MapController mc = mv.getController();
	      /* 移至該地理座標位址 */
	      mc.animateTo(gp);
	      
	      /* 放大地圖層級 */
	      mc.setZoom(zoomLevel);
	      
	      /* 延伸學習：取得MapView的最大放大層級 */
	      //mv.getMaxZoomLevel()
	      
	      /* 設定MapView的顯示選項（衛星、街道）*/
	      if(bIfSatellite)
	      {
	        mv.setTraffic(true);
	      //設定地圖檢示模式
			//.setTraffic(true)：一般地圖
			//.setSatellite(true)：衛星地圖
			//.setStreetView：街景圖
	      }
	      else
	      {
	        mv.setTraffic(false);
	      }
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	  
	  /* 當手機收到位置變更時，將location傳入更新當下GeoPoint及MapView */
	  private void processLocationUpdated(Location location)
	  {
	    /* 傳入Location物件，取得GeoPoint地理座標 */
	    currentGeoPoint = getGeoByLocation(location);
	    
	    creatMap(String.valueOf((int)currentGeoPoint.getLongitudeE6()/1E6),String.valueOf((int)currentGeoPoint.getLatitudeE6()/1E6));
	    
	    mTextView01.setText
	    (
	      getResources().getText(R.string.strmylocation).toString()+"\n"+
	      /* 延伸學習：取出GPS地理座標： */
	      
	      getResources().getText(R.string.str_longitude).toString()+
	      String.valueOf((int)currentGeoPoint.getLongitudeE6()/1E6)+"\n"+
	      getResources().getText(R.string.str_latitude).toString()+
	      String.valueOf((int)currentGeoPoint.getLatitudeE6()/1E6)+"\n"
	      
	      
	    );
	    mTextView02.setText(getAddressbyGeoPoint(currentGeoPoint));
	  }
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
