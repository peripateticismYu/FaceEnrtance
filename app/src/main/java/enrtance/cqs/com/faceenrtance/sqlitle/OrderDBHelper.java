package enrtance.cqs.com.faceenrtance.sqlitle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "myFace.db";
    public static final String TABLE_Faces = "Faces";
    public static final String TABLE_Record = "PassRecord";
    public static final String TABLE_Image = "RecordImage";

    String tableFaces = "create table if not exists " + TABLE_Faces + " (Id text,Name text,Sex text,FaceImageUrl text,FaceEigenvalue text)";
    String tablePassRecord = "create table if not exists " + TABLE_Record + " (Id text, userId text,Name text, recordTime INTEGER, imageId text, status text)";
    String tableRecordImage = "create table if not exists " + TABLE_Image + " (Id text,imageData text,isDownload text,recordTime INTEGER)";

    String upData_tableFaces = "DROP TABLE IF EXISTS " + TABLE_Faces;
    String upData_tablePassRecord = "DROP TABLE IF EXISTS " + TABLE_Record;
    String upData_tableRecordImage = "DROP TABLE IF EXISTS " + TABLE_Image;


    public OrderDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tableFaces);
        sqLiteDatabase.execSQL(tablePassRecord);
        sqLiteDatabase.execSQL(tableRecordImage);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL(upData_tableFaces);
        sqLiteDatabase.execSQL(upData_tablePassRecord);
        sqLiteDatabase.execSQL(upData_tableRecordImage);
        onCreate(sqLiteDatabase);
    }
}

