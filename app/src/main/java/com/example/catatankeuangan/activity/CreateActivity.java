package com.example.catatankeuangan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.catatankeuangan.R;
import com.example.catatankeuangan.adapter.CategoryAdapter;
import com.example.catatankeuangan.adapter.TransactionAdapter;
import com.example.catatankeuangan.databinding.ActivityCreateBinding;
import com.example.catatankeuangan.model.CategoryResponse;
import com.example.catatankeuangan.model.SubmitResponse;
import com.example.catatankeuangan.model.TransactionResponse;
import com.example.catatankeuangan.preferences.PreferencesManager;
import com.example.catatankeuangan.retrofit.ApiEndpoint;
import com.example.catatankeuangan.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class CreateActivity extends BaseActivity {
    private final String TAG = "CreateActivity";

    private ActivityCreateBinding binding;
    private final ApiEndpoint api = ApiService.endpoint();
    private PreferencesManager pref;
    private CategoryAdapter categoryAdapter;
    private String type = "";
    private Integer category = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pref = new PreferencesManager(this);
        setupRecyclerview();
        setupListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCategory();
    }

    private void setupRecyclerview(){
        categoryAdapter = new CategoryAdapter(CreateActivity.this,new ArrayList<>(), new CategoryAdapter.AdapterListener(){

            @Override
            public void onClick(CategoryResponse.Data data) {
                category = Integer.valueOf(data.getId());
            }
        });
        binding.listCategory.setAdapter(categoryAdapter);
    }

    private void setupListener(){
        binding.buttonIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "IN";
                binding.buttonIn.setTextColor(getResources().getColor(R.color.white));
                ViewCompat.setBackgroundTintList(
                        binding.buttonIn, ColorStateList.valueOf(getResources().getColor(R.color.teal_700))
                );
                binding.buttonOut.setTextColor(getResources().getColor(R.color.teal_200));
                ViewCompat.setBackgroundTintList(
                        binding.buttonOut, ColorStateList.valueOf(getResources().getColor(R.color.white))
                );

            }
        });
        binding.buttonOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "OUT";
                binding.buttonOut.setTextColor(getResources().getColor(R.color.white));
                ViewCompat.setBackgroundTintList(
                        binding.buttonOut, ColorStateList.valueOf(getResources().getColor(R.color.teal_700))
                );
                binding.buttonIn.setTextColor(getResources().getColor(R.color.teal_200));
                ViewCompat.setBackgroundTintList(
                        binding.buttonIn, ColorStateList.valueOf(getResources().getColor(R.color.white))
                );


            }
        });

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRequired()) {
                    binding.buttonSave.setEnabled(false);

                    api.transaction(
                            category,
                            pref.getInt("pref_user_id"),
                            binding.editNote.getText().toString(),
                            Integer.parseInt(binding.editAmount.getText().toString()),
                            type


                    ).enqueue(new Callback<SubmitResponse>() {
                                @Override
                                public void onResponse(Call<SubmitResponse> call, Response<SubmitResponse> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Transkasi berhasil", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SubmitResponse> call, Throwable t) {

                                }
                            });

                }

            }
        });


    }

    private void getCategory(){
        api.listCategory().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.isSuccessful()){
                    List <CategoryResponse.Data> dataList = response.body().getData();
//                    Log.e(TAG, dataList.toString());
                    categoryAdapter.setData(dataList);
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });

    }

    private Boolean isRequired(){
        if (type.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Tentukan tipe masuk atau keluar", Toast.LENGTH_SHORT).show();
            return false;
        }else if (category == 0) {
            Toast.makeText(getApplicationContext(), "Kategori transaksi tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.editAmount.getText().toString().isEmpty()) {
            binding.editAmount.setError("Masukkan nominal transaksi");
            return false;
        } else if (binding.editNote.getText().toString().isEmpty()) {
            binding.editNote.setError("Masukkan catatan transaksi");
            return false;
        }

        return true;
    }
}