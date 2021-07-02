package com.example.note_taker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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

public class MainActivity extends AppCompatActivity {


    private EditText mloginemail,mloginpassword;
    private RelativeLayout mlogin,mgototsignup;
    private TextView mgotoforggtpassword;

    private TextView mtextview1, mtextview2, mtextview3;
    private Typeface mtypeface1, mtypeface2, mtypeface3, mtypeface4;

    private FirebaseAuth firebaseauth;

    ProgressBar mprogressbarofmainactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().hide();

        mloginemail = findViewById(R.id.loginemail);
        mloginpassword = findViewById(R.id.loginpassword);
        mlogin = findViewById(R.id.login);
        mgototsignup = findViewById(R.id.gotosignup);
        mgotoforggtpassword = findViewById(R.id.gotoforgotpassword);

        mtextview1 = findViewById(R.id.text1id);
        mtextview2 = findViewById(R.id.text2id);
        mtextview3 = findViewById(R.id.textviewlastid);

        mtypeface1 = Typeface.createFromAsset(getAssets(),"kaushanscript/KaushanScriptRegular.otf");
        mtextview1.setTypeface( mtypeface1);
        mtypeface2 = Typeface.createFromAsset(getAssets(),"fontg/alpha_echo.ttf");
        mtextview2.setTypeface( mtypeface2);
        mtypeface3 = Typeface.createFromAsset(getAssets(),"fonte/ShrikhandRegular.otf");
        mgotoforggtpassword.setTypeface( mtypeface3);
        mtypeface4 = Typeface.createFromAsset(getAssets(),"fonth/hetilica.ttf");
        mtextview3.setTypeface( mtypeface4);

        mprogressbarofmainactivity = findViewById(R.id.progreesbarofmainactivity);

        firebaseauth = FirebaseAuth.getInstance();
        FirebaseUser firebaseuser = firebaseauth.getCurrentUser();

        if(firebaseuser!=null){
            finish();
            startActivity(new Intent(MainActivity.this, notesActivity.class));
        }

        mgototsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,sign_up.class));
            }
        });
        mgotoforggtpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,forgotpassword.class));
            }
        });
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mloginemail.getText().toString().trim();
                String password = mloginpassword.getText().toString().trim();

                if(mail.isEmpty()||password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Both fields are required",Toast.LENGTH_SHORT).show();
                }
                else{

                    mprogressbarofmainactivity.setVisibility(View.VISIBLE);

                    firebaseauth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                checkmailVerification();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Incorrect password or account doesn't exist",Toast.LENGTH_SHORT).show();
                                mprogressbarofmainactivity.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });

    }

    private void checkmailVerification(){
        FirebaseUser firebaseuser = firebaseauth.getCurrentUser();
        if(firebaseuser.isEmailVerified()==true){
            Toast.makeText(getApplicationContext(),"Logged in",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this,notesActivity.class));
        }
        else{
            mprogressbarofmainactivity.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"Verify your mail first",Toast.LENGTH_SHORT).show();
            firebaseauth.signOut();
        }
    }
}