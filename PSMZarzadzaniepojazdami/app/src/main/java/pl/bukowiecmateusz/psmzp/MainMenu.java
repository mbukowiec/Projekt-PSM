package pl.bukowiecmateusz.psmzp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void openInfoTab(View view) {
        Intent intent = new Intent(getApplicationContext(), Info.class);
        startActivity(intent);
    }

    public void openProfileTab(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void openSpendTab(View view) {
        Intent intent = new Intent(getApplicationContext(), SpendingsActivity.class);
        startActivity(intent);
    }

    public void openCitySelect(View view) {
        Intent intent = new Intent(getApplicationContext(), PriceCitySelect.class);
        startActivity(intent);
    }

    public void openPodsumowanieTab(View view) {
        Intent intent = new Intent(getApplicationContext(), Podsumowanie.class);
        startActivity(intent);
    }
}
