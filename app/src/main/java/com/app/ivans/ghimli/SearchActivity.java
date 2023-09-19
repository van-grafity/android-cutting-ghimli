package com.app.ivans.ghimli;

import static com.app.ivans.ghimli.utils.Extension.HISTORY_DATA;
import static com.app.ivans.ghimli.utils.Extension.KEYWORD;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.app.ivans.ghimli.adapter.WordAdapter;
import com.app.ivans.ghimli.base.BaseActivity;
import com.app.ivans.ghimli.databinding.ActivityCuttingOrderRecordDetailBinding;
import com.app.ivans.ghimli.databinding.ActivitySearchBinding;
import com.app.ivans.ghimli.databinding.ToolbarBinding;
import com.app.ivans.ghimli.net.FAPI;
import com.app.ivans.ghimli.viewmodel.CuttingViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";
    private ActivitySearchBinding binding;
    private ToolbarBinding toolbarBinding;
    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_RIGHT = 2;

    private String word;
    private WordAdapter adapter;
    private List<String> list;
    private SharedPreferences sharedpreferences;

    private CuttingViewModel mCuttingViewModel;

    @NonNull
    @Override
    protected ViewBinding createViewBinding(LayoutInflater layoutInflater) {
        binding = ActivitySearchBinding.inflate(layoutInflater);
        toolbarBinding = binding.toolbar;
        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbarBinding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mCuttingViewModel = new ViewModelProvider(SearchActivity.this).get(CuttingViewModel.class);

        // binding.wordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //     @Override
        //     public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //         int pos = adapterView.getPositionForView(view);
        //         Toast.makeText(SearchActivity.this, list.get(i), Toast.LENGTH_SHORT).show();
        //         Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        //         // Send KEYWORD to ResultActivity
        //         intent.putExtra(KEYWORD, list.get(i));
        //         startActivity(intent);
        //     }
        // });

        // public function search(Request $request)
    // {
    //     $input = $request->all();
    //     $cuttingOrderRecord = CuttingOrderRecord::with('cuttingOrderRecordDetail', 'cuttingOrderRecordDetail.color')->where('serial_number', 'like', '%' . $input['serial_number'] . '%')->get();
        
    //     if ($cuttingOrderRecord->isEmpty()) return $this->onError(404, 'Cutting Order Record not found.');

    //     $data = collect(
    //         [
    //             'cutting_order_record' => $cuttingOrderRecord
    //         ]
    //     );
    //     return $this->onSuccess($data, 'Cutting Order Record retrieved successfully.');
    // }

    // @FormUrlEncoded
    // @POST("cutting-orders/search")
    // Call<APIResponse> searchCuttingOrder(@Header("Authorization") String auth, @Field("serial_number") String serialNumber);

    // search
    // public LiveData<APIResponse> searchCuttingOrderLiveData(String auth, String serialNumber) {
    //     productResponseData = favoriteRepository.searchCuttingOrderResponse(auth, serialNumber);
    //     return productResponseData;
    // }

        // binding.wordList api searchCuttingOrderLiveData
        binding.wordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // binding.wordList api searchCuttingOrderLiveData
                mCuttingViewModel.searchCuttingOrderLiveData(FAPI.getToken(SearchActivity.this), list.get(i)).observe(SearchActivity.this, apiResponse -> {
                    if (apiResponse != null) {
                         if (apiResponse.getStatus() == 200) {
                             // binding.wordList api searchCuttingOrderLiveData
                             Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                             // Send KEYWORD to ResultActivity
                             intent.putExtra(KEYWORD, list.get(i));
                             startActivity(intent);
                         } else {
                             Toast.makeText(SearchActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                         }
//                        Toast.makeText(SearchActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        
        binding.wordList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("long clicked", "position: " + i);
                // Get value of a specific position
                word = list.get(i);
                if (word != null) {
                    // Remove a specific item from SharedPreferences
                    clearOneItemInSharedPreferences(word, getApplicationContext());
                    // Remove a specific item from ListView
                    adapter.remove(word);
                }
                Toast.makeText(SearchActivity.this, "Removed " + word, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        toolbarBinding.tvTitle.setVisibility(View.GONE);
        toolbarBinding.searchInHome.setVisibility(View.GONE);
        toolbarBinding.searchNew.setVisibility(View.VISIBLE);


        list = new ArrayList<>(getWords(this).keySet());

        adapter = new WordAdapter(this, list);
        binding.wordList.setDivider(null);
        binding.wordList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter = new WordAdapter(this, list);
        binding.wordList.setDivider(null);
        binding.wordList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll(v);
            }
        });

        toolbarBinding.editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
//                if(list.contains(s.toString())){
//                    adapter.getFilter().filter(s.toString());
//                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (toolbarBinding.editQuery.getText().toString().trim().length() == 1) {

                    toolbarBinding.editQuery.clearFocus();
                    toolbarBinding.editQuery.requestFocus();
                    toolbarBinding.editQuery.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, R.drawable.ic_close_gray, 0);
                } else if (toolbarBinding.editQuery.getText().toString().trim().length() == 0) {
                    toolbarBinding.editQuery.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, 0, 0);
                } else {
                    toolbarBinding.editQuery.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, R.drawable.ic_close_gray, 0);
                }
            }
        });

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        toolbarBinding.editQuery.requestFocus();

        toolbarBinding.editQuery.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // Your piece of code on keyboard search click
                Intent searchIntent = new Intent(SearchActivity.this, ResultActivity.class);
                word = toolbarBinding.editQuery.getText().toString().trim();
                // Set Key with its specific key
                setWord(getApplicationContext(), word, word);
                searchIntent.putExtra(KEYWORD, word);
                startActivity(searchIntent);
                return true;
            }
            return false;
        });

        toolbarBinding.editQuery.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (toolbarBinding.editQuery.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                    if (event.getRawX() >= (toolbarBinding.editQuery.getRight() - toolbarBinding.editQuery.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        toolbarBinding.editQuery.getText().clear();
                        return true;
                    }
                } else if (toolbarBinding.editQuery.getCompoundDrawables()[DRAWABLE_LEFT] != null) {
                    if (event.getRawX() <= (toolbarBinding.editQuery.getLeft() + toolbarBinding.editQuery.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                        // your action here
                        Toast.makeText(SearchActivity.this, "Search", Toast.LENGTH_SHORT).show();
                        //                    Intent mainIntent = new Intent(SearchActivity.this, ProductActivity.class);
//                    startActivity(mainIntent);
                        return true;
                    }
                }
            }
            return false;
        });

        sharedpreferences.registerOnSharedPreferenceChangeListener(prefChangeListener);
    }

    public void setWord(Context context, String key, String word) {
        sharedpreferences = context.getSharedPreferences(HISTORY_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(String.valueOf(key), word);
        editor.apply();
    }

    public Map<String, ?> getWords(Context context) {
        sharedpreferences = context.getSharedPreferences(HISTORY_DATA, Context.MODE_PRIVATE);
        // Returns a map containing a list of pairs key/value representing the preferences.
        return sharedpreferences.getAll();
    }

    public static void clearSharedPreferences(Context context) {
        context.getSharedPreferences(HISTORY_DATA, Context.MODE_PRIVATE).edit().clear().apply();
    }

    public static void clearOneItemInSharedPreferences(String key, Context context) {
        context.getSharedPreferences(HISTORY_DATA, Context.MODE_PRIVATE).edit().remove(key).apply();
    }

    @Override
    protected void onDestroy() {
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(prefChangeListener);
        super.onDestroy();
    }

    public void clearAll(View view) {
        clearSharedPreferences(getApplicationContext());
        adapter.clear();
        Toast.makeText(SearchActivity.this, "Cleared", Toast.LENGTH_SHORT).show();
    }

    private final SharedPreferences.OnSharedPreferenceChangeListener prefChangeListener = (sharedPreferences, key) -> {
        if (key.equals(word)) {
            // Clear the adapter, then add list
            adapter.clear();
            list = new ArrayList<>(getWords(getApplicationContext()).keySet());
            adapter.addAll(list);
            binding.wordList.setAdapter(adapter);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                if (getCurrentFocus() != null) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        } catch (Exception exception) {
            Log.e("HIDEKEYBOARD", "" + exception);
        }
    }
}