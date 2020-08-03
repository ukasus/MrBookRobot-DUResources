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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText emailText ;
    EditText usernameText ;
    EditText numberText ;
    EditText passwordText ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String checkPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users");

        emailText = findViewById(R.id.editText3);
        usernameText = findViewById(R.id.editText4);
        numberText = findViewById(R.id.editText5);
        passwordText = findViewById(R.id.editText6);

        findViewById(R.id.textView2).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);

    }

    public void userSignUp(){

        final String email = emailText.getText().toString();
        String username = usernameText.getText().toString();
        String phNum = numberText.getText().toString();
        String password = passwordText.getText().toString();

        // Validation
        if(email.isEmpty()){
            emailText.setError("Email required");
            emailText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("Please enter a valid email");
            emailText.requestFocus();
            return;
        }

        if(password.isEmpty()){
            passwordText.setError("Password required");
            passwordText.requestFocus();
            return;
        }

        if(username.isEmpty()){
            usernameText.setError("Username required");
            usernameText.requestFocus();
            return;
        }

        if(phNum.isEmpty()){
            numberText.setError("Number required");
            numberText.requestFocus();
            return;
        }else if(phNum.length() != 10){
            numberText.setError("Enter valid number");
            numberText.requestFocus();
            return;
        }

        SignUpData user = new SignUpData(username, phNum, email, password);

        // checking if the user already exists
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SignUpData user = dataSnapshot.getValue(SignUpData.class);
                    if(user != null && user.get_email().equals(email)){
                        clearFields();
                        Toast.makeText(getApplicationContext(), "e-mail already exists", Toast.LENGTH_SHORT).show();
                        return ;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        if(user.get_password().equals(checkPassword)) {
            databaseReference.child(email.replace(".", "")).setValue(user);
            clearFields();
            Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity.class));
        }
        else{
            Toast.makeText(getApplicationContext(), "Wrong Password!! Please Check your E-mail.", Toast.LENGTH_SHORT).show();
        }
    }
    private void generateSudoPassword() {
        final String email = emailText.getText().toString();
        String username = usernameText.getText().toString();
        String phNum = numberText.getText().toString();
        String password = passwordText.getText().toString();

        // Validation
        if(email.isEmpty()){
            emailText.setError("Email required");
            emailText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("Please enter a valid email");
            emailText.requestFocus();
            return;
        }

        /*if(password.isEmpty()){
            passwordText.setError("Password required");
            passwordText.requestFocus();
            return;
        }*/

        if(username.isEmpty()){
            usernameText.setError("Username required");
            usernameText.requestFocus();
            return;
        }

        if(phNum.isEmpty()){
            numberText.setError("Number required");
            numberText.requestFocus();
            return;
        }else if(phNum.length() != 10){
            numberText.setError("Enter valid number");
            numberText.requestFocus();
            return;
        }

        //SignUpData user = new SignUpData(username, phNum, email, password);

        // checking if the user already exists
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SignUpData user = dataSnapshot.getValue(SignUpData.class);
                    if(user != null && user.get_email().equals(email)){
                        clearFields();
                        Toast.makeText(getApplicationContext(), "e-mail already exists try to login", Toast.LENGTH_SHORT).show();
                        return ;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        PasswordGenerator sudoPassword=new PasswordGenerator();
        checkPassword=sudoPassword.generate();
        sendPassToEmail();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textView2:
                finish();           // finish this activity
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.button2:
                userSignUp();
                break;
            case R.id.button4:
                
                generateSudoPassword();

                break;
        }
    }

    private void sendPassToEmail(){
        String mail=emailText.getText().toString();
        String message="Your Password for registration is:\n"+checkPassword;
        String subject="Randomly Generated Registration Password";

        //Send Email
        JavaMailAPI javaMailAPI=new JavaMailAPI(this,mail,subject,message);
        javaMailAPI.execute();

    }

    private void clearFields(){
        emailText.setText("");
        usernameText.setText("");
        numberText.setText("");
        passwordText.setText("");
    }
}

