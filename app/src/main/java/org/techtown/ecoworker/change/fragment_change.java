package org.techtown.ecoworker.change;


import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.Spinner;
        import androidx.fragment.app.Fragment;
        import org.techtown.ecoworker.R;

public class fragment_change extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_change, container, false);

        final String[] food = {"식품명", "요거트", "계란", "식빵", "액상커피", "우유", "슬라이스 치즈", "두부", "라면", "냉동만두", "고추장", "참기름", "식용유", "참치캔"};

        Spinner spinnerFood= (Spinner) rootview.findViewById(R.id.spinnerFood);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, food);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFood.setAdapter(adapter);


        Button expAlarm = (Button) rootview.findViewById(R.id.Alarm);
        expAlarm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final String[] versionArray = new String[]{"7일 전", "3일 전", "1일 전", "당일"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("기간 설정");
                dlg.setSingleChoiceItems(versionArray, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                dlg.setPositiveButton("설정", null);
                dlg.show();
            }
        });
        return rootview;
    }
}
