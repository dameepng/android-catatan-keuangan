package com.example.catatankeuangan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.catatankeuangan.databinding.ActivityRegisterBinding;
import com.example.catatankeuangan.model.LoginResponse;
import com.example.catatankeuangan.model.SubmitResponse;
import com.example.catatankeuangan.retrofit.ApiEndpoint;
import com.example.catatankeuangan.retrofit.ApiService;
import com.example.catatankeuangan.util.ErrorUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding binding;
    private final ApiEndpoint api = ApiService.endpoint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showProgress(false);
    }

    private void setupListener() {
        binding.textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRequired()) {

                    showProgress(true);
                    api.register(
                            binding.editName.getText().toString(),
                            binding.editEmail.getText().toString(),
                            binding.editPassword.getText().toString()
                    ).enqueue(new Callback<SubmitResponse>() {
                        @Override
                        public void onResponse(Call<SubmitResponse> call, Response<SubmitResponse> response) {
                            showProgress(false);
                            if (response.isSuccessful()){
                                SubmitResponse submitResponse = response.body();
                                Toast.makeText(RegisterActivity.this, submitResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                finish();

                            }

                        }

                        @Override
                        public void onFailure(Call<SubmitResponse> call, Throwable t) {
                            showProgress(false);
                        }
                    });

                } else {
                    Toast.makeText(RegisterActivity.this, "Isi Data", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void showProgress(Boolean show) {
        if (show) {
            binding.progress.setVisibility(View.VISIBLE);
            binding.buttonRegister.setVisibility(View.GONE);
        }else {
            binding.progress.setVisibility(View.GONE);
            binding.buttonRegister.setVisibility(View.VISIBLE);
        }
    }

    private Boolean isRequired(){
        return (!binding.editName.getText().toString().isEmpty() &&
                !binding.editEmail.getText().toString().isEmpty() &&
                !binding.editPassword.getText().toString().isEmpty());
    }
}