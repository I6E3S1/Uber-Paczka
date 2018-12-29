package com.example.dominik.uberpaczka.login;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dominik.uberpaczka.util.HasActionButtons;
import com.example.dominik.uberpaczka.util.HasValidation;
import com.example.dominik.uberpaczka.util.ConnectivityChecker;
import com.example.dominik.uberpaczka.map.MapsActivity;
import com.example.dominik.uberpaczka.R;
import com.example.dominik.uberpaczka.registration.RegistrationActivity;
import com.example.dominik.uberpaczka.registration.registration_usable.UserInfo;
import com.example.dominik.uberpaczka.validators_patterns.EmailValidator;
import com.example.dominik.uberpaczka.validators_patterns.PasswordValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.dominik.uberpaczka.util.ActivityUtils.hideKeyboard;


public class LoginActivity extends AppCompatActivity implements HasValidation, HasActionButtons {

    private static final String TAG = "logowanie_email";
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;
    private FirebaseAuth mAuth;
    private UserInfo userInfo;

    private EmailValidator emailValidator;
    private PasswordValidator passwordValidator;

    private Button regButton;
    private Button subButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        subButton = findViewById(R.id.submit);
        regButton = findViewById(R.id.register);
        emailInputLayout=findViewById(R.id.email_login_layout);
        passwordInputLayout=findViewById(R.id.password_login_layout);

        initValidationPatterns();
        initButtonListeners();
    }

    public void signin() {

        String email=emailInputLayout.getEditText().getText().toString();
        String password=passwordInputLayout.getEditText().getText().toString();

        //firebase check
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.v(TAG, "Zalogowano");

                            android.content.Intent myIntent = new android.content.Intent(getApplicationContext(), MapsActivity.class);
                            startActivity(myIntent);
                            finish();

                        } else {
                            Log.v(TAG, "Błąd logowania", task.getException());
                            Toast.makeText(LoginActivity.this, "Błąd logowania.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public boolean validate() {
        return emailValidator.validate() & passwordValidator.validate();
    }

    @Override
    public void initValidationPatterns() {
        emailValidator=new EmailValidator(emailInputLayout, getString(R.string.error_email), getString(R.string.error_blank));
        passwordValidator=new PasswordValidator(passwordInputLayout, getString(R.string.error_password), getString(R.string.error_blank));
    }

    @Override
    public void initButtonListeners() {
        regButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                android.content.Intent myIntent = new android.content.Intent(v.getContext(), RegistrationActivity.class);
                startActivity(myIntent);
            }
        });

        subButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ConnectivityChecker.checkInternetConnection(getApplicationContext(), getSupportFragmentManager())) {
                    if(validate())
                        signin();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    // hiding soft keyboard on lost textbox focus
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    hideKeyboard(this, v);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
}
