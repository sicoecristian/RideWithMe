package com.app.ridewithme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterAccountActivity extends AppCompatActivity {
    private Button goBackToLogInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        goBackToLogInButton=(Button) findViewById(R.id.goBackToLogInScreenButton);
        goBackToLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogIn();
            }
        });
    }
    public void openLogIn(){
        startActivity(new Intent(this, MainActivity.class));
    }
}