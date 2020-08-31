package com.example.duresources;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class WriteBlog extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText blog;
    EditText heading;
    Button requestToPublish;
    Switch anonymous;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_blog);
        Spinner mySpinner= (Spinner) findViewById(R.id.blog_genre);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(WriteBlog.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.genre)
        );
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(this);

        heading=findViewById(R.id.heading);
        blog=findViewById(R.id.blogContent);
        //requestToPublish=(Button) findViewById(R.id.requestToPublish);
        anonymous=(Switch) findViewById(R.id.switch_anonymous);
        /*requestToPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publish();
            }
        });*/


    }




/*        private void sendRequestEmailAnonymous(){
        String mail="travel.nag.exit@gmail.com";
        String message="Anonymous Blog "+"\n"+heading.getText().toString()+"\n"+blog.getText().toString();
        String subject="Blog From "+MainActivity.getEmail();
        String function="Request for publishing this blog has been sent to ";
        //Send Email
        JavaMailAPI javaMailAPI=new JavaMailAPI(this,mail,subject,message,function);
        javaMailAPI.execute();

    }*/
    private void sendRequestEmail(String bGenre){
        String mail="travel.nag.exit@gmail.com";
        String message;
        if(anonymous.isChecked()){
            message="Anonymous Blog "+"\n"+"Genre: "+bGenre+"\n"+"Heading: "+heading.getText().toString()+"\n"+blog.getText().toString();
        }
        else {
            message = "Blog " + "\n"+"Genre: "+bGenre+"\n"+"Heading: "+ heading.getText().toString() + "\n" + blog.getText().toString();
        }
        String subject="Blog From "+MainActivity.getEmail();
        String function="Request for publishing this blog has been sent to ";

        //Send Email
        JavaMailAPI javaMailAPI=new JavaMailAPI(this,mail,subject,message,function);
        javaMailAPI.execute();

    }

    private void publish(String genre)
    {
        String sBlog=blog.getText().toString();
        String sHeading=heading.getText().toString();
        if(!sBlog.isEmpty() && !sHeading.isEmpty()) {
            sendRequestEmail(genre);
            /*if (anonymous.isChecked()) {
                sendRequestEmailAnonymous();

            }
            else {
                sendRequestEmail();

            }*/
        }
        else if(sBlog.isEmpty()) {
            blog.setError("Cannot Be Empty");
            blog.requestFocus();
        }
        else {
            heading.setError("Cannot Be Empty");
            heading.requestFocus();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        requestToPublish=(Button) findViewById(R.id.requestToPublish);
        final String text=adapterView.getItemAtPosition(i).toString();
        requestToPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text.equals("Politics")){
                    publish(text);
                }
                if(text.equals("Innovation")){
                    publish(text);
                }
                if(text.equals("Philosophy")){
                    publish(text);
                }
                if(text.equals("Creative")){
                    publish(text);
                }
                if(text.equals("Inspiring")){
                    publish(text);
                }
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
