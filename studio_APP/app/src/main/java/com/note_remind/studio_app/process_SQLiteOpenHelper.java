package com.note_remind.studio_app;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by stn on 2014/12/29.
 */
public class process_SQLiteOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static  final String DATABASE_NAME="Remind_SQLite",
    TABLE_NAME="process_data",
    KEY_ID="id",
    PC_MOD="mod",
    PC_TITLE="title",
    PC_TIME="time",
    PC_DATA="data",
    PC_CONTENT="content",
    PC_ADDRESS="address";

    public process_SQLiteOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

}
