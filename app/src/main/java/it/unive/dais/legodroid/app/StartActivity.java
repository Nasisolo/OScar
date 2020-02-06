package it.unive.dais.legodroid.app;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //value of Field's size
        EditText MsizeValue = (EditText)findViewById(R.id.MsizeValue);
        EditText NsizeValue = (EditText)findViewById(R.id.NsizeValue);

        //value of initial position of the robot
        EditText MinitValue = (EditText)findViewById(R.id.MinitValue);
        EditText NinitValue = (EditText)findViewById(R.id.NinitValue);

        RadioGroup radioOrientation = (RadioGroup)findViewById(R.id.radioOrientation);

        radioOrientation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch(checkedId){
                    case R.id.radioNorth:
                        Toast.makeText(getApplicationContext(),"NORD", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioEast:
                        Toast.makeText(getApplicationContext(), "EST", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioSouth:
                        Toast.makeText(getApplicationContext(), "SUD", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioWest:
                        Toast.makeText(getApplicationContext(), "OVEST", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        RadioButton radioNorth = (RadioButton)findViewById(R.id.radioNorth);
        RadioButton radioEast = (RadioButton)findViewById(R.id.radioEast);
        RadioButton radioSouth = (RadioButton)findViewById(R.id.radioSouth);
        RadioButton radioWest = (RadioButton)findViewById(R.id.radioWest);


        Button buttonEnter = (Button)findViewById(R.id.buttonEnter);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioOrientation.getCheckedRadioButtonId();

                if(selectedId == radioNorth.getId())
                    Toast.makeText(getApplicationContext(), "NOOOOOOOOOOOOOOOOORD", Toast.LENGTH_SHORT).show();
                if(selectedId == radioEast.getId())
                    Toast.makeText(getApplicationContext(), "EEEEEEEEEEEEEEEEEEST", Toast.LENGTH_SHORT).show();
                if(selectedId == radioSouth.getId())
                    Toast.makeText(getApplicationContext(), "SUUUUUUUUUUUUUUUUUUD", Toast.LENGTH_SHORT).show();
                if(selectedId == radioWest.getId())
                    Toast.makeText(getApplicationContext(), "OOOOOOOOOOOOOOOOOVEST", Toast.LENGTH_SHORT).show();

            }
        });



    }

}
