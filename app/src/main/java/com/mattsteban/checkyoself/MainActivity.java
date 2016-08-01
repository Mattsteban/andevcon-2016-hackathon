package com.mattsteban.checkyoself;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mattsteban.checkyoself.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @BindView(R.id.current_logged_in_user_email)
    TextView tvCurrentLoggedInEmail;

    @BindView(R.id.btn_trigger_user_call)
    Button btnTriggerUserCall;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance();
        final DatabaseReference dbRefUsers = database.getReference("users");


        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                    // Name, email address, and profile photo Url
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();


                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getToken() instead.
                    String uid = user.getUid();

                    tvCurrentLoggedInEmail.setText(email);

                    dbRefUsers.setValue(new User(email, name, email, true, photoUrl != null ? photoUrl.toString() : "SOME_URL.com"));
                } else {
                    // User is signed out
                    tvCurrentLoggedInEmail.setText("Not Currently Signed In.");
                }
            }
        });

//        //for testing move elsewhere later
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.replace(R.id.frame_container, new FragmentMain());
//        fragmentTransaction.commit();
    }

    private void writeNewUser(){

    }

    @OnClick(R.id.btn_trigger_user_call)
    public void onBtnTriggerUserCallClick(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
            tvCurrentLoggedInEmail.setText(email);
        }
        else {
            tvCurrentLoggedInEmail.setText("Not Currently Signed In.");
        }
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
