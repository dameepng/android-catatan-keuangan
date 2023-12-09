package com.example.catatankeuangan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.catatankeuangan.R;
import com.example.catatankeuangan.databinding.ActivityLoginBinding;
import com.example.catatankeuangan.model.LoginResponse;
import com.example.catatankeuangan.preferences.PreferencesManager;
import com.example.catatankeuangan.retrofit.ApiEndpoint;
import com.example.catatankeuangan.retrofit.ApiService;
import com.example.catatankeuangan.util.ErrorUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    private final ApiEndpoint api = ApiService.endpoint();
    private PreferencesManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pref = new PreferencesManager(this);
        setupListener();

    }

    @Override
    protected void onStart() {
        super.onStart();
        showProgress(false);
    }

    private void setupListener(){
        binding.textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRequired()) {

                    showProgress(true);
                    api.login(
                            binding.editEmail.getText().toString(),
                            binding.editPassword.getText().toString()
                    ).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            showProgress(false);
                            if (response.isSuccessful()) {
                                LoginResponse loginResponse = response.body();
                                Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                loginResponse(loginResponse);
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, ErrorUtil.getMessage(response), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            showProgress(false);

                        }
                    });

                } else {
                    Toast.makeText(LoginActivity.this, "Isi Data", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void showProgress(Boolean show) {
        if (show) {
            binding.progress.setVisibility(View.VISIBLE);
            binding.buttonLogin.setVisibility(View.GONE);
        }else {
            binding.progress.setVisibility(View.GONE);
            binding.buttonLogin.setVisibility(View.VISIBLE);
        }
    }

    private Boolean isRequired(){
        return (!binding.editEmail.getText().toString().isEmpty() && !binding.editPassword.getText().toString().isEmpty());
    }

    private void loginResponse(LoginResponse loginResponse){
        LoginResponse.Data data = loginResponse.getData();
        pref.put("pref_is_login", true);
        pref.put("pref_user_id", data.getId());
        pref.put("pref_user_name", data.getName());
        pref.put("pref_user_email", data.getEmail());
        pref.put("pref_user_date", data.getDate());
        pref.put("pref_user_avatar", R.drawable.avatar1);
    }
}