package com.mjacksi.novapizzaowner.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.mjacksi.novapizzaowner.R;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 444;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        toolbarSetup();


    }

    private void toolbarSetup() {
        Toolbar toolbar = findViewById(R.id.order_toolbar);
        toolbar.setTitle("NovaApp");
        setSupportActionBar(toolbar);
    }

    public void login(View view) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build());
        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AppTheme_NoActionBar)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                updateUI(user);
            } else {
                Snackbar.make(getWindow().getDecorView().getRootView()
                        , "Failed login, retry again!", Snackbar.LENGTH_LONG)
                        .setAction("Failed", null).show();
            }
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null && user.getPhoneNumber().equals("+9647502120570")) {
            Intent intent = new Intent(MainActivity.this, OrdersActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if(user != null){
            FirebaseAuth.getInstance().signOut();
            TextView textView = findViewById(R.id.note_text_view);
            textView.setText("Please if you want to try this app as a manager use the number mentioned before!");
        }
    }
}
