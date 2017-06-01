package pl.bukowiecmateusz.psmzp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class PbPriceTab extends AppCompatActivity {
    ListView lv_pb;
    PbLvAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    static String STACJA = "stacja";
    static String PB = "pb";
    String miasto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb_price_tab);
        new JsoupListView().execute();

        Intent cityIntent = getIntent();
        // Get the result of rank
        miasto = cityIntent.getStringExtra("miasto");
    }
    String url;
    // Title AsyncTask
    private class JsoupListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(PbPriceTab.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Ceny paliw");
            // Set progressdialog message
            mProgressDialog.setMessage("Pobieranie danych...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            url = String.valueOf("http://www.stacjebenzynowe.pl/search_stacje.php?action=srch&searchstacja_woj=&searchstacja_miasto="+miasto+"&tankowanie_miast=&paliwo=Pb95&sort=2");
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();

            try {
                // Connect to the Website URL
                Document doc = Jsoup.connect(url).get();
                // Identify Table Class "worldpopulation"
                for (Element table : doc.select("tr[bgcolor='#4C5662']")) {

                    // Identify all the table row's(tr)
                    for (Element row : table.select("tr:gt(1)")) {
                        HashMap<String, String> map = new HashMap<String, String>();

                        // Identify all the table cell's(td)
                        Elements tds = row.select("td");

                        // Retrive Jsoup Elements
                        // Get the first td
                        map.put("stacja", tds.get(1).text());
                        // Get the second td
                        map.put("pb", tds.get(2).text());
                        // Set all extracted Jsoup Elements into the array
                        arraylist.add(map);
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            lv_pb = (ListView) findViewById(R.id.lv_pb);
            // Pass the results into ListViewAdapter.java
            adapter = new PbLvAdapter(PbPriceTab.this, arraylist);
            // Set the adapter to the ListView
            lv_pb.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }
}
