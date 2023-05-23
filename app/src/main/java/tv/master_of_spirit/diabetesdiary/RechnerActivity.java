package tv.master_of_spirit.diabetesdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RechnerActivity extends AppCompatActivity {

    //    allgemein
    Integer seletionid;
    Double resultIE;
    Double resultKH;
    Double ausgleich;
    Double blutzucker;
    Double multiplikator;
    TextView ResultText;
    MyDatabaseHelper myDB = new MyDatabaseHelper(RechnerActivity.this);

    //    Rechner
    Spinner MultiplikatorSelection;
    FloatingActionButton save_Button;
    ArrayList<Double> multiplikatorliste = new ArrayList<>();
    Button calcutalteButton;
    String selectedmultiplikator;
    List<String> defaulthintlist = new ArrayList<>();
    List<TextInputLayout> ikh = new ArrayList<>();
    List<TextInputLayout> igewicht = new ArrayList<>();
    ArrayList<String> data_pro100, data_gewicht;

    //    Ausgleichswert
    TextView errechnet;
    TextInputLayout input_Korrekturwert;
    TextInputLayout input_Blutzucker;
    FloatingActionButton checkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechner);
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.title_rechner));

        CreateCalculator();

        data_pro100 = new ArrayList<>();
        data_gewicht = new ArrayList<>();

        save_Button = findViewById(R.id.save_button);
        ResultText = findViewById(R.id.restext);
        save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(ResultText.getText().length() == 0)) {
                    String date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
                    String blutzuckertext;
                    if (blutzucker == null) {
                        blutzuckertext = "N/A";
                    }
                    else {
                        blutzuckertext = blutzucker + " mg/dl";
                    }
                    String value = "Blutzucker: " + blutzuckertext + "\n" +
                            "Ausgleichswert: " + ausgleich + " IE\n" +
                            "Esssen: " + resultKH + " KH -> " + resultIE + " IE\n" +
                            "Multiplikator: " + selectedmultiplikator;
                    myDB.addMessung(date, value);
                    for (int i = 0; i < data_pro100.size(); i++) {
                        myDB.addRechnung(date, data_pro100.get(i), data_gewicht.get(i));
                    }
                }
                else {
                    Toast.makeText(RechnerActivity.this, "Kein Wert zum speichern errechnent!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        createmultiplikatorliste();
        reloadMultiplicatorSelectorList();
    }

    private void CreateCalculator() {
        MultiplikatorSelection = findViewById(R.id.MultiplikaorSelection);

        reloadMultiplicatorSelectorList();
        calcutalteButton = findViewById(R.id.CalculateButton);

        ikh.add(findViewById(R.id.inputKH0));
        ikh.add(findViewById(R.id.inputKH1));
        ikh.add(findViewById(R.id.inputKH2));
        ikh.add(findViewById(R.id.inputKH3));
        ikh.add(findViewById(R.id.inputKH4));
        ikh.add(findViewById(R.id.inputKH5));
        ikh.add(findViewById(R.id.inputKH6));
        ikh.add(findViewById(R.id.inputKH7));
        ikh.add(findViewById(R.id.inputKH8));
        ikh.add(findViewById(R.id.inputKH9));

        igewicht.add(findViewById(R.id.inputGewicht0));
        igewicht.add(findViewById(R.id.inputGewicht1));
        igewicht.add(findViewById(R.id.inputGewicht2));
        igewicht.add(findViewById(R.id.inputGewicht3));
        igewicht.add(findViewById(R.id.inputGewicht4));
        igewicht.add(findViewById(R.id.inputGewicht5));
        igewicht.add(findViewById(R.id.inputGewicht6));
        igewicht.add(findViewById(R.id.inputGewicht7));
        igewicht.add(findViewById(R.id.inputGewicht8));
        igewicht.add(findViewById(R.id.inputGewicht9));

        calcutalteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });
    }

    private void setResultText() {
        MultiplikatorSelection.setSelection(seletionid);
        String resulttext = resultKH + " KH -> " + (Math.round((resultIE + ausgleich) * 10) / 10.0) + " IE";
        ResultText.setText(resulttext);
    }

    private void calculate() {
        resultIE = 0.0;
        resultKH = 0.0;
        seletionid = MultiplikatorSelection.getSelectedItemPosition();
        selectedmultiplikator = MultiplikatorSelection.getSelectedItem().toString();
        multiplikator = multiplikatorliste.get(seletionid);

        for (int i = 0; i < ikh.size(); i++) {
            if (ikh.get(i).getEditText().getText().toString().length() > 0
                    && igewicht.get(i).getEditText().getText().toString().length() > 0) {
                data_pro100.add(ikh.get(i).getEditText().getText().toString());
                data_gewicht.add(igewicht.get(i).getEditText().getText().toString());

                Double kh = Double.parseDouble(ikh.get(i).getEditText().getText().toString());
                Double gewicht = Double.parseDouble(igewicht.get(i).getEditText().getText().toString());
                double k = (kh * gewicht) / 100;
                resultKH += k;
                double r = (kh * gewicht) / 1000 * multiplikator;
                resultIE += r;
            }
        }
        resultKH = (Math.round(resultKH * 100) / 10.0);
        resultIE = (Math.round(resultIE * 10) / 10.0);

        createAusgleichswert();
    }

    private void reloadMultiplicatorSelectorList() {
        createdefaulthintlist();
        createmultiplikatorliste();

        ArrayList<String> selectlist = new ArrayList<>();
        selectlist.add("Morgens (" + multiplikatorliste.get(0) + ")");
        selectlist.add("Zwischenmahlzeit Morgens (" + multiplikatorliste.get(1) + ")");
        selectlist.add("Mittags (" + multiplikatorliste.get(2) + ")");
        selectlist.add("Zwischenmahlzeit Mittags (" + multiplikatorliste.get(3) + ")");
        selectlist.add("Abends (" + multiplikatorliste.get(4) + ")");
        selectlist.add("Spätmahlzeit (" + multiplikatorliste.get(5) + ")");

        ArrayAdapter adapter = new ArrayAdapter(RechnerActivity.this, android.R.layout.simple_spinner_item, selectlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MultiplikatorSelection.setAdapter(adapter);
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

    //    Ausgleichswert

    private void createAusgleichswert() {
        View include_Rechner = findViewById(R.id.include_rechner);
        View include_Ausgleich = findViewById(R.id.include_ausgleich);
        include_Rechner.setVisibility(View.GONE);
        include_Ausgleich.setVisibility(View.VISIBLE);

        errechnet = findViewById(R.id.DisplayText);
        input_Korrekturwert = findViewById(R.id.input_Korrekturwert);
        input_Blutzucker = findViewById(R.id.input_Blutzucker);
        checkButton = findViewById(R.id.checkButton);

        input_Korrekturwert.getEditText().setText(null);
        input_Blutzucker.getEditText().setText(null);

        String displaytext = resultIE + " IE";
        errechnet.setText(displaytext);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_Korrekturwert.getEditText().getText().length() > 0) {
                    ausgleich = Double.parseDouble(input_Korrekturwert.getEditText().getText().toString());

                    if (input_Blutzucker.getEditText().getText().length() > 0) {
                        blutzucker = Double.parseDouble(input_Blutzucker.getEditText().getText().toString());
                    }
                    include_Rechner.setVisibility(View.VISIBLE);
                    include_Ausgleich.setVisibility(View.GONE);

                    setResultText();
                }
                else {
                    Toast.makeText(RechnerActivity.this, "Gib die Ausgleichswert ein!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}