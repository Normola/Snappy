package uk.co.undergroundbunker.snappy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static final String DB_NAME = "snaps";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "snap";
    public static final String DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String GET_SNAPS_QUERY = "SELECT * FROM " + TABLE_NAME;

    public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "" +
            "(" +
            "photoId TEXT PRIMARY KEY not null, " +
            "photoData blob not null)";

    public Database(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL(DROP_QUERY);
        onCreate(db);
    }

    public void addData(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("photoId", dataModel.getName());
        values.put("photoData", dataModel.getBitmap());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
}