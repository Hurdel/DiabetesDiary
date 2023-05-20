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

    private static final String TABLE_NAME_MESSUNGEN = "tagebuch";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TIMESTAMP = "timestamp";
    private static final String COLUMN_VALUE = "value";
    private static final String TABLE_NAME_SETTINGS = "settingsdb";
    private static final String COLUMN_SETTINGNAME = "settingname";
    private static final String COLUMN_SETTINGVALUE = "einstellung";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MESSUNGEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SETTINGS);
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

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME_MESSUNGEN + " ORDER BY " + COLUMN_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
