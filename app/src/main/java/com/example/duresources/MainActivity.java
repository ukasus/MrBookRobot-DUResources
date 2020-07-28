package com.example.duresources;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText emailEditText ;
    EditText passwordEditText ;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users");

        // Initialization of EditTexts
        emailEditText = findViewById(R.id.editText);
        passwordEditText = findViewById(R.id.editText2);

        findViewById(R.id.textView).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
    }

    private void userLogin(){
        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        // Validation
        if(email.isEmpty()){
            emailEditText.setError("Email required");
            emailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please enter a valid email");
            emailEditText.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            passwordEditText.setError("Password required");
            passwordEditText.requestFocus();
            return;
        }

        // checking user exists or not
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean found = true;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SignUpData user = dataSnapshot.getValue(SignUpData.class);
                    if(user != null && user.get_email().equals(email) && user.get_password().equals(password)){
                        found = false;
                        finish();
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    }
                }
                if(found){
                    Toast.makeText(getApplicationContext(), "e-mail/password is invalid", Toast.LENGTH_SHORT).show();
                    return ;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textView:
                finish();           // finish this activity
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.button:
                userLogin();
                break;
        }
    }
}