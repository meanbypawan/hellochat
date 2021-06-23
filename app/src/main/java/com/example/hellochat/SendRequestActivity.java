package com.example.hellochat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hellochat.databinding.CreateProfileBinding;
import com.example.hellochat.databinding.SendRequestActivityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SendRequestActivity extends AppCompatActivity {
    SendRequestActivityBinding binding;
    DatabaseReference chatRequestReference;
    String currentUserId,receiverUserId;

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = SendRequestActivityBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        Intent in = getIntent();
        User user = (User)in.getSerializableExtra("user");
        if(user!=null){
            Picasso.get().load(user.getImageUrl()).into(binding.profileImage);
            binding.tvName.setText(user.getName());
            binding.tvAbout.setText(user.getAbout());
            receiverUserId = user.getUid();
        }
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        chatRequestReference = FirebaseDatabase.getInstance().getReference("ChatRequest").child(currentUserId);

        isRequestSent();
        binding.btnSendRquest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.btnSendRquest.getText().toString().equalsIgnoreCase("Send request")) {
                    sendRequest();
                }
                else if(binding.btnSendRquest.getText().toString().equalsIgnoreCase("Cancel request")){
                    cancelRequest();
                }
            }
        });
    }
    private void cancelRequest(){
      chatRequestReference.child(receiverUserId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull  Task<Void> task) {
             if(task.isSuccessful()){
                 FirebaseDatabase.getInstance().getReference("ChatRequest").child(receiverUserId).child(currentUserId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull  Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SendRequestActivity.this, "Request cancelled", Toast.LENGTH_SHORT).show();
                                binding.btnSendRquest.setText("Send request");
                            }
                     }
                 });
             }
          }
      });
    }
    private void isRequestSent(){
        chatRequestReference.child(receiverUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
              if(snapshot.exists()){
                 ChatRequest cr =  snapshot.getValue(ChatRequest.class);
                 if(cr.getRequestType().equalsIgnoreCase("sent")){
                     binding.btnSendRquest.setText("Cancel request");
                 }


              }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }
    private void sendRequest(){
        ChatRequest request = new ChatRequest("sent");
        chatRequestReference.child(receiverUserId).setValue(request).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference("ChatRequest").child(receiverUserId).child(currentUserId).setValue(new ChatRequest("received")).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull  Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SendRequestActivity.this, "Friend request sent", Toast.LENGTH_SHORT).show();
                                binding.btnSendRquest.setText("Cancel request");
                            }
                        }
                    });
                }
            }
        });
    }
}
