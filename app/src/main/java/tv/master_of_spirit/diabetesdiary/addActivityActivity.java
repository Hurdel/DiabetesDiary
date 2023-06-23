package tv.master_of_spirit.diabetesdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class addActivityActivity extends AppCompatActivity {

    FloatingActionButton save_activity;
    TextInputLayout input_time, input_activity;

    MyDatabaseHelper myDB = new MyDatabaseHelper(addActivityActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Aktivität hinzufügen");

        input_time = findViewById(R.id.input_activitytime);
        input_activity = findViewById(R.id.input_activity);
        save_activity = findViewById(R.id.saveActivity);

        String date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
        input_time.getEditText().setText(date);

        input_activity.requestFocus();


        save_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_activity.getEditText().getText().length() > 0 && input_time.getEditText().getText().length() > 0) {
                    myDB.addActivity(input_time.getEditText().getText().toString(), input_activity.getEditText().getText().toString());

                    Intent intent = new Intent(addActivityActivity.this, ActivityActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}