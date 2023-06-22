package tv.master_of_spirit.diabetesdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ActivityActivity extends AppCompatActivity {

    RecyclerView activityView;
    FloatingActionButton add_Button;
    ArrayList<String> data_id, data_time, data_value;
    CustomAdapter customAdapter;

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

        data_id = new ArrayList<>();
        data_time = new ArrayList<>();
        data_value = new ArrayList<>();

        loadAdapter();

        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addActivity();
            }
        });
    }

    private void addActivity() {
        Intent intent = new Intent(ActivityActivity.this, addActivityActivity.class);
        startActivity(intent);
    }

    private void loadAdapter() {
        storeDataInArrays();

        activityView.removeAllViewsInLayout();

        customAdapter = new CustomAdapter(ActivityActivity.this, data_id, data_time, data_value);
        activityView.setAdapter(customAdapter);
        activityView.setLayoutManager(new LinearLayoutManager(ActivityActivity.this));
    }

    private void storeDataInArrays() {
        Cursor cursor = myDB.getActivitys();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Activity!", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                data_id.add(cursor.getString(0));
                data_time.add(cursor.getString(1));
                data_value.add(cursor.getString(2));
            }
        }
    }
}