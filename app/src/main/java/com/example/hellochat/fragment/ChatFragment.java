package com.example.hellochat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hellochat.User;
import com.example.hellochat.adapter.ChatAdapter;
import com.example.hellochat.databinding.ChatFragmentBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatFragment extends Fragment {
    FirebaseRecyclerOptions<User> option;
    ChatFragmentBinding binding;
    ChatAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
         binding = ChatFragmentBinding.inflate(LayoutInflater.from(getActivity()));
        DatabaseReference contactReference = FirebaseDatabase.getInstance().getReference("Contacts");
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("User");
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

         option = new FirebaseRecyclerOptions.Builder<User>()
                .setIndexedQuery(contactReference.child(currentUserId),userReference,User.class)
                .build();

        return binding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new ChatAdapter(option,getActivity());
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
