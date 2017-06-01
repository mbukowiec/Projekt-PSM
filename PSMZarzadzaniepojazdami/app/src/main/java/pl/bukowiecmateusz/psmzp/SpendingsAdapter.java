package pl.bukowiecmateusz.psmzp;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpendingsAdapter extends ArrayAdapter<Spendings> {
    private Activity context;
    private List<Spendings> spendings;
    public SpendingsAdapter(Activity context, List<Spendings> spendings) {
        super(context, R.layout.spendings_item, spendings);
        this.context = context;
        this.spendings = spendings;
    }

    static class ViewHolder {
        public TextView spending_item_opis;
        public TextView spending_item_cena;
        public ConstraintLayout spendingListItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View rowView = convertView;
        if(rowView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.spendings_item, null, true);
            viewHolder = new ViewHolder();
            viewHolder.spending_item_opis = (TextView) rowView.findViewById(R.id.spending_item_opis);
            viewHolder.spending_item_cena = (TextView) rowView.findViewById(R.id.spending_item_cena);
            viewHolder.spendingListItem = (ConstraintLayout) rowView.findViewById(R.id.spendingListItem);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        Spendings spending = spendings.get(position);
        viewHolder.spending_item_opis.setText(spending.getOpis());
        viewHolder.spending_item_cena.setText(spending.getCena());
        if(spending.isUsun()) {
            viewHolder.spendingListItem.setBackgroundColor(Color.LTGRAY);
        } else {
            viewHolder.spendingListItem.setBackgroundColor(Color.WHITE);
        }
        return rowView;
    }
}