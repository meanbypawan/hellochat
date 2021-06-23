package com.example.hellochat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hellochat.SendRequestActivity;
import com.example.hellochat.User;
import com.example.hellochat.databinding.FindFriendListItemBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends FirebaseRecyclerAdapter<User, UserAdapter.UserViewHolder> {

    Context context;
    public  UserAdapter(FirebaseRecyclerOptions<User> option,Context context){

        super(option);
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull  ViewGroup viewGroup, int i) {
        FindFriendListItemBinding binding = FindFriendListItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()),viewGroup,false);
        return new UserViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull  UserAdapter.UserViewHolder holder, int position, @NonNull  User model) {
      holder.binding.tvName.setText(model.getName());
        Picasso.get().load(model.getImageUrl()).into(holder.binding.ivProfilePic);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sendUserToSendRequestActivity(model);
            }
        });
    }
    private void sendUserToSendRequestActivity(User modal){
        Intent in = new Intent(context, SendRequestActivity.class);
        in.putExtra("user",modal);
        context.startActivity(in);
    }
    public class UserViewHolder extends RecyclerView.ViewHolder {
      FindFriendListItemBinding binding;
      public UserViewHolder(FindFriendListItemBinding binding){
          super(binding.getRoot());
          this.binding = binding;
      }
  }
}
