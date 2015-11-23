package ericfo.isogon;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import ericfo.isogon.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import ericfo.isogon.Activity_prodclass3.sendPostRunnable2;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;

public class Activity_order_details extends Activity implements OnClickListener {
	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();
	GridView gridview = null, gridview_CK;

	String[] a = { "1123", "2123", "2", "3" };
	String[] b = { "sadasd", "2123", "dewrwe", "3" };
	String[] c = { "1123", "2123", "2", "123", "3" };
	String[] d = { "1ssdasdasd", "asdasd", "333333", "3" };
	static Activity Activity_class4;
	private Button fare;
	static int much_prod;
	private String urlApi = "http://www.isogon.com.tw/Isogon_M/main/ShoppingCart.aspx";
	private String urlApi1 = "http://www.isogon.com.tw/Isogon_M/main/ShoppingCart.aspx";
	public static int Wm = 0, CK = 0;
	public static int line, line2;
	private ProgressDialog dialog;
	private String UrlStr;
	public static Handler mHandler;
	private Thread mThread;
	private static String array, details, od_status,SmallSub,Shipping,Bonus,total,AddBonus,add_Bonus,catch_details;
	private static String[] store_info = new String[3];
	private static String[] prod_all,store_PDname;
	private static String[] store_name1, store_prod, prod_much_prod,prod_price, prod_address;
	private static String[] pc_store, pc_small2, quantity, pc_total;
	private String ImgListArray;
	private static String massage;

	private static String __EVENTVALIDATION, __EVENTVALIDATION2;
	private static String Orders;
	private String PHPSESSID = "", user_name;
	ArrayList<String[]> alldata;
	private Button btn_menu1;
	private Button btn_Order1;
	private Button btn_Out1;
	private Button go_buy;
	private static int ItemMach;

