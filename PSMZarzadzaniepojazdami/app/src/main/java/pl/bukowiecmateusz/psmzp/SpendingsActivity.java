package pl.bukowiecmateusz.psmzp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
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

public class SpendingsActivity extends Activity {
    private Button Spending_Dodaj;
    private Button Spending_Usun;
    private Button Spending_Zapisz;
    private Button Spending_Anuluj;
    private EditText Spending_Opis;
    private EditText Spending_Cena;
    private ListView lvSpendings;
    private LinearLayout SpendingControlButtons;
    private LinearLayout NewSpendingButtons;

    private SpendingsDbAdapter spendingsDbAdapter;
    private Cursor spendingsCursor;
    private List<Spendings> spendings;
    private SpendingsAdapter spendingListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spendings_main);
        initUiElements();
        initListView();
        initButtonsOnClickListeners();
    }

    private void initUiElements() {
        Spending_Dodaj = (Button) findViewById(R.id.Spending_Dodaj);
        Spending_Usun = (Button) findViewById(R.id.Spending_Usun);
        Spending_Zapisz = (Button) findViewById(R.id.Spending_Zapisz);
        Spending_Anuluj = (Button) findViewById(R.id.Spending_Anuluj);
        Spending_Opis = (EditText) findViewById(R.id.Spendings_Opis);
        Spending_Cena = (EditText) findViewById(R.id.Spendings_Cena);
        lvSpendings = (ListView) findViewById(R.id.lvSpendings);
        SpendingControlButtons = (LinearLayout) findViewById(R.id.SpendingControlButtons);
        NewSpendingButtons = (LinearLayout) findViewById(R.id.NewSpendingButtons);
    }

    private void initListView() {
        fillListViewData();
        initListViewOnItemClick();
    }

    private void fillListViewData() {
        spendingsDbAdapter = new SpendingsDbAdapter(getApplicationContext());
        spendingsDbAdapter.open();
        getAllSpendings();
        spendingListAdapter = new SpendingsAdapter(this, spendings);
        lvSpendings.setAdapter(spendingListAdapter);
    }

    private void getAllSpendings() {
        spendings = new ArrayList<Spendings>();
        spendingsCursor = getAllEntriesFromDb();
        updateSpendingsList();
    }

    private Cursor getAllEntriesFromDb() {
        spendingsCursor = spendingsDbAdapter.getAllSpendings();
        if(spendingsCursor != null) {
            startManagingCursor(spendingsCursor);
            spendingsCursor.moveToFirst();
        }
        return spendingsCursor;
    }

    private void updateSpendingsList() {
        if(spendingsCursor != null && spendingsCursor.moveToFirst()) {
            do {
                long id = spendingsCursor.getLong(SpendingsDbAdapter.ID_COLUMN);
                String opis = spendingsCursor.getString(SpendingsDbAdapter.OPIS_COLUMN);
                String cena = spendingsCursor.getString(SpendingsDbAdapter.CENA_COLUMN);
                boolean usun = spendingsCursor.getInt(SpendingsDbAdapter.USUN_COLUMN) > 0 ? true : false;
                spendings.add(new Spendings(id, opis, cena, usun));
            } while(spendingsCursor.moveToNext());
        }
    }

    @Override
    protected void onDestroy() {
        if(spendingsDbAdapter != null)
            spendingsDbAdapter.close();
        super.onDestroy();
    }

    private void initListViewOnItemClick() {
        lvSpendings.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                Spendings spending = spendings.get(position);
                if(spending.isUsun()){
                    spendingsDbAdapter.updateSpendings(spending.getId(), spending.getOpis(), spending.getCena(), false);
                } else {
                    spendingsDbAdapter.updateSpendings(spending.getId(), spending.getOpis(), spending.getCena(), true);
                }
                updateListViewData();
            }
        });
    }

    private void updateListViewData() {
        spendingsCursor.requery();
        spendings.clear();
        updateSpendingsList();
        spendingListAdapter.notifyDataSetChanged();
    }

    private void initButtonsOnClickListeners() {
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.Spending_Dodaj:
                        addSpending();
                        break;
                    case R.id.Spending_Zapisz:
                        saveSpending();
                        break;
                    case R.id.Spending_Anuluj:
                        cancelSpending();
                        break;
                    case R.id.Spending_Usun:
                        clearSpendings();
                        break;
                    default:
                        break;
                }
            }
        };
        Spending_Dodaj.setOnClickListener(onClickListener);
        Spending_Usun.setOnClickListener(onClickListener);
        Spending_Zapisz.setOnClickListener(onClickListener);
        Spending_Anuluj.setOnClickListener(onClickListener);
    }

    private void showOnlyNewSpendingPanel() {
        setVisibilityOf(SpendingControlButtons, false);
        setVisibilityOf(NewSpendingButtons, true);
        setVisibilityOf(Spending_Opis, true);
        setVisibilityOf(Spending_Cena, true);
    }

    private void showOnlyControlPanel() {
        setVisibilityOf(SpendingControlButtons, true);
        setVisibilityOf(NewSpendingButtons, false);
        setVisibilityOf(Spending_Opis, false);
        setVisibilityOf(Spending_Cena, false);
    }

    private void setVisibilityOf(View v, boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        v.setVisibility(visibility);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(Spending_Opis.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(Spending_Cena.getWindowToken(), 0);
    }

    private void addSpending(){
        showOnlyNewSpendingPanel();
    }

    private void saveSpending(){
        String opis = Spending_Opis.getText().toString();
        String cena = Spending_Cena.getText().toString();
        if(opis.equals("") || cena.equals("")){
            Spending_Opis.setError("Opis nie może być pusty!");
            Spending_Cena.setError("Cena nie może być pusta!");
        } else {
            spendingsDbAdapter.insertSpending(opis, cena);
            Spending_Opis.setText("");
            Spending_Cena.setText("");
            hideKeyboard();
            showOnlyControlPanel();
        }
        updateListViewData();
    }

    private void cancelSpending() {
        Spending_Opis.setText("");
        Spending_Cena.setText("");
        showOnlyControlPanel();
    }

    private void clearSpendings(){
        if(spendingsCursor != null && spendingsCursor.moveToFirst()) {
            do {
                if(spendingsCursor.getInt(SpendingsDbAdapter.USUN_COLUMN) == 1) {
                    long id = spendingsCursor.getLong(SpendingsDbAdapter.ID_COLUMN);
                    spendingsDbAdapter.deleteSpending(id);
                }
            } while (spendingsCursor.moveToNext());
        }
        updateListViewData();
    }
}