package tv.master_of_spirit.diabetesdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActivityActivity extends AppCompatActivity {

    View activityView, activityview_add;
    FloatingActionButton add_Button, save_activity;
    TextInputLayout input_time, input_activity;

    MyDatabaseHelper myDB = new MyDatabaseHelper(ActivityActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_activitis));

        add_Button = findViewById(R.id.addactivity_button);
        activityView = findViewById(R.id.activityView);
        activityview_add = findViewById(R.id.add_activityView);
        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addActivity();
            }
        });
    }

    private void addActivity() {
        add_Button.setVisibility(View.INVISIBLE);
        activityView.setVisibility(View.INVISIBLE);
        activityview_add.setVisibility(View.VISIBLE);

        input_time = findViewById(R.id.input_activitytime);
        input_activity = findViewById(R.id.input_activity);
        save_activity = findViewById(R.id.saveActivity);

        String date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
        input_time.getEditText().setText(date);
        input_activity.getEditText().setText(null);

        save_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_activity.getEditText().getText().length() > 0 && input_time.getEditText().getText().length() > 0) {
                    myDB.addActivity(input_time.getEditText().getText().toString(), input_activity.getEditText().getText().toString());
                    activityview_add.setVisibility(View.INVISIBLE);
                    add_Button.setVisibility(View.VISIBLE);
                    activityView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}