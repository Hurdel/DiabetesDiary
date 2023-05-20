package tv.master_of_spirit.diabetesdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class AusgleichwertActivity extends AppCompatActivity {

    Integer seletionid;
    Double resultIE;
    Double resultKH;
    String selectedmultiplikator;
    Double korrektur;
    Double blutzucker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ausgleichwert);

        TextView textView = findViewById(R.id.DisplayText);
        TextInputLayout input_Korrekturwert = findViewById(R.id.input_Korrekturwert);
        TextInputLayout input_Blutzucker = findViewById(R.id.input_Blutzucker);
        FloatingActionButton checkButton = findViewById(R.id.checkButton);

        if (getIntent().hasExtra("resultIE") &&
                getIntent().hasExtra("resultKH") &&
                getIntent().hasExtra("selectedmultiplikator") &&
                getIntent().hasExtra("seletionid")) {
            resultIE = getIntent().getDoubleExtra("resultIE", 0.0);
            resultKH = getIntent().getDoubleExtra("resultKH", 0.0);
            selectedmultiplikator = getIntent().getStringExtra("selectedmultiplikator");
            seletionid = getIntent().getIntExtra("seletionid", 0);
            getIntent().removeExtra("resultIE");
            getIntent().removeExtra("resultKH");
            getIntent().removeExtra("selectedmultiplikator");
            getIntent().removeExtra("seletionid");
        }
        String displaytext = resultIE + " IE";
        textView.setText(displaytext);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_Korrekturwert.getEditText().getText().length() > 0) {
                    korrektur = Double.parseDouble(input_Korrekturwert.getEditText().getText().toString());

                    Intent rechnerintentent = new Intent(AusgleichwertActivity.this, RechnerActivity.class);
                    rechnerintentent.putExtra("resultKH", resultKH);
                    rechnerintentent.putExtra("resultIE", resultIE);
                    rechnerintentent.putExtra("korrektur", korrektur);
                    rechnerintentent.putExtra("selectedmultiplikator", selectedmultiplikator);
                    rechnerintentent.putExtra("seletionid", seletionid);
                    if (input_Blutzucker.getEditText().getText().length() > 0) {
                        blutzucker = Double.parseDouble(input_Blutzucker.getEditText().getText().toString());
                        rechnerintentent.putExtra("blutzucker", blutzucker);
                    }
                    startActivity(rechnerintentent);
                }
                else {
                    Toast.makeText(AusgleichwertActivity.this, "Gib die Ausgleichswert ein!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}