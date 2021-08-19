package org.techtown.ecoworker.alarm;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.techtown.ecoworker.R;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {
    private Context context;
    private List list;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
    }

    class ViewHolder {
        public TextView foodName;
        public TextView alarmDate;
        public TextView expDate;
        public TextView useDate;
    }

    public CustomAdapter(Context context, int a, ArrayList list) {
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
            convertView = layoutInflater.inflate(R.layout.round_theme, parent, false);
        }

        viewHolder = new ViewHolder();

        viewHolder.foodName = (TextView) convertView.findViewById(R.id.foodName);
        viewHolder.alarmDate = (TextView) convertView.findViewById(R.id.alarmDate);
        viewHolder.expDate = (TextView) convertView.findViewById(R.id.expDate);
        viewHolder.useDate = (TextView) convertView.findViewById(R.id.useDate);

        final Alarm alarm = (Alarm) list.get(position);
        viewHolder.foodName.setText(alarm.getFoodName());
        viewHolder.alarmDate.setText(String.valueOf(alarm.getAlarmDate()));
        viewHolder.expDate.setText(alarm.getExpDate());
        viewHolder.useDate.setText(alarm.getUseDate());

        return convertView;
    }
}
