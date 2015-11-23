package com.lujianfei.active;

 
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;





public class VideoprojActivity extends Activity {
	
	
	String [] a={"1123","2123","2","3"};
	String [] b={"sadasd","2123","dewrwe","3"};
	String [] c={"1sssssss","2123","2","jhkhjkh"};
	String [] d={"1ssdasdasd","asdasd","333333","3"};
    GridView gridview = null;  
  

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoproj_gridview_layout);
        gridview = (GridView) findViewById(R.id.gridview);    
        
        
        ArrayList<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();  
        HashMap<String,Object> map=null;  
         
       
          
        for(int i=0;i<4;i++)  
        {  
        map=new HashMap<String,Object>();
        map.put("store",a[i]);
        map.put("pn",b[i]);
        map.put("pc",a[i]);
        map.put("pp",a[i]);
        list.add(map);  
        }
        SimpleAdapter adapter = new SimpleAdapter(this,list,R.layout.videoproj_gridview_item_layout,new String[]{"store","pn","pc","pp"},new int[]{R.id.store_name,R.id.product_name,R.id.product_quantity,R.id.quantity});  
        adapter.setViewBinder(new ViewBinder(){  
  
          public boolean setViewValue(View view, Object data,   
                  String textRepresentation) {   
                //判断是否为我们要处理的对象    
                if(view instanceof ImageView && data instanceof Bitmap){   
                  ImageView iv = (ImageView) view;   
                  iv.setImageBitmap((Bitmap) data);   
                  return true;   
                }else   
                return false;   
              }   
  
      
      });  

        gridview.setAdapter(adapter);    

    }
     
}