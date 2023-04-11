package com.app.ivans.ghimli.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.model.Department;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder> {
    private ArrayList<Department> mItems;
    private Context mContext;
    private DepartmentAdapter.OnItemClickListener onItemClickListener;

    public DepartmentAdapter(ArrayList<Department> items, Context context, DepartmentAdapter.OnItemClickListener onItemClickListener) {
        this.mItems = items;
        this.mContext = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dept, parent, false);
        return new DepartmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentViewHolder holder, int position) {
        Department searchEngine = mItems.get(position);
        holder.tvName.setText(searchEngine.getName());
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.llStream.setBackgroundColor(currentColor);
//        Glide.with(mContext)
//                .load(searchEngine.getImg())
//                .into(holder.ivImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnClick(v, holder.getAdapterPosition(), searchEngine);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public interface OnItemClickListener {
        void OnClick(View view, int position, Department searchEngine);
    }

    public static class DepartmentViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivImage;
        LinearLayout llStream;


        public DepartmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivImage = itemView.findViewById(R.id.ivImage);
            llStream = itemView.findViewById(R.id.llStream);
        }
    }
}