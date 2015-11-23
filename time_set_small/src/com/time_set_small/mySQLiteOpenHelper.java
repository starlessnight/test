package com.time_set_small;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class mySQLiteOpenHelper extends SQLiteOpenHelper
{
  private final static String DATABASE_NAME = "todo_db";
  private final static int DATABASE_VERSION = 2;
  private final static String TABLE_NAME = "process";
  public final static String PROCESS_id = "_id";
  public final static String PROCESS_name = "process_name";
  public final static String PROCESS_ftTime = "PROCESS_ftTimetext";
  public final static String PROCESS_lstTime = "PROCESS_lstTimetext";
  public final static String PROCESS_weekday = "weekday";
  
	 
	 
	   
	   
  public mySQLiteOpenHelper(Context context)
  {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
 public mySQLiteOpenHelper(Context context,String name) { 
	 super(context, DATABASE_NAME, null, DATABASE_VERSION); 
	   } 
  @Override
  public void onCreate(SQLiteDatabase db)
  {
	  
    
    String sql = "CREATE TABLE " + TABLE_NAME + " (" + PROCESS_id
        + " INTEGER primary key autoincrement, "  + PROCESS_name + " TEXT,"+PROCESS_ftTime+" TEXT,"+PROCESS_lstTime+" TEXT,"+PROCESS_weekday+" TEXT)";
    db.execSQL(sql);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
  {
	//oldVersion=舊的資料庫版本；newVersion=新的資料庫版本
	  
	  if (newVersion > oldVersion) {
	   db.beginTransaction();//建立交易
	     
	    boolean success = false;//判斷參數
	        
	    //由之前不用的版本，可做不同的動作     
	    switch (oldVersion) {
	    case 1:           
	      db.execSQL("ALTER TABLE process ADD COLUMN weekday TEXT ");
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
    /*String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
    db.execSQL(sql);
    onCreate(db);*/
  }

  public Cursor select()
  {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
    return cursor;
  }

  public long insert(String name,String ftTime,String lstTime,String weekdays)
  {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues cv=new ContentValues();
    cv.put(PROCESS_name, name);
    cv.put(PROCESS_ftTime, ftTime);
    cv.put(PROCESS_lstTime, lstTime);
    cv.put(PROCESS_weekday, weekdays);
    long row =db.insert(TABLE_NAME, null, cv);
    
    return  row  ;
    
  }

  public void delete(int id)
  {
    SQLiteDatabase db = this.getWritableDatabase();
    String where = PROCESS_id + " = ?";
    String[] whereValue =
    { Integer.toString(id) };
    db.delete(TABLE_NAME, where, whereValue);
    
    
  }

  public void update(int id,String name,String ftTime,String lstTime,String weekdays)
  {
    SQLiteDatabase db = this.getWritableDatabase();
    String where = PROCESS_id + " = ?";
    String[] whereValue =
    { Integer.toString(id) };
    ContentValues cv=new ContentValues();
    cv.put(PROCESS_name, name);
    cv.put(PROCESS_ftTime, ftTime);
    cv.put(PROCESS_lstTime, lstTime);
    cv.put(PROCESS_weekday, weekdays);
    db.update(TABLE_NAME, cv, where, whereValue);
    
    
    
  }
}