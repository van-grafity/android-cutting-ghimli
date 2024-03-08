package com.app.ivans.ghimli.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.adapter.MenuAdapter;
import com.app.ivans.ghimli.model.Menu;
import com.app.ivans.ghimli.ui.activity.MenuActivity;
import com.app.ivans.ghimli.ui.activity.ScanQrActivity;
import com.app.ivans.ghimli.ui.activity.StockOutActivity;
import com.app.ivans.ghimli.ui.viewmodel.HomeViewModel;
import com.app.ivans.ghimli.utils.Extension;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private RecyclerView rvMenu;
    private MenuAdapter mMenuAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.fragment_home, container, false);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvMenu = view.findViewById(R.id.rvMenu);

        rvMenu.setLayoutManager(new GridLayoutManager(getContext(), 5, GridLayoutManager.VERTICAL, false));

        loadMenu();

        return view;
    }

    private void loadMenu() {
        ArrayList<Menu> menus = new ArrayList<>();

        menus.add(new Menu("Layer", R.drawable.ic_layers_yellow));
        menus.add(new Menu("Cutter", R.drawable.ic_cut_yellow));
        menus.add(new Menu("Bundle", R.drawable.ic_in_yellow));
        menus.add(new Menu("Stock In", R.drawable.ic_ingoing_yellow));
        menus.add(new Menu("Stock Out", R.drawable.ic_outgoing_yellow));
        menus.add(new Menu("Tentang", R.drawable.ic_info_yellow));

        mMenuAdapter = new MenuAdapter(getActivity(), menus, new MenuAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position, Menu menu) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new LayerFragment()).commit();
                ((MenuActivity) getActivity()).setCheckedItem(1);
            }
        }, new MenuAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position, Menu menu) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new CutterFragment()).commit();
                ((MenuActivity) getActivity()).setCheckedItem(2);
            }
        }, new MenuAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position, Menu menu) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new BundleFragment()).commit();
                ((MenuActivity) getActivity()).setCheckedItem(3);
            }
        }, new MenuAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position, Menu menu) {
                Intent intent = new Intent(getActivity(), ScanQrActivity.class);
                intent.putExtra(Extension.CUTTING_QR, "CT");
                startActivity(intent);
                ((MenuActivity) getActivity()).setCheckedItem(4);
            }
        }, new MenuAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position, Menu menu) {
                Intent intent = new Intent(getActivity(), StockOutActivity.class);
                startActivity(intent);
                ((MenuActivity) getActivity()).setCheckedItem(5);
            }
        }, new MenuAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position, Menu menu) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new AboutFragment()).commit();
                ((MenuActivity) getActivity()).setCheckedItem(6);
            }
        });

        rvMenu.setHasFixedSize(true);
        rvMenu.setAdapter(mMenuAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

    private void showAboutDrawerFragment() {
        getFragmentManager().beginTransaction().replace(R.id.frame_container, new AboutFragment()).commit();
        ((MenuActivity) getActivity()).setCheckedItem(4);
    }

}