	// 主程式的執行
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_details);
		Activity_class4 = this;

		Bundle bundle = this.getIntent().getExtras();
		details = bundle.getString("details");

		SharedPreferences MemCookie = getSharedPreferences("MSG_RESULT",
				MODE_WORLD_READABLE);
		PHPSESSID = MemCookie.getString("Cookie", null);
		user_name = MemCookie.getString("user", null);
		urlApi += Utils.getIPAddress(true);
		// System.out.println("@@@@"+urlApi);
		Wm = 0;
		/*
		 * Thread t = new Thread(new sendPostRunnable()); t.start();
		 */
		delHTMLTag(details);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/*
		 * urlApi1 +=Utils.getIPAddress(true);; Thread t1 = new Thread(new
		 * sendPostRunnable()); t1.start();
		 */

		// prepare the dialog box
		dialog = new ProgressDialog(this);

		// make the progress bar cancelable
		dialog.setCancelable(false);

		// set a message text
		dialog.setMessage("Loading...");

		// show it
		dialog.show();

		// 開啓執行緒下载網頁數據
		if (mThread == null || !mThread.isAlive()) {
			mThread = new Thread() {

				@Override
				public void run() {
					try {
						Thread.sleep(1000);

						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);

						Thread.sleep(1000);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			mThread.start();
		}
		
		  boolean b = true;
		  
		  while (b) { 
			  if (Wm == 1) 
			  b = false;
		  }
		 
		
		
		//建立GridView1
				gridview = (GridView) findViewById(R.id.gridview_details);    

		        
		        ArrayList<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();  
		        HashMap<String,Object> map=null;  
		         
		       
		          
		        for(int i=0;i<line;i++)  
		        {  
		        map=new HashMap<String,Object>();
		        map.put("pn",store_PDname[i]);
		        map.put("pc",prod_much_prod[i]);
		        map.put("pp",prod_price[i]);
		        list.add(map);  
		        }
		        SimpleAdapter adapter = new SimpleAdapter(this,list,R.layout.activity_fare_item,new String[]{"pn","pc","pp"},new int[]{R.id.product_name,R.id.product_quantity,R.id.quantity});  
		        adapter.setViewBinder(new ViewBinder(){  
		  
		          public boolean setViewValue(View view, Object data,   
		                  String textRepresentation) {   
		                //判断是否为我们要处理的对象    
		                if(view instanceof ImageView && data instanceof Bitmap){   
		                  ImageView iv = (ImageView) view;   
		                  iv.setImageBitmap((Bitmap) data);   
		                  return true;   
		                }else   
		                return false;   
		          
		       
		      }});  
		        
		        
		        
		//將資料放入對應欄位        
		gridview.setAdapter(adapter);
		TextView user_name1 = (TextView) findViewById(R.id.store_name);
		user_name1.setText("會員:" + user_name);
		TextView q_Style1=(TextView)findViewById(R.id.q_Style);
		q_Style1.setText(catch_details);
        TextView Sub=(TextView)findViewById(R.id.Sub);
        Sub.setText("NT"+SmallSub);
        TextView Shipping1=(TextView)findViewById(R.id.Shipping);
        Shipping1.setText("NT"+Shipping);
        TextView Bonus1=(TextView)findViewById(R.id.Bonus);
        Bonus1.setText(Bonus);
        TextView total1=(TextView)findViewById(R.id.total);
        total1.setText("NT"+total);
        TextView AddBonus1=(TextView)findViewById(R.id.AddBonus);
        AddBonus1.setText(AddBonus);
        
		//開啓對應物件
		FindView();
		
		ListenerButtonView();

	}
	//對應Layout的物件
	private void FindView() {
		this.btn_menu1 = (Button) findViewById(R.id.btn_menu);
		this.btn_Order1 = (Button) findViewById(R.id.btn_Order);
		this.btn_Out1 = (Button) findViewById(R.id.btn_Out);
		
	}
	//按鈕監聽初始化
	private void ListenerButtonView() {
		this.btn_Order1.setOnClickListener(this);
		this.btn_menu1.setOnClickListener(this);
		this.btn_Out1.setOnClickListener(this);
		
	}
	//按鈕觸發設定
	@Override
	public void onClick(View v) {

		Bundle bundle = new Bundle();
		Intent intent = new Intent();
		// MainActivity.this.finish();

		switch (v.getId()) {
		case R.id.btn_Order:
			intent.setClass(Activity_order_details.this, Activity_Order.class);
			startActivity(intent);
			Activity_order_details.this.finish();
			
			break;
		case R.id.btn_menu:
			intent.setClass(Activity_order_details.this, Activity_prodclass4.class);
			startActivity(intent);
			 Activity_order_details.this.finish();
			break;
		case R.id.btn_Out:
			intent.setClass(Activity_order_details.this, MainActivity.class);
			if (!MainActivity.Activity_Main.isFinishing())
				MainActivity.Activity_Main.finish();
			startActivity(intent);
			Activity_order_details.this.finish();
			break;
		

		}
	}

	// 執行緒
	class sendPostRunnable implements Runnable {
		@Override
		public void run() {
			sendPostDataToInternet();
		}
	}

	class sendPostRunnable1 implements Runnable {
		@Override
		public void run() {
			sendPostDataToInternet1();

		}
	}

	// 將網頁上的資料抓回來
	private String sendPostDataToInternet() {

		/* 建立HTTP Post連線 */
		HttpPost httpRequest = new HttpPost(urlApi1);
		/*
		 * Post運作傳送變數必須用NameValuePair[]陣列儲存
		 */
		String a = Integer.toString(ItemMach);
		System.out.println("ItemMach=" + a);
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		try {
			/* 發出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			if (PHPSESSID != null) {
				httpRequest.setHeader("Cookie", "ASP.NET_SessionId="
						+ PHPSESSID);
				// System.out.println("123654");
			}
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);

			/* 若狀態碼為200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 取出回應字串 */
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				System.out.println("asdadad" + strResult);
				delHTMLTag(strResult);
				// ImgTag(strResult);

				// 回傳回應字串
				return strResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 將網頁上的資料抓回來
	private String sendPostDataToInternet1() {

		/* 建立HTTP Post連線 */
		HttpPost httpRequest = new HttpPost(urlApi1);
		/*
		 * Post運作傳送變數必須用NameValuePair[]陣列儲存
		 */
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		try {
			/* 發出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			if (PHPSESSID != null) {
				httpRequest.setHeader("Cookie", "ASP.NET_SessionId="
						+ PHPSESSID);
				// System.out.println("123654");
			}
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);

			/* 若狀態碼為200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 取出回應字串 */
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				System.out.println("555555555" + strResult + "qweqwe");

				//delHTMLTag(strResult);
				// ImgTag(strResult);

				// 回傳回應字串
				return strResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 取出文字，把取回的資料清除多於的字串
	public static void delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		String mc;
		int much_prod;

		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		htmlStr = htmlStr.replaceAll("\\s*", "");
		htmlStr = htmlStr.replaceAll("&nbsp;", "");
		//取出需要的內容
		htmlStr =htmlStr.substring(htmlStr.indexOf("內容一覽")+4 , htmlStr.length());
		System.out.println("All====="+htmlStr);
		catch_details=htmlStr.substring(htmlStr.indexOf("‧訂單基本資料") , htmlStr.lastIndexOf("店家品名數量小計")-1);
		System.out.println("od_status1====="+od_status);
		System.out.println("catch_details====="+catch_details);
		catch_details= catch_details.replaceAll("訂單編號：", "\n訂單編號：");
		catch_details= catch_details.replaceAll("訂單日期：", "\n訂單日期：");
		catch_details= catch_details.replaceAll("付款方式：", "\n付款方式：");
		catch_details= catch_details.replaceAll("訂購人姓名：", "\n訂購人姓名：");
		catch_details= catch_details.replaceAll("收貨人姓名：", "\n收貨人姓名：");
		catch_details= catch_details.replaceAll("收貨人電話：", "\n收貨人電話：");
		catch_details= catch_details.replaceAll("收貨人地址：", "\n收貨人地址：");
		catch_details= catch_details.replaceAll("備註事項：", "\n備註事項：");
		catch_details= catch_details.replaceAll("◆訂單狀態：", "\n◆訂單狀態：");
		
		//System.out.println("od_status1====="+od_status);
		//各商品
		mc = htmlStr.substring(htmlStr.lastIndexOf("店家品名數量小計") -1, htmlStr.indexOf("店家品名數量小計") );
		
		much_prod=Integer.parseInt(mc);
		if (much_prod != 0) {
			line=much_prod;
			htmlStr =htmlStr.substring(htmlStr.indexOf("店家品名數量小計") + 8, htmlStr.length());
		int k=1;
		prod_all=new String[much_prod];
		 String p,b;
		for(int m=0;m<much_prod;m++){
			System.out.println("555555"+m);
			p= Integer.toString(k);
			if((k+1)<=much_prod)
			b= "@!"+Integer.toString(k+1);
			else
			b="小計NT$";	
			prod_all[m]=htmlStr.substring(htmlStr.indexOf(p+".%"),htmlStr.indexOf(b));
			System.out.println("111111"+prod_all[m]);
			k++;
			
		}
		k=1;
		store_PDname=new String[much_prod];
		for(int m=0;m<much_prod;m++){
			b="%";
			p= Integer.toString(k);
			store_PDname[m]=p+"."+prod_all[m].substring(prod_all[m].indexOf("%")+1,prod_all[m].lastIndexOf("%"));
			k++;
			System.out.println("222222"+store_PDname[m]);
		}
		prod_much_prod=new String[much_prod];
		for(int m=0;m<much_prod;m++){
			System.out.println("gggg"+m);
			prod_much_prod[m]=prod_all[m].substring(prod_all[m].lastIndexOf("%")+1,prod_all[m].indexOf("NT$"));
			System.out.println("333333"+prod_much_prod[m]);
		}
		
		prod_price=new String[much_prod];
		for(int m=0;m<much_prod;m++){
			prod_price[m]=prod_all[m].substring(prod_all[m].indexOf("NT$"),prod_all[m].length()-1);
			System.out.println("444444"+prod_price[m]);
			
		}
		//小計
		SmallSub=htmlStr.substring(htmlStr.indexOf("小計NT$") + 5,
				htmlStr.indexOf("運費NT$"));
		//運費
		Shipping=htmlStr.substring(htmlStr.indexOf("運費NT$") + 5,
				htmlStr.indexOf("本次可扣抵紅利點數"));
		//紅利
		Bonus=htmlStr.substring(htmlStr.indexOf("本次可扣抵紅利點數") +9,
				htmlStr.indexOf("合計NT$"));
		
		//合計
		total=htmlStr.substring(htmlStr.indexOf("合計NT$") + 5,
				htmlStr.indexOf("(本次新增紅利點數："));
		add_Bonus=htmlStr.substring(htmlStr.indexOf("(本次新增紅利點數：") ,
				htmlStr.length());
			Wm = 1;
		 } else 
			 Wm = 1;
		 
	}


	// 等待資料取完成
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialog.dismiss();

				break;
			default:

				break;
			}
		}

	};

	public static String getMacAddress(Context context) {
		WifiManager wifiMan = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInf = wifiMan.getConnectionInfo();
		return wifiInf.getMacAddress();
	}

	public void showToast(final String msg) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(Activity_order_details.this, msg,
						Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_prodclass3, menu);
		return true;
	}

}
