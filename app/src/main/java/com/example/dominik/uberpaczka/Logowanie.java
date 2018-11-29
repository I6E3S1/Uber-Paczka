package com.example.dominik.uberpaczka;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logowanie extends AppCompatActivity {

    private static final String TAG = "logowanie_email";

    private FirebaseAuth mAuth;

    private UserInfo userInfo;

    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logowanie);

        mAuth = FirebaseAuth.getInstance();
        userInfo = new UserInfo();

        final Button subbutton = findViewById(R.id.submit);


        subbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                email = findViewById(R.id.email);
                password = findViewById(R.id.password);
                String emailS = email.getText().toString();
                String passwordS = password.getText().toString();

                //Tak wyglada email w wyrażeniach reguralnych
                if (userInfo.check(emailS, email, "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", "Należy podać adres e-mail."))
                    if (userInfo.check(passwordS, password, "\\w{6,}", "Za krótkie hasło."))
                        signin(emailS, passwordS, v);

            }
        });

        final Button regButton = findViewById(R.id.register);

        regButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                android.content.Intent myIntent = new android.content.Intent(v.getContext(), Rejestracja.class);
                startActivity(myIntent);
            }
        });
    }

    void signin(String emailS, String passwordS, final View v) {


        FirebaseAuth.getInstance().signInWithEmailAndPassword(emailS, passwordS)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.v(TAG, "Zalogowano");

                            android.content.Intent myIntent = new android.content.Intent(v.getContext(), MainActivity.class);
                            startActivity(myIntent);

                        } else {
                            Log.v(TAG, "Błąd logowania", task.getException());
                            Toast.makeText(Logowanie.this, "Błąd logowania.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
