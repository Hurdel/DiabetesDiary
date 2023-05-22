package tv.master_of_spirit.diabetesdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_activitis));

        //TODO
    }
}