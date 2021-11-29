package com.amita.memeshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import com.google.firebase.FirebaseApp;

public class LogInActivity extends AppCompatActivity {

    TextInputEditText email, password;
    TextView goToSignUp;
    Button login;
    ProgressDialog progressDialog;

    FirebaseAuth auth;
    FirebaseUser user;

    Button sign;

    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        FirebaseApp.initializeApp(LogInActivity.this);

        init();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, MainActivity.class));

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = email.getText().toString();
                String p = password.getText().toString();

                if (e.isEmpty()) {
                    email.setError("Please enter valid email..");
                } else if (p.isEmpty()) {
                    password.setError("Please enter password..");
                } else {
                    progressDialog = new ProgressDialog(LogInActivity.this);
                    progressDialog.setTitle("Logging");
                    progressDialog.setMessage("Please wait..");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    auth.signInWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(LogInActivity.this, HomeActivity.class));
                                Toast.makeText(LogInActivity.this, "Login success...", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                Toast.makeText(LogInActivity.this, "Failed to login " + task.getException(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }

    private void init() {
        email = findViewById(R.id.email_id);
        goToSignUp = findViewById(R.id.sign_up_txt);
        login = findViewById(R.id.sign_in);


        sign=findViewById(R.id.signIn);
        password=findViewById(R.id.password_id);
    }
}








