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
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;

public class Activity_salenews extends Activity{

	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();

	private Context mContext;
	private static String[] array = {};

	protected static final int REFRESH_DATA = 0x00000001, updata = 2;

	private String urlApi = "http://211.72.86.193/Isogon_M/main/saleNews.aspx";
	public static Handler mHandler;

	ListView listView;
	LinearLayout loadingLayout;
	private Thread mThread;
	private ProgressDialog dialog;

	List<String> ImgListArray;

	private ProgressBar progressBar;
	//主程式的執行
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		this.mContext = getApplicationContext();

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_salenews);

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

		// 開啓執行緒去下载網路的數據
		if (mThread == null || !mThread.isAlive()) {
			mThread = new Thread() {
				@Override
				public void run() {
					try {

						Thread.sleep(5000);

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
		
		// ListView 設定
		ListView listview = (ListView) findViewById(R.id.listView1);
		listview.setAdapter(new ItemAdapter());
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				Intent intent = new Intent();
        		intent.setClass(Activity_salenews.this , Activity_salenews2.class );
        		Bundle bundle = new Bundle(); 
        		bundle.putInt("Guess_Num", arg2); 
        		intent.putExtras(bundle); 
        		startActivity(intent);
				//showToast(""+arg2);
			}
		});
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
				return array.length;
			}

			@Override
			public Object getItem(int position) {
				return position;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				View view = convertView;
				final ViewHolder holder;
				if (convertView == null) {
					view = getLayoutInflater().inflate(R.layout.activity_salenews_item, parent, false);
					holder = new ViewHolder();
					holder.text = (TextView) view.findViewById(R.id.text);
					holder.image = (ImageView) view.findViewById(R.id.image);
					view.setTag(holder);
				} else {
					holder = (ViewHolder) view.getTag();
				}

				holder.text.setText(array[position]);

				imageLoader.displayImage(ImgListArray.get(position), holder.image, options, animateFirstListener);

				return view;
			}
		}

		// 圖片顯示動畫
		private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

			static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
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
	//等待所需的資料讀取完成	
	private Handler handler = new Handler() {
		int i;

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialog.dismiss();
				// 重新刷新Listview的adapter里面数据
				ImageLoaderOptions();
								
				break;
			
			default:
				// listItemAdapter.notifyDataSetChanged();

				break;
			}
		}

	};

	class sendPostRunnable implements Runnable {
		@Override
		public void run() {
			sendPostDataToInternet();
		}

	}

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

		htmlStr = htmlStr.substring(htmlStr.indexOf("優惠訊息") + 4,
				htmlStr.length());
		array = htmlStr.split("】");

		for (int i = 0; i < array.length; i++) {
			array[i] = "發佈日期：" + array[i] + "】";
			array[i] = array[i].replaceFirst("【", "\n \t【");
			// System.out.println(array[i]);
		}
	}

	public void ImgTag(String htmlStr) {
		String regEx_html = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>"; // 定义HTML标签的正则表达式
		htmlStr = htmlStr.substring(htmlStr.indexOf("優惠訊息"), htmlStr.length());

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);

		ImgListArray = new ArrayList<String>();

		while (m_html.find()) {

			String ImgStr = m_html.group(1);

			// if ((ImgStr.indexOf(("../images")) != 0)) {
			// ImgStr = ImgStr.replace("../", "http://211.72.86.193/Handmade/");
			ImgListArray.add(ImgStr);
			// }

		}
	}

	public void showToast(final String msg) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(Activity_salenews.this, msg, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

}
