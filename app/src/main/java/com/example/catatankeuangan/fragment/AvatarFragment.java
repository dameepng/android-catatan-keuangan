package com.example.catatankeuangan.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.catatankeuangan.R;
import com.example.catatankeuangan.adapter.AvatarAdapter;
import com.example.catatankeuangan.databinding.FragmentAvatarBinding;
import com.example.catatankeuangan.databinding.FragmentProfileBinding;
import com.example.catatankeuangan.preferences.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class AvatarFragment extends Fragment {

    private FragmentAvatarBinding binding;
    private AvatarAdapter avatarAdapter;
    private PreferencesManager pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAvatarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pref = new PreferencesManager((getContext()));
        setupRyclerView();
    }

    private void setupRyclerView(){
        List<Integer> avatars = new ArrayList<>();
        avatars.add(R.drawable.avatar1);
        avatars.add(R.drawable.avatar2);
        avatarAdapter = new AvatarAdapter(avatars, new AvatarAdapter.AdapterListener() {
            @Override
            public void onClick(Integer avatar) {
                pref.put("pref_user_avatar", avatar);
                NavHostFragment.findNavController(AvatarFragment.this)
                        .navigateUp();

            }
        });
        binding.listAvatar.setAdapter(avatarAdapter);
    }
}