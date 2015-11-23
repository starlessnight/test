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


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener; 

public class Activity_Store extends Activity {
	
	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();

	ArrayList<String[]> alldata;
	List<String> ImgListArray;
	List<String> UrlListArray;
	GetWebImg ImgCache = new GetWebImg(Activity_Store.this);
	private GridView gridView;
	private String urlApi = "http://211.72.86.193/Isogon_M/main/store.aspx";
	private static String[] array = {};
	private ProgressDialog dialog;
	private Thread mThread;
	private int[] x , y;
	
	//主程式的執行
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_store);
		
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
								
								Thread.sleep(5000);
								
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
	//將擷取下來的資料打亂
	private void Shuffle_poker_Algorithms(int num){
		
		x = new int[num];
		
	    for(int i=0;i<num;i++){
	        x[i]=i;		//將x陣列的1~10元素依序填入資料，例如x[1]=1,x[2]=2
	    }

	    for(int i=0;i<num;i++){
	        int n1=(int) ((Math.random()*num)%num);		//產生0～9的亂數
	        int n2=(int) ((Math.random()*num)%num);
	        //將陣列資料進行交換（打散）
			int temp=x[n1];
	        x[n1]=x[n2];
	        x[n2]=temp;
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
		
		// GridView 設定
		gridView = (GridView) findViewById(R.id.gridview); 
		gridView.setAdapter(new ItemAdapter());
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				Intent intent = new Intent();
        		intent.setClass(Activity_Store.this , Activity_Store2.class );
        		Bundle bundle = new Bundle(); 
        		bundle.putString("UrlStr",UrlListArray.get(x[arg2])); 
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
			//把圖片跟文字放入對應的物件
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				View view = convertView;
				final ViewHolder holder;
				if (convertView == null) {
					view = getLayoutInflater().inflate(R.layout.activity_store_item, parent, false);
					holder = new ViewHolder();
					holder.text = (TextView) view.findViewById(R.id.main_content_text);
					holder.image = (ImageView) view.findViewById(R.id.main_content_pic);
					view.setTag(holder);
				} else {
					holder = (ViewHolder) view.getTag();
				}

				holder.text.setText(array[x[position]]);

				imageLoader.displayImage(ImgListArray.get(x[position]), holder.image, options, animateFirstListener);

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
	
	/**
	 * 设置适配器内容
	 */
		//把要送的資料以陣列的方式儲存
	private void initListView() {

		alldata = new ArrayList<String[]>();
		
		Shuffle_poker_Algorithms(array.length);
		for (int i = 0; i <array.length; i++) {
			alldata.add(createData(array[x[i]],ImgListArray.get(x[i])));
		}
	}
	//等待所需的資料讀取完成
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialog.dismiss();
				// 重新刷新Listview的adapter里面数据
				Shuffle_poker_Algorithms(array.length);
				ImageLoaderOptions();
				/*initListView();
				BitmapHelper adapter =new BitmapHelper(Activity_Store.this,alldata, ImgCache,R.layout.activity_store_item);
				gridView.setAdapter(adapter);*/
				// Activity_salenews.this.setListAdapter(listItemAdapter)
				break;
			default:
				// listItemAdapter.notifyDataSetChanged();

				break;
			}
		}

	};
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

				delHTMLTag(strResult);
				ImgTag(strResult);
				UrlTag(strResult);
				
				// 回傳回應字串
				return strResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//取出文字，清除多於餘的字串
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
		htmlStr = htmlStr.substring(htmlStr.indexOf("傳奇店家")+4,htmlStr.length());
		
		array = htmlStr.split("】");
		
		for (int i = 0; i < array.length; i++) {
			array[i]=array[i]+"】";
		}
		
	}
	//取出圖片的網址，清除多餘的字串
	public void ImgTag(String htmlStr) {
		String regEx_html = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>"; // 定义HTML标签的正则表达式
		htmlStr = htmlStr.substring(htmlStr.indexOf("傳奇店家"),htmlStr.length());

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);

		ImgListArray = new ArrayList<String>();

		while (m_html.find()) {

			String ImgStr = m_html.group(1);
			ImgStr=ImgStr.trim();
			ImgListArray.add(ImgStr);
		}
	}
	
	String temp=" ";
	//取出網頁連接的網址，清除多餘的字串
	public void UrlTag(String htmlStr) {
		String regEx_html = "<a[^>]+href\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>"; // 定义HTML标签的正则表达式
		htmlStr = htmlStr.substring(htmlStr.indexOf("傳奇店家"),htmlStr.length());

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);

		UrlListArray = new ArrayList<String>();

		while (m_html.find()) {

			String UrlStr = m_html.group(1);
			
			UrlStr=UrlStr.substring(UrlStr.indexOf("?")+1,UrlStr.length());
			
			if(!(temp.equals(UrlStr))){
				UrlListArray.add(UrlStr);
				//System.out.println(UrlStr);
				temp = UrlStr;
			}
			
			
			//if ((ImgStr.indexOf(("../images")) != 0)) {
				//ImgStr = ImgStr.replace("../", "http://211.72.86.193/Handmade/");
				//ImgListArray.add(ImgStr);
			//}

		}
	}
	
	private String[] createData(String text, String imgurl) {
		String temp[] = { text, imgurl };
		return temp;
	}

	
	
	public void showToast(final String msg) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(Activity_Store.this, msg, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_store, menu);
		return true;
	}

}