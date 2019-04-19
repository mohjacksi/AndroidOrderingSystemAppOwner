package com.mjacksi.novapizzaowner.Activites;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mjacksi.novapizzaowner.R;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (user == null) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }else{
                    intent = new Intent(SplashActivity.this, OrdersActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2000);

    }

}
