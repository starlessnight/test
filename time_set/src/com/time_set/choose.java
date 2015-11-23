package com.time_set;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class choose extends Activity {
	private Button revise, delete;
	private mySQLiteOpenHelper mydb= new mySQLiteOpenHelper(choose.this);
	private SQLiteDatabase db;
	private int _id,updata_id;
	//private String _id;
	private Bundle b ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose);

		find_view();
		setTitle("請選擇修改或刪除");
		b = this.getIntent().getExtras();
		_id=b.getInt("which");
		System.out.println("updata_id"+_id);
		revise.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
			}
		});

		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(_id!=0){
					mydb.delete(_id);
					Intent intent=new Intent();
					intent.setClass(choose.this, process.class);
					process.process.finish();
					stopService(intent);
					startActivity(intent);
					//db.close();
					choose.this.finish();   
				  }
			}
		});
		
		revise.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				Bundle b=new Bundle();
				b.putInt("id", _id);
				intent.setClass(choose.this, reviseActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				choose.this.finish();
			}
		});
	}

	private void find_view() {
		revise = (Button) findViewById(R.id.revise);
		delete = (Button) findViewById(R.id.delete);

	}

}
