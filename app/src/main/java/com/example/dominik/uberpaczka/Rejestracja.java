package com.example.dominik.uberpaczka;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.dominik.uberpaczka.Launch.networkCheck;


public class Rejestracja extends AppCompatActivity {


    private static final String TAG = "rejBlad";
    private FirebaseAuth mAuth;
    private UserInfo userInfo;

    EditText newemail, newpassword, name, surname, date, phone, karta, ccv, street, flat, city;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);
        final Context context = getApplicationContext();


        final Button button = findViewById(R.id.registernew);

        newemail = findViewById(R.id.email);
        newpassword = findViewById(R.id.newpassword);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        date = findViewById(R.id.date);
        phone = findViewById(R.id.phone);
        karta = findViewById(R.id.karta);
        ccv = findViewById(R.id.ccv);
        street = findViewById(R.id.street);
        flat = findViewById(R.id.flat);
        city = findViewById(R.id.city);


        mAuth = FirebaseAuth.getInstance();
        userInfo = new UserInfo();



        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String emailS = newemail.getText().toString();
                String passwordS = newpassword.getText().toString();
                if(networkCheck(context))
                if (userInfo.check(emailS, newemail, "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", "Należy podać adres e-mail."))
                    if (userInfo.check(passwordS, newpassword, "\\w{6,}", "Za krótkie hasło."))
                        if (infoCheck())
                            register(emailS, passwordS, v);

            }
        });
    }

    public void register(String emailS, String passwordS, final View v) {


        mAuth.createUserWithEmailAndPassword(emailS, passwordS)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //success
                            Log.d(TAG, "createUserWithEmail:success");
                            userInfo.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            sendUserInfo();

                            Toast.makeText(Rejestracja.this, "Prawidłowo utworzono konto.",
                                    Toast.LENGTH_SHORT).show();

                            SystemClock.sleep(1000);

                            android.content.Intent myIntent = new android.content.Intent(v.getContext(), MainActivity.class);
                            startActivity(myIntent);
                        } else {
                            //fail
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Rejestracja.this, "Konto nie zostało utworzone.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    public void sendUserInfo() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .add(userInfo.getUserInfo())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.w(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public boolean infoCheck() {

        if (userInfo.check(name.getText().toString(), name, "[a-zA-Z0-9\\._\\-]{3,}", "Nieprawidłowe imie.")) {
            if (userInfo.check(surname.getText().toString(), surname, "[a-zA-Z0-9\\._\\-]{3,}", "Nieprawidłowe nazwisko."))
                if (userInfo.check(date.getText().toString(), date, "[0-3]\\d\\.(0\\d|1[0-2])\\.[1-2]\\d{3}", "Nieprawidłowe data urodzin. DD.MM.YYYY"))
                    if (userInfo.check(phone.getText().toString(), phone, "\\d{9}", "Nieprawidłowy numer telefonu."))
                        if (userInfo.check(karta.getText().toString(), karta, "\\d{16}", "Nieprawidłowy numer karty."))
                            if (userInfo.check(ccv.getText().toString(), ccv, "\\d{3}", "Nieprawidłowy numer CCV."))
                                if (userInfo.check(street.getText().toString(), street, "[a-zA-Z0-9\\._\\-]{3,}", "Nieprawidłowa ulica."))
                                    if (userInfo.check(flat.getText().toString(), flat, "\\d{1,}", "Nieprawidłowy numer."))
                                        if (userInfo.check(city.getText().toString(), city, "[a-zA-Z0-9\\._\\-]{3,}", "Nieprawidłowe miasto.")) {

                                            userInfo.setName(name.getText().toString());

                                            userInfo.setSurname(surname.getText().toString());

                                            userInfo.setDate(date.getText().toString());

                                            userInfo.setPhone(phone.getText().toString());

                                            userInfo.setKarta(karta.getText().toString());

                                            userInfo.setCcv(ccv.getText().toString());

                                            userInfo.setStreet(street.getText().toString());

                                            userInfo.setFlat(flat.getText().toString());

                                            userInfo.setCity(city.getText().toString());

                                            return true;
                                        } else {
                                            return false;
                                        }
        }
        return false;
    }

}
