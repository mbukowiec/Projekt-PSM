package pl.bukowiecmateusz.psmzp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class OnView extends Activity {
    // Declare Variables
    String stacja;
    String on;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.on_price_item);

        Intent i = getIntent();
        // Get the result of rank
        stacja = i.getStringExtra("stacja");
        // Get the result of country
        on = i.getStringExtra("on");


        // Locate the TextViews in singleitemview.xml
        TextView tvStacja = (TextView) findViewById(R.id.stacja);
        TextView tvOn = (TextView) findViewById(R.id.on);

        // Set results to the TextViews
        tvStacja.setText(stacja);
        tvOn.setText(on);

    }
}