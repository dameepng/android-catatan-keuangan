package com.example.catatankeuangan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catatankeuangan.R;
import com.example.catatankeuangan.databinding.AdapterTransactionBinding;
import com.example.catatankeuangan.model.TransactionResponse;
import com.example.catatankeuangan.util.FormatUtil;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<TransactionResponse.Data> dataList;
    private AdapterListener listener;

    public TransactionAdapter(List<TransactionResponse.Data> dataList, AdapterListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                AdapterTransactionBinding
                        .inflate(LayoutInflater.from(parent.getContext()), parent,  false )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {

        final TransactionResponse.Data data = dataList.get( position );

        holder.binding.textCategory.setText(data.getCategory());
        holder.binding.textDescription.setText(data.getDescription());
        holder.binding.textDate.setText(data.getDate());
        holder.binding.textAmount.setText(FormatUtil.number(data.getAmount()));

        if (data.getType().equals("IN")) holder.binding.imgType.setImageResource(R.drawable.ic_in);
        else holder.binding.imgType.setImageResource(R.drawable.ic_out);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(data);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(data);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterTransactionBinding binding;
        public ViewHolder(AdapterTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setData(List<TransactionResponse.Data> data){
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public interface AdapterListener {
        void onClick(TransactionResponse.Data data);
        void onLongClick(TransactionResponse.Data data);

    }
}
