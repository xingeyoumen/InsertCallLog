package com.syxfree.insertcalllog;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.syxfree.insertcalllog.databinding.ItemDemoBinding;

import java.util.List;

public class DemoStrAdapter extends RecyclerView.Adapter<DemoStrAdapter.DemoViewHolder> {
    private List<String> demoList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public DemoStrAdapter(Context context, List<String> demoList) {
        this.context = context;
        this.demoList = demoList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public DemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDemoBinding binding = ItemDemoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DemoViewHolder(binding, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DemoViewHolder holder, int position) {
        String itemStr = demoList.get(position);
        holder.binding.tgryhyjuhygt.setText(demoList.get(position));
    }

    @Override
    public int getItemCount() {
        return demoList.size();
    }

    class DemoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemDemoBinding binding = null;
        OnItemClickListener listener;

        public DemoViewHolder(@NonNull ItemDemoBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.listener = listener;
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClick(view, getPosition());
            }
        }
    }


}
