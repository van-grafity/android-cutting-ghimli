package com.app.ivans.ghimli.adapter;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;

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
        holder.tvColor.setText(model.getLayingPlanningDetail().getLayingPlanning().getColor().getName());
        holder.tvStyle.setText(model.getLayingPlanningDetail().getLayingPlanning().getStyle().getStyle());
//        holder.tvStatusProgressLayer.setText(model.getStatusLayer().getName());
//        holder.tvStatusProgressCut.setText(model.getStatusCut().getName());

//        int progressDrawable = getProgressDrawable(model);
//        holder.viewProgress.setBackground(mContext.getResources().getDrawable(progressDrawable));
        if (model.getStatusLayer().getId() == 1) {
            Glide.with(mContext)
                    .load(R.drawable.dot_not_yet_start)
                    .into(holder.viewProgressLayer);
            holder.tvStatusProgressLayer.setText("Belum Layer");
        } else if (model.getStatusLayer().getId() == 2) {
            Glide.with(mContext)
                    .load(R.drawable.dot_complete)
                    .into(holder.viewProgressLayer);
            holder.tvStatusProgressLayer.setText("Sudah Layer");
        } else if (model.getStatusLayer().getId() == 4) {
            Glide.with(mContext)
                    .load(R.drawable.dot_on_progress)
                    .into(holder.viewProgressLayer);
        } else {
            Glide.with(mContext)
                    .load(R.drawable.dot_on_progress)
                    .into(holder.viewProgressLayer);
            holder.tvStatusProgressLayer.setText("Sedang di Layer");
        }

        if (model.getStatusCut().getId() == 1) {
            Glide.with(mContext)
                    .load(R.drawable.dot_not_yet_start)
                    .into(holder.viewProgressCut);
            holder.tvStatusProgressCut.setText("Belum Potong");
        } else if (model.getStatusCut().getId() == 2) {
            Glide.with(mContext)
                    .load(R.drawable.dot_complete)
                    .into(holder.viewProgressCut);
            holder.tvStatusProgressCut.setText("Sudah Potong");
        } else {
            Glide.with(mContext)
                    .load(R.drawable.dot_on_progress)
                    .into(holder.viewProgressCut);
            holder.tvStatusProgressCut.setText("Sedang di Potong");
        }

        setClickListener(holder, model);
        setClickListenerCopy(holder, model);
    }

    private void setClickListenerCopy(CuttingViewHolder holder, CuttingOrderRecord model) {
        holder.ivCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", model.getSerialNumber());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, "Copy serial number " + model.getSerialNumber(), Toast.LENGTH_SHORT).show();
            }
        });
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
        TextView tvColor;
        TextView tvStyle;
        TextView tvStatusProgressLayer;
        TextView tvStatusProgressCut;
        ImageView ivCopy;
        RelativeLayout viewContentSn;
        ImageView viewProgressLayer;
        ImageView viewProgressCut;

        private CuttingViewHolder(View itemView) {
            super(itemView);
            tvSerialNumber = itemView.findViewById(R.id.tvSerialNumber);
//            tvSerialNumber.setTypeface(Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/Lato-Regular.ttf"));
            tvColor = itemView.findViewById(R.id.tvColor);
            tvStyle = itemView.findViewById(R.id.tvStyle);
            tvStatusProgressLayer = itemView.findViewById(R.id.tvStatusProgressLayer);
            tvStatusProgressCut = itemView.findViewById(R.id.tvStatusProgressCut);
            ivCopy = itemView.findViewById(R.id.iv_copy);
            viewContentSn = itemView.findViewById(R.id.viewContentSn);
            viewProgressLayer = itemView.findViewById(R.id.viewProgressLayer);
            viewProgressCut = itemView.findViewById(R.id.viewProgressCut);
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

    public enum Level {
        NORMAL,
        lAYERS,
        CUTTER
    }
}