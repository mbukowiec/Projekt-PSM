package pl.bukowiecmateusz.psmzp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PbView extends Activity {
    // Declare Variables
    String stacja;
    String pb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.pb_price_item);

        Intent i = getIntent();
        // Get the result of rank
        stacja = i.getStringExtra("stacja");
        // Get the result of country
        pb = i.getStringExtra("pb");

        // Locate the TextViews in singleitemview.xml
        TextView tvStacja = (TextView) findViewById(R.id.stacja);
        TextView tvPb = (TextView) findViewById(R.id.pb);

        // Set results to the TextViews
        tvStacja.setText(stacja);
        tvPb.setText(pb);
    }
}