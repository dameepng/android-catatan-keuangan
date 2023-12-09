package com.example.catatankeuangan.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catatankeuangan.R;
import com.example.catatankeuangan.databinding.AdapterAvatarBinding;
import com.example.catatankeuangan.databinding.AdapterTransactionBinding;
import com.example.catatankeuangan.model.TransactionResponse;
import com.example.catatankeuangan.util.FormatUtil;

import java.util.List;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.ViewHolder> {

    private List<Integer> dataList;
    private AdapterListener listener;

    public AvatarAdapter(List<Integer> dataList, AdapterListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AvatarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                AdapterAvatarBinding
                        .inflate(LayoutInflater.from(parent.getContext()), parent,  false )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.binding.imageAvatar.setImageResource(dataList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(dataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterAvatarBinding binding;
        public ViewHolder(AdapterAvatarBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setData(List<Integer> data){
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public interface AdapterListener {
        void onClick(Integer avatar);

    }
}
