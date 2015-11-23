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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;

public class Activity_prodCheckOut extends Activity implements OnClickListener {
	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();
	GridView gridview = null, gridview_CK;

	String[] a = { "1123", "2123", "2", "3" };
	String[] b = { "sadasd", "2123", "dewrwe", "3" };
	String[] c = { "1123", "2123", "2", "123", "3" };
	String[] d = { "1ssdasdasd", "asdasd", "333333", "3" };
	static int much_prod, pay_much;
	private String urlApi = "http://211.72.86.193/Isogon_M/main1/Orders.aspx";
	public static int Wm = 0, buy_Cs, mode_ch = 0, waittime;
	public static int line, line2;
	private ProgressDialog dialog;
	private String UrlStr;
	public static Handler mHandler;
	public static EditText mb_name1, mb_tell1, mb_cell1, mb_address1,
			mb_maill1, mb_else;
	private Thread mThread;
	private static String st_name, Account, SmallSub, Shipping, Bonus, total,
			AddBonus, mb_name, mb_cell, mb_tell, mb_address, mb_mail, mb_say;
	private static String[] store_info = new String[3];
	private static String[] store_all;
	private static String[] store_PDname, store_prod, prod_much_prod,
			prod_price, pay_mode, pay_value;
	private static String[] pc_store, pc_small2, quantity, pc_total;
	private String ImgListArray;
	private static String massage, __VIEWSTATE, __EVENTVALIDATION,
			prod_address;
	private String PHPSESSID = "", Orders, gender, gender2;
	ArrayList<String[]> alldata;
	private Button send_fare;
	private Button fare_cancel;
	private RadioGroup pay_rad, time_rad;
	private RadioButton pay1, pay2, pay3;

