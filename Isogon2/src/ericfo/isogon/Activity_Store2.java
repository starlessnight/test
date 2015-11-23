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
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Store2 extends ListActivity {

	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();

	private String urlApi;

	private ProgressDialog dialog;
	private String UrlStr;
	public static Handler mHandler;
	private Thread mThread;
	private static String array;
	private String ImgListArray;
	ArrayList<String[]> alldata;
	private String UrlListArray;
	MydataAdapter adapter;
	GetWebImg ImgCache = new GetWebImg(this);
	//主程式的執行
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// setContentView(R.layout.activity_store2);
		imageLoader.clearDiscCache();
		
		Bundle bundle = this.getIntent().getExtras();

		UrlStr = bundle.getString("UrlStr");

		urlApi = "http://www.isogon.com.tw/Isogon_M/main/storeDetail.aspx?"
				+ UrlStr;

		Thread t = new Thread(new sendPostRunnable());
		t.start();
		
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
						Thread.sleep(1000);

						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);

						Thread.sleep(3000);

						Button storemarketplace = (Button) findViewById(R.id.StoreMarketplace);

						storemarketplace
								.setOnClickListener(new OnClickListener() {
									public void onClick(View v) {
										Intent intent = new Intent();
										intent.setClass(Activity_Store2.this,
												Activity_prodclass2.class);
										Bundle bundle = new Bundle();
										bundle.putString("SendUrl",
												UrlListArray);
										intent.putExtras(bundle);
										startActivity(intent);
									}
								});
						// ListenerButtonView();
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
				.showImageOnFail(R.drawable.ic_error).cacheInMemory()
				.cacheOnDisc().build();

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
		//將資料進getView
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				view = getLayoutInflater().inflate(R.layout.activity_store2,
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

			holder.text.setText(Html.fromHtml(array));
			//holder.text.setTextColor(0x00ff0000);
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
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);

			/* 若狀態碼為200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 取出回應字串 */
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				//將資料去做擷取
				delHTMLTag(strResult);
				ImgTag(strResult);
				UrlTag(strResult);
				// System.out.println(strResult);
				// 回傳回應字串
				return strResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//從抓取的資料中取出文字，把取出的資料清除多於的字串
	public static void delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		/*
		 * Pattern p_html = Pattern.compile(regEx_html,
		 * Pattern.CASE_INSENSITIVE); Matcher m_html = p_html.matcher(htmlStr);
		 * htmlStr = m_html.replaceAll(""); // 过滤html标签
		 */
		htmlStr = htmlStr.replaceAll("\\s*", "");
		//htmlStr = htmlStr.replaceAll("&nbsp;", "    ");
		
		

		// showToast(htmlStr);
		//擷取需要資料
		htmlStr = htmlStr.substring(htmlStr.indexOf("內容一覽") + 4,
				htmlStr.length());
		htmlStr = htmlStr.replaceFirst("店家", "<center><h4>店家");
		htmlStr = htmlStr.replaceFirst("購買本店商品","</h4 ></center><br>");
		htmlStr = htmlStr.replaceAll("購買本店商品","<br>");
		htmlStr = htmlStr.replaceAll("<b>","<pre>&nbsp;&nbsp;<b>");
		htmlStr = htmlStr.replaceAll("</b>","</b></pre>");
		htmlStr = htmlStr.replaceFirst("連絡人","<font color=#0000FF><br>連絡人</font>");
		htmlStr = htmlStr.replaceFirst("電話：","<font color=#0000FF><br><br>電話：</font>");
		htmlStr = htmlStr.replaceFirst("傳真：","<font color=#0000FF><br><br>傳真：</font>");
		htmlStr = htmlStr.replaceFirst("手機：","<font color=#0000FF><br><br>手機：</font>");
		htmlStr = htmlStr.replaceFirst("地址：","<font color=#0000FF><br><br>地址：</font>");
		htmlStr = htmlStr.replaceFirst("網站一：","<font color=#0000FF><br><br>網站一：</font>");
		htmlStr = htmlStr.replaceFirst("網站二：","<font color=#0000FF><br><br>網站二：</font>");
		htmlStr = htmlStr.replaceFirst("電子郵件：","<font color=#0000FF><br><br>電子郵件：</font>");
		System.out.println(htmlStr);
		array = htmlStr;

	}

	int i = 0;
	//從抓取的資料中取出圖片的網址，把取出的資料清除多於的字串
	public void ImgTag(String htmlStr) {
		String regEx_html = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>"; // 定义HTML标签的正则表达式

		htmlStr = htmlStr.substring(htmlStr.indexOf("內容一覽"), htmlStr.length());

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);

		// ImgListArray = new ArrayList<String>();

		while (m_html.find()) {

			String ImgStr = m_html.group(1);

			// System.out.println(ImgStr);

			if ((ImgStr.indexOf(("../images")) != 0) && i == 0) {
				ImgListArray = ImgStr;
				i++;
			}

		}
	}

	String temp = " ";
	//從抓取的資料中取出要的連接網址，把取出的資料清除多於的字串
	public void UrlTag(String htmlStr) {
		htmlStr = htmlStr.substring(htmlStr.indexOf("store81"),
				htmlStr.indexOf("購買本店商品"));

		htmlStr = htmlStr.substring(htmlStr.indexOf("?") + 1,
				htmlStr.length() - 2);

		UrlListArray = htmlStr;

	}

	//等待所需的資料讀取資料
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialog.dismiss();
				ImageLoaderOptions();
				
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
				Toast.makeText(Activity_Store2.this, msg, Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_store2, menu);
		return true;
	}

}
