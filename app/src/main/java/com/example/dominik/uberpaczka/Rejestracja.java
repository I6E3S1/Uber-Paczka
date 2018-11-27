package com.example.dominik.uberpaczka;

import android.os.Bundle;
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


public class Rejestracja extends AppCompatActivity {

    private static final String TAG = "rejBlad";
    private FirebaseAuth mAuth;
    private UserInfo userInfo;

    EditText newemail, newpassword, name, surname, date, phone, karta, ccv, street, flat, city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);

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

                register(emailS, passwordS, v);

                /*                            android.content.Intent myIntent = new android.content.Intent(v.getContext(), MainActivity.class);
                            startActivity(myIntent);*/
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



                            android.content.Intent myIntent = new android.content.Intent(v.getContext(), MainActivity.class);
                            startActivity(myIntent);


                            Toast.makeText(Rejestracja.this, "Prawidłowo utworzono konto.",
                                     Toast.LENGTH_SHORT).show();
                            //updateUI(user);*/
                        } else {
                            //fail
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Rejestracja.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }


    public void sendUserInfo()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        userInfo.setName(name.getText().toString());
        userInfo.setSurname(surname.getText().toString());
        userInfo.setDate(date.getText().toString());
        userInfo.setPhone(phone.getText().toString());
        userInfo.setKarta(karta.getText().toString());
        userInfo.setCcv(ccv.getText().toString());
        userInfo.setStreet(street.getText().toString());
        userInfo.setFlat(flat.getText().toString());
        userInfo.setCity(city.getText().toString());

        // Add a new document with a generated ID
        db.collection("users")
                .add(userInfo.getUserInfo())
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

}
