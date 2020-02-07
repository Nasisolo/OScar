package it.unive.dais.legodroid.app;

import android.content.Intent;
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


        RadioGroup radioTest = (RadioGroup)findViewById(R.id.radioTest);
        radioTest.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch(checkedId){
                    case R.id.radioTest1:
                        //Toast.makeText(getApplicationContext(),"MOVIMENTO", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioTest2:
                        //Toast.makeText(getApplicationContext(), "COMUNICAZIONE", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioTest3:
                        //Toast.makeText(getApplicationContext(), "INTERAZIONE", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        RadioButton radioTest1 = (RadioButton)findViewById(R.id.radioTest1);
        RadioButton radioTest2 = (RadioButton)findViewById(R.id.radioTest2);
        RadioButton radioTest3 = (RadioButton)findViewById(R.id.radioTest3);








        Button buttonEnter = (Button)findViewById(R.id.buttonEnter);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioTest.getCheckedRadioButtonId();
                Intent i;


                if(selectedId == radioTest1.getId()) {
                    i = new Intent(StartActivity.this, MainActivity1.class);
                    i.putExtra("Msize", MsizeValue.getText().toString());
                    i.putExtra("Nsize", NsizeValue.getText().toString());
                    i.putExtra("Minit", MinitValue.getText().toString());
                    i.putExtra("Ninit", NinitValue.getText().toString());

                    startActivity(i);
                }

                if(selectedId == radioTest2.getId()) {
                    i = new Intent(StartActivity.this, MainActivity2.class);
                    startActivity(i);
                }

                if(selectedId == radioTest3.getId()){
                    i = new Intent(StartActivity.this, MainActivity3.class);
                    //i.putExtra("Msize", MsizeValue.getText().toString());
                    //System.out.println("CIAOOOOOOOOOOOOOOOOOOOOOO"+MsizeValue.getText().toString());
                    /*
                    i.putExtra("Msize", MsizeValue.toString());
                    i.putExtra("Nsize", NsizeValue.toString());
                    i.putExtra("Minit", MinitValue.toString());
                    i.putExtra("Ninit", NinitValue.toString());
                    */
                    startActivity(i);
                }


                /*
                if(selectedId == radioTest2.getId())
                    Toast.makeText(getApplicationContext(), "EEEEEEEEEEEEEEEEEEST", Toast.LENGTH_SHORT).show();
                if(selectedId == radioSouth.getId())
                    Toast.makeText(getApplicationContext(), "SUUUUUUUUUUUUUUUUUUD", Toast.LENGTH_SHORT).show();
                if(selectedId == radioWest.getId())
                    Toast.makeText(getApplicationContext(), "OOOOOOOOOOOOOOOOOVEST", Toast.LENGTH_SHORT).show();
                */


            }
        });



    }


    private void main(){

    }

}
