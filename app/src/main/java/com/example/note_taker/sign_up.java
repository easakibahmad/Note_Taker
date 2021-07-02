package com.example.note_taker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sign_up extends AppCompatActivity {

    private EditText msignupgmail,msignuppassword;
    private RelativeLayout msignup;
    private TextView mgotologin;

    private TextView mtextview1, mtextview2;
    private Typeface mtypeface1, mtypeface2, mtypeface3;

    private FirebaseAuth firebaseauth;

    ProgressBar mprogressbarofsignupactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        msignupgmail = findViewById(R.id.signupemail);
        msignuppassword = findViewById(R.id.signuppassword);
        msignup = findViewById(R.id.signup);
        mgotologin = findViewById(R.id.gotologin);

        mtextview1 = findViewById(R.id.textview1id);
        mtextview2 = findViewById(R.id.textview2id);

        mtypeface1 = Typeface.createFromAsset(getAssets(),"fontf/HOMINIS.ttf");
        mtextview1.setTypeface( mtypeface1);
        mtypeface2 = Typeface.createFromAsset(getAssets(),"fontg/alpha_echo.ttf");
        mtextview2.setTypeface( mtypeface2);
        mtypeface3 = Typeface.createFromAsset(getAssets(),"fonte/ShrikhandRegular.otf");
        mgotologin.setTypeface( mtypeface3);

        mprogressbarofsignupactivity = findViewById(R.id.progreesbarofsignupactivity);

        firebaseauth = FirebaseAuth.getInstance();

        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sign_up.this,MainActivity.class);
                startActivity(intent);
            }
        });

        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = msignupgmail.getText().toString().trim();
                String password = msignuppassword.getText().toString().trim();

                if(mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Both fields are required",Toast.LENGTH_SHORT).show();
                }
                else if (password.length()<7){
                    Toast.makeText(getApplicationContext(),"Password must be greater than 7 digits",Toast.LENGTH_SHORT).show();
                }
                else{
                    mprogressbarofsignupactivity.setVisibility(View.VISIBLE);
                    firebaseauth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Registration successful",Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Failed to register",Toast.LENGTH_SHORT).show();
                                mprogressbarofsignupactivity.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseuser = firebaseauth.getCurrentUser();
        if (firebaseuser!=null){
            firebaseuser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(),"Verification email is sent,please verify and log in",Toast.LENGTH_SHORT).show();
                    firebaseauth.signOut();
                    finish();
                    startActivity(new Intent(sign_up.this,MainActivity.class));
                }
            });
        }
        else {
            mprogressbarofsignupactivity.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"Failed to send verification email",Toast.LENGTH_SHORT).show();
        }
    }
}