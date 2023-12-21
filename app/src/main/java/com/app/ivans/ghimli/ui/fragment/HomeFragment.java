package com.app.ivans.ghimli.ui.fragment;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.adapter.MenuAdapter;
import com.app.ivans.ghimli.model.Menu;
import com.app.ivans.ghimli.ui.activity.MenuActivity;
import com.app.ivans.ghimli.ui.viewmodel.HomeViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private CardView btn_layer, btn_cutter, btn_cut_piece_stock, btn_about;
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

        rvMenu.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        loadMenu();

        btn_layer = view.findViewById(R.id.btn_layer);
        btn_cutter = view.findViewById(R.id.btn_cutter);
        btn_cut_piece_stock = view.findViewById(R.id.btn_cut_piece_stock);
        btn_about = view.findViewById(R.id.btn_about);

        btn_layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLayerDrawerFragment();
            }
        });

        btn_cutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCutterDrawerFragment();
            }
        });

        btn_cut_piece_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCutPieceStockDrawerFragment();
            }
        });

        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAboutDrawerFragment();
            }
        });
        return view;
    }

    private void loadMenu() {
        ArrayList<Menu> menus = new ArrayList<>();

        menus.add(new Menu("Layer"));
        menus.add(new Menu("Cutter"));
        menus.add(new Menu("Bundle"));
        menus.add(new Menu("Stock In"));
        menus.add(new Menu("Stock Out"));
        menus.add(new Menu("Tentang"));

        mMenuAdapter = new MenuAdapter(getActivity(), menus, new MenuAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position, Menu menu) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new LayerFragment()).commit();
                ((MenuActivity)getActivity()).setCheckedItem(1);
            }
        }, new MenuAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position, Menu menu) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new LayerFragment()).commit();
                ((MenuActivity)getActivity()).setCheckedItem(1);
            }
        }, new MenuAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position, Menu menu) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new LayerFragment()).commit();
                ((MenuActivity)getActivity()).setCheckedItem(1);
            }
        }, new MenuAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position, Menu menu) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new LayerFragment()).commit();
                ((MenuActivity)getActivity()).setCheckedItem(1);
            }
        }, new MenuAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position, Menu menu) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new LayerFragment()).commit();
                ((MenuActivity)getActivity()).setCheckedItem(1);
            }
        }, new MenuAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position, Menu menu) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new LayerFragment()).commit();
                ((MenuActivity)getActivity()).setCheckedItem(1);
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

    private void showLayerDrawerFragment() {
        btn_layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new LayerFragment()).commit();
                ((MenuActivity)getActivity()).setCheckedItem(1);
            }
        });
    }

    private void showCutterDrawerFragment() {
        btn_cutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new CutterFragment()).commit();
                ((MenuActivity)getActivity()).setCheckedItem(2);
            }
        });
    }

    private void showCutPieceStockDrawerFragment() {
        btn_cut_piece_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new CutPieceStockFragment()).commit();
                ((MenuActivity)getActivity()).setCheckedItem(3);
            }
        });
    }

    private void showAboutDrawerFragment() {
        getFragmentManager().beginTransaction().replace(R.id.frame_container, new AboutFragment()).commit();
        ((MenuActivity)getActivity()).setCheckedItem(4);
    }

}