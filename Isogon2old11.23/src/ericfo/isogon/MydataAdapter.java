package ericfo.isogon;

import java.util.ArrayList;

import ericfo.isogon.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MydataAdapter extends BaseAdapter {	
	private LayoutInflater mInflater;
	public ArrayList<String[]> data;//陣列是存網址及文字說明
	GetWebImg ImgCache;
	int tableID;
	int count;
	public MydataAdapter(Context c,ArrayList<String[]> d,GetWebImg cache, int tableid,int showlabel) {			
		mInflater = LayoutInflater.from(c);
		data = d;		
		ImgCache=cache;
		tableID=tableid;
		count=showlabel;
	}
	public int getCount() {
		return count;
	}
	public Object getItem(int position) {
		return position;
	}
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			view = mInflater.inflate(tableID, null);
			holder = new ViewHolder();
			holder.text = (TextView) view.findViewById(R.id.main_content_text);
			holder.pic = (ImageView) view.findViewById(R.id.main_content_pic);
			//holder.wait = (ProgressBar) view.findViewById(R.id.main_content_wait);
			view.setTag(holder);        		
		} else {
			holder = (ViewHolder) view.getTag();
		} 
		holder.text.setText(Html.fromHtml(data.get(position)[0]));//顯示文字說明
		holder.pic.setVisibility(View.INVISIBLE);
		holder.wait.setVisibility(View.VISIBLE);
		if (ImgCache.IsCache(data.get(position)[1]) == false){//如果圖片沒有暫存
			ImgCache.LoadUrlPic(data.get(position)[1],h);
		}else if (ImgCache.IsDownLoadFine(data.get(position)[1]) == true){//如果已經下載完成，就顯示圖片並把ProgressBar隱藏
			holder.pic.setImageBitmap(ImgCache.getImg(data.get(position)[1]));
			holder.wait.setVisibility(View.GONE);
			holder.pic.setVisibility(View.VISIBLE);	
		}else{
			//這裡是下載中，什麼事都不用做
		}		
		return view;
	}
	Handler h = new Handler(){//告訴BaseAdapter資料已經更新了
		@Override
		public void handleMessage(Message msg) {
			Log.d("m", "notifyDataSetChanged");
			notifyDataSetChanged();
			super.handleMessage(msg);
		}
	};	
	class ViewHolder{
		TextView text;
		ImageView pic;
		ProgressBar wait;
	}
}