package com.app.ridewithme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.ridewithme.exceptions.UserIsNullException;
import com.app.ridewithme.user.DAOUser;
import com.app.ridewithme.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class RegisterAccountActivity extends AppCompatActivity {
    private Button goBackToLogInButton;
    private Button registerAccountButton;



    private static int ok = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
        DAOUser daoUser = new DAOUser();



        final EditText emailField = findViewById(R.id.registerUsernameInput);
        final EditText passwordField = findViewById(R.id.registerPasswordInput);
        final EditText repeatPasswordField = findViewById(R.id.registerPasswordRepeat);
        goBackToLogInButton = (Button) findViewById(R.id.goBackToLogInScreenButton);
        registerAccountButton = (Button) findViewById(R.id.registerAccountButton);
        FirebaseAuth mAuth=FirebaseAuth.getInstance();

        FirebaseUser mUser=mAuth.getCurrentUser();




        goBackToLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogIn();
            }
        });


//        registerAccountButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatabaseReference rootRef = daoUser.getDatabaseReference();
//                DatabaseReference userNameRef = rootRef.child("User").child("email").child(emailField.getText().toString());
//
//                daoUser.getDatabaseReference().child("User").addValueEventListener(new ValueEventListener() {
//                    /**
//                     *
//                     * @param snapshot
//                     */
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Iterable<DataSnapshot> children = snapshot.getChildren();
//                        for (DataSnapshot child : children
//                        ) {
//                            System.out.println("        !!!!!!!!!!!!!!! Size-ul lui chilren");
//                            User usr = child.getValue(User.class);
//                            if (Objects.equals(usr.getEmail(), emailField.getText().toString())) {
//                                System.out.println("        !!!!!!!!!!!! APARE");
//                                ok = 0;
//                            } else {
//                                System.out.println("        !!!!!!!!!!!! NUUU APARE");
//                            }
//                        }
//
//                        if (ok == 1) {
//                            User newUser = new User(emailField.getText().toString(), passwordField.getText().toString(), false);
//                            if (!Objects.equals(passwordField.getText().toString(), repeatPasswordField.getText().toString())) {
//                                Toast.makeText(getApplicationContext(), "Passwords does not match!", Toast.LENGTH_SHORT).show();
//                            } else {
//                                try {
//                                    daoUser.add(newUser).addOnSuccessListener(suc -> {
//                                        Toast.makeText(getApplicationContext(), "Successfully registered user!", Toast.LENGTH_SHORT).show();
//                                    }).addOnFailureListener(er -> {
//                                        Toast.makeText(getApplicationContext(), "Unable to register user!", Toast.LENGTH_SHORT).show();
//                                    });
//                                } catch (UserIsNullException e) {
//                                    e.printStackTrace();
//                                    System.out.println("User is null");
//                                }
//                            }
//                        } else {
//                            Toast.makeText(getApplicationContext(), "User already exists!", Toast.LENGTH_SHORT).show();
//                            ok = 1;
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(getApplicationContext(), "User DataBase error !", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        });

        registerAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                if (emailField.getText().toString().equals("") || passwordField.getText().toString().equals("") || repeatPasswordField.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please complete all fields!", Toast.LENGTH_SHORT).show();
                } else if(!emailField.getText().toString().matches(emailPattern)){
                    Toast.makeText(getApplicationContext(), "Invalid mail format!", Toast.LENGTH_SHORT).show();
                } else
                if (!Objects.equals(passwordField.getText().toString(), repeatPasswordField.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Passwords does not match!", Toast.LENGTH_SHORT).show();
                } else {
                    //addUser(daoUser, emailField.getText().toString(), passwordField.getText().toString());

                    // FirebaseDatabase.getInstance("https://ridewithmedatabase-default-rtdb.europe-west1.firebasedatabase.app").getCu

                    mAuth.createUserWithEmailAndPassword(emailField.getText().toString(),passwordField.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                openLogIn();
                                Toast.makeText(getApplicationContext(), "Successfully registered user!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),""+ task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }


    public void openLogIn() {
        startActivity(new Intent(this, MainActivity.class));

    }

    public void addUser(DAOUser daoUser, String inputMail, String inputPassword) {
        daoUser.getDatabaseReference().child("User").orderByChild("email").equalTo(inputMail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    Toast.makeText(getApplicationContext(), "User already exists!", Toast.LENGTH_SHORT).show();
                } else {
                    User newUser = new User(inputMail, inputPassword, false);
                    try {
                        daoUser.add(newUser).addOnSuccessListener(suc -> {
                            Toast.makeText(getApplicationContext(), "Successfully registered user!", Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(er -> {
                            Toast.makeText(getApplicationContext(), "Unable to register user!", Toast.LENGTH_SHORT).show();
                        });
                    } catch (UserIsNullException e) {
                        e.printStackTrace();
                        System.out.println("User is null");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "User DataBase error !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}