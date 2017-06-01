package pl.bukowiecmateusz.psmzp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SpendingsDbAdapter {
    private static final String DEBUG_TAG = "SqLiteSpendingsManager";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "spendings_database.db";
    private static final String DB_SPENDINGS_TABLE = "spendings";

    public static final String KEY_ID = "_id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;

    public static final String KEY_OPIS = "opis";
    public static final String OPIS_OPTIONS = "TEXT NOT NULL";
    public static final int OPIS_COLUMN = 1;

    public static final String KEY_CENA = "cena";
    public static final String CENA_OPTIONS = "TEXT NOT NULL";
    public static final int CENA_COLUMN = 2;

    public static final String KEY_USUN = "completed";
    public static final String USUN_OPTIONS = "INTEGER DEFAULT 0";
    public static final int USUN_COLUMN = 3;

    private static final String DB_CREATE_SPENDINGS_TABLE =
            "CREATE TABLE " + DB_SPENDINGS_TABLE + "( " +
                    KEY_ID + " " + ID_OPTIONS + ", " +
                    KEY_OPIS + " " + OPIS_OPTIONS + ", " +
                    KEY_CENA + " " + CENA_OPTIONS + ", " +
                    KEY_USUN + " " + USUN_OPTIONS +
                    ");";
    private static final String DROP_SPENDINGS_TABLE =
            "DROP TABLE IF EXISTS " + DB_SPENDINGS_TABLE;

    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name,
                              CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE_SPENDINGS_TABLE);

            Log.d(DEBUG_TAG, "Database creating...");
            Log.d(DEBUG_TAG, "Table " + DB_SPENDINGS_TABLE + " ver." + DB_VERSION + " created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_SPENDINGS_TABLE);

            Log.d(DEBUG_TAG, "Database updating...");
            Log.d(DEBUG_TAG, "Table " + DB_SPENDINGS_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "All data is lost.");

            onCreate(db);
        }
    }

    public SpendingsDbAdapter(Context context) {
        this.context = context;
    }

    public SpendingsDbAdapter open(){
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insertSpending(String opis, String cena) {
        ContentValues newSpendingsValues = new ContentValues();
        newSpendingsValues.put(KEY_OPIS, opis);
        newSpendingsValues.put(KEY_CENA, cena);
        return db.insert(DB_SPENDINGS_TABLE, null, newSpendingsValues);
    }

    public boolean updateSpendings(Spendings spending) {
        long id = spending.getId();
        String opis = spending.getOpis();
        String cena = spending.getCena();
        boolean usun = spending.isUsun();
        return updateSpendings(id, opis, cena, usun);
    }

    public boolean updateSpendings(long id, String opis, String cena, boolean usun) {
        String where = KEY_ID + "=" + id;
        int delSpending = usun ? 1 : 0;
        ContentValues updateSpendingValues = new ContentValues();
        updateSpendingValues.put(KEY_OPIS, opis);
        updateSpendingValues.put(KEY_CENA, cena);
        updateSpendingValues.put(KEY_USUN, usun);
        return db.update(DB_SPENDINGS_TABLE, updateSpendingValues, where, null) > 0;
    }

    public boolean deleteSpending(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(DB_SPENDINGS_TABLE, where, null) > 0;
    }

    public Cursor getAllSpendings() {
        String[] columns = {KEY_ID, KEY_OPIS, KEY_CENA, KEY_USUN};
        return db.query(DB_SPENDINGS_TABLE, columns, null, null, null, null, null);
    }

    public Spendings getSpending(long id) {
        String[] columns = {KEY_ID, KEY_OPIS, KEY_CENA, KEY_USUN};
        String where = KEY_ID + "=" + id;
        Cursor cursor = db.query(DB_SPENDINGS_TABLE, columns, where, null, null, null, null);
        Spendings spending = null;
        if(cursor != null && cursor.moveToFirst()) {
            String opis = cursor.getString(OPIS_COLUMN);
            String cena = cursor.getString(CENA_COLUMN);
            boolean usun = cursor.getInt(USUN_COLUMN) > 0 ? true : false;
            spending = new Spendings(id, opis, cena, usun);
        }
        return spending;
    }
}