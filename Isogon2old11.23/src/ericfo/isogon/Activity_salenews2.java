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

	private String urlApi = "http://211.72.86.193/Isogon_M/main/saleNews.aspx";
	public static Handler mHandler;
	private Thread mThread;
	private static String array;
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
	//將網頁上的資料抓回來
	private String sendPostDataToInternet() {

		/* 建立HTTP Post連線 */
		HttpPost httpRequest = new HttpPost(urlApi);
		/*
		 * Post運作傳送變數必須用NameValuePair[]陣列儲存
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("__EVENTTARGET",
				"ctl00$ContentPlaceHolder1$DataList_Newss$ctl0" + guess
						+ "$linkbtn_title"));
		params.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		params.add(new BasicNameValuePair(
				"__VIEWSTATE",
				"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"));
		params.add(new BasicNameValuePair(
				"__EVENTVALIDATION",
				"/wEWFQKh2/KdDgLo266WBwKM+4PyAwLo26r5DgKM+4fRCwLp29a8BAKX+/uIAQLl29IjAov7/+8MAubbvpkIAo778/UEAubbuvwPAo7799QMAufbptAFAon766sCAuPborsBAo3775oOAuTbjo4JApD7o/oFAuTbinECkPun2Q1x6xmqnL3FujTeO0czfIzj1+2Gjg=="));

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

		// array.indexOf("\\d+/\\d+/\\d+");
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
