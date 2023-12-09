package com.example.catatankeuangan.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.example.catatankeuangan.activity.BaseActivity;
import com.example.catatankeuangan.adapter.TransactionAdapter;
import com.example.catatankeuangan.databinding.ActivityHomeBinding;
import com.example.catatankeuangan.model.SubmitResponse;
import com.example.catatankeuangan.model.TransactionResponse;
import com.example.catatankeuangan.preferences.PreferencesManager;
import com.example.catatankeuangan.retrofit.ApiEndpoint;
import com.example.catatankeuangan.retrofit.ApiService;
import com.example.catatankeuangan.util.FormatUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity {

    private ActivityHomeBinding binding;
    private final ApiEndpoint api = ApiService.endpoint();
    private TransactionAdapter transactionAdapter;
    private PreferencesManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        pref = new PreferencesManager(this);
        setupRecyclerview();
        setupListener();


    }

    @Override
    protected void onStart() {
        super.onStart();
        setAvatar();
        getTransaction();
    }

    private void setupRecyclerview(){
        transactionAdapter = new  TransactionAdapter(new ArrayList<>(), new TransactionAdapter.AdapterListener(){

            @Override
            public void onClick(TransactionResponse.Data data) {
                startActivity(new Intent(HomeActivity.this, UpdateActivity.class)
                        .putExtra("intent_data",data));

            }
            @Override
            public void onLongClick(TransactionResponse.Data data){
                AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                alertDialog.setTitle("Hapus");
                alertDialog.setMessage("Hapus Transaksi " + data.getDescription() + "?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteTransaction(Integer.valueOf(data.getId()));
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        binding.listTransaction.setAdapter(transactionAdapter);
    }

    private void setupListener(){
        binding.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class)
                        .putExtra("balance", binding.textBalance.getText().toString())
                );
            }
        });
        binding.fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(HomeActivity.this, CreateActivity.class));
            }
        });
    }

    private void getTransaction(){

        api.listTransaction(pref.getInt("pref_user_id"))
                .enqueue(new Callback<TransactionResponse>() {
                    @Override
                    public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                        if (response.isSuccessful()) {
                            TransactionResponse transactionResponse = response.body();
                            setBalance(transactionResponse);
                            transactionAdapter.setData( transactionResponse.getData() );

                        }
                    }

                    @Override
                    public void onFailure(Call<TransactionResponse> call, Throwable t) {
                        Log.e("HomeActivity", t.toString());

                    }
                });

    }

    private void setBalance(TransactionResponse transactionResponse){
        binding.textBalance.setText("Rp "+FormatUtil.number(transactionResponse.getBalance() ) );
        binding.textIn.setText("Rp "+FormatUtil.number(transactionResponse.getTotalIn() ) );
        binding.textOut.setText("Rp "+FormatUtil.number(transactionResponse.getTotalOut() ) );

    }

    private void setAvatar(){
        binding.textAvatar.setText(pref.getString("pref_user_name"));
        binding.imgAvatar.setImageResource(pref.getInt("pref_user_avatar"));

    }

    private void deleteTransaction(Integer id){
        api.deleteTransaction( id ).enqueue(new Callback<SubmitResponse>() {
            @Override
            public void onResponse(Call<SubmitResponse> call, Response<SubmitResponse> response) {
                if (response.isSuccessful()) {
                    getTransaction();
                }
            }

            @Override
            public void onFailure(Call<SubmitResponse> call, Throwable t) {

            }
        });
    }
}