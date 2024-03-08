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
import com.app.ivans.ghimli.model.CuttingOrderRecordDetail;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class FabricStickerAdapter extends RecyclerView.Adapter<FabricStickerAdapter.FabricStickerViewHolder> {
    private ArrayList<CuttingOrderRecordDetail> mItems;
    private Context mContext;
    private FabricStickerAdapter.OnItemClickListener onItemClickListener;

    public FabricStickerAdapter(ArrayList<CuttingOrderRecordDetail> items, Context context, FabricStickerAdapter.OnItemClickListener onItemClickListener) {
        this.mItems = items;
        this.mContext = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public FabricStickerAdapter.FabricStickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sticker_roll_no, parent, false);
        return new FabricStickerAdapter.FabricStickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FabricStickerAdapter.FabricStickerViewHolder holder, int position) {
        CuttingOrderRecordDetail model = mItems.get(position);
        holder.tvStickerNo.setText(model.getFabricRoll());
        holder.tvBalanceEnd.setText(String.valueOf(model.getBalanceEnd()));
        holder.tvBatch.setText(String.valueOf(model.getFabricBatch()));
        holder.tvLayer.setText(String.valueOf(model.getLayer()));
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
        void OnClick(View view, int position, CuttingOrderRecordDetail model);
    }

    public static class FabricStickerViewHolder extends RecyclerView.ViewHolder {
        TextView tvStickerNo;
        TextView tvBalanceEnd;
        TextView tvBatch;
        TextView tvLayer;
        RelativeLayout viewContentSn;

        public FabricStickerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStickerNo = itemView.findViewById(R.id.tvStickerNo);
            tvBalanceEnd = itemView.findViewById(R.id.tvBalanceEnd);
            tvBatch = itemView.findViewById(R.id.tvBatch);
            tvLayer = itemView.findViewById(R.id.tvLayer);
            viewContentSn = itemView.findViewById(R.id.viewContentSn);
        }
    }
}