package com.example.hellochat;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hellochat.adapter.ChatRequestAdapter;
import com.example.hellochat.databinding.RequestActiivtyBinding;
import com.example.hellochat.databinding.SendRequestActivityBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestActivity extends AppCompatActivity {
    RequestActiivtyBinding binding;
    DatabaseReference chatRequestReference;
    String currentUserId;
    ChatRequestAdapter adapter;
    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RequestActiivtyBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.requestToolBar.setTitle("Friend request");
        setSupportActionBar(binding.requestToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chatRequestReference = FirebaseDatabase.getInstance().getReference("ChatRequest");
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<ChatRequest> option = new FirebaseRecyclerOptions.Builder<ChatRequest>()
                .setQuery(chatRequestReference.child(currentUserId),ChatRequest.class)
                .build();
        adapter = new ChatRequestAdapter(option,this);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(RequestActivity.this));
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
