package com.e.authorpoint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText uemail,pwd;
    Button regBtn,loginBtn;
    TextView forgot_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        regBtn = findViewById(R.id.RegBtn);
        //forgot_pwd = findViewById(R.id.forget_pwd);

        uemail = findViewById(R.id.userEmail);
        pwd = findViewById(R.id.userPwd);

        /*forgot_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ForgotPassword.class));
            }
        });*/

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = uemail.getText().toString().trim();
                String password = pwd.getText().toString().trim();

                if (!validateLogin()) {
                    return;
                }

                if (true) { //check firebase login
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private boolean validateLogin() {
        boolean valid = true;

        String uemailString = uemail.getText().toString().trim();
        String password = pwd.getText().toString().trim();

        if (password.length() < 8) {
            valid = false;
            Toast.makeText(Login.this, "Password should be more than 8 characters", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password)) {
            pwd.setError("Required");
            valid = false;
            Toast.makeText(Login.this, "Please fill password", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(uemailString)) {
            uemail.setError("Required");
            valid = false;
            Toast.makeText(Login.this, "Please fill username", Toast.LENGTH_SHORT).show();
        }

        return valid;
    }

}