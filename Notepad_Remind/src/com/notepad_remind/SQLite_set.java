package com.notepad_remind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite_set extends SQLiteOpenHelper{

	private final static String DATABASE_NAME = "note_db";
	private final static int DATABASE_VERSION = 1;
	private final static String TABLE_NAME="table_name";
	private final static String NOTE_id="note_id";
	private final static String NOTE_title="note_title";
	private final static String NOTE_content="note_content";
	private final static String NOTE_date="note_date";
	private final static String NOTE_time="note_time";
	private final static String NOTE_kind="note_kind";
	private final static String NOTE_address="note_addressTEXT";
	public SQLite_set(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	public SQLite_set(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//建構資料庫的資料表各個欄位
		String sql="CREATE TABlE "+TABLE_NAME+" ("+NOTE_id+" INTEGER primary key autoincrement, "+
		 
													NOTE_title+" TEXT,"+
													NOTE_content+" TEXT,"+
													NOTE_date+" TEXT,"+
													NOTE_time+" TEXT,"+
													NOTE_kind+" TEXT,"+
													NOTE_address+" TEXT)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//oldVersion=舊的資料庫版本；newVersion=新的資料庫版本
		  
		  if (newVersion > oldVersion) {
		   db.beginTransaction();//建立交易
		     
		    boolean success = false;//判斷參數
		        
		    //由之前不用的版本，可做不同的動作     
		    switch (oldVersion) {
		    case 2:           
		      db.execSQL("ALTER TABLE table_name ADD COLUMN note_address TEXT ");
		      oldVersion++;
		             
		     success = true;
		     break;
		    }
		                
		     if (success) {
		       db.setTransactionSuccessful();//正確交易才成功
		      }
		    db.endTransaction();
		  }
		  else {
		    onCreate(db);
		  }
	}

	public Cursor select()
	  {
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
	    return cursor;
	  }
	public long insert(String title,String content,String date,String time,String kind,String address)
	  {
	    SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues cv=new ContentValues();
	    cv.put(NOTE_title, title);
	    cv.put(NOTE_content, content);
	    cv.put(NOTE_date, date);
	    cv.put(NOTE_time, time);
	    cv.put(NOTE_kind, kind);
	    cv.put(NOTE_address, address);
	    long row =db.insert(TABLE_NAME, null, cv);
	    
	    return  row  ;
	    
	  }
	
	public void delete(int id)
	  {
	    SQLiteDatabase db = this.getWritableDatabase();
	    String where = NOTE_id + " = ?";
	    String[] whereValue =
	    { Integer.toString(id) };
	    db.delete(TABLE_NAME, where, whereValue);
	    
	    
	  }
	
	public long update(int id,String title,String content,String date,String time,String address)
	  {
		System.out.print("@555@"+id);
	    SQLiteDatabase db = this.getWritableDatabase();
	    String where = NOTE_id + " = ?";
	    String[] whereValue =
	    { Integer.toString(id) };
	    ContentValues cv=new ContentValues();
	    cv.put(NOTE_title, title);
	    cv.put(NOTE_content, content);
	    cv.put(NOTE_date, date);
	    cv.put(NOTE_time, time);
	    cv.put(NOTE_address, address);
	    long row=db.update(TABLE_NAME, cv, where, whereValue);
	    return  row;
	    
	    
	  }
}
