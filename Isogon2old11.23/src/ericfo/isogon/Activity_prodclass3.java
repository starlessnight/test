package ericfo.isogon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import ericfo.isogon.Activity_addmember.sendPostRunnable;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_prodclass3 extends ListActivity {

	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();
	static Activity Activity_prodclass3;
	private String urlApi = "http://211.72.86.193/Isogon_M/main1/ProdDetail.aspx?";
	private String urlApi1 = "http://211.72.86.193/Isogon_M/main1/transfee.aspx?";
	int Wm=0;
	private final static int REQUESTCODE = 1;// 返回的结果码
	private ProgressDialog dialog;
	private String UrlStr;
	public static Handler mHandler;
	private Thread mThread;
	private static String array;
	private static String[] store_info = new String[3];
	private String PHPSESSID="";
	private static String __VIEWSTATE;
	private static String Sqty;
	private static String __EVENTVALIDATION;
	private String ImgListArray;
	private static String massage;
	ArrayList<String[]> alldata;
	private boolean goBuy=true;
	private int goBuy1=0;
	private static int PP_PD=0;
	private EditText qty;
	@Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
            // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTCODE){
            if(resultCode==2){
                //setTitle("Cancel****");
            }else 
                if(resultCode==1){
//              String  Name=data.getStringExtra("username");
                Bundle bundle = data.getExtras();
                //PHPSESSID = bundle.getString("PHPSESSID");
                //Log.d("prodclass", PHPSESSID);
            }
        }
    }
	//主程式的執行
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Activity_prodclass3=this;
		// setContentView(R.layout.activity_store2);
		//imageLoader.clearDiscCache();
		
		Bundle bundle = this.getIntent().getExtras();

		UrlStr = bundle.getString("UrlStr");
		//PHPSESSID = bundle.getString("PHPSESSID");
		//Log.d("class3",PHPSESSID);
		urlApi += UrlStr;
		Thread t = new Thread(new sendPostRunnable());
		t.start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		urlApi1 += UrlStr;
		Thread t1 = new Thread(new sendPostRunnable1());
		t1.start();

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
				boolean t=true;
				@Override
				public void run() {
					try {
						Thread.sleep(1000);

						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);

						while(t){
							
							Thread.sleep(1000);
								
								if(Wm>=1){
									t=false;				
								}
							}
						Button Explanation = (Button) findViewById(R.id.Explanation);
						Button Shopping_Cart = (Button) findViewById(R.id.Shopping_Cart);
						Explanation.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								showToast(massage);
							}
						});
						//取出Cookie
						
						
						Shopping_Cart.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								
								SharedPreferences MemCookie=getSharedPreferences("MSG_RESULT",MODE_WORLD_READABLE);
								PHPSESSID=MemCookie.getString("Cookie",null);
								System.out.println("666666666666"+PHPSESSID);
								Intent intent = new Intent();
								Bundle bundle = new Bundle(); 
								System.out.println("@@@22225555@@"+PHPSESSID);
								if(PHPSESSID != null)
								{
									intent.setClass(Activity_prodclass3.this , Activity_prodclass4.class );
					        		bundle.putString("UrlButtonStr",(urlApi));
					        		//bundle.putString("PHPSESSID", PHPSESSID);
					        		//bundle.putString("return","false");
					        		Thread n = new Thread(new sendPostRunnable2());
									n.start();
									intent.putExtras(bundle); 
					        		startActivity(intent);
					        		Activity_prodclass2.Activity_prodclass2.finish();
					        		Activity_prodclass3.this.finish();
									//showToast("123456789");
								}
								else
								{
					        		
					        		intent.setClass(Activity_prodclass3.this , Login_member.class );
					        		//bundle.putString("UrlButtonStr",(urlApi));
					        		//bundle.putString("PHPSESSID", PHPSESSID);
					        		//intent.putExtras(bundle);
									startActivityForResult(intent,REQUESTCODE);
								}
							}
						});
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			mThread.start();
		}
	}
	
	
	
	//載入圖片
	private void ImageLoaderOptions() {
		// ImageLoader 初始設定
		 options = new DisplayImageOptions.Builder()
     	.showStubImage(R.drawable.ic_stub)
     	.showImageForEmptyUri(R.drawable.ic_empty)
     	.showImageOnFail(R.drawable.ic_error)
			.cacheInMemory()
			.cacheOnDisc()
			.build();
		 
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(options)
				.build();
		imageLoader.init(config);
		setListAdapter(new ItemAdapter());
		// ListView 設定
		// ListView listview = (ListView) findViewById(R.id.listView1);
		// listview.setAdapter(new ItemAdapter());
		// listview.setOnItemClickListener(new OnItemClickListener() {

		/*
		 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
		 * arg2,long arg3) { //Intent intent = new Intent();
		 * //intent.setClass(Activity_salenews.this , Activity_salenews2.class
		 * ); //Bundle bundle = new Bundle(); //bundle.putInt("Guess_Num",
		 * arg2); //intent.putExtras(bundle); //startActivity(intent);
		 * //showToast(""+arg2); } });
		 */
	}
	//清除記記憶體
	@Override
	public void onBackPressed() {
		imageLoader.stop();
		AnimateFirstDisplayListener.displayedImages.clear();
		super.onBackPressed();
	}

	// ListView Adapter
	//讓圖片顯示到Layout
	class ItemAdapter extends BaseAdapter {

		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

		private class ViewHolder {
			public TextView text, text1, text2;
			public ImageView image;
		}

		@Override
		public int getCount() {
			return 1;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		//把圖片跟文字放入對應的物件
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				view = getLayoutInflater().inflate(
						R.layout.activity_prodclass3, parent, false);
				holder = new ViewHolder();
				holder.text = (TextView) view
						.findViewById(R.id.main_content_text);
				holder.text1 = (TextView) view
						.findViewById(R.id.main_content_text1);
				holder.text2 = (TextView) view
						.findViewById(R.id.main_content_text2);
				holder.image = (ImageView) view
						.findViewById(R.id.main_content_pic);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.text.setText(store_info[0]);
			holder.text1.setText(Html.fromHtml(store_info[1]));
			holder.text2.setText(store_info[2]);

			imageLoader.displayImage(ImgListArray, holder.image, options,
					animateFirstListener);

			return view;
		}
	}

	// 圖片顯示動畫
	//執行緒
	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
				} else {
					imageView.setImageBitmap(loadedImage);
				}
				displayedImages.add(imageUri);
			}
		}
	}

	//執行緒
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
	class sendPostRunnable2 implements Runnable {
		@Override
		public void run() {
			sendPostDataToInternet2();
		}
	}
	//將網頁上的資料抓回來
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
			
			if(PHPSESSID!=null){
				httpRequest.setHeader("Cookie","ASP.NET_SessionId="+PHPSESSID);
				//System.out.println("123654");
			}
			
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);

			/* 若狀態碼為200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 取出回應字串 */
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());

				delHTMLTag(strResult);
				ImgTag(strResult);
				 //System.out.println("AAA"+strResult);
				// 回傳回應字串
				return strResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//將網頁上的資料抓回來
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
			if(PHPSESSID!=null){
				httpRequest.setHeader("Cookie","ASP.NET_SessionId="+PHPSESSID);
				//System.out.println("123654");
			}
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);

			/* 若狀態碼為200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 取出回應字串 */
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());

				delHTMLTag1(strResult);
				// ImgTag(strResult);

				// 回傳回應字串
				return strResult;
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
		qty=(EditText)findViewById(R.id.td_qty);
		Sqty=qty.getText().toString();
		System.out.println("2222222222222"+Sqty);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$tb_qty",Sqty));
		params.add(new BasicNameValuePair("__EVENTTARGET","ctl00$ContentPlaceHolder1$LinkButton1"));
		params.add(new BasicNameValuePair("__EVENTARGUMENT",""));
		params.add(new BasicNameValuePair("__VIEWSTATE",__VIEWSTATE));
		params.add(new BasicNameValuePair("__EVENTVALIDATION",__EVENTVALIDATION));
		
		try {
			/* 發出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			if(PHPSESSID!=null){
				httpRequest.setHeader("Cookie","ASP.NET_SessionId="+PHPSESSID);
				//System.out.println("123654");
			}
			
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);

			/* 若狀態碼為200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 取出回應字串 */
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				//System.out.println("asdzxcqqwe"+strResult+"13644");
				//delHTMLTag1(strResult);
				// ImgTag(strResult);
				
				// 回傳回應字串
				return strResult;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//取出文字，把取回的資料清除多於的字串
	public static void delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

		
		//擷取參數
		__VIEWSTATE= htmlStr.substring(htmlStr.indexOf("<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"") + 64,
				htmlStr.indexOf("<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"")-8);
		__EVENTVALIDATION=htmlStr.substring(htmlStr.indexOf("<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"") + 76,
				htmlStr.indexOf("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; background-color: #2e0900;\" align=\"center\"><tr><td align=\"center\">")-10);
		
		System.out.println("9999999999999999999"+__VIEWSTATE);
		System.out.println("9999999999999999999"+__EVENTVALIDATION);
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
		htmlStr = htmlStr.replaceAll("&nbsp;", " ");
		//if(htmlStr.indexOf("補貨中")==-1){
		htmlStr = htmlStr.substring(htmlStr.indexOf("內容一覽") + 4,
				htmlStr.length());

		store_info[0] = htmlStr.substring(0, htmlStr.indexOf("‧原"));
		store_info[0] = store_info[0].replaceFirst("‧", "\n‧");
		store_info[1] = htmlStr.substring(htmlStr.indexOf("‧原"),
				htmlStr.indexOf("‧購買數"));

		store_info[1] = store_info[1].replaceFirst("‧特", "<br><font color='red'>‧特");
		store_info[1]=store_info[1]+"</font>";
		store_info[2] = htmlStr.substring(htmlStr.indexOf("‧產品說明"),
				htmlStr.length());
		store_info[2] = store_info[2].replaceFirst("‧產品說明", "\n‧產品說明\n");
		store_info[2] = store_info[2].replaceFirst("‧產品規格", "\n\n‧產品規格\n");
		store_info[2] = store_info[2].replaceFirst("‧其他說明", "\n\n‧其他說明\n");
		
		
	}
	//取出文字，把取回的資料清除多於的字串
	public static void delHTMLTag1(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		//擷取參數
		/*		__VIEWSTATE= htmlStr.substring(htmlStr.indexOf("<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"") + 23,
						htmlStr.indexOf("<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"")-3);
				__EVENTVALIDATION=htmlStr.substring(htmlStr.indexOf("<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"") + 29,
						htmlStr.indexOf("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; background-color: #2e0900;\" align=\"center\"><tr><td align=\"center\">")-3);
				*/

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
		htmlStr = htmlStr.replaceAll("&nbsp;", " ");

		htmlStr = htmlStr.substring(htmlStr.indexOf("回上頁") + 3,htmlStr.length());
		htmlStr=htmlStr.trim();
		htmlStr = htmlStr.replaceFirst("】","】\n");
		htmlStr = htmlStr.replaceAll("‧消費", "\n  ‧消費");
		massage = htmlStr;
		System.out.println("3333333333333"+massage);
		// showToast(htmlStr);

	}
	
	
	

	int i = 0;
	//取出圖片的網址，把取回的資料清除多於的字串
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
				Wm=ImgListArray.length();
				i++;
			}

		}
	}
	//等待資料取完成
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialog.dismiss();
				// 重新刷新Listview的adapter里面数据
				// initListView();
				ImageLoaderOptions();
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

	public void showToast(final String msg) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(Activity_prodclass3.this, msg, Toast.LENGTH_LONG)
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
