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
import ericfo.isogon.Activity_prodclass4.sendPostRunnable1;

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

public class Activity_Order extends Activity implements OnClickListener {
	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();
	GridView gridview = null, gridview_CK;

	String[] a = { "1123", "2123", "2", "3" };
	String[] b = { "sadasd", "2123", "dewrwe", "3" };
	String[] c = { "1123", "2123", "2", "123", "3" };
	String[] d = { "1ssdasdasd", "asdasd", "333333", "3" };
	private Button fare;
	static int much_prod;
	private String urlApi = "http://www.isogon.com.tw/Isogon_M/Customer/OrderList.aspx";
	private String urlApi1 = "http://www.isogon.com.tw/Isogon_M/Customer/OrderList.aspx";
	public static int Wm = 0, CK = 0;
	public static int line, line2;
	private ProgressDialog dialog;
	private String UrlStr;
	public static Handler mHandler;
	private Thread mThread;
	private static String array,details;
	private static String[] store_info = new String[3];
	private static String[] store_all;
	private static String[] buyday, pd_info, pay_pdprice, prod_price;
	private static String[] pc_store, pc_small2, quantity, pc_total;
	private String ImgListArray;
	private static String massage;
	private static String __VIEWSTATE, __VIEWSTATE2;
	private static String __EVENTVALIDATION, __EVENTVALIDATION2;
	private static String Orders;
	private String PHPSESSID = "";
	ArrayList<String[]> alldata;
	private static int ItemMach;
	private Button btn_menu1;
	private Button btn_Bonus1;
	private Button btn_Out1;

