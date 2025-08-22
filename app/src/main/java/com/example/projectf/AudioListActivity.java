package com.example.projectf;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectf.data.QuranData;

import java.util.ArrayList;
import java.util.List;

public class AudioListActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM = "EXTRA_ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);

        ListView listView = findViewById(R.id.audioListView);

        List<SurahItem> data = new ArrayList<>();
        for (QuranData.Surah s : QuranData.getSurahList()) {
            int rawResId = getResources().getIdentifier(
                    "surah_" + s.number, "raw", getPackageName()
            );

            SurahItem item = new SurahItem(
                    s.number,
                    s.name,
                    s.ayahs,
                    s.type,
                    rawResId
            );

            data.add(item);
        }

        AudioListAdapter adapter = new AudioListAdapter(this, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            SurahItem selected = adapter.getItem(position);
            Intent i = new Intent(AudioListActivity.this, PlayerActivity.class);
            i.putExtra(EXTRA_ITEM, selected); // Parcelable
            startActivity(i);
        });
    }
}
