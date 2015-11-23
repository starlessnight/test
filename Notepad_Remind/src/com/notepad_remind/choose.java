package com.notepad_remind;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;

public class choose extends Activity{

	private SQLite_set mydb=new SQLite_set(choose.this);
	private Button btn_del,btn_chg;
	private Bundle d;
	int id;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview_longbutton);
		findView();
		
		d=this.getIntent().getExtras();
		id=d.getInt("which");
		
		
		btn_del.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				if(id!=0){
					mydb.delete(id);
					Intent intent=new Intent();
					intent.setClass(choose.this, NoteActivity.class);
					
					NoteActivity.NoteActivity.finish();
					//stopService(intent);
					startActivity(intent);
					mydb.close();
					choose.this.finish();   
				}
				
				
			}
		});
		
		
		btn_chg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(id!=0){
					Intent intent=new Intent();
					Bundle b=new Bundle();
					intent.setClass(choose.this, Revise_activity.class);
					b.putInt("id", id);
					intent.putExtras(b);
					startActivity(intent);
					mydb.close();
					choose.this.finish();   
				}
			}
		});
		
	}
	
	private void findView(){
		btn_del=(Button)findViewById(R.id.delete);
		btn_chg=(Button)findViewById(R.id.change);
		
	}
	
	
	
	
	
	
}
