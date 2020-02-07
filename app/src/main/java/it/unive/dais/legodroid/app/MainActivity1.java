package it.unive.dais.legodroid.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.GridView;


public class MainActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        Intent intent = getIntent();

        int Msize = Integer.parseInt(intent.getStringExtra("Msize"));
        int Nsize = Integer.parseInt(intent.getStringExtra("Nsize"));
        int Minit = Integer.parseInt(intent.getStringExtra("Minit"));
        int Ninit = Integer.parseInt(intent.getStringExtra("Ninit"));

        //Integer[] field = new Integer[Msize*Nsize];
        String[] field = new String[Msize*Nsize];
        Integer[] buffer = new Integer[Msize];
        int invIter = (Msize*Nsize)-1;

        for(int i = 0; i< Msize*Nsize; i++)
            field[i]="";

        //System.arraycopy(field, 0, buffer, 0, Msize);

        Integer [][] nomi={
                {Msize,Nsize,Minit,Ninit,0,1},
                {0,0,0,0,0,2},
                {0,0,0,0,0,3},
                {0,0,0,0,0,4},

        };


        gridView.setNumColumns(Msize);




        ArrayAdapter<String> adapter;

            adapter = new ArrayAdapter<String>(this, R.layout.row, field);
            gridView.setAdapter(adapter);
        }



    }
