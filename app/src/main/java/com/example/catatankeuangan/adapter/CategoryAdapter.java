package com.example.catatankeuangan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catatankeuangan.R;
import com.example.catatankeuangan.databinding.AdapterCategoryBinding;
import com.example.catatankeuangan.model.CategoryResponse;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryResponse.Data> dataList;
    private AdapterListener listener;

    private Context context;
    private List<MaterialButton> buttonList = new ArrayList<>();

    public CategoryAdapter(Context context, List<CategoryResponse.Data> dataList, AdapterListener listener) {
        this.dataList = dataList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                AdapterCategoryBinding
                        .inflate(LayoutInflater.from(parent.getContext()), parent,  false )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final CategoryResponse.Data data = dataList.get(position);
        holder.binding.buttonCategory.setText(data.getName());
        buttonList.add(holder.binding.buttonCategory);
        holder.binding.buttonCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(dataList.get(position));
                setButtonList(holder.binding.buttonCategory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterCategoryBinding binding;
        public ViewHolder(AdapterCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setData(List<CategoryResponse.Data> data){
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public interface AdapterListener {
        void onClick(CategoryResponse.Data data);

    }

    private void setButtonList(MaterialButton materialButton) {
        for (MaterialButton button: buttonList) {
            button.setTextColor(context.getResources().getColor(R.color.teal_200));
            ViewCompat.setBackgroundTintList(
                    button, ColorStateList.valueOf(context.getResources().getColor(R.color.white))
            );
        }
        materialButton.setTextColor(context.getResources().getColor(R.color.white));
        ViewCompat.setBackgroundTintList(
                materialButton, ColorStateList.valueOf(context.getResources().getColor(R.color.teal_700))
        );
    }

    public void setButtonList(CategoryResponse.Data category) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (MaterialButton button: buttonList) {
                    if (button.getText().toString().contains(category.getName())) {
                        button.setTextColor(context.getResources().getColor(R.color.white));
                        ViewCompat.setBackgroundTintList(
                                button, ColorStateList.valueOf(context.getResources().getColor(R.color.teal_700))
                        );
                    }
                }
            }
        }, 500);
    }
}