	// 主程式的執行
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);

		
		SharedPreferences MemCookie = getSharedPreferences("MSG_RESULT",
				MODE_WORLD_READABLE);
		PHPSESSID = MemCookie.getString("Cookie", null);
		Thread t = new Thread(new sendPostRunnable());
		t.start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		

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
		Wm = 0;
		Thread t1 = new Thread(new sendPostRunnable());
		t1.start();
		boolean b = true;
		
		while (b) {
			if (Wm == 1)
				b = false;
		}

		// 建立GridView1
		gridview = (GridView) findViewById(R.id.gridview_order);

		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = null;

		for (int i = 0; i < line; i++) {
			map = new HashMap<String, Object>();
			map.put("pn", buyday[i]);
			map.put("pc", pd_info[i]);
			map.put("pp", pay_pdprice[i]);
			list.add(map);
		}
		//將資料放入對應位子
		SimpleAdapter adapter = new SimpleAdapter(this, list,R.layout.activity_order_item,new String[] { "pn", "pc", "pp" }, new int[] { R.id.Od_day,R.id.product_info, R.id.pd_price });
		adapter.setViewBinder(new ViewBinder() {

			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				// 判断是否为我们要处理的对象
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView iv = (ImageView) view;
					iv.setImageBitmap((Bitmap) data);
					return true;
				} else
					return false;
			}

		});
		//gridView的按鈕觸發設定
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				CK = 0;
				ItemMach = arg2 + 1;
				Thread n = new Thread(new sendPostRunnable2());
				n.start();
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(Activity_Order.this,Activity_order_details.class);
				boolean b = true;
				
				while (b) {
					if (CK == 1)
						b = false;
				}
				
				bundle.putString("details",details);
				intent.putExtras(bundle);
				 startActivity(intent);
				 //Activity_Order.this.finish();
				// showToast(""+(arg2+ index * VIEW_COUNT));
			}
		});
		gridview.setAdapter(adapter);

		

		FindView();
		ListenerButtonView();
	}
	//對應Layout物件
	private void FindView() {
		this.btn_menu1 = (Button) findViewById(R.id.btn_menu);
		this.btn_Bonus1 = (Button) findViewById(R.id.btn_Bonus);
		this.btn_Out1 = (Button) findViewById(R.id.btn_Out);
	}
	//按鈕監聽初始化
	private void ListenerButtonView() {
		this.btn_menu1.setOnClickListener(this);
		this.btn_Bonus1.setOnClickListener(this);
		this.btn_Out1.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {

		Bundle bundle = new Bundle();
		Intent intent = new Intent();
		// MainActivity.this.finish();

		switch (v.getId()) {
		case R.id.btn_menu:
			intent.setClass(Activity_Order.this, Activity_prodclass4.class);
			if (!Activity_prodclass4.Activity_class4.isFinishing())
				Activity_prodclass4.Activity_class4.finish();
			startActivity(intent);
			Activity_Order.this.finish();
			break;
		case R.id.btn_Bonus:
			intent.setClass(Activity_Order.this, Activity_Bonus.class);
			intent.putExtras(bundle);
			startActivity(intent);
			Activity_Order.this.finish();
			break;
		case R.id.btn_Out:

			intent.setClass(Activity_Order.this, MainActivity.class);
			if (!MainActivity.Activity_Main.isFinishing())
				MainActivity.Activity_Main.finish();
			startActivity(intent);
			Activity_Order.this.finish();
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

	

	class sendPostRunnable2 implements Runnable {
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

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		try {
			/* 發出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			if (PHPSESSID != null) {
				httpRequest.setHeader("Cookie", "ASP.NET_SessionId="+ PHPSESSID);
			}
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);

			/* 若狀態碼為200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 取出回應字串 */
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				System.out.println("525252525252" + strResult);
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
		HttpPost httpRequest = new HttpPost(
				"http://www.isogon.com.tw/Isogon_M/Customer/OrderList.aspx");
		/*
		 * Post運作傳送變數必須用NameValuePair[]陣列儲存
		 */

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String ItemMach2;
		if(ItemMach<10){
			ItemMach2=String.valueOf(ItemMach);
			ItemMach2="0"+ItemMach2;
		}else
			ItemMach2=String.valueOf(ItemMach);
		
		params.add(new BasicNameValuePair("__EVENTTARGET","ctl00$ContentPlaceHolder1$DataList_Order$ctl" + ItemMach2+ "$LinkButton_deatle"));
		params.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		params.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));
		params.add(new BasicNameValuePair("__EVENTVALIDATION",
				__EVENTVALIDATION));

		try {
			/* 發出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			if (PHPSESSID != null) {
				httpRequest.setHeader("Cookie", "ASP.NET_SessionId="
						+ PHPSESSID);
			}
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);

			/* 若狀態碼為200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 取出回應字串 */
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				System.out.println("zxczxczxczxc" + strResult);
				delHTMLTag1(strResult);
				// 回傳回應字串
				return strResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 將網頁上的資料抓回來
		private String sendPostDataToInternet2() {

			/* 建立HTTP Post連線 */
			HttpPost httpRequest = new HttpPost(urlApi1);
			/*
			 * Post運作傳送變數必須用NameValuePair[]陣列儲存
			 */
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("__EVENTTARGET","ctl00$ContentPlaceHolder1$DataList_Order$ctl02$DataList_ProdName$ctl00$LinkButton_prodName"));
			params.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
			params.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));
			params.add(new BasicNameValuePair("__EVENTVALIDATION",
					__EVENTVALIDATION));
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
					System.out.println("414141414141" + strResult + "zzxxcc");

					// delHTMLTag1(strResult);
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
		String mc, n;
		int much_prod;

		// 擷取參數
		__VIEWSTATE = htmlStr.substring(htmlStr.indexOf("<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"") + 64,
						htmlStr.indexOf("<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"") - 8);
		__EVENTVALIDATION = htmlStr.substring(htmlStr.indexOf("<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"") + 76,
						htmlStr.indexOf("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; background-color: #2e0900;\" align=\"center\"><tr><td align=\"center\">") - 10);
		System.out.println("all_code" + htmlStr);
		System.out.println("888888888888" + __VIEWSTATE);
		System.out.println("888888888888" + __EVENTVALIDATION);
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
		//取出需要的字串
		System.out.println(htmlStr);
		mc = htmlStr.substring(htmlStr.indexOf("請按產品內容進入查看明細") + 12,
				htmlStr.lastIndexOf("訂單日期產品內容總價查看") );
		System.out.println("mc=" + mc);
		much_prod = Integer.parseInt(mc);
		if (much_prod != 0) {
			mc = htmlStr.substring(htmlStr.indexOf("請按產品內容進入查看明細") + 12,
					htmlStr.lastIndexOf("訂單日期產品內容總價查看") );
			// 訂單數量
			much_prod = Integer.parseInt(mc);
			store_all = new String[much_prod];

			line = much_prod;
			htmlStr = htmlStr.substring(htmlStr.indexOf("訂單日期產品內容總價") + 10,
					htmlStr.length());

			int k = 1;
			String p, b;
			for (int m = 0; m < much_prod; m++) {
				System.out.println("555555" + m);
				p = Integer.toString(k);
				if ((k + 1) <= much_prod) {
					b = "%" + Integer.toString(k + 1) + ".";
					System.out.println("bbbbbbbbbb=" + b);
					store_all[m] = htmlStr.substring(htmlStr.indexOf(p + "."),
							htmlStr.lastIndexOf(b)+1);
				} else
					store_all[m] = htmlStr.substring(htmlStr.indexOf(p + "."),
							htmlStr.length() );
				

				System.out.println("111111" + store_all[m]);
				k++;

			}

			k = 1;
			buyday = new String[much_prod];
			for (int m = 0; m < much_prod; m++) {
				b = "!";
				p = Integer.toString(k);
				buyday[m] = store_all[m].substring(store_all[m].indexOf(b) + 1,	store_all[m].lastIndexOf(b));
				k++;
			}
			pd_info = new String[much_prod];
			for (int m = 0; m < much_prod; m++) {

				pd_info[m] = store_all[m].substring(store_all[m].indexOf("@") + 1,store_all[m].lastIndexOf("@"));
				System.out.println("gggg" + pd_info[m]);
			}

			pay_pdprice = new String[much_prod];
			for (int m = 0; m < much_prod; m++) {
				pay_pdprice[m] = store_all[m].substring(store_all[m].indexOf("%") + 1, store_all[m].lastIndexOf("%"));
				System.out.println("ccccc" + pay_pdprice[m]);

			}
			Wm = 1;
		} else
			Wm = 1;
	}

	public static void delHTMLTag1(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		String mc, n;
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
		details=htmlStr;
		System.out.println("查看:" + htmlStr);
		CK=1;
	}

	int i = 0;

	// 取出圖片的網址，把取回的資料清除多於的字串
	public void ImgTag(String htmlStr) {
		String regEx_html = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>"; // 定义HTML标签的正则表达式

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);

		// ImgListArray = new ArrayList<String>();

		while (m_html.find()) {

			String ImgStr = m_html.group(1);

			System.out.println(ImgStr);

			if ((ImgStr.indexOf(("../images")) != 0) && i == 0) {
				// ImgStr = ImgStr.replace("../",
				// "http://211.72.86.193/Handmade/");
				// ImgListArray.add(ImgStr);
				ImgListArray = ImgStr;
				i++;
			}

		}
	}

	// 等待資料取完成
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialog.dismiss();
				// 重新刷新Listview的adapter里面数据
				// initListView();
				// ImageLoaderOptions();
				// BitmapHelper3 adapter = new
				// BitmapHelper3(Activity_prodclass3.this , alldata , store ,
				// ImgCache , R.layout.activity_prodclass3 , 1);
				// setListAdapter(adapter);
				// Activity_salenews.this.setListAdapter(listItemAdapter)
				break;
			default:
				// listItemAdapter.notifyDataSetChanged();

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
				Toast.makeText(Activity_Order.this, msg, Toast.LENGTH_LONG)
						.show();
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
