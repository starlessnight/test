package com.givemepass.FragmentTabs;



import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Text extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text);
		Toast.makeText(Text.this, "text",    
	            Toast.LENGTH_SHORT).show();
	}
}
