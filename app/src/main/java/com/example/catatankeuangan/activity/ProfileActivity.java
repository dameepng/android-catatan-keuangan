package com.example.catatankeuangan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.catatankeuangan.R;
import com.example.catatankeuangan.databinding.ActivityLoginBinding;
import com.example.catatankeuangan.databinding.ActivityProfileBinding;
import com.example.catatankeuangan.preferences.PreferencesManager;

public class ProfileActivity extends BaseActivity {

    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}