package com.example.projectf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnIndex, btnFav, btnSettings, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIndex = findViewById(R.id.btnIndex);
        btnFav = findViewById(R.id.btnFav);
        btnSettings = findViewById(R.id.btnSettings);
        btnAbout = findViewById(R.id.btnAbout);

        // الزر الأول يفتح قائمة الصوتيات
        btnIndex.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AudioListActivity.class);
            startActivity(intent);
        });
    }
}
