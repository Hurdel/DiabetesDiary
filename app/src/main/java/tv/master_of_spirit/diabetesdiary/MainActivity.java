package tv.master_of_spirit.diabetesdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    FrameLayout toCalculator, toDiary, toActivity, toSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toCalculator = findViewById(R.id.tocalcutator);
        toDiary = findViewById(R.id.todiary);
        toActivity = findViewById(R.id.toactivity);
        toSettings = findViewById(R.id.tosettings);

        toCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCalculator();
            }
        });

        toDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDiary();
            }
        });

        toActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivitis();
            }
        });

        toSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettings();
            }
        });
    }

    private void startCalculator() {
        Intent rechnerinzent = new Intent(MainActivity.this, RechnerActivity.class);
        startActivity(rechnerinzent);
        //TODO eingabe erneuern
    }

    private void startDiary() {
        Intent Diaryintent = new Intent(MainActivity.this, TagebuchActivity.class);
        startActivity(Diaryintent);
    }

    private void startActivitis() {
        Intent activityintent = new Intent(MainActivity.this, ActivityActivity.class);
        startActivity(activityintent);
    }

    private void startSettings() {
        Intent settingsintent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(settingsintent);
        //TODO ändern
    }
}