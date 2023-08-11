package com.app.ivans.ghimli.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.model.CuttingOrderRecord;

import java.util.ArrayList;
import java.util.Random;

public class CuttingOrderRecordAdapter extends RecyclerView.Adapter<CuttingOrderRecordAdapter.CuttingOrderRecordViewHolder> {
    private ArrayList<CuttingOrderRecord> mItems;
    private Context mContext;
    private CuttingOrderRecordAdapter.OnItemClickListener onItemClickListener;

    public CuttingOrderRecordAdapter(ArrayList<CuttingOrderRecord> items, Context context, CuttingOrderRecordAdapter.OnItemClickListener onItemClickListener) {
        this.mItems = items;
        this.mContext = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CuttingOrderRecordAdapter.CuttingOrderRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cutting_order_record, parent, false);
        return new CuttingOrderRecordAdapter.CuttingOrderRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CuttingOrderRecordAdapter.CuttingOrderRecordViewHolder holder, int position) {
        CuttingOrderRecord model = mItems.get(position);
        holder.tvSerialNumber.setText(model.getSerialNumber());
//        Random rnd = new Random();
//        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//        holder.viewContentSn.setBackgroundColor(currentColor);
        if (model.getCuttingOrderRecordDetail().size() == 0) {
            holder.viewProgress.setBackground(mContext.getResources().getDrawable(R.drawable.dot_not_yet_start));
        } else if (model.getStatusLayer().getId() == 2) {
            holder.viewProgress.setBackground(mContext.getResources().getDrawable(R.drawable.dot_complete));
        } else {
            holder.viewProgress.setBackground(mContext.getResources().getDrawable(R.drawable.dot_on_progress));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnClick(v, holder.getAdapterPosition(), model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public interface OnItemClickListener {
        void OnClick(View view, int position, CuttingOrderRecord model);
    }

    public void setItems(ArrayList<CuttingOrderRecord> cuttingOrderRecords) {
        this.mItems = cuttingOrderRecords;
    }

    public static class CuttingOrderRecordViewHolder extends RecyclerView.ViewHolder {
        TextView tvSerialNumber;
        RelativeLayout viewContentSn;
        View viewProgress;

        public CuttingOrderRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSerialNumber = itemView.findViewById(R.id.tvSerialNumber);
            viewContentSn = itemView.findViewById(R.id.viewContentSn);
            viewProgress = itemView.findViewById(R.id.viewProgress);
        }
    }
}