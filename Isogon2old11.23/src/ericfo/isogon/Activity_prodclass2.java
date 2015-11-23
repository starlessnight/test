package ericfo.isogon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ericfo.isogon.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Activity_prodclass2 extends Activity implements OnClickListener {

	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();
	static Activity Activity_prodclass2;
	private boolean load1=false,load2=false,load3=false;
	private String urlApi;
	private static int UrlID;
	private static String UrlStr , SendUrl;
	private static String[] array = {};
	private ProgressDialog dialog;
	private Thread mThread;
	//ArrayList<String[]> alldata;
	List<String> ImgListArray;
	List<String> UrlListArray;
	//GetWebImg ImgCache = new GetWebImg(Activity_prodclass2.this);
	private GridView gridView;
	private Button Go_Back, Next;
	private ItemAdapter adapter;
	private String PHPSESSID="";
	int index = 0;
	int Wm=0;
	int VIEW_COUNT = 6;
	//主程式的執行
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prodclass2);
		Activity_prodclass2=this;
		Bundle bundle = this.getIntent().getExtras();
		UrlStr = bundle.getString("UrlStr");
		SendUrl = bundle.getString("SendUrl");
		//PHPSESSID = bundle.getString("PHPSESSID");
		//System.out.println("class2"+PHPSESSID);
		
		UrlID = bundle.getInt("UrlID");
		
		if(UrlID != 0){
			urlApi = "http://211.72.86.193/Isogon_M/main/Products.aspx?productclass_id=c000000"	+ UrlID;
		}else if(!(SendUrl.equals(""))){
			urlApi = "http://211.72.86.193/Isogon_M/main1/Products.aspx?"+ SendUrl;
		}
		
		FindView();
		ListenerButtonView();
		changeButtonStatus();

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

		// 開啓執行緒去下载網頁數據
		if (mThread == null || !mThread.isAlive()) {
			mThread = new Thread() {
				boolean t=true;
				
				@Override
				public void run() {
					try {
						while(t){
						
						Thread.sleep(1000);
						
							if(Wm>=6){
								t=false;					
							}
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
		
		/*gridView.setOnItemClickListener(new OnItemClickListener() 
        { 
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
            { 
            	showToast(""+(position+ index * VIEW_COUNT));
            //	Intent intent = new Intent();
        		//intent.setClass(Activity_prodclass2.this , Activity_salenews2.class );
        	//	Bundle bundle = new Bundle(); 
        		//bundle.putInt("Guess_Num", position); 
        	//	intent.putExtras(bundle); 
        	//	startActivity(intent);
            } 
        });*/
	}

	//監聽所需的按鈕
	private void ListenerButtonView() {
		this.Go_Back.setOnClickListener(this);
		this.Next.setOnClickListener(this);
	}

	//對應Layout的物件
	private void FindView() {
		Go_Back = (Button) findViewById(R.id.Go_Back);
		Next = (Button) findViewById(R.id.Next);
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
		gridView = (GridView) findViewById(R.id.gridview);
		adapter=new ItemAdapter();
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				Intent intent = new Intent();
        		intent.setClass(Activity_prodclass2.this , Activity_prodclass3.class );
        		Bundle bundle = new Bundle(); 
        		bundle.putString("UrlStr",UrlListArray.get(arg2)); 
        		bundle.putString("PHPSESSID", PHPSESSID);
        		intent.putExtras(bundle); 
        		startActivity(intent);
				//showToast(""+(arg2+ index * VIEW_COUNT));
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
				// ori表示到目前为止的前幾頁的總共的個數。
				int ori = VIEW_COUNT * index;

				if (array.length- ori < VIEW_COUNT) {
					return array.length - ori;
				} else {
					return VIEW_COUNT;
				}
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
				
				int num=(position + index * VIEW_COUNT);
				
				final ViewHolder holder;
				if (convertView == null) {
					view = getLayoutInflater().inflate(R.layout.activity_prodclass2_item, parent, false);
					holder = new ViewHolder();
					holder.text = (TextView) view.findViewById(R.id.main_content_text);
					holder.image = (ImageView) view.findViewById(R.id.main_content_pic);
					view.setTag(holder);
				} else {
					holder = (ViewHolder) view.getTag();
				}

				holder.text.setText(Html.fromHtml(array[num]));
				
				try{
					imageLoader.displayImage(ImgListArray.get(num), holder.image, options, animateFirstListener);
				}
				catch(NullPointerException e){
					e.printStackTrace();
				}
				
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
		
	//等待網面資料抓取完成	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				dialog.dismiss();
				// 重新刷新Listview的adapter里面数据
				ImageLoaderOptions();
				adapter.notifyDataSetChanged();
				break;
			case 2:
				dialog.dismiss();
				imageLoader.clearDiscCache();
				imageLoader.clearMemoryCache();
				adapter.notifyDataSetChanged();
				break;
			default:
				adapter.notifyDataSetChanged();
				break;
			}
		}

	};

	//點擊按鈕所觸發的事件
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Go_Back:
			leftView();
			break;
		case R.id.Next:
			rightView();
			break;

		}
	}

	/**
	 * 點擊左邊的Button，表示向前翻頁，索引值要減1.
	 */
	public void leftView() {
		index-=1;
		// prepare the dialog box
		dialog = new ProgressDialog(this);

		// make the progress bar cancelable
		dialog.setCancelable(false);

		// set a message text
		dialog.setMessage("讀取中...");

		// show it
		dialog.show();

		if (mThread == null || !mThread.isAlive()) {
			mThread = new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Message message = new Message();
					message.what = 2;
					handler.sendMessage(message);
				}
			};
			mThread.start();
		}
		// 刷新ListView裏面的數值。
		changeButtonStatus();
	}

	/**
	 * 點擊右邊的Button，表示向後翻頁，索引值要加1.
	 */
	public void rightView() {
		index+=1;
		// prepare the dialog box
		dialog = new ProgressDialog(this);

		// make the progress bar cancelable
		dialog.setCancelable(false);

		// set a message text
		dialog.setMessage("讀取中...");

		// show it
		dialog.show();

		if (mThread == null || !mThread.isAlive()) {
			mThread = new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Message message = new Message();
					message.what = 2;
					handler.sendMessage(message);
				}
			};
			mThread.start();
		}
		// 刷新ListView裏面的數值。
		changeButtonStatus();
	}
	
	/**
	 * 變更btnLeft與btnRight按鈕是否可用。
	 */
	public void changeButtonStatus() {
		//showToast(""+index);
		if (index <= 0) {
			Next.setEnabled(true);
			Go_Back.setEnabled(false);
		} else if (array.length - index * VIEW_COUNT <= VIEW_COUNT) {
			Next.setEnabled(false);
			Go_Back.setEnabled(true);
		} else {
			Go_Back.setEnabled(true);
			Next.setEnabled(true);
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
	//抓取文字，把取回的資料清除多於的字串
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

		if(UrlStr != null){
			htmlStr = htmlStr.substring(htmlStr.indexOf(UrlStr) + 4 , htmlStr.length());
		}else if(!(SendUrl.equals(""))){
			htmlStr = htmlStr.substring(htmlStr.indexOf("#")+1 , htmlStr.length());
		}

		array = htmlStr.split("】");

		for (int i = 0; i < array.length; i++) {
			// System.out.println(array[i]);
			array[i] = array[i].replaceFirst("原價", "<br>原價");
			array[i] = array[i].replaceFirst("特價", "<br><font color='red'>特價");
			array[i] = array[i] + "</font>";
		}

	}
	//抓取圖片網址，把取回的資料清除多於的字串
	public void ImgTag(String htmlStr) {
		String regEx_html = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>"; // 定义HTML标签的正则表达式

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);

		ImgListArray = new ArrayList<String>();

		while (m_html.find()) {

			String ImgStr = m_html.group(1);

			// System.out.println(ImgStr);

			if ((ImgStr.indexOf(("../images")) != 0)) {
				// ImgStr = ImgStr.replace("../",
				// "http://211.72.86.193/Handmade/");
				ImgStr=ImgStr.trim();
				ImgListArray.add(ImgStr);
				// ImgListArray = ImgStr;
				Wm=ImgListArray.size();
			}
			

		}
	}
	
	String temp=" ";
	//抓取連接網址，把取回的資料清除多於的字串
	public void UrlTag(String htmlStr) {
		String regEx_html = "<a[^>]+href\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>"; // 定义HTML标签的正则表达式
		//htmlStr = htmlStr.substring(htmlStr.indexOf("傳奇店家"),htmlStr.length());

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);

		UrlListArray = new ArrayList<String>();

		while (m_html.find()) {

			String UrlStr = m_html.group(1);
			
			UrlStr=UrlStr.substring(UrlStr.indexOf("?")+1,UrlStr.length());
			
			if(!(temp.equals(UrlStr)) && (UrlStr.indexOf("class_id")>0)){
				UrlListArray.add(UrlStr);
				//System.out.println(UrlStr);
				temp = UrlStr;
			}
		}
	}


	public void showToast(final String msg) {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(Activity_prodclass2.this, msg,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_prodclass2, menu);
		return true;
	}

}
