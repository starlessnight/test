package com.gotomovie;

import java.util.ArrayList;
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

import android.content.Context;
import android.content.Intent;
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



public class Movie_th extends Fragment{
	private GridView gridview;
	private TextView t1;
	private static String[] array = {};
	private SimpleAdapter adapter;
	private ArrayList<HashMap<String,Object>> list2;
	private String[] a={"1","2","3"};
	private String[] b={"1a","2a","3a"},th_name;
	private String[] c={"1b","2b","3b"};
	private String url="http://120.119.76.178/lbs/team7/";
	private int Wm=0;
	View view ;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	     
	    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		 view = inflater.inflate(R.layout.theater, container, false);  
		 gridview = (GridView) view.findViewById(R.id.gridview); 
	       
		 Mv_find fl = new Mv_find();
		 fl.start();
		 
	     
		return view;
		
	}
	
	// 取得好友清單
	class Mv_find extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				HttpClient client = new DefaultHttpClient();
				url=url+"get_theater.php";
				HttpGet get = new HttpGet(url);
				HttpResponse response = client.execute(get);
				HttpEntity resEntity = response.getEntity();
				String result = EntityUtils.toString(resEntity);
				Log.d("result", result);
				
				Bundle countBundle = new Bundle();
				countBundle.putString("th", result);
				//打包朋友清單相關資料，並命名為 list
				Message msg = new Message();
				msg.setData(countBundle);
				mHandler.sendMessage(msg);
				//將打包資料方置放在 Message 物件當中，並傳送給 handler
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			setList(msg.getData().getString("th"));
			/*
			         透過 handler 來請 main thread 處理 setList
			    sub thread 不能值接對 UI 作控制，必須透過 handler 來請主程序作
			*/
		}
	};
	
	public void setList(String list) {
		JSONArray json;
		try {
			json = new JSONArray(list);
			th_name = new String[json.length()];
			Wm=json.length();
			for (int i = 0; i < json.length(); i++) {
				JSONObject jsonData = new JSONObject(json.get(i).toString());
				th_name[i] = jsonData.getString("ThName");
				System.out.println(th_name[i]);
				
			}
			
			list2=new ArrayList<HashMap<String,Object>>();  
	        HashMap<String,Object> map=null;  
	         
	       
	        //將資料從字串取出  
	        for(int i=0;i<Wm;i++)  
	        {  
	        map=new HashMap<String,Object>();
	        map.put("store",th_name[i]);
	        map.put("pn",th_name[i]);
	        map.put("pc",th_name[i]);
	        list2.add(map);  
	        }
	       
	        //將資料放入對應的位子
	        adapter = new SimpleAdapter(getActivity(),list2,R.layout.theater_item,new String[]{"store","pn","pc"},new int[]{R.id.MvTheater,R.id.Show_time,R.id.Distance});  
	        
	        
	        
	 //GridView的按鈕觸發事件
	        gridview.setNumColumns(1);
	        gridview.setAdapter(adapter);
	        gridview.setOnItemClickListener(new OnItemClickListener() {

	        	@Override
	        	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
	        		
	        		Intent intent =new Intent();
	        		intent.setClass(getActivity(), find_theater.class);
	        		getActivity().startActivity(intent);
	        		
	        	}
	        });
	        
		 gridview.setAdapter(adapter);  
		     
			//將字串 list 回傳的 JSON 透過解析後存到 values 陣列並傳給 listView 作清單的顯示
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	 

	
}
