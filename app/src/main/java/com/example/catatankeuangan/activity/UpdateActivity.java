package com.example.catatankeuangan.activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.catatankeuangan.R;
import com.example.catatankeuangan.adapter.CategoryAdapter;
import com.example.catatankeuangan.databinding.ActivityCreateBinding;
import com.example.catatankeuangan.model.CategoryResponse;
import com.example.catatankeuangan.model.SubmitResponse;
import com.example.catatankeuangan.model.TransactionRequest;
import com.example.catatankeuangan.model.TransactionResponse;
import com.example.catatankeuangan.preferences.PreferencesManager;
import com.example.catatankeuangan.retrofit.ApiEndpoint;
import com.example.catatankeuangan.retrofit.ApiService;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends BaseActivity {
    private final String TAG = "";

    private ActivityCreateBinding binding;
    private final ApiEndpoint api = ApiService.endpoint();
    private PreferencesManager pref;
    private TransactionResponse.Data data;
    private String type = "";
    private Integer categoryid = 0;
    private CategoryAdapter categoryAdapter;

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
        getTransaction();
    }

    private void setupRecyclerview(){
        categoryAdapter = new CategoryAdapter(UpdateActivity.this,new ArrayList<>(), new CategoryAdapter.AdapterListener(){

            @Override
            public void onClick(CategoryResponse.Data data) {
                categoryid = Integer.valueOf(data.getId());
                Log.e(TAG, "categoryid"+ categoryid);
            }
        });
        binding.listCategory.setAdapter(categoryAdapter);
    }

    private void setupListener(){
        binding.buttonIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "IN";
                setButton((MaterialButton) v);

            }
        });
        binding.buttonOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "OUT";
                setButton((MaterialButton) v);

            }
        });
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionRequest transactionRequest = new TransactionRequest(
                        data.getId(),
                        categoryid,
                        pref.getInt("pref_user_id"),
                        binding.editNote.getText().toString(),
                        Integer.parseInt(binding.editAmount.getText().toString()),
                        type
                );
                api.editTransaction(transactionRequest).enqueue(new Callback<SubmitResponse>() {
                    @Override
                    public void onResponse(Call<SubmitResponse> call, Response<SubmitResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Update sukses!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<SubmitResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void setButton(MaterialButton buttonSelected){
        List<MaterialButton> buttonList = new ArrayList<>();
        buttonList.add(binding.buttonIn);
        buttonList.add(binding.buttonOut);
        for (MaterialButton button: buttonList) {
            button.setTextColor(getResources().getColor(R.color.teal_200));
            ViewCompat.setBackgroundTintList(
                    button, ColorStateList.valueOf(getResources().getColor(R.color.white))
            );

        }
        buttonSelected.setTextColor(getResources().getColor(R.color.white));
        ViewCompat.setBackgroundTintList(
                buttonSelected, ColorStateList.valueOf(getResources().getColor(R.color.teal_700))
        );
    }

    private void getTransaction(){
        data = (TransactionResponse.Data) getIntent().getSerializableExtra("intent_data");
        Log.e(TAG, "intentData" + data.toString());

        type = data.getType();
        if (type.equals("IN")) {
            setButton(binding.buttonIn);
        }else { setButton(binding.buttonOut);}

        binding.editAmount.setText(data.getAmount().toString());
        binding.editNote.setText(data.getDescription());
    }

    private void getCategory(){
        api.listCategory().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.isSuccessful()){
                    List<CategoryResponse.Data> dataList = response.body().getData();
                    categoryAdapter.setData(dataList);
                    for (CategoryResponse.Data category: dataList) {
                        if (category.getName().contains(data.getCategory())) {
                            categoryAdapter.setButtonList(category);
                            categoryid = Integer.valueOf(category.getId());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });

    }
}