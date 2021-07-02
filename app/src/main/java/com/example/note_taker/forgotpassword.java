package com.example.note_taker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {

    private EditText mforgotpassword;
    private Button mpasswordrecoverybutton;
    private TextView mgobacktologin;

    //Testing Commit

    private TextView mtextview1, mtextview2;
    private Typeface mtypeface1, mtypeface2,mtypeface3;

    ProgressBar mprogressbarofpasswordrecovery;

    FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        getSupportActionBar().hide();

        mforgotpassword = findViewById(R.id.forgotpassword);
        mpasswordrecoverybutton = findViewById(R.id.passwordrecoverbutton);
        mgobacktologin = findViewById(R.id.gobackloginscreen);

        mtextview1 = findViewById(R.id.textview1id);
        mtextview2 = findViewById(R.id.textview2id);

        mtypeface1 = Typeface.createFromAsset(getAssets(),"fonte/ShrikhandRegular.otf");
        mtextview1.setTypeface( mtypeface1);
        mtypeface2 = Typeface.createFromAsset(getAssets(),"fontg/alpha_echo.ttf");
        mtextview2.setTypeface( mtypeface2);
        mtypeface3 = Typeface.createFromAsset(getAssets(),"fonth/hetilica.ttf");
        mgobacktologin.setTypeface( mtypeface3);

        mprogressbarofpasswordrecovery = findViewById(R.id.progreesbarofpasswordrecovery);

        firebaseauth = FirebaseAuth.getInstance();

        mgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (forgotpassword.this,MainActivity.class);
                startActivity(intent);
            }
        });
        mpasswordrecoverybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mforgotpassword.getText().toString().trim();

                if(mail.isEmpty()){
                  Toast.makeText(getApplicationContext(), "Enter your email first", Toast.LENGTH_SHORT).show();
                }
                else{

                    mprogressbarofpasswordrecovery.setVisibility(View.VISIBLE);

                    firebaseauth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Mail sent, now you can recover your password using mail", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(forgotpassword.this, MainActivity.class));
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Email is wrong or account doesn't exist",Toast.LENGTH_SHORT).show();
                                mprogressbarofpasswordrecovery.setVisibility(View.INVISIBLE);
                            }
                        }

                    });
                }
            }
        });
    }
}