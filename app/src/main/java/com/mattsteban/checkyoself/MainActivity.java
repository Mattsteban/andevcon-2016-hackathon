package com.mattsteban.checkyoself;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Toast.makeText(getApplicationContext(), "User Signed In!", Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    Toast.makeText(getApplicationContext(), "User Not In!", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    // Options Menu Selection. Sign In or Sign Out.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_in:
                Toast.makeText(getApplicationContext(), "Sign In", Toast.LENGTH_SHORT).show();

                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setProviders(
                                        AuthUI.EMAIL_PROVIDER,
                                        AuthUI.GOOGLE_PROVIDER)
                                .build(),
                        Static.RC_SIGN_IN);
                break;
            case R.id.sign_out:
                AuthUI.getInstance().signOut(this);
                break;
        }
        return true;
    }

    // Create Options Menu. Check if User is already logged in.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sign_in_out, menu);
        return true;
    }

    // Check Result of Sign In Intent
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Static.RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // user is signed in!
                Toast.makeText(getApplicationContext(), "User Successfully Signed In!", Toast.LENGTH_SHORT).show();
            } else {
                // user is not signed in. Maybe just wait for the user to press
                // "sign in" again, or show a message
                Toast.makeText(getApplicationContext(), "User Sign In Failure.", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
