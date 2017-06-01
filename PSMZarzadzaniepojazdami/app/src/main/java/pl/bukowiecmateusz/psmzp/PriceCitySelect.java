package pl.bukowiecmateusz.psmzp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PriceCitySelect extends Activity {

    private ListView cityListView;
    static String MIASTO = "";
    TextView miasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_city_select);

        cityListView = (ListView) findViewById(R.id.lvCitySelect);

        String cities[] = {
                "Kraków",
                "Warszawa",
                "Gdańsk",
                "Białystok",
                "Kielce",
                "Poznań",
                "Toruń",
                "Bydgoszcz",
                "Lublin",
                "Rzeszów",
                "Łódź",
                "Olsztyn",
                "Sopot",
                "Wrocław",
                "Gdynia",
                "Katowice",
                "Opole",
                "Szczecin"};

        ListAdapter cityAdapter = new ArrayAdapter<String>(this, R.layout.price_city_select_item, cities);
        ListView cityListView = (ListView) findViewById(R.id.lvCitySelect);
        cityListView.setAdapter(cityAdapter);

        cityListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String cities = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(PriceCitySelect.this, "Wybrano miasto "+cities, Toast.LENGTH_LONG).show();

                        MIASTO = String.valueOf(parent.getItemAtPosition(position));

                            Intent myIntent = new Intent(view.getContext(), PriceTypeSelect.class);
                            myIntent.putExtra("miasto", PriceCitySelect.MIASTO);
                            startActivityForResult(myIntent, 0);

                    }

        });
    }
}
