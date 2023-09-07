package com.app.ivans.ghimli.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.model.Comment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context mContext;
    private ArrayList<Comment> mItems;
    private CommentAdapter.OnItemClickListener mListener;

    public CommentAdapter(Context context, ArrayList<Comment> items, CommentAdapter.OnItemClickListener listener) {
        this.mContext = context;
        this.mItems = items;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
        Comment comment = mItems.get(position);
        Glide.with(mContext)
                .load(comment.getUrlToImage())
                .into(holder.ivProfile);
        holder.tvName.setText(comment.getName());
        holder.tvCurrentTime.setText(comment.getCurrentTime());
        holder.tvDesciption.setText(comment.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(v, position, comment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, Comment comment);
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile;
        TextView tvName;
        TextView tvCurrentTime;
        TextView tvDesciption;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvName = itemView.findViewById(R.id.tvName);
            tvCurrentTime = itemView.findViewById(R.id.tvCurrentTime);
            tvDesciption = itemView.findViewById(R.id.tvDesciption);

        }
    }
}

