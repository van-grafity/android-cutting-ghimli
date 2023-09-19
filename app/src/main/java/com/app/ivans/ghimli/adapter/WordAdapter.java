package com.app.ivans.ghimli.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.app.ivans.ghimli.R;

import java.util.List;


public class WordAdapter extends ArrayAdapter<String> {


    public WordAdapter(Context context, List<String> words) {
        super(context, 0, words);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_word_list, parent, false);
        }

        final String currentWord = getItem(position);

        TextView name = listItemView.findViewById(R.id.txtWord);
        ImageView remove = listItemView.findViewById(R.id.iv_remove);
        name.setText(currentWord);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return listItemView;
    }

    private View.OnClickListener mMyButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();
            Toast.makeText(getContext(), "Row " + position + " was clicked!", Toast.LENGTH_SHORT).show();
        }
    };
}
