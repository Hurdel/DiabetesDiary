package tv.master_of_spirit.diabetesdiary;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TagebuchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> data_id, data_time, data_value;
    CustomAdapter_clickable customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tagebuch_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_tagebuch));

        recyclerView = findViewById(R.id.tagebuchView);
        myDB = new MyDatabaseHelper(TagebuchActivity.this);
        data_id = new ArrayList<>();
        data_time = new ArrayList<>();
        data_value = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter_clickable(TagebuchActivity.this, data_id, data_time, data_value);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TagebuchActivity.this));
    }

    private void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data!", Toast.LENGTH_SHORT).show();
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