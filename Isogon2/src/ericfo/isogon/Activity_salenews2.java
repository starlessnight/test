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

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_salenews2 extends ListActivity {

	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();

	private String urlApi = "http://www.isogon.com.tw/Isogon_M/main/saleNews.aspx";
	public static Handler mHandler;
	private static int Wm=0,SM=0;
	private Thread mThread;
	private static String array,__VIEWSTATE,__EVENTVALIDATION;
	private String ImgListArray;
	private ProgressDialog dialog;
	ArrayList<String[]> alldata;
	GetWebImg ImgCache = new GetWebImg(this);
	private int guess;
	MydataAdapter adapter;
	//主程式的執行
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = this.getIntent().getExtras();
		guess = bundle.getInt("Guess_Num");
		//啓動執行序(擷取頁面資料
		Thread t = new Thread(new sendPostRunnable2());
		t.start();
		boolean b=true;
		//下面為一個鎖(為了等待資料擷取完成
		while(b){
			if 	(Wm==1)
				b=false;
		}
		SM=0;
		//
		Thread t2 = new Thread(new sendPostRunnable());
		t2.start();

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
						// init();
						
						boolean b=true;
						
						while(b){
							if 	(SM==1)
								b=false;
							Thread.sleep(1000);
						}
						

						// ListenerButtonView();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);

				}
			};

			mThread.start();
		}
	}
	//載入圖片
	private void ImageLoaderOptions() {
		// ImageLoader 初始設定
		//圖片的預設質
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

	}
	//清除記憶體
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
			public TextView text;
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
				view = getLayoutInflater().inflate(R.layout.activity_salenews2,
						parent, false);
				holder = new ViewHolder();
				holder.text = (TextView) view
						.findViewById(R.id.main_content_text);
				holder.image = (ImageView) view
						.findViewById(R.id.main_content_pic);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.text.setText(array);

			imageLoader.displayImage(ImgListArray, holder.image, options,
					animateFirstListener);

			return view;
		}
	}

	// 圖片顯示動畫
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

		params.add(new BasicNameValuePair("__EVENTTARGET","ctl00$ContentPlaceHolder1$DataList_Newss$ctl0" + guess+ "$linkbtn_title"));
		params.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		params.add(new BasicNameValuePair("__VIEWSTATE",__VIEWSTATE));
		params.add(new BasicNameValuePair("__EVENTVALIDATION",__EVENTVALIDATION));

		try {
			/* 發出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
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

				// 回傳回應字串
				return strResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//接收網頁資料
	private String sendPostDataToInternet2() {

		/* 建立HTTP Post連線 */
		HttpPost httpRequest = new HttpPost("http://www.isogon.com.tw/Isogon_M/main/saleNews.aspx");
		/*
		 * Post運作傳送變數必須用NameValuePair[]陣列儲存
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		try {
			/* 發出HTTP request */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);

			/* 若狀態碼為200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 取出回應字串 */
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());

				delHTMLTag1(strResult);
				ImgTag(strResult);

				// 回傳回應字串
				return strResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//取得文字，把取回的資料清除多於的字串
	public static void delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

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

		// showToast(htmlStr);

		htmlStr = htmlStr.substring(htmlStr.indexOf("內容一覽") + 4,
				htmlStr.indexOf(">>"));
		// System.out.println(htmlStr);
		array = htmlStr;
		// array = htmlStr.split("瀏覽數：\\d+");

		array = array.replaceFirst("】", "】\n\n \t");
		array = array.replaceFirst("\\d+/\\d+/\\d+", "\n\n \t");
		array = array + "\n";
		SM=1;
		// array.indexOf("\\d+/\\d+/\\d+");
	}
	public static void delHTMLTag1(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

		//擷取參數
		__VIEWSTATE= htmlStr.substring(htmlStr.indexOf("<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"") + 64,
				htmlStr.indexOf("<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"")-8);
		__EVENTVALIDATION=htmlStr.substring(htmlStr.indexOf("<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"") + 76,
				htmlStr.indexOf("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; background-color: #2e0900;\" align=\"center\"><tr><td align=\"center\">")-10);
		Wm=1;
	}
	//取得圖片網址，把取回的資料清除多於的字串
	public void ImgTag(String htmlStr) {
		String regEx_html = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>"; // 定义HTML标签的正则表达式

		htmlStr = htmlStr.substring(htmlStr.indexOf("內容一覽"), htmlStr.length());

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);

		while (m_html.find()) {

			String ImgStr = m_html.group(1);

			// System.out.println(ImgStr);

			if ((ImgStr.indexOf(("../images")) != 0)) {
				ImgListArray = ImgStr;
			}

		}
	}

	//等待所需資料讀取完成
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialog.dismiss();
				// 重新刷新Listview的adapter里面数据
				ImageLoaderOptions();

				break;
			default:
				break;
			}
		}

	};
	//顯示訊息
	public void showToast(final String msg) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(Activity_salenews2.this, msg, Toast.LENGTH_LONG)
						.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_salenews2, menu);
		return true;
	}

}
