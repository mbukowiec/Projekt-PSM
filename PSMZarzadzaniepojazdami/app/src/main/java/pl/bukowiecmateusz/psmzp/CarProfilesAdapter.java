package pl.bukowiecmateusz.psmzp;

        import java.util.List;

        import android.app.Activity;
        import android.graphics.Color;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

public class CarProfilesAdapter extends ArrayAdapter<CarProfiles> {
    private Activity context;
    private List<CarProfiles> tasks;
    public CarProfilesAdapter(Activity context, List<CarProfiles> tasks) {
        super(context, R.layout.car_list_item, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    static class ViewHolder {
        public TextView typeRejestracja;
        public TextView typeMarka;
        public TextView typeUbezpieczenie;
        public TextView typePrzeglad;
        public RelativeLayout carItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View rowView = convertView;
        if(rowView == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            rowView = layoutInflater.inflate(R.layout.car_list_item, null, true);
            viewHolder = new ViewHolder();
            viewHolder.typeRejestracja = (TextView) rowView.findViewById(R.id.typeRejestracja);
            viewHolder.typeMarka = (TextView) rowView.findViewById(R.id.typeMarka);
            viewHolder.typeUbezpieczenie = (TextView) rowView.findViewById(R.id.typeUbezpieczenie);
            viewHolder.typePrzeglad = (TextView) rowView.findViewById(R.id.typePrzeglad);
            viewHolder.carItem = (RelativeLayout) rowView.findViewById(R.id.CarListItem);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        CarProfiles task = tasks.get(position);
        viewHolder.typeRejestracja.setText(task.getRejestracja());
        viewHolder.typeMarka.setText(task.getMarka());
        viewHolder.typeUbezpieczenie.setText(task.getUbezpieczenie());
        viewHolder.typePrzeglad.setText(task.getPrzeglad());

        if(task.isUsun()) {
            viewHolder.carItem.setBackgroundColor(Color.LTGRAY);


        } else {
            viewHolder.carItem.setBackgroundColor(Color.WHITE);

        }
        return rowView;
    }
}