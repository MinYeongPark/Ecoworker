package org.techtown.ecoworker.alarm;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import androidx.fragment.app.Fragment;

import org.techtown.ecoworker.R;

public class fragment_alarm extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_alarm, container, false);

        @SuppressWarnings("deprecation")
        class MainActivity extends TabActivity {

            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                TabHost tabHost = getTabHost();

                TabSpec tabSpecSong = tabHost.newTabSpec("SONG").setIndicator("음악별");
                tabSpecSong.setContent(R.id.tabSong);
                tabHost.addTab(tabSpecSong);

                TabSpec tabSpecArtist = tabHost.newTabSpec("ARTIST")
                        .setIndicator("가수별");
                tabSpecArtist.setContent(R.id.tabArtist);
                tabHost.addTab(tabSpecArtist);

                TabSpec tabSpecAlbum = tabHost.newTabSpec("ALBUM").setIndicator("앨범별");
                tabSpecAlbum.setContent(R.id.tabAlbum);
                tabHost.addTab(tabSpecAlbum);

                tabHost.setCurrentTab(0);
            }
        }
        return rootview;
    }
}
