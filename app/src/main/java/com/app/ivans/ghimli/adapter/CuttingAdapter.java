package com.app.ivans.ghimli.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.model.CuttingOrderRecord;

public class CuttingAdapter extends PagedListAdapter<CuttingOrderRecord, RecyclerView.ViewHolder> {
    private static final String TAG = "CuttingAdapter";
    private final Context mContext;
    private final ItemAdapterOnClickHandler mItemClick;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public interface ItemAdapterOnClickHandler {
        void onClick(CuttingOrderRecord cuttingOrder, View view, int position);
    }

    public CuttingAdapter(Context context, ItemAdapterOnClickHandler itemClick) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.mItemClick = itemClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_ITEM) {
            return createItemViewHolder(inflater, parent);
        } else {
            return createLoadingViewHolder(inflater, parent);
        }
    }

    private RecyclerView.ViewHolder createItemViewHolder(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_cutting_order_record, parent, false);
        return new CuttingViewHolder(view);
    }

    private RecyclerView.ViewHolder createLoadingViewHolder(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_loading, parent, false);
        return new LoadingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CuttingOrderRecord model = getItem(position);

        if (holder instanceof CuttingViewHolder) {
            bindCuttingViewHolder((CuttingViewHolder) holder, model);
        } else if (holder instanceof LoadingViewHolder) {
            bindLoadingViewHolder((LoadingViewHolder) holder);
        }
    }

    private void bindCuttingViewHolder(CuttingViewHolder holder, CuttingOrderRecord model) {
        holder.tvSerialNumber.setText(model.getSerialNumber());

        int progressDrawable = getProgressDrawable(model);
        holder.viewProgress.setBackground(mContext.getResources().getDrawable(progressDrawable));

        setClickListener(holder, model);
    }

    private int getProgressDrawable(CuttingOrderRecord model) {
        if (model.getCuttingOrderRecordDetail().isEmpty()) {
            return R.drawable.dot_not_yet_start;
        } else if (model.getStatusLayer().getId() == 2) {
            return R.drawable.dot_complete;
        } else {
            return R.drawable.dot_on_progress;
        }
    }

    private void setClickListener(CuttingViewHolder holder, CuttingOrderRecord model) {
        holder.itemView.setOnClickListener(v -> mItemClick.onClick(model, v, holder.getAdapterPosition()));
    }

    private void bindLoadingViewHolder(LoadingViewHolder holder) {
        holder.progressBar.setVisibility(View.VISIBLE);
    }

    static class CuttingViewHolder extends RecyclerView.ViewHolder {
        TextView tvSerialNumber;
        RelativeLayout viewContentSn;
        View viewProgress;

        private CuttingViewHolder(View itemView) {
            super(itemView);
            tvSerialNumber = itemView.findViewById(R.id.tvSerialNumber);
            viewContentSn = itemView.findViewById(R.id.viewContentSn);
            viewProgress = itemView.findViewById(R.id.viewProgress);
        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        private LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private static final DiffUtil.ItemCallback<CuttingOrderRecord> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CuttingOrderRecord>() {
                @Override
                public boolean areItemsTheSame(CuttingOrderRecord oldItem, CuttingOrderRecord newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(CuttingOrderRecord oldItem, CuttingOrderRecord newItem) {
                    return oldItem.getId() == newItem.getId();
                }
            };
}