	// 主程式的執行
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fare);

		/*
		 * LinearLayout n1=(LinearLayout)findViewById(R.id.);
		 * n1.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) {
		 * 
		 * Intent intent = new Intent();
		 * intent.setClass(Activity_prodCheckOut.this ,
		 * Activity_prodchange.class ); startActivity(intent); } });
		 */

		// imageLoader.clearDiscCache();

		Bundle bundle = this.getIntent().getExtras();
		// Orders=bundle.getString("Orders");
		__VIEWSTATE = bundle.getString("__VIEWSTATE2");
		__EVENTVALIDATION = bundle.getString("__EVENTVALIDATION2");
		prod_address = bundle.getString("prod_address");
		System.out.println("asdasdasdasd" + Orders);
		SharedPreferences MemCookie = getSharedPreferences("MSG_RESULT",
				MODE_WORLD_READABLE);
		PHPSESSID = MemCookie.getString("Cookie", null);
		urlApi = prod_address;
		System.out.println("@@@@" + urlApi);
		/*
		 * Thread t = new Thread(new sendPostRunnable()); t.start();
		 */

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Wm = 0;
		// delHTMLTag(Orders);

		Thread t1 = new Thread(new sendPostRunnable2());
		t1.start();
		/*
		 * // prepare the dialog box dialog = new ProgressDialog(this);
		 * 
		 * // make the progress bar cancelable dialog.setCancelable(false);
		 * 
		 * // set a message text dialog.setMessage("Loading...");
		 * 
		 * // show it dialog.show();
		 * 
		 * // 開啓執行緒下载網頁數據 if (mThread == null || !mThread.isAlive()) { mThread =
		 * new Thread() {
		 * 
		 * @Override public void run() { try {
		 * 
		 * 
		 * Message message = new Message();
		 * 
		 * handler.sendMessage(message);
		 * 
		 * 
		 * Thread.sleep(1000);
		 * 
		 * 
		 * } catch (InterruptedException e) { e.printStackTrace(); } } };
		 * mThread.start(); }
		 */
		boolean b = true;

		while (b) {
			if (Wm == 1)
				b = false;
		}
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.pay_radiogroup);
		RadioButton[] tv = new RadioButton[pay_much];

		for (int c = 0; c < pay_much; c++) {
			tv[c] = new RadioButton(this);
			tv[c].setId(c + 1);
			tv[c].setTextSize(12);
			tv[c].setText(pay_mode[c]);
			radioGroup.addView(tv[c]);
		}

		// GridView1
		gridview = (GridView) findViewById(R.id.gridview_persion);

		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = null;

		for (int i = 0; i < line; i++) {
			map = new HashMap<String, Object>();
			map.put("pn", store_PDname[i]);
			map.put("pc", prod_much_prod[i]);
			map.put("pp", prod_price[i]);
			list.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, list,
				R.layout.activity_fare_item, new String[] { "pn", "pc", "pp" },
				new int[] { R.id.product_name, R.id.product_quantity,
						R.id.quantity });
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

		gridview.setAdapter(adapter);

		TextView store_name = (TextView) findViewById(R.id.store_name);
		store_name.setText("店家:" + st_name);
		TextView q_Style = (TextView) findViewById(R.id.q_Style);
		q_Style.setText(Account);
		TextView Sub = (TextView) findViewById(R.id.Sub);
		Sub.setText("NT" + SmallSub);
		TextView Shipping1 = (TextView) findViewById(R.id.Shipping);
		Shipping1.setText("NT" + Shipping);
		TextView Bonus1 = (TextView) findViewById(R.id.Bonus);
		Bonus1.setText(Bonus);
		TextView total1 = (TextView) findViewById(R.id.total);
		total1.setText("NT" + total);
		TextView AddBonus1 = (TextView) findViewById(R.id.AddBonus);
		AddBonus1.setText(AddBonus);

		this.mb_else = (EditText) findViewById(R.id.mb_say);
		this.mb_name1 = (EditText) findViewById(R.id.mb_name);
		this.mb_name1.setText(mb_name);
		this.mb_cell1 = (EditText) findViewById(R.id.mb_cell);
		this.mb_cell1.setText(mb_cell);
		this.mb_tell1 = (EditText) findViewById(R.id.mb_tell);
		this.mb_tell1.setText(mb_tell);
		this.mb_address1 = (EditText) findViewById(R.id.mb_address);
		this.mb_address1.setText(mb_address);
		this.mb_maill1 = (EditText) findViewById(R.id.mb_mail);
		this.mb_maill1.setText(mb_mail);
		FindView();
		ListenerButtonView();
	}

	private void FindView() {
		this.send_fare = (Button) findViewById(R.id.send_fare);
		this.fare_cancel = (Button) findViewById(R.id.fare_cancel);
		this.pay_rad = (RadioGroup) findViewById(R.id.pay_radiogroup);

		this.time_rad = (RadioGroup) findViewById(R.id.time_radiogroup);
	}

	private void ListenerButtonView() {
		this.send_fare.setOnClickListener(this);
		this.fare_cancel.setOnClickListener(this);
		this.pay_rad.setOnCheckedChangeListener(radiogrouplistener);
		this.time_rad.setOnCheckedChangeListener(radiogrouplistener2);
	}

	// 付款方式

	private RadioGroup.OnCheckedChangeListener radiogrouplistener = new RadioGroup.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			buy_Cs = checkedId - 1;
			System.out.println("checkedId=" + checkedId);
			if ((checkedId) == 1) {
				// 1
				waittime = 0;
				gender = pay_value[checkedId - 1];
				Thread t1 = new Thread(new sendPostRunnable());
				t1.start();
				boolean b = true;
				while (b) {
					if (waittime == 1)
						b = false;
				}
				TextView q_Style = (TextView) findViewById(R.id.q_Style);
				q_Style.setText(Account);
			} else if ((checkedId) == 2) {
				// 2
				waittime = 0;
				gender = pay_value[checkedId - 1];
				Thread t1 = new Thread(new sendPostRunnable());
				t1.start();
				boolean b = true;
				while (b) {
					if (waittime == 1)
						b = false;
				}
				TextView q_Style = (TextView) findViewById(R.id.q_Style);
				q_Style.setText(Account);
			} else if ((checkedId) == 3) {
				// 3
				waittime = 0;
				gender = pay_value[checkedId - 1];
				Thread t1 = new Thread(new sendPostRunnable());
				t1.start();
				boolean b = true;
				while (b) {
					if (waittime == 1)
						b = false;
				}
				TextView q_Style = (TextView) findViewById(R.id.q_Style);
				q_Style.setText(Account);
			}
			System.out.println("gender=" + gender);
		}
	};
	// 收貨時段
	private RadioGroup.OnCheckedChangeListener radiogrouplistener2 = new RadioGroup.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			System.out.println("checkedId2=" + checkedId);
			if ((checkedId) == 2131230762) {
				// 1
				gender2 = "RadioButton_a";
				// Thread t1 = new Thread(new sendPostRunnable());
				// t1.start();
			} else if ((checkedId) == 2131230763) {
				// 2
				gender2 = "RadioButton_h";
				// Thread t1 = new Thread(new sendPostRunnable());
				// t1.start();
			} else if ((checkedId) == 2131230764) {
				// 3
				gender2 = "RadioButton_f";
				// Thread t1 = new Thread(new sendPostRunnable());
				// t1.start();
			} else if ((checkedId) == 2131230765) {
				// 3
				gender2 = "RadioButton_n";
				// Thread t1 = new Thread(new sendPostRunnable());
				// t1.start();
			}
			System.out.println("gender2=" + gender2);
		}
	};

	/*
	 * private RadioGroup.OnCheckedChangeListener radiogrouplistener2 = new
	 * RadioGroup.OnCheckedChangeListener() {
	 * 
	 * @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
	 * // TODO Auto-generated method stub if (checkedId == rd1.getId()) { // 男
	 * gender = "RadioButton_M"; } else if (checkedId == rd2.getId()) { // 女
	 * gender = "RadioButton_W"; } } };
	 */

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.send_fare:
			Thread t1 = new Thread(new sendPostRunnable3());
			t1.start();
			break;
		case R.id.fare_cancel:
			intent.setClass(Activity_prodCheckOut.this,
					Activity_prodclass4.class);
			// if(!Activity_prodclass4.Activity_class4.isFinishing())
			// Activity_prodclass4.Activity_class4.finish();
			// startActivity(intent);
			Activity_prodCheckOut.this.finish();
			break;

		}

	}

	// 執行緒
	class sendPostRunnable implements Runnable {
		@Override
		public void run() {
			// delHTMLTag(Orders);
			sendPostDataToInternet1();
		}
	}

	class sendPostRunnable2 implements Runnable {
		@Override
		public void run() {
			// delHTMLTag(Orders);
			sendPostDataToInternet();
		}
	}

	class sendPostRunnable3 implements Runnable {
		Intent intent = new Intent();

		@Override
		public void run() {
			// delHTMLTag(Orders);
			String name = mb_name1.getText().toString();
			String tel = mb_tell1.getText().toString();
			String cell = mb_cell1.getText().toString();
			String address = mb_address1.getText().toString();
			String mail = mb_maill1.getText().toString();
			System.out.println("name="+name);
			System.out.println("tel="+tel);
			System.out.println("cell="+cell);
			System.out.println("address="+address);
			System.out.println("mail="+mail);
			
			if (!name.equals("") && !tel.equals("") && !cell.equals("")
					&& !address.equals("") && !mail.equals("")) { // &&
																	// !gender.equals("")
																	// &&
																	// !gender2.equals("")){*/
				if (gender != null & gender2 != null) {
					sendPostDataToInternet2();
					showToast("您已完成結帳，請至訂單頁面查看");
					intent.setClass(Activity_prodCheckOut.this,Activity_prodclass4.class);
					Activity_prodclass4.Activity_class4.finish();
					startActivity(intent);
					Activity_prodCheckOut.this.finish();
				} else
					showToast("資料請填寫、付款方式或送貨時段未選");
			}else{
				showToast("資料請填寫不足!!");
			}
		}
	}

	// 將網頁上的資料抓回來
	private String sendPostDataToInternet() {

		/* 建立HTTP Post連線 */
		HttpPost httpRequest = new HttpPost(urlApi);
		/*
		 * Post運作傳送變數必須用NameValuePair[]陣列儲存
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		try {
			/* 發出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			if (PHPSESSID != null) {
				httpRequest.setHeader("Cookie", "ASP.NET_SessionId="+ PHPSESSID);
				/* 取得HTTP response */
				HttpResponse httpResponse = new DefaultHttpClient()
				.execute(httpRequest);

				/* 若狀態碼為200 ok */
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					/* 取出回應字串 */
					String strResult = EntityUtils.toString(httpResponse
							.getEntity());
					System.out.println(strResult);
					delHTMLTag(strResult);
					// ImgTag(strResult);

					// 回傳回應字串
					return strResult;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 將網頁上的資料抓回來
	private String sendPostDataToInternet1() {

		/* 建立HTTP Post連線 */
		HttpPost httpRequest = new HttpPost(urlApi);
		/*
		 * Post運作傳送變數必須用NameValuePair[]陣列儲存
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String a = Integer.toString(buy_Cs);

		params.add(new BasicNameValuePair("__EVENTTARGET",
				"ctl00$ContentPlaceHolder1$LinkButton1"));
		params.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		params.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));
		params.add(new BasicNameValuePair("__EVENTVALIDATION",
				__EVENTVALIDATION));
		params.add(new BasicNameValuePair(
				"ctl00$ContentPlaceHolder1$RadioButtonList1", gender));
		try {
			/* 發出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			if (PHPSESSID != null) {
				httpRequest.setHeader("Cookie", "ASP.NET_SessionId="
						+ PHPSESSID);
				/* 取得HTTP response */
				HttpResponse httpResponse = new DefaultHttpClient()
						.execute(httpRequest);

				/* 若狀態碼為200 ok */
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					/* 取出回應字串 */
					String strResult = EntityUtils.toString(httpResponse
							.getEntity());
					System.out.println("sasasasasasasassasasas=" + strResult);
					delHTMLTag(strResult);
					// ImgTag(strResult);

					// 回傳回應字串
					return strResult;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String sendPostDataToInternet2() {

		/* 建立HTTP Post連線 */
		HttpPost httpRequest = new HttpPost(urlApi);
		/*
		 * Post運作傳送變數必須用NameValuePair[]陣列儲存
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("__EVENTTARGET","ctl00$ContentPlaceHolder1$Imagebtn_ok"));
		params.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		params.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));
		params.add(new BasicNameValuePair("__EVENTVALIDATION",__EVENTVALIDATION));
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txtname",mb_name1.getText().toString()));
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txtmobile", mb_tell1.getText()	.toString()));
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txttel",mb_cell1.getText().toString()));
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txtmailaddr", mb_address1.getText().toString()));
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txtMail",mb_maill1.getText().toString()));
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txt_message", mb_else.getText().toString()));
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$time",gender2));
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$RadioButtonList1", gender));
		try {
			/* 發出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			if (PHPSESSID != null) {
				httpRequest.setHeader("Cookie", "ASP.NET_SessionId="
						+ PHPSESSID);
				/* 取得HTTP response */
				HttpResponse httpResponse = new DefaultHttpClient()
						.execute(httpRequest);

				/* 若狀態碼為200 ok */
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					/* 取出回應字串 */
					String strResult = EntityUtils.toString(httpResponse
							.getEntity());
					System.out.println("send!!!!!!!!!!!!!=" + strResult);
					// delHTMLTag(strResult);
					// ImgTag(strResult);

					// 回傳回應字串
					return strResult;
				}
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
		String mc, pay, test, test2;
		int much_prod;

		__VIEWSTATE = htmlStr
				.substring(
						htmlStr.indexOf("<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"") + 64,
						htmlStr.indexOf("<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"") - 8);
		__EVENTVALIDATION = htmlStr
				.substring(
						htmlStr.indexOf("<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"") + 76,
						htmlStr.indexOf("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; background-color: #2e0900;\" align=\"center\"><tr><td align=\"center\">") - 10);
		System.out.println("__VIEWSTATE=" + __VIEWSTATE);
		System.out.println("__EVENTVALIDATION=" + __EVENTVALIDATION);

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

		htmlStr = htmlStr.substring(htmlStr.indexOf("我的購物清單結帳") + 8,
				htmlStr.length());
		System.out.println("testtttt" + htmlStr);
		pay_much = Integer.parseInt(htmlStr.substring(
				htmlStr.indexOf("Step1.付款方式@") + 11,
				htmlStr.lastIndexOf("@付款方式：")));
		System.out.println("pay_much" + pay_much);
		int z1 = htmlStr.indexOf("Step2. 產品清單") - 1;
		int z2 = htmlStr.indexOf("Step2. 產品清單");
		System.out.println("z1=" + z1 + "z2=" + z2);
		test = htmlStr.substring(htmlStr.indexOf("Step2.產品清單") - 1,
				htmlStr.indexOf("Step2.產品清單"));
		if (test == "&")
			test2 = "&";
		else
			test2 = "Step2.產品清單";

		pay = htmlStr.substring(htmlStr.lastIndexOf("@付款方式：") + 5,
				htmlStr.indexOf(test2));
		System.out.println("pay_much" + pay);
		pay_mode = new String[pay_much];
		pay_value = new String[pay_much];
		if (pay_much == 1) {
			pay_mode[0] = pay.substring(pay.indexOf("◎") + 1,
					pay.lastIndexOf("◎"));
			pay_value[0] = pay.substring(pay.indexOf("▲") + 1,
					pay.lastIndexOf("▲"));
		} else if (pay_much == 2) {
			pay_mode[0] = pay.substring(pay.indexOf("◎") + 1,
					pay.lastIndexOf("◎"));
			pay_value[0] = pay.substring(pay.indexOf("▲") + 1,
					pay.lastIndexOf("▲"));
			pay_mode[1] = pay.substring(pay.indexOf("“") + 1,
					pay.lastIndexOf("“"));
			pay_value[1] = pay.substring(pay.indexOf("『") + 1,
					pay.lastIndexOf("『"));

		} else if (pay_much == 3) {
			pay_mode[0] = pay.substring(pay.indexOf("◎") + 1,
					pay.lastIndexOf("◎"));
			pay_value[0] = pay.substring(pay.indexOf("▲") + 1,
					pay.lastIndexOf("▲"));
			pay_mode[1] = pay.substring(pay.indexOf("“") + 1,
					pay.lastIndexOf("“"));
			pay_value[1] = pay.substring(pay.indexOf("『") + 1,
					pay.lastIndexOf("『"));
			pay_mode[2] = pay.substring(pay.indexOf("《") + 1,
					pay.lastIndexOf("《"));
			pay_value[2] = pay.substring(pay.indexOf("□") + 1,
					pay.lastIndexOf("□"));
		}

		mc = htmlStr.substring(htmlStr.indexOf("Step2.產品清單") + 10,
				htmlStr.indexOf("品名數量小計"));
		// 商品數量
		much_prod = Integer.parseInt(mc);
		store_all = new String[much_prod];
		line = much_prod;
		htmlStr = htmlStr.substring(htmlStr.indexOf("店家品名數量小計") + 8,
				htmlStr.length());
		// 店家名
		st_name = htmlStr.substring(htmlStr.indexOf("結帳店家：") + 5,
				htmlStr.indexOf("Step1.付款方式"));

		// 帳戶
		test = htmlStr.substring(htmlStr.indexOf("Step2.產品清單") - 1,
				htmlStr.indexOf("Step2.產品清單"));
		System.out.println("test=" + test);
		if (test.equals("&")) {
			Account = htmlStr.substring(htmlStr.indexOf("&") + 1,
					htmlStr.lastIndexOf("&"));
			Account = Account.replaceAll("銀行", "\n銀行");
			Account = Account.replaceAll("號碼", "\n號碼");
			Account = Account.replaceAll("帳號", "\n帳號");
			Account = Account.replaceAll("戶名", "\n戶名");
			waittime = 1;
		} else {
			Account = "請選擇以何種方式結帳";
		}

		System.out.println("Account=" + Account);
		// 各商品
		mode_ch += 1;
		int k = 1;
		String p, b;
		for (int m = 0; m < much_prod; m++) {
			System.out.println("555555" + m);
			p = Integer.toString(k);
			if ((k + 1) <= much_prod)
				b = "@!" + Integer.toString(k + 1);
			else
				b = "小計NT$";
			store_all[m] = htmlStr.substring(htmlStr.indexOf(p + ".%"),
					htmlStr.indexOf(b));
			System.out.println("111111" + store_all[m]);
			k++;

		}
		k = 1;
		store_PDname = new String[much_prod];
		for (int m = 0; m < much_prod; m++) {
			b = "%";
			p = Integer.toString(k);
			store_PDname[m] = p
					+ "."
					+ store_all[m].substring(store_all[m].indexOf("%") + 1,
							store_all[m].lastIndexOf("%"));
			k++;
		}
		/*
		 * store_prod=new String[much_prod]; for(int m=0;m<much_prod;m++){
		 * System.out.println("gggg"+m);
		 * store_prod[m]=store_all[m].substring(store_all
		 * [m].indexOf("")+1,store_all[m].lastIndexOf("%")); }
		 */

		prod_much_prod = new String[much_prod];
		for (int m = 0; m < much_prod; m++) {
			System.out.println("gggg" + m);
			prod_much_prod[m] = store_all[m].substring(
					store_all[m].lastIndexOf("%") + 1,
					store_all[m].indexOf("NT$"));
		}
		k = 1;
		prod_price = new String[much_prod];
		for (int m = 0; m < much_prod; m++) {
			prod_price[m] = store_all[m].substring(store_all[m].indexOf("NT$"),
					store_all[m].length() - 1);

			k++;
		}

		// 小計
		SmallSub = htmlStr.substring(htmlStr.indexOf("小計NT$") + 5,
				htmlStr.indexOf("運費NT$"));
		// 運費
		Shipping = htmlStr.substring(htmlStr.indexOf("運費NT$") + 5,
				htmlStr.indexOf("本次可扣抵紅利點數"));
		// 紅利
		Bonus = htmlStr.substring(htmlStr.indexOf("本次可扣抵紅利點數") + 9,
				htmlStr.indexOf("合計NT$"));

		// 合計
		total = htmlStr.substring(htmlStr.indexOf("合計NT$") + 5,
				htmlStr.indexOf("#(本次新增紅利點數："));

		// 增加的紅利
		AddBonus = htmlStr.substring(htmlStr.indexOf("#") + 1,
				htmlStr.lastIndexOf("#"));
		mb_name = htmlStr.substring(htmlStr.indexOf("*姓名") + 3,
				htmlStr.lastIndexOf("先生*"));
		mb_cell = htmlStr.substring(htmlStr.indexOf("*手機") + 3,
				htmlStr.lastIndexOf("電話"));
		mb_tell = htmlStr.substring(htmlStr.indexOf("電話") + 2,
				htmlStr.lastIndexOf("*地址"));
		mb_address = htmlStr.substring(htmlStr.indexOf("*地址") + 3,
				htmlStr.lastIndexOf("*Mail"));
		mb_mail = htmlStr.substring(htmlStr.indexOf("*Mail") + 5,
				htmlStr.lastIndexOf("*收貨時段任何時段"));
		System.out.println("AddBonus" + AddBonus);
		Wm = 1;

		// htmlStr=htmlStr.trim();

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
				Toast.makeText(Activity_prodCheckOut.this, msg,
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
