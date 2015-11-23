package com.gotomovie;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.gotomovie.movie_ch.Mv_find;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;



public class Movie_th2 extends Activity{
	private GridView gridview;
	private TextView t1;
	private static String[] array = {};
	private SimpleAdapter adapter;
	//宣告定位管理控制
	private LocationManager mLocationManager;
	//建立List，屬性為Poi物件
	private ArrayList<Poi> Pois = new ArrayList<Poi>();
	private ArrayList<HashMap<String,Object>> list2;
	private String[] th_name,th_distance,St_distance,address,mv_time;
	private double[] lat,lng;
	private String url="http://120.119.76.178/lbs/team7/",wh;
	private int Wm=0;
	View view ;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.theater);
	        
	        gridview = (GridView)findViewById(R.id.gridview); 
		    
	        Bundle b=this.getIntent().getExtras();
	        
	        th_name=b.getStringArray("th_name");
	        lat=b.getDoubleArray("lat");
	        lng=b.getDoubleArray("lng");
	        address=b.getStringArray("address");
	        mv_time=b.getStringArray("mv_time");
	        wh=b.getString("wh");
	        setTitle(wh);
	        th_distance=new String [address.length];
	        Wm=th_name.length;
	        for(int a=0;a<Wm;a++){
				 Pois.add(new Poi(th_name[a] , lat[a] ,lng[a],address[a],mv_time[a] ));
			 }
		//取得定位權限
  		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,10000.0f,LocationChange);
		
			 //Mv_find fl = new Mv_find();
			 //fl.start();
			 
			 //按下按鈕後讀取我的位置，定位抓取方式為網路讀取(若欲以GPS為定位抓取方式則更改成LocationManager.GPS_PROVIDER)，最後則帶入定位更新Listener。
			 
			 
			 //建立物件，並放入List裡 (建立物件需帶入名稱、緯度、經度)
			 /*
	  		Pois.add(new Poi("台北車站" , 25.04661 , 121.5168 ));
	     	Pois.add(new Poi("台中車站" , 24.13682 , 120.6850 ));
	     	Pois.add(new Poi("台北101" , 25.03362 , 121.56500 ));
	     	Pois.add(new Poi("高雄85大樓" , 22.61177 , 120.30031 ));
	     	Pois.add(new Poi("九份老街" , 25.10988 , 121.84519 ));*/
			 
			 
	        
			 gridview.setOnItemClickListener(new OnItemClickListener() {

			     	@Override
			     	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
			     		
			     		Intent intent =new Intent();
			     		
			     		Bundle b=new Bundle();
			     		b.putString("address1", String.valueOf(Pois.get(arg2).getLatitude()));
			     		b.putString("address2", String.valueOf(Pois.get(arg2).getLongitude()));
			     		b.putString("theater_name", Pois.get(arg2).getName());
			     		b.putString("theater", Pois.get(arg2).getaddress());
			     		intent.putExtras(b);
			     		intent.setClass(Movie_th2.this, find_theater.class);
			     		startActivity(intent);
			     		
			     	}
			     });
					//將字串 list 回傳的 JSON 透過解析後存到 values 陣列並傳給 listView 作清單的顯示
			        gridview.setAdapter(adapter);  
					
			
	    }
	
	
	
	
	
	private void put_gridview(){
		list2=new ArrayList<HashMap<String,Object>>();  
        HashMap<String,Object> map=null;  
        System.out.println("@@@@@@@@@"+Wm);
        St_distance=new String[Pois.size()];
        for(int a = 0 ; a < Pois.size() ; a++ )
        {
        	St_distance[a]=DistanceText(Pois.get(a).getDistance());
        	
        	
        }
       
        
        //將資料從字串取出  
        for(int i=0;i<Wm;i++)  
        {  
        map=new HashMap<String,Object>();
        map.put("th",th_distance[i]);
        map.put("time",mv_time[i]);
        map.put("pc",St_distance[i]);
        list2.add(map);  
        }
       
        //將資料放入對應的位子
        adapter = new SimpleAdapter(Movie_th2.this,list2,R.layout.theater_item,new String[]{"th","time","pc"},new int[]{R.id.MvTheater,R.id.Show_time,R.id.Distance});  
        
        
        
        //GridView的按鈕觸發事件
        gridview.setNumColumns(1);
        gridview.setAdapter(adapter);
        

	}
	
		//更新定位Listener
		public LocationListener LocationChange = new LocationListener() 
		{
			public void onLocationChanged(Location mLocation) 
			{	 
				for(Poi mPoi : Pois) 	
				{
					//for迴圈將距離帶入，判斷距離為Distance function，需帶入使用者取得定位後的緯度、經度、景點店家緯度、經度。 
					mPoi.setDistance(Distance(mLocation.getLatitude(),mLocation.getLongitude(),mPoi.getLatitude(),mPoi.getLongitude()));
		        }
		        	
				//依照距離遠近進行List重新排列
		        DistanceSort(Pois);
		        for(int i = 0 ; i < Pois.size() ; i++ )
		        {
		        System.out.println("@7@7@7@"+Pois.get(i).getName());
		        th_distance[i]=Pois.get(i).getName();
		        mv_time[i]=Pois.get(i).getmv_time();
		        }
				//印出我的座標-經度緯度
				//TEXT = "我的座標 - 經度 : " + mLocation.getLongitude() + "  , 緯度 : " + mLocation.getLatitude() ;
				
				//for迴圈，印出景點店家名稱及距離，並依照距離由近至遠排列，第一筆為最近的景點店家，最後一筆為最遠的景點店家
		       /*	for(int i = 0 ; i < Pois.size() ; i++ )
		        {
		       		//範圍距離篩選，小於範圍內的才可以顯示
		       		if(Pois.get(i).getDistance() < DISTANCE)
		       		{
		       			TEXT = TEXT + "\n" + "地點 : " + Pois.get(i).getName() + "  , 距離為 : " + DistanceText(Pois.get(i).getDistance()) ;
		       		}
		        }*/
		        
		        put_gridview();
		    }
		         
		    public void onProviderDisabled(String provider) 
		    {
		    }
		         
		    public void onProviderEnabled(String provider) 
		    {
		    }
		         
		    public void onStatusChanged(String provider, int status,Bundle extras) 
		    {
		    }
		};

		@Override
		protected void onDestroy() 
		{
			super.onDestroy();
			mLocationManager.removeUpdates(LocationChange);  //程式結束時停止定位更新
		}

		//帶入距離回傳字串 (距離小於一公里以公尺呈現，距離大於一公里以公里呈現並取小數點兩位)
		private String DistanceText(double distance)
		{
			if(distance < 1000 ) return String.valueOf((int)distance) + "m" ;
			else return new DecimalFormat("#.00").format(distance/1000) + "km" ;
		}

		//List排序，依照距離由近開始排列，第一筆為最近，最後一筆為最遠
		private void DistanceSort(ArrayList<Poi> poi)
		{
			Collections.sort(poi, new Comparator<Poi>() 
			{
				@Override
				public int compare(Poi poi1, Poi poi2) 
				{
					return poi1.getDistance() < poi2.getDistance() ? -1 : 1 ;
				}
			});
		}

		//帶入使用者及景點店家經緯度可計算出距離
		public double Distance(double longitude1, double latitude1, double longitude2,double latitude2) 
		{
			double radLatitude1 = latitude1 * Math.PI / 180;
			double radLatitude2 = latitude2 * Math.PI / 180;
			double l = radLatitude1 - radLatitude2;
			double p = longitude1 * Math.PI / 180 - longitude2 * Math.PI / 180;
			double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(l / 2), 2)
			                 + Math.cos(radLatitude1) * Math.cos(radLatitude2)
			                 * Math.pow(Math.sin(p / 2), 2)));
			distance = distance * 6378137.0;
			distance = Math.round(distance * 10000) / 10000;
			    
			return distance ;
		}

		/*** 印出的結果為 : 
		      * 
		      * (未設定範圍距離)
		      * 我的座標 - 經度 : 121.56024  , 緯度 : 25.03935
		      * 地點 : 台北101  , 距離為 : 626m
		      * 地點 : 台北車站  , 距離為 : 4.85km
		      * 地點 : 九份老街  , 距離為 : 31.99km
		      * 地點 : 台中車站  , 距離為 : 110.40km
		      * 地點 : 高雄85大樓  , 距離為 : 197.37km
		      * 
		      * 
		      * (設定範圍距離為50000則顯示為:)
		      * 我的座標 - 經度 : 121.56024  , 緯度 : 25.03935
		      * 地點 : 台北101  , 距離為 : 626m
		      * 地點 : 台北車站  , 距離為 : 4.85km
		      * 地點 : 九份老街  , 距離為 : 31.99km
		      * 
		*/
	
	 

	
}
