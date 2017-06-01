package pl.bukowiecmateusz.psmzp;

import pl.bukowiecmateusz.psmzp.CarProfiles;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CarsDbAdapter {
    private static final String DEBUG_TAG = "SqLiteCarsManager";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";
    private static final String DB_CARS_TABLE = "cars";

    public static final String KEY_ID = "_id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;

    public static final String KEY_REJESTRACJA = "rejestracja";
    public static final String REJESTRACJA_OPTIONS = "TEXT NOT NULL";
    public static final int REJESTRACJA_COLUMN = 1;

    public static final String KEY_USUN = "usun";
    public static final String USUN_OPTIONS = "INTEGER DEFAULT 0";
    public static final int USUN_COLUMN = 2;

    public static final String KEY_MARKA = "marka";
    public static final String MARKA_OPTIONS = "TEXT NOT NULL";
    public static final int MARKA_COLUMN = 3;

    public static final String KEY_UBEZPIECZENIE = "ubezpieczenie";
    public static final String UBEZPIECZENIE_OPTIONS = "TEXT NOT NULL";
    public static final int UBEZPIECZENIE_COLUMN = 4;

    public static final String KEY_PRZEGLAD = "przeglad";
    public static final String PRZEGLAD_OPTIONS = "TEXT NOT NULL";
    public static final int PRZEGLAD_COLUMN = 5;

    private static final String DB_CREATE_CARS_TABLE =
            "CREATE TABLE " + DB_CARS_TABLE + "( " +
                    KEY_ID + " " + ID_OPTIONS + ", " +
                    KEY_REJESTRACJA + " " + REJESTRACJA_OPTIONS + ", " +
                    KEY_USUN + " " + USUN_OPTIONS + ", " +
                    KEY_MARKA + " " + MARKA_OPTIONS + ", " +
                    KEY_UBEZPIECZENIE + " " + UBEZPIECZENIE_OPTIONS + ", " +
                    KEY_PRZEGLAD + " " + PRZEGLAD_OPTIONS +
                    ");";
    private static final String DROP_CARS_TABLE =
            "DROP TABLE IF EXISTS " + DB_CARS_TABLE;

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
            db.execSQL(DB_CREATE_CARS_TABLE);

            Log.d(DEBUG_TAG, "Database creating...");
            Log.d(DEBUG_TAG, "Table " + DB_CARS_TABLE + " ver." + DB_VERSION + " created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_CARS_TABLE);

            Log.d(DEBUG_TAG, "Database updating...");
            Log.d(DEBUG_TAG, "Table " + DB_CARS_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "All data is lost.");

            onCreate(db);
        }
    }

    public CarsDbAdapter(Context context) {
        this.context = context;
    }

    public CarsDbAdapter open() {
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

    public long insertCars(String rejestracja, String marka, String ubezpieczenie, String przeglad) {
        ContentValues newCarsValues = new ContentValues();
        newCarsValues.put(KEY_REJESTRACJA, rejestracja);
        newCarsValues.put(KEY_MARKA, marka);
        newCarsValues.put(KEY_UBEZPIECZENIE, ubezpieczenie);
        newCarsValues.put(KEY_PRZEGLAD, przeglad);
        return db.insert(DB_CARS_TABLE, null, newCarsValues);
    }

    public boolean updateCars(long id, String rejestracja, boolean usun, String marka, String ubezpiecznie, String przeglad) {
        String where = KEY_ID + "=" + id;
        int usunPojazd = usun ? 1 : 0;
        ContentValues updateCarsValues = new ContentValues();
        updateCarsValues.put(KEY_REJESTRACJA, rejestracja);
        updateCarsValues.put(KEY_USUN, usunPojazd);
        updateCarsValues.put(KEY_MARKA, marka);
        updateCarsValues.put(KEY_UBEZPIECZENIE, ubezpiecznie);
        updateCarsValues.put(KEY_PRZEGLAD, przeglad);

        return db.update(DB_CARS_TABLE, updateCarsValues, where, null) > 0;
    }

    public boolean deleteCars(long id) {
        String where = KEY_ID + "=" + id;
        return db.delete(DB_CARS_TABLE, where, null) > 0;
    }


    public Cursor getAllCars() {
        String[] columns = {KEY_ID, KEY_REJESTRACJA, KEY_USUN, KEY_MARKA, KEY_UBEZPIECZENIE, KEY_PRZEGLAD};
        return db.query(DB_CARS_TABLE, columns, null, null, null, null, null, null);
    }
}