package com.example.hellochat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hellochat.databinding.HomeBinding;

public class HomeActivity extends AppCompatActivity {
    HomeBinding binding;
    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeBinding.inflate(LayoutInflater.from(this));

        sendUserToProfileActivity();
    }
    private void sendUserToProfileActivity(){
        Intent in = new Intent(this,ProfileActivity.class);
        startActivity(in);
    }
}
