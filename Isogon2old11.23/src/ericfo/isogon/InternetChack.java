package ericfo.isogon;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


public class InternetChack {
		/*
	     * 打開網路設置介面
	     * */
	    public static void setNetworkMethod(final Context context){
	        //提示對話框
	        AlertDialog.Builder builder=new Builder(context);
	        builder.setTitle("網路設置提示").setMessage("網路不可連接，請進行設置!").setPositiveButton("設置", new DialogInterface.OnClickListener() {
	            
	        	//主程式的執行
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                // TODO Auto-generated method stub
	                Intent intent=null;
	                //判斷手機版本  API>10 要3.0版本已上 
	                if(android.os.Build.VERSION.SDK_INT>10){
	                    intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
	                }else{
	                    intent = new Intent();
	                    ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
	                    intent.setComponent(component);
	                    intent.setAction("android.intent.action.VIEW");
	                }
	                context.startActivity(intent);
	            }
	        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {          
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                // TODO Auto-generated method stub
	                dialog.dismiss();
	                System.exit(0);
	            }
	        }).show();
	    }
}
