package com.example.catatankeuangan.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.catatankeuangan.R;
import com.example.catatankeuangan.activity.LoginActivity;
import com.example.catatankeuangan.databinding.FragmentProfileBinding;
import com.example.catatankeuangan.preferences.PreferencesManager;
import com.example.catatankeuangan.util.FormatUtil;


public class ProfileFragment extends Fragment {

    private PreferencesManager pref;
    private FragmentProfileBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pref = new PreferencesManager(getContext());
        setupListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        getProfile();

    }

    private void setupListener(){
        binding.imageAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ProfileFragment.this)
                        .navigate(R.id.action_profileFragment_to_avatarFragment);
            }
        });
        binding.cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.clear();
                Toast.makeText(getContext(), "Keluar", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(requireActivity(), LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TOP)
                );
                requireActivity().finish();
            }
        });
    }


    private void getProfile(){
        binding.imageAvatar.setImageResource(pref.getInt("pref_user_avatar"));
        binding.textName.setText(pref.getString("pref_user_name"));
        binding.textEmail.setText(pref.getString("pref_user_email"));
        binding.textBalance.setText(getActivity().getIntent().getStringExtra("balance"));
        binding.textCreated.setText(FormatUtil.date(pref.getString("pref_user_date")));
    }
}