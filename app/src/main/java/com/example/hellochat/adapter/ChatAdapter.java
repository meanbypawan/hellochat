package com.example.hellochat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellochat.User;
import com.example.hellochat.databinding.FindFriendListItemBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class ChatAdapter extends FirebaseRecyclerAdapter<User, ChatAdapter.ChatViewHolder> {
    Context context;
    public ChatAdapter(FirebaseRecyclerOptions<User>option, Context context){
        super(option);
        this.context = context;
    }
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FindFriendListItemBinding binding = FindFriendListItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ChatViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position, @NonNull  User user) {
        Picasso.get().load(user.getImageUrl()).into(holder.binding.ivProfilePic);
        holder.binding.tvName.setText(user.getName());
        holder.binding.tvLastSeen.setText(user.getAbout());
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{
        FindFriendListItemBinding binding;
        public ChatViewHolder(FindFriendListItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
