package com.gotomovie;

import com.gotomovie.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.widget.TabHost;
import android.widget.TabWidget;

/**
 * This demonstrates how you can implement switching between the tabs of a
 * TabHost through fragments.  It uses a trick (see the code below) to allow
 * the tabs to switch between fragments instead of simple views.
 */
public class FragmentTabs extends FragmentActivity {
    private TabHost mTabHost;
    private TabManager mTabManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.fragment_tabs);
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        
        mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);
        
        mTabHost.setCurrentTab(0); 
        mTabManager.addTab(mTabHost.newTabSpec("Fragment1").setIndicator("Movie_th",this.getResources().getDrawable(android.R.drawable.ic_dialog_alert)),
        		Movie_th.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("Fragment0").setIndicator("Fragment0",this.getResources().getDrawable(android.R.drawable.ic_lock_lock)),
        		Movie_about.class, null);
       
       
        
      
		   
        DisplayMetrics dm = new DisplayMetrics();   
        getWindowManager().getDefaultDisplay().getMetrics(dm); //�����o�ù��ѪR��  
        int screenWidth = dm.widthPixels;   //���o�ù����e
           
           
        TabWidget tabWidget = mTabHost.getTabWidget();   //���otab������
        int count = tabWidget.getChildCount();   //���otab���������X��
        if (count > 2) {   //�p�G�W�L�T�ӴN�ӳB�z�ư�
            for (int i = 0; i < count; i++) {   
                tabWidget.getChildTabViewAt(i).setMinimumWidth((screenWidth) / 2);//�]�w�C�@�Ӥ����̤p���e��   
            }   
        }

    }
}