package com.example.hellochat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellochat.ChatRequest;
import com.example.hellochat.User;
import com.example.hellochat.databinding.RequestItemListBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ChatRequestAdapter extends FirebaseRecyclerAdapter<ChatRequest, ChatRequestAdapter.ChatRequestViewHolder> {
    DatabaseReference userReference,contatReference;
    Context context;
    public ChatRequestAdapter(FirebaseRecyclerOptions<ChatRequest> option,Context context){
        super(option);
        userReference = FirebaseDatabase.getInstance().getReference("User");
        contatReference = FirebaseDatabase.getInstance().getReference("Contacts");
        this.context = context;
    }
    @NonNull
    @Override
    public ChatRequestViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        RequestItemListBinding binding = RequestItemListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ChatRequestViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull  ChatRequestAdapter.ChatRequestViewHolder holder, int position, @NonNull  ChatRequest model) {
      String userId = getRef(position).getKey();
      userReference.child(userId).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull  DataSnapshot snapshot) {
              if(snapshot.exists()){
                  User user = snapshot.getValue(User.class);
                  Picasso.get().load(user.getImageUrl()).into(holder.binding.ivProfilePic);
                  holder.binding.tvName.setText(user.getName());
                  holder.binding.tvLastSeen.setText(user.getAbout());
                  holder.binding.btnAccept.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                       String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                       String friendId  = user.getUid();
                          HashMap<String,String>hm = new HashMap<String,String>();
                          hm.put("contact","saved");
                       contatReference.child(currentUserId).child(friendId).setValue(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull  Task<Void> task) {
                               if(task.isSuccessful()){
                                   contatReference.child(friendId).child(currentUserId).setValue(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull  Task<Void> task) {
                                           if(task.isSuccessful()){
                                               DatabaseReference chatRequestReference = FirebaseDatabase.getInstance().getReference("ChatRequest");
                                               chatRequestReference.child(currentUserId).child(friendId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                   @Override
                                                   public void onComplete(@NonNull  Task<Void> task) {
                                                       if(task.isSuccessful()){
                                                           chatRequestReference.child(friendId).child(currentUserId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                               @Override
                                                               public void onComplete(@NonNull  Task<Void> task) {
                                                                 if(task.isSuccessful()){
                                                                     Toast.makeText(context, "Contact savec..", Toast.LENGTH_SHORT).show();
                                                                 }
                                                               }
                                                           });
                                                       }
                                                   }
                                               });
                                           }
                                       }
                                   });
                               }
                           }
                       });
                      }
                  });
              }
          }

          @Override
          public void onCancelled(@NonNull  DatabaseError error) {

          }
      });

    }

    public class ChatRequestViewHolder extends RecyclerView.ViewHolder{
     RequestItemListBinding binding;
     public ChatRequestViewHolder(RequestItemListBinding binding){
         super(binding.getRoot());
         this.binding = binding;
     }
 }
}
