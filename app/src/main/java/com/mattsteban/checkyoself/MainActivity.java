package com.mattsteban.checkyoself;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mattsteban.checkyoself.adapter.RatingPagerAdapter;
import com.mattsteban.checkyoself.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @BindView(R.id.current_logged_in_user_email)
    TextView tvCurrentLoggedInEmail;

    @BindView(R.id.rating_pager)
    ViewPager ratingViewPager;

    FirebaseDatabase database;
    User currentUser;

    RatingPagerAdapter pagerAdapter;

    List<User> userList = new ArrayList<>();
    boolean isComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance();

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

                    DatabaseReference dbRefUsers = database.getReference(Static.USERS + "/" + uid + "/");

                    tvCurrentLoggedInEmail.setText(email);
                    currentUser = new User(email, name, uid, true, photoUrl != null ? photoUrl.toString() : "https://randomuser.me/api/portraits/men/80.jpg");
                    dbRefUsers.setValue(currentUser);
                } else {
                    // User is signed out
                    tvCurrentLoggedInEmail.setText("Not Currently Signed In.");
                }
            }
        });

        DatabaseReference dbRefUsers = database.getReference(Static.USERS);
        dbRefUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    userList.add(snapshot.getValue(User.class));
                }

                if (!isComplete){
                    //TODO this will probably have to be changed due to view pager
                    isComplete = true;
                    FragmentManager fm =getSupportFragmentManager();

                    pagerAdapter = new RatingPagerAdapter(fm, new ArrayList<>(userList));
                    ratingViewPager.setAdapter(pagerAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

    @Override
    protected void onResume() {
        super.onResume();
        if (currentUser != null && database != null){
            currentUser.isOnline = true;
            database.getReference(Static.USERS + "/" + currentUser.id + "/").setValue(currentUser);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (currentUser != null && database != null){
            currentUser.isOnline = false;
            database.getReference(Static.USERS + "/" + currentUser.id + "/").setValue(currentUser);
        }
        BusProvider.getInstance().unregister(this);
    }
}
