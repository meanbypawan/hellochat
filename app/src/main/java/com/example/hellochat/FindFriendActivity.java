package com.example.hellochat;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hellochat.adapter.UserAdapter;
import com.example.hellochat.databinding.FindFriednActivityBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FindFriendActivity extends AppCompatActivity {
    FindFriednActivityBinding binding;
    DatabaseReference userReference;
    UserAdapter adapter;
    FirebaseRecyclerOptions<User> option;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FindFriednActivityBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        setSupportActionBar(binding.findFriendToolBar);
        binding.findFriendToolBar.setTitle("Find friend");
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        userReference = FirebaseDatabase.getInstance().getReference("User");
         option =new FirebaseRecyclerOptions.Builder<User>()
                .setQuery(userReference,User.class)
                .build();
        adapter = new UserAdapter(option,FindFriendActivity.this);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
