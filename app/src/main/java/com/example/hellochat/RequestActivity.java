package com.example.hellochat;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hellochat.databinding.RequestActiivtyBinding;
import com.example.hellochat.databinding.SendRequestActivityBinding;

public class RequestActivity extends AppCompatActivity {
    RequestActiivtyBinding binding;

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RequestActiivtyBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
    }
}
