package pl.bukowiecmateusz.psmzp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private Button dodajPojazd;
    private Button usunZaznaczone;
    private Button zapisz;
    private Button anuluj;

    private EditText numerRejestracyjny;
    private EditText marka;
    private EditText ubezpieczenie;
    private EditText przeglad;

    private ListView lvCars;

    private LinearLayout addDelButtons;
    private LinearLayout newCarButtons;

    private CarsDbAdapter carsDbAdapter;
    private Cursor carsCursor;
    private List<CarProfiles> cars;
    private CarProfilesAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initUiElements();
        initListView();
        initButtonsOnClickListeners();
    }

    private void initUiElements() {
        dodajPojazd = (Button) findViewById(R.id.dodajPojazd);
        usunZaznaczone = (Button) findViewById(R.id.usunZaznaczone);
        zapisz = (Button) findViewById(R.id.zapisz);
        anuluj = (Button) findViewById(R.id.anuluj);

        numerRejestracyjny = (EditText) findViewById(R.id.numerRejestracyjny);
        marka = (EditText) findViewById(R.id.marka);
        ubezpieczenie = (EditText) findViewById(R.id.ubezpieczenie);
        przeglad = (EditText) findViewById(R.id.przeglad);

        lvCars = (ListView) findViewById(R.id.lvCars);

        addDelButtons = (LinearLayout) findViewById(R.id.addDelButtons);
        newCarButtons = (LinearLayout) findViewById(R.id.newCarButtons);
    }

    private void initListView() {
        fillListViewData();
        initListViewOnItemClick();
    }

    private void fillListViewData() {
        carsDbAdapter = new CarsDbAdapter(getApplicationContext());
        carsDbAdapter.open();
        getAllCars();
        listAdapter = new CarProfilesAdapter(this, cars);
        lvCars.setAdapter(listAdapter);
    }

    private void getAllCars() {
        cars = new ArrayList<CarProfiles>();
        carsCursor = getAllEntriesFromDb();
        updateCarList();
    }

    private Cursor getAllEntriesFromDb() {
        carsCursor = carsDbAdapter.getAllCars();
        if(carsCursor != null) {
            startManagingCursor(carsCursor);
            carsCursor.moveToFirst();
        }
        return carsCursor;
    }

    private void updateCarList() {
        if(carsCursor != null && carsCursor.moveToFirst()) {
            do {
                long id = carsCursor.getLong(CarsDbAdapter.ID_COLUMN);
                String rejestracja = carsCursor.getString(CarsDbAdapter.REJESTRACJA_COLUMN);
                boolean usun = carsCursor.getInt(CarsDbAdapter.USUN_COLUMN) > 0;
                String marka = carsCursor.getString(CarsDbAdapter.MARKA_COLUMN);
                String ubezpieczenie = carsCursor.getString(CarsDbAdapter.UBEZPIECZENIE_COLUMN);
                String przeglad = carsCursor.getString(CarsDbAdapter.PRZEGLAD_COLUMN);

                cars.add(new CarProfiles(id, rejestracja, usun, marka, ubezpieczenie, przeglad));

            } while(carsCursor.moveToNext());
        }
    }

    @Override
    protected void onDestroy() {
        if(carsDbAdapter != null)
            carsDbAdapter.close();
        super.onDestroy();
    }

    private void initListViewOnItemClick() {
        lvCars.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                CarProfiles car = cars.get(position);
                if(car.isUsun()){
                    carsDbAdapter.updateCars(car.getId(), car.getRejestracja(), false, car.getMarka(), car.getUbezpieczenie(), car.getPrzeglad());
                } else {
                    carsDbAdapter.updateCars(car.getId(), car.getRejestracja(), true, car.getMarka(), car.getUbezpieczenie(), car.getPrzeglad());
                }
                updateListViewData();
            }
        });
    }

    private void updateListViewData() {
        carsCursor.requery();
        cars.clear();
        updateCarList();
        listAdapter.notifyDataSetChanged();
    }

    private void initButtonsOnClickListeners() {
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.dodajPojazd:
                        addNewCar();
                        break;
                    case R.id.zapisz:
                        saveNewCar();
                        break;
                    case R.id.anuluj:
                        cancelNewCar();
                        break;
                    case R.id.usunZaznaczone:
                        clearCar();
                        break;
                    default:
                        break;
                }
            }
        };
        dodajPojazd.setOnClickListener(onClickListener);
        usunZaznaczone.setOnClickListener(onClickListener);
        zapisz.setOnClickListener(onClickListener);
        anuluj.setOnClickListener(onClickListener);
    }

    private void showOnlyNewCarPanel() {
        setVisibilityOf(addDelButtons, false);
        setVisibilityOf(newCarButtons, true);
        setVisibilityOf(numerRejestracyjny, true);
        setVisibilityOf(marka, true);
        setVisibilityOf(ubezpieczenie, true);
        setVisibilityOf(przeglad, true);
    }

    private void showOnlyControlPanel() {
        setVisibilityOf(addDelButtons, true);
        setVisibilityOf(newCarButtons, false);
        setVisibilityOf(numerRejestracyjny, false);
        setVisibilityOf(marka, false);
        setVisibilityOf(ubezpieczenie, false);
        setVisibilityOf(przeglad, false);
    }


    private void setVisibilityOf(View v, boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        v.setVisibility(visibility);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(numerRejestracyjny.getWindowToken(), 0);
    }

    private void addNewCar(){
        showOnlyNewCarPanel();
    }


    private void saveNewCar(){
        String carDescription = numerRejestracyjny.getText().toString();
        String carInfo = marka.getText().toString();
        String insurance = ubezpieczenie.getText().toString();
        String przegladDate = przeglad.getText().toString();

        if(carDescription.equals("") || carInfo.equals("") || insurance.equals("") || przegladDate.equals("")){
            numerRejestracyjny.setError("Your car description couldn't be empty string.");
        } else {
            carsDbAdapter.insertCars(carDescription, carInfo, insurance, przegladDate);
            numerRejestracyjny.setText("");
            marka.setText("");
            ubezpieczenie.setText("");
            przeglad.setText("");
            hideKeyboard();
            showOnlyControlPanel();
        }

        updateListViewData();
    }

    private void cancelNewCar() {
        numerRejestracyjny.setText("");
        marka.setText("");
        ubezpieczenie.setText("");
        przeglad.setText("");
        showOnlyControlPanel();
    }

    private void clearCar(){
        if(carsCursor != null && carsCursor.moveToFirst()) {
            do {
                if(carsCursor.getInt(CarsDbAdapter.USUN_COLUMN) == 1) {
                    long id = carsCursor.getLong(CarsDbAdapter.ID_COLUMN);
                    carsDbAdapter.deleteCars(id);
                }
            } while (carsCursor.moveToNext());
        }
        updateListViewData();
    }
}