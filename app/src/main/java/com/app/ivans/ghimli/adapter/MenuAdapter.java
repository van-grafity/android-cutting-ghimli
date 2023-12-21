package com.app.ivans.ghimli.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.model.Menu;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private Context mContext;
    private ArrayList<Menu> mMenus;
    private MenuAdapter.onItemClickListener mClickedLayer;
    private MenuAdapter.onItemClickListener mClickedCutter;
    private MenuAdapter.onItemClickListener mClickedBundle;
    private MenuAdapter.onItemClickListener mClickedStockIn;
    private MenuAdapter.onItemClickListener mClickedStockOut;
    private MenuAdapter.onItemClickListener mClickedAbout;

    public interface onItemClickListener {
        void onClick(View view, int position, Menu menu);
    }

    public MenuAdapter(Context mContext, ArrayList<Menu> mMenus, onItemClickListener mClickedLayer, onItemClickListener mClickedCutter, onItemClickListener mClickedBundle, onItemClickListener mClickedStockIn, onItemClickListener mClickedStockOut, onItemClickListener mClickedAbout) {
        this.mContext = mContext;
        this.mMenus = mMenus;
        this.mClickedLayer = mClickedLayer;
        this.mClickedCutter = mClickedCutter;
        this.mClickedBundle = mClickedBundle;
        this.mClickedStockIn = mClickedStockIn;
        this.mClickedStockOut = mClickedStockOut;
        this.mClickedAbout = mClickedAbout;
    }

    @NonNull
    @Override
    public MenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.MenuViewHolder holder, int position) {
        Menu menu = mMenus.get(position);

        holder.tvName.setText(menu.getName());

        if (position == 0) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickedLayer.onClick(v, holder.getAdapterPosition(), menu);
                }
            });
        } else if (position == 1) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickedCutter.onClick(v, holder.getAdapterPosition(), menu);
                }
            });
        } else if (position == 2) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickedBundle.onClick(v, holder.getAdapterPosition(), menu);
                }
            });
        } else if (position == 3) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickedBundle.onClick(v, holder.getAdapterPosition(), menu);
                }
            });
        } else if (position == 4) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickedStockIn.onClick(v, holder.getAdapterPosition(), menu);
                }
            });
        } else if (position == 5) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickedStockOut.onClick(v, holder.getAdapterPosition(), menu);
                }
            });
        } else if (position == 6) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickedAbout.onClick(v, holder.getAdapterPosition(), menu);
                }
            });
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.menu_not_found), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return mMenus.size() == 0 ? 0 : mMenus.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
