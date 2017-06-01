package pl.bukowiecmateusz.psmzp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PriceTypeSelect extends AppCompatActivity {
    String miasto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_type_select);

        Intent cityIntent = getIntent();
        // Get the result of rank
        miasto = cityIntent.getStringExtra("miasto");
    }

    public void openPbPriceTab(View view) {
        Intent intent = new Intent(getApplicationContext(), PbPriceTab.class);
        intent.putExtra("miasto", PriceCitySelect.MIASTO);
        startActivityForResult(intent, 0);
    }

    public void openOnPriceTab(View view) {
        Intent intent = new Intent(getApplicationContext(), OnPriceTab.class);
        intent.putExtra("miasto", PriceCitySelect.MIASTO);
        startActivityForResult(intent, 0);
    }

    public void openLpgPriceTab(View view) {
        Intent intent = new Intent(getApplicationContext(), LpgPriceTab.class);
        intent.putExtra("miasto", PriceCitySelect.MIASTO);
        startActivityForResult(intent, 0);
    }
}
