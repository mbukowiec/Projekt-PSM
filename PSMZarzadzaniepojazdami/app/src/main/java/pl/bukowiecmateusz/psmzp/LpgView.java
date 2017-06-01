package pl.bukowiecmateusz.psmzp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class LpgView extends Activity {
    // Declare Variables
    String stacja;
    String lpg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.lpg_price_item);

        Intent i = getIntent();
        // Get the result of rank
        stacja = i.getStringExtra("stacja");
        // Get the result of country
        lpg = i.getStringExtra("on");


        // Locate the TextViews in singleitemview.xml
        TextView tvStacja = (TextView) findViewById(R.id.stacja);
        TextView tvOn = (TextView) findViewById(R.id.lpg);

        // Set results to the TextViews
        tvStacja.setText(stacja);
        tvOn.setText(lpg);

    }
}