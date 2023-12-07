package com.app.ivans.ghimli.ui.fragment;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.ui.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private CardView btn_layer, btn_cutter, btn_cut_piece_stock, btn_about;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.fragment_home, container, false);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

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
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                LayerFragment layerFragment = new LayerFragment();
                ft.replace(R.id.frame_container, layerFragment);
                ft.commit();
            }
        });
    }

    private void showCutterDrawerFragment() {
        btn_cutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                CutterFragment cutterFragment = new CutterFragment();
                ft.replace(R.id.frame_container, cutterFragment);
                ft.commit();
            }
        });
    }

    private void showCutPieceStockDrawerFragment() {
        btn_cut_piece_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                CutPieceStockFragment cutPieceStockFragment = new CutPieceStockFragment();
                ft.replace(R.id.frame_container, cutPieceStockFragment);
                ft.commit();
            }
        });
    }

    private void showAboutDrawerFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        AboutFragment aboutFragment = new AboutFragment();
        ft.replace(R.id.frame_container, aboutFragment);
        ft.commit();
    }

}