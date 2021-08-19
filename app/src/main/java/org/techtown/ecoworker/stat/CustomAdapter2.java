package org.techtown.ecoworker.stat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.techtown.ecoworker.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter2 extends ArrayAdapter implements AdapterView.OnItemClickListener {
    private Context context;
    private List list;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
    }

    class ViewHolder {
        public TextView foodName;
        public TextView amount;
        public TextView carbonAmount;
    }

    public CustomAdapter2(Context context, int a, ArrayList list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.round_theme_2, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.foodName = (TextView) convertView.findViewById(R.id.foodName);
        viewHolder.amount = (TextView) convertView.findViewById(R.id.amount);
        viewHolder.carbonAmount = (TextView) convertView.findViewById(R.id.carbonAmount);
        final Stat stat = (Stat) list.get(position);
        viewHolder.foodName.setText(stat.getFoodName());
        viewHolder.amount.setText(String.valueOf(stat.getAmount()));
        viewHolder.carbonAmount.setText(String.valueOf(stat.getCarbonAmount()));
        return convertView;
    }
}
