package com.example.catatankeuangan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.catatankeuangan.R;
import com.example.catatankeuangan.preferences.PreferencesManager;

public class Splashscreen extends BaseActivity {

    private PreferencesManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        pref = new PreferencesManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pref.getBoolean("pref_is_login"))
                    startActivity(new Intent(Splashscreen.this, HomeActivity.class));
                else
                    startActivity(new Intent(Splashscreen.this, LoginActivity.class));
                finish();
            }
        },2000);
    }
}