package com.app.ivans.ghimli.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.model.CuttingTicket;

import java.util.List;

public class TransferAdapter extends RecyclerView.Adapter<TransferAdapter.TransferViewHolder> {
    private List<CuttingTicket> mItems;
    private Context mContext;

    public TransferAdapter(Context mContext, List<CuttingTicket> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @NonNull
    @Override
    public TransferAdapter.TransferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_transfer, parent, false);
        return new TransferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransferAdapter.TransferViewHolder holder, int position) {
        CuttingTicket model = mItems.get(position);
        holder.tvSerialNumber.setText(String.valueOf(model.getSerialNumber()));


    }

    @Override
    public int getItemCount() {
        return mItems.size() == 0 ? 0 : mItems.size();
    }

    public class TransferViewHolder extends RecyclerView.ViewHolder {
        TextView tvSerialNumber;

        public TransferViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSerialNumber = itemView.findViewById(R.id.tvSerialNumber);
        }
    }
}
