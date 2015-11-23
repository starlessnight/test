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

import com.gotomovie.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class movie_ch extends Activity{
	DisplayImageOptions options;
	private GridView gridview;
	private int[] a={R.drawable.w,R.drawable.xman,R.drawable.god,R.drawable.tokoy,R.drawable.buler};
	
	private String[] c={"1b","2b","3b"};
	private String[] Mv_name,Sh_time,Mv_about,th_name,address,mv_time;
	private double[] lat,lng;
	private String url="http://120.119.76.178/lbs/team7/",url1,url2,url3;
	private Intent intent=new Intent();
	private Bundle b=new Bundle();
	
	private static String[] array = {};
	int index = 0;
	private int Wm=0,wh_Mv,which;
	int VIEW_COUNT = 6;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_th);
		setTitle("上映電影");
		
		start_gridView();
		Mv_find fl = new Mv_find();
		fl.start();
		
		
	}
	
	public void start_gridView(){
		
		gridview = (GridView) findViewById(R.id.gridview);    

        
      
	
	}
	
	// 取得好友清單
			class Mv_find extends Thread {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					try {
						HttpClient client = new DefaultHttpClient();
						url1=url+"get_Mv.php";
						HttpGet get = new HttpGet(url1);
						HttpResponse response = client.execute(get);
						HttpEntity resEntity = response.getEntity();
						String result = EntityUtils.toString(resEntity);
						Log.d("result", result);
						
						Bundle countBundle = new Bundle();
						countBundle.putString("Mv", result);
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
					setList(msg.getData().getString("Mv"));
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
					Mv_name = new String[json.length()];
					Sh_time	= new String[json.length()];
					Mv_about=new String[json.length()];
					Wm=json.length();
					for (int i = 0; i < json.length(); i++) {
						JSONObject jsonData = new JSONObject(json.get(i).toString());
						Sh_time[i] = "上映日期:"+jsonData.getString("ShTime");
						Mv_name[i] = jsonData.getString("MvName");
						Mv_about[i]=jsonData.getString("about");
						System.out.println(Mv_name[i]);
						System.out.println(Sh_time[i]);
						
					}
					
					  ArrayList<HashMap<String,Object>> list2=new ArrayList<HashMap<String,Object>>();  
				        HashMap<String,Object> map=null;  
				        
				       
				        //將資料從字串取出  
				         for(int i=0;i<Wm;i++)  
					        {  
				        	 
					        map=new HashMap<String,Object>();
					        map.put("store",a[i]);
					        map.put("pn",Mv_name[i]);
					        map.put("pc",Sh_time[i]);
					        map.put("Mb", Mv_about[i]);
					        list2.add(map);  
					        }
				        
				        //將資料放入對應的位子
				        SimpleAdapter adapter = new SimpleAdapter(this,list2,R.layout.movie_ch_item,new String[]{"store","pn","pc","Mb"},new int[]{R.id.Mv_image,R.id.MV_name,R.id.Mv_who,R.id.Mv_about});  
				        gridview.setNumColumns(1);
				        gridview.setAdapter(adapter);
				        gridview.setOnItemClickListener(new OnItemClickListener() {

				        	@Override
				        	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				        		
								wh_Mv=arg2+1;
								th_find f2 = new th_find();
								time_find f3 = new time_find();
								f2.start();
								f3.start();
								
								
				        	}
				        });
				        
					 gridview.setAdapter(adapter);  
				     
					//將字串 list 回傳的 JSON 透過解析後存到 values 陣列並傳給 listView 作清單的顯示
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			// 取得電影院
						class th_find extends Thread {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								super.run();
								try {
									HttpClient client = new DefaultHttpClient();
									url3=url+"find_th.php?mid="+wh_Mv;
									//System.out.println("@@url@@"+url3);
									HttpGet get = new HttpGet(url3);
									HttpResponse response = client.execute(get);
									HttpEntity resEntity = response.getEntity();
									String result = EntityUtils.toString(resEntity);
									Log.d("result", result);
									
									Bundle countBundle = new Bundle();
									countBundle.putString("th", result);
									//打包朋友清單相關資料，並命名為 list
									Message msg = new Message();
									msg.setData(countBundle);
									mHandler2.sendMessage(msg);
									//將打包資料方置放在 Message 物件當中，並傳送給 handler
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						
						private Handler mHandler2 = new Handler() {
							public void handleMessage(Message msg) {
								super.handleMessage(msg);
								setList2(msg.getData().getString("th"));
								/*
								         透過 handler 來請 main thread 處理 setList
								    sub thread 不能值接對 UI 作控制，必須透過 handler 來請主程序作
								*/
							}
						};
						
						public void setList2(String list) {
							JSONArray json;
							try {
								json = new JSONArray(list);
								th_name = new String[json.length()];
								address = new String[json.length()];
								lat=new double[json.length()];
								lng=new double[json.length()];
								for (int i = 0; i < json.length(); i++) {
									JSONObject jsonData = new JSONObject(json.get(i).toString());
									th_name[i] = jsonData.getString("ThName");
									lat[i] = Double.parseDouble(jsonData.getString("lat"));
									lng[i] = Double.parseDouble(jsonData.getString("lng"));
									address[i]=jsonData.getString("address");
									
									//System.out.println("@@th@@"+th_name[i]);
								
								}
								
								b.putStringArray("th_name", th_name);
								b.putDoubleArray("lat",lat);
								b.putDoubleArray("lng", lng);
								b.putStringArray("address", address);
								b.putString("wh", Mv_name[wh_Mv-1]);
								intent.putExtras(b);

								
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						// 取得電影院
						class time_find extends Thread {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								super.run();
								try {
									HttpClient client = new DefaultHttpClient();
									url2=url+"find_time.php?mid="+wh_Mv;
									System.out.println("@@url@@"+url2);
									HttpGet get = new HttpGet(url2);
									HttpResponse response = client.execute(get);
									HttpEntity resEntity = response.getEntity();
									String result = EntityUtils.toString(resEntity);
									Log.d("result", result);
									
									Bundle countBundle = new Bundle();
									countBundle.putString("time", result);
									//打包朋友清單相關資料，並命名為 list
									Message msg = new Message();
									msg.setData(countBundle);
									mHandler3.sendMessage(msg);
									//將打包資料方置放在 Message 物件當中，並傳送給 handler
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						
						private Handler mHandler3 = new Handler() {
							public void handleMessage(Message msg) {
								super.handleMessage(msg);
								setList3(msg.getData().getString("time"));
								/*
								         透過 handler 來請 main thread 處理 setList
								    sub thread 不能值接對 UI 作控制，必須透過 handler 來請主程序作
								*/
							}
						};
						
						public void setList3(String list) {
							JSONArray json;
							try {
								json = new JSONArray(list);
								mv_time = new String[json.length()];
								for (int i = 0; i < json.length(); i++) {
									JSONObject jsonData = new JSONObject(json.get(i).toString());
									mv_time[i] = jsonData.getString("session");
									System.out.println("@@time@@"+mv_time[i]);
								
								}
								
								intent.setClass(movie_ch.this,Movie_th2.class);
								b.putStringArray("mv_time", mv_time);
								intent.putExtras(b);
								startActivity(intent);
								
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
