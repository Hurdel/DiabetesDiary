package tv.master_of_spirit.diabetesdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Insulinrechner.db";
    private static final int DATABASE_VERSION = 1;

    //    namespace messungen
    private static final String TABLE_NAME_MESSUNGEN = "tagebuch";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TIMESTAMP = "timestamp";
    private static final String COLUMN_VALUE = "value";

    //    namespace settings
    private static final String TABLE_NAME_SETTINGS = "settingsdb";
    private static final String COLUMN_SETTINGNAME = "settingname";
    private static final String COLUMN_SETTINGVALUE = "einstellung";

    //    namespace activity
    private static final String TABLE_NAME_ACTIVITY = "activitydb";
    private static final String COLUMN_ACTIVITYID = "_id";
    private static final String COLUMN_ACTIVITYTIME = "timestamp";
    private static final String COLUMN_ACTIVITYNAME = "activity";

    //    namespace rechnung
    private static final String TABLE_NAME_RECHNUNG = "rechnungendb";
    private static final String COLUMN_RECHNUNGID = "_id";
    private static final String COLUMN_RECHUNGTIME = "timestamp";
    private static final String COLUMN_RECHUNGPRO100 = "khpro100";
    private static final String COLUMN_RECHNUNGGEWICHT = "gewicht";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String querymessung =
                "CREATE TABLE " + TABLE_NAME_MESSUNGEN +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TIMESTAMP + " TEXT, " +
                        COLUMN_VALUE + " TEXT);";
        db.execSQL(querymessung);
        String querysettings =
                "CREATE TABLE " + TABLE_NAME_SETTINGS +
                        " (" + COLUMN_SETTINGNAME + " TEXT PRIMARY KEY, " +
                        COLUMN_SETTINGVALUE + " DOUBLE);";
        db.execSQL(querysettings);
        String queryactivity =
                "CREATE TABLE " + TABLE_NAME_ACTIVITY +
                        " (" + COLUMN_ACTIVITYID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_ACTIVITYTIME + " TEXT, " +
                        COLUMN_ACTIVITYNAME + " TEXT);";
        db.execSQL(queryactivity);
        String queryrechung =
                "CREATE TABLE " + TABLE_NAME_RECHNUNG +
                        " (" + COLUMN_RECHNUNGID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_RECHUNGTIME + "STRING, " +
                        COLUMN_RECHUNGPRO100 + " DOUBLE, " +
                        COLUMN_RECHNUNGGEWICHT + " DOUBLE);";
        db.execSQL(queryrechung);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MESSUNGEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SETTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_RECHNUNG);
        onCreate(db);
    }

    void addMessung(String timestamp, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TIMESTAMP, timestamp);
        cv.put(COLUMN_VALUE, value);
        long result = db.insert(TABLE_NAME_MESSUNGEN, null, cv);
        if (result == -1) {
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        }
//        else {
//            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
//        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME_MESSUNGEN + " ORDER BY " + COLUMN_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void setSettings(String settingname, Double settingvalue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SETTINGNAME, settingname);
        cv.put(COLUMN_SETTINGVALUE, settingvalue);

        long result = db.insert(TABLE_NAME_SETTINGS, null, cv);
        if (result == -1) {
            result = db.update(TABLE_NAME_SETTINGS, cv, "settingname=?", new String[]{settingname});
            if (result == -1) {
                Toast.makeText(context, "Failed to save!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    Cursor getSettings() {
        String query = "SELECT * FROM " + TABLE_NAME_SETTINGS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void addActivity(String time, String activity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ACTIVITYTIME, time);
        cv.put(COLUMN_ACTIVITYNAME, activity);

        long result = db.insert(TABLE_NAME_ACTIVITY, null, cv);
        if (result == -1) {
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor getActivitys() {
        String query = "SELECT * FROM " + TABLE_NAME_ACTIVITY;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void addRechnung(String time, Double pro100, Double gewicht) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RECHUNGTIME, time);
        cv.put(COLUMN_RECHUNGPRO100, pro100);
        cv.put(COLUMN_RECHNUNGGEWICHT, gewicht);

        long result = db.insert(TABLE_NAME_RECHNUNG, null, cv);
        if (result == -1) {
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor getRechnung(String time) {
        String query = "SELECT * FROM " + TABLE_NAME_RECHNUNG + " WHERE " + COLUMN_RECHUNGTIME + " = " + time;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
