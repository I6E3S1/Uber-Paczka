package com.example.dominik.uberpaczka;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class Logowanie extends AppCompatActivity {

    private static final String TAG = "logowanie_email";
    private FirebaseAuth mAuth;

    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logowanie);

        mAuth = FirebaseAuth.getInstance();

        final Button subbutton = findViewById(R.id.submit);

        subbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                email = findViewById(R.id.email);
                password = findViewById(R.id.password);
                String emailS = email.getText().toString();
                String passwordS = password.getText().toString();

                signin(emailS, passwordS, v);

            }
        });

        final Button rejbutton = findViewById(R.id.register);

        rejbutton.setOnClickListener(new View.OnClickListener() {
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
                            Log.v(TAG, "sukces");
                            String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            android.content.Intent myIntent = new android.content.Intent(v.getContext(), MainActivity.class);
                            startActivity(myIntent);
                            /*Toast.makeText(Logowanie.this, user,
                                     Toast.LENGTH_SHORT).show();*/
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "blad", task.getException());
                            Toast.makeText(Logowanie.this, "Błąd logowania.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }
}
