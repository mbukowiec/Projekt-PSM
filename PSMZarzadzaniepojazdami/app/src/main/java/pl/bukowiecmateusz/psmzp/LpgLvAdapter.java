package pl.bukowiecmateusz.psmzp;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LpgLvAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public LpgLvAdapter(Context context,
                           ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView stacja;
        TextView lpg;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.lpg_price_item, parent, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        stacja = (TextView) itemView.findViewById(R.id.stacja);
        lpg = (TextView) itemView.findViewById(R.id.lpg);

        // Capture position and set results to the TextViews
        stacja.setText(resultp.get(LpgPriceTab.STACJA));
        lpg.setText(resultp.get(LpgPriceTab.LPG));

        // Capture ListView item click
        itemView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);
                Intent intent = new Intent(context, LpgView.class);
                // Pass all data rank
                intent.putExtra("stacja", resultp.get(LpgPriceTab.STACJA));
                intent.putExtra("lpg",resultp.get(LpgPriceTab.LPG));
                // Start SingleItemView Class
                context.startActivity(intent);

            }
        });
        return itemView;
    }
}
