package tv.master_of_spirit.diabetesdiary;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    List<TextInputLayout> multiinputlist = new ArrayList<>();
    List<String> defaulthintlist = new ArrayList<>();
    ArrayList<Double> multiplikatorliste = new ArrayList<>();
    Button multsetbtn;
    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_einstellungen));

        myDB = new MyDatabaseHelper(SettingsActivity.this);

        createmultiinputlist();
        createdefaulthintlist();
        createmultiplikatorliste();

        multsetbtn = findViewById(R.id.multiplikatorWerteSet);

        for (TextInputLayout input : multiinputlist) {
            int index = multiinputlist.indexOf(input);
            input.setHint(defaulthintlist.get(index) + " (" + multiplikatorliste.get(index) + ")");
        }

        multsetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSettings();
            }
        });

    }

    private void createmultiinputlist() {
        multiinputlist.add(findViewById(R.id.inputMorgen));
        multiinputlist.add(findViewById(R.id.inputZwischenMorgen));
        multiinputlist.add(findViewById(R.id.inputMittag));
        multiinputlist.add(findViewById(R.id.inputZwischenMittag));
        multiinputlist.add(findViewById(R.id.inputAbend));
        multiinputlist.add(findViewById(R.id.inputSpätmahlzeit));
    }

    private void createdefaulthintlist() {
        defaulthintlist.add("Morgens");
        defaulthintlist.add("Zwischenmahlzeit Morgens");
        defaulthintlist.add("Mittags");
        defaulthintlist.add("Zwischenmahlzeit Mittags");
        defaulthintlist.add("Abends");
        defaulthintlist.add("Spätmahlzeit");
    }

    private void createmultiplikatorliste() {
        multiplikatorliste.add(2.0);
        multiplikatorliste.add(1.5);
        multiplikatorliste.add(1.5);
        multiplikatorliste.add(1.4);
        multiplikatorliste.add(1.3);
        multiplikatorliste.add(1.3);
        Cursor cursor = myDB.getSettings();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data!", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                if (defaulthintlist.contains(cursor.getString(0))) {
                    multiplikatorliste.set(defaulthintlist.indexOf(cursor.getString(0)), cursor.getDouble(1));
                }
            }
        }
    }

    private void setSettings() {
        for (TextInputLayout i : multiinputlist) {
            if (i.getEditText().getText().length() > 0) {
                Double multiplikator = Double.parseDouble(i.getEditText().getText().toString());
                String defaultname = defaulthintlist.get(multiinputlist.indexOf(i));
                multiplikatorliste.set(multiinputlist.indexOf(i), multiplikator);
                i.setHint(defaultname.split(" ")[0] + " (" + multiplikator + ")");
                myDB.setSettings(defaultname, multiplikator);
            }
            i.getEditText().setText(null);
            i.getEditText().clearFocus();
        }
    }
}