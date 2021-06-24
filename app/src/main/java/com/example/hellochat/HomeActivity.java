package com.example.hellochat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hellochat.databinding.HomeBinding;
import com.example.hellochat.fragment.ChatFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    HomeBinding binding;
    String currentUserId;
    DatabaseReference userReference;
    User user;
    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userReference = FirebaseDatabase.getInstance().getReference("User");
        setSupportActionBar(binding.homeToolBar);
        isUserPrfoileCreated();
        userReference.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
              if(snapshot.exists()){
                  User u = snapshot.getValue(User.class);
                  Picasso.get().load(u.getImageUrl()).into(binding.profileImage);
                  binding.tvCurrentUser.setText(u.getName());
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.homeTab.addTab(binding.homeTab.newTab().setText("CHAT"));
        binding.homeTab.addTab(binding.homeTab.newTab().setText("Group"));
        binding.homeTab.addTab(binding.homeTab.newTab().setText("Story"));

        binding.homeTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabTitle = tab.getText().toString();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if(tabTitle.equalsIgnoreCase("CHAT")){
                   transaction.replace(R.id.ll,new ChatFragment());
                }
                else if(tabTitle.equalsIgnoreCase("Group")){

                }
                else if(tabTitle.equalsIgnoreCase("Story")){

                }
                transaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void sendUserToProfileActivity(){
        Intent in = new Intent(this,ProfileActivity.class);
        startActivity(in);
    }
    private void isUserPrfoileCreated(){
       userReference.child(currentUserId).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull  DataSnapshot snapshot) {
             if(!snapshot.exists()){
                 sendUserToProfileActivity();
             }
             else{
                user =  snapshot.getValue(User.class);
             }
           }
           @Override
           public void onCancelled(@NonNull  DatabaseError error) {

           }
       });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Find friends");
        menu.add("Settings");
        menu.add("Incoming request");
        menu.add("Signout");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String title = item.getTitle().toString();
        if(title.equalsIgnoreCase("Find friends")){
             sendUserFindFriendActivity();
        }
        else if(title.equalsIgnoreCase("Settings")){

        }
        else if(title.equalsIgnoreCase("Incoming request")){
          sendUserToRequestActivity();
        }
        else if(title.equalsIgnoreCase("Signout")){
            FirebaseAuth.getInstance().signOut();
            sendUserToLoginActivity();
        }
        return super.onOptionsItemSelected(item);
    }
    private void sendUserToRequestActivity(){
        Intent in = new Intent(HomeActivity.this,RequestActivity.class);
        startActivity(in);
    }
    private void sendUserToLoginActivity(){
        Intent in = new Intent(HomeActivity.this,MainActivity.class);
        startActivity(in);
        finish();
    }
    private void sendUserFindFriendActivity(){
        Intent in = new Intent(HomeActivity.this,FindFriendActivity.class);
        startActivity(in);
    }
}
