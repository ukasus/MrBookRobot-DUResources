package com.example.duresources;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView logout;
    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Spinner mySpinner= (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(HomeActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.sections)
                );
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(this);

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        final String text=adapterView.getItemAtPosition(i).toString();
        go=findViewById(R.id.button3);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text.equals("Blogs")){
                    Intent i = new Intent(HomeActivity.this,BlogActivity.class);
                    startActivity(i);
                }
                if(text.equals("Notes")){
                    Intent i = new Intent(HomeActivity.this,NotesActivity.class);
                    startActivity(i);
                }
                if(text.equals("Suggested Reading Material")){
                    Intent i = new Intent(HomeActivity.this,SuggestedReadingsActivity.class);
                    startActivity(i);
                }
                if(text.equals("Book Robot")){
                    Intent i = new Intent(HomeActivity.this,BookRobotActivity.class);
                    startActivity(i);
                }
                if(text.equals("GD PI Section")){
                    Intent i = new Intent(HomeActivity.this,GDPIActivity.class);
                    startActivity(i);
                }

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
