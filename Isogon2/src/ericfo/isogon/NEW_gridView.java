package ericfo.isogon;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class NEW_gridView extends GridView{

	public NEW_gridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public NEW_gridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		}

		public NEW_gridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		}
		public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){  
	          int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
	          super.onMeasure(widthMeasureSpec, mExpandSpec);  
	     }  
		     
		       //通过重新dispatchTouchEvent方法来禁止滑动
		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(ev.getAction() == MotionEvent.ACTION_MOVE){
		           return true;//禁止Gridview进行滑动
		       }
		return super.dispatchTouchEvent(ev);
		}
}
