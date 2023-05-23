package tv.master_of_spirit.diabetesdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewRechnungActivity extends AppCompatActivity {

    RecyclerView recycleViewRechnung;
    ArrayList<String> data_pro100, data_gewicht;
    String data_time;
    RechnungAdapter rechnungAdapter;
    MyDatabaseHelper myDB = new MyDatabaseHelper(ViewRechnungActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rechnung);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("N/A");

        recycleViewRechnung = findViewById(R.id.recycleViewRechnung);

        data_pro100 = new ArrayList<>();
        data_gewicht = new ArrayList<>();

        getTime();

        loadAdapter();
    }

    private void getTime() {
        if (getIntent().hasExtra("timestamp")) {
            data_time = getIntent().getStringExtra("timestamp");
            getIntent().removeExtra("timestamp");
            getSupportActionBar().setTitle(String.valueOf(data_time));
        }
    }

    private void loadAdapter() {
        storeDataInArrays();

        rechnungAdapter = new RechnungAdapter(ViewRechnungActivity.this, data_pro100, data_gewicht);
        recycleViewRechnung.setAdapter(rechnungAdapter);
        recycleViewRechnung.setLayoutManager(new LinearLayoutManager(ViewRechnungActivity.this));
    }

    private void storeDataInArrays() {
        Cursor cursor = myDB.getRechnung(data_time);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data!", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                data_pro100.add(cursor.getString(1));
                data_gewicht.add(cursor.getString(2));
            }
        }
    }
}