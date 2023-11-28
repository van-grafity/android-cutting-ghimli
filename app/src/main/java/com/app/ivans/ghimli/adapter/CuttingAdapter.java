package com.app.ivans.ghimli.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.model.CuttingOrderRecord;

public class CuttingAdapter extends PagedListAdapter<CuttingOrderRecord, CuttingAdapter.CuttingViewHolder> {
    private static final String TAG = "MenuAdapter";
    private Context mContext;
    private itemAdapterOnClickHandler mItemClick;

    public interface itemAdapterOnClickHandler {
        void onClick(CuttingOrderRecord cuttingOrder, View view, int position);
    }

    public CuttingAdapter(Context context, itemAdapterOnClickHandler itemClick) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.mItemClick = itemClick;
    }

    @Override
    public CuttingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cutting_order_record, parent, false);
        return new CuttingAdapter.CuttingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CuttingViewHolder holder, int position) {
        CuttingOrderRecord model = getItem(position);
//        Toast.makeText(mContext, model.getSerialNumber(),Toast.LENGTH_SHORT).show();
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
                mItemClick.onClick(model, v, holder.getAdapterPosition());
            }
        });
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

    private static DiffUtil.ItemCallback<CuttingOrderRecord> DIFF_CALLBACK =
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
