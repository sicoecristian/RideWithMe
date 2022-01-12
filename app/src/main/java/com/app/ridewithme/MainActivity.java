package com.app.ridewithme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.ridewithme.user.DAOUser;
import com.app.ridewithme.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button createAccountButton;
    private Button logInButton;
    private ImageView googleSignInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText emailField= findViewById(R.id.usernameInput);
        final EditText passwordField=findViewById(R.id.passwordInput);
        createAccountButton= (Button) findViewById(R.id.createAccount);
        logInButton=(Button) findViewById(R.id.logInButton);
        googleSignInButton=findViewById(R.id.googleSignIn);
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();


        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterAccountActivity();
            }
        });
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=emailField.getText().toString();
                String pw=passwordField.getText().toString();
                if (emailField.getText().toString().equals("") || passwordField.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please complete all fields!", Toast.LENGTH_SHORT).show();
                }
                else{
                    //logIn(username,pw);
                    mAuth.signInWithEmailAndPassword(username,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(MainActivity.this,MainScreen.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(),""+ task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,GoogleSignInActivity.class));
            }
        });
    }



    public void openRegisterAccountActivity(){
        startActivity(new Intent(this, RegisterAccountActivity.class));
    }

    public void logIn(String username,String pw){
        DAOUser daoUser=new DAOUser();
        User newUser=new User(username,pw,false);
        daoUser.getDatabaseReference().child("User").equalTo(String.valueOf(newUser)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
//user exists, do something
                    Toast.makeText(getApplicationContext(), "Wrong credentials!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,LogedInScreenActivity.class));
                } else {
//user does not exist, do something else
                    Toast.makeText(getApplicationContext(), "Wrong credentials!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}