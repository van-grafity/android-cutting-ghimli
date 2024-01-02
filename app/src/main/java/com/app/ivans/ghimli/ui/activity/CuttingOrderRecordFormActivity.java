package com.app.ivans.ghimli.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.adapter.ColorAdapter;
import com.app.ivans.ghimli.base.BaseActivity;
import com.app.ivans.ghimli.databinding.ActivityCuttingOrderRecordFormBinding;
import com.app.ivans.ghimli.databinding.ToolbarBinding;
import com.app.ivans.ghimli.model.APIResponse;
import com.app.ivans.ghimli.model.CuttingOrderRecordDetail;
import com.app.ivans.ghimli.model.CuttingRecordRemark;
import com.app.ivans.ghimli.net.API;
import com.app.ivans.ghimli.ui.custom.CommentContentBottomSheetDialog;
import com.app.ivans.ghimli.ui.viewmodel.CuttingViewModel;
import com.app.ivans.ghimli.utils.Extension;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CuttingOrderRecordFormActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, CommentContentBottomSheetDialog.ItemClickListener {
    private static final String TAG = "CuttingLayingSheetFormActivity";
    private ActivityCuttingOrderRecordFormBinding binding;
    private CuttingViewModel cuttingViewModel;
    private String mSerialNumber, mFabricRoll, mFabricBatch, colorName, mYard, mWeight, mLayer, mJoint, mBalanceEnd, mCode;
    private ColorAdapter mColorAdapter;
    private ToolbarBinding toolbarBinding;
    private ArrayList<String> items;
    private double inch;
    private String nameUser;
    CommentContentBottomSheetDialog commentContentBottomSheetDialog;

    private double yrd;
    private ArrayAdapter<String> remarks;
    private String spItem;

    @NonNull
    @Override
    protected ViewBinding createViewBinding(LayoutInflater layoutInflater) {
        binding = ActivityCuttingOrderRecordFormBinding.inflate(layoutInflater);
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
        toolbarBinding.tvTitle.setVisibility(View.VISIBLE);
        toolbarBinding.tvTitle.setText("Cutting Order Record");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                mSerialNumber = null;
                mCode = null;
            } else {
                mSerialNumber = extras.getString("serialNumber");
                mCode = extras.getString(Extension.CUTTING_CODE);
                binding.etSerialNumber.setText(mSerialNumber);
                binding.etSerialNumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(CuttingOrderRecordFormActivity.this, "Serial number can't edit", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }


        commentContentBottomSheetDialog = CommentContentBottomSheetDialog.newInstance();
        cuttingViewModel = new ViewModelProvider(CuttingOrderRecordFormActivity.this).get(CuttingViewModel.class);

        binding.etOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CuttingOrderRecordFormActivity.this, "User can't edit", Toast.LENGTH_SHORT).show();
            }
        });

        getInitDataFirst();

        binding.spRemarks.setOnItemSelectedListener(this);

        binding.etLayer.addTextChangedListener(mTextWatcher);
        binding.etYardage.addTextChangedListener(mTextWatcher);

        submit();

        binding.ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showComment();
            }
        });
    }

    private void getInitDataFirst() {
        runOnUiThread(new Runnable() {
            public void run() {
                Extension.showLoading(CuttingOrderRecordFormActivity.this);
            }
        });
        cuttingViewModel.getLayingPlanningBySerialNumberLiveData(API.getToken(CuttingOrderRecordFormActivity.this), mSerialNumber).observe(CuttingOrderRecordFormActivity.this, new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
                if (apiResponse.getData() == null) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingOrderRecordFormActivity.this);

                    alertDialogBuilder.setTitle(getString(R.string.app_name));
                    alertDialogBuilder
                            .setMessage(apiResponse.getMessage())
                            .setCancelable(false)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(CuttingOrderRecordFormActivity.this, ScanQrActivity.class));
                                    finish();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                }

                if (mCode.equals("LAYER_CODE")) {
                    Toast.makeText(CuttingOrderRecordFormActivity.this, mCode, Toast.LENGTH_SHORT).show();
                    if (apiResponse.getData().getCuttingOrderRecord().getStatusLayer().getName().equals("completed") && apiResponse.getData().getCuttingOrderRecord().getStatusCut().getName().equals("belum")) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingOrderRecordFormActivity.this);

                        alertDialogBuilder.setTitle(getString(R.string.app_name));
                        alertDialogBuilder
                                .setMessage("Cutting Order ini sudah selesai di layer.")
                                .setCancelable(false)
                                .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(CuttingOrderRecordFormActivity.this, ScanQrActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else if (apiResponse.getData().getCuttingOrderRecord().getStatusLayer().getName().equals("completed") && apiResponse.getData().getCuttingOrderRecord().getStatusCut().getName().equals("sudah")) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingOrderRecordFormActivity.this);

                        alertDialogBuilder.setTitle(getString(R.string.app_name));
                        alertDialogBuilder
                                .setMessage("Cutting Order ini sudah selesai di potong.")
                                .setCancelable(false)
                                .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(CuttingOrderRecordFormActivity.this, ScanQrActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
                        nameUser = API.currentUser(CuttingOrderRecordFormActivity.this).getName();
                        binding.etOperator.setText(nameUser);

                        colorName = apiResponse.getData().getCuttingOrderRecord().getLayingPlanningDetail().getLayingPlanning().getColor().getName();
                        yrd = apiResponse.getData().getCuttingOrderRecord().getLayingPlanningDetail().getMarkerYard();
                        inch = apiResponse.getData().getCuttingOrderRecord().getLayingPlanningDetail().getMarkerInch();
                        binding.etColor.setText(colorName);

                        loadDataRemarks();

                        binding.etColor.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(CuttingOrderRecordFormActivity.this, "Color can't edit", Toast.LENGTH_SHORT).show();
                            }
                        });
                        binding.etMarkerYard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(CuttingOrderRecordFormActivity.this, "Marker yard can't edit", Toast.LENGTH_SHORT).show();
                            }
                        });
                        binding.etMarkerYard.setText(new DecimalFormat("##.##").format(Double.parseDouble(markerYard(yrd))));
                    }
                } else if (mCode.equals("CUT_CODE")) {
                    Toast.makeText(CuttingOrderRecordFormActivity.this, mCode, Toast.LENGTH_SHORT).show();
                if (apiResponse.getData().getCuttingOrderRecord().getStatusLayer().getName().equals("not completed")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingOrderRecordFormActivity.this);

                    alertDialogBuilder.setTitle(getString(R.string.app_name));
                    alertDialogBuilder
                            .setMessage("Cutting Order ini belum di layer.")
                            .setCancelable(false)
                            .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(CuttingOrderRecordFormActivity.this, ScanQrActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else if (apiResponse.getData().getCuttingOrderRecord().getStatusLayer().getName().equals("on progress")){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingOrderRecordFormActivity.this);

                    alertDialogBuilder.setTitle(getString(R.string.app_name));
                    alertDialogBuilder
                            .setMessage("Cutting Order ini sedang di layer.")
                            .setCancelable(false)
                            .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(CuttingOrderRecordFormActivity.this, ScanQrActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else if (apiResponse.getData().getCuttingOrderRecord().getStatusLayer().getName().equals("completed") && apiResponse.getData().getCuttingOrderRecord().getStatusCut().getName().equals("belum")) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingOrderRecordFormActivity.this);

                        alertDialogBuilder.setTitle(getString(R.string.app_name));
                        alertDialogBuilder
                                .setMessage("Layer status " + apiResponse.getMessage() + "." + "\nApakah sudah selesai potong?")
                                .setCancelable(false)
                                .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                Extension.showLoading(CuttingOrderRecordFormActivity.this);
                                            }
                                        });
                                        cuttingViewModel.postStatusCutBySerialNumberLiveData(API.getToken(CuttingOrderRecordFormActivity.this), mSerialNumber, "sudah").observe(CuttingOrderRecordFormActivity.this, new Observer<APIResponse>() {
                                            @Override
                                            public void onChanged(APIResponse apiResponse) {
                                                Toast.makeText(CuttingOrderRecordFormActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        finish();
                                    }
                                }).setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(CuttingOrderRecordFormActivity.this, ScanQrActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .setNegativeButton(R.string.not_yet_cut, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        cuttingViewModel.postStatusCutBySerialNumberLiveData(API.getToken(CuttingOrderRecordFormActivity.this), mSerialNumber, "belum").observe(CuttingOrderRecordFormActivity.this, new Observer<APIResponse>() {
                                            @Override
                                            public void onChanged(APIResponse apiResponse) {
                                                Toast.makeText(CuttingOrderRecordFormActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        finish();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else if (apiResponse.getData().getCuttingOrderRecord().getStatusLayer().getName().equals("completed") && apiResponse.getData().getCuttingOrderRecord().getStatusCut().getName().equals("sudah")) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingOrderRecordFormActivity.this);

                        alertDialogBuilder.setTitle(getString(R.string.app_name));
                        alertDialogBuilder
                                .setMessage("Cutting Order ini sudah di potong.")
                                .setCancelable(false)
                                .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(CuttingOrderRecordFormActivity.this, ScanQrActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                } else {
                    Toast.makeText(CuttingOrderRecordFormActivity.this, "Perhatikan role department anda.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void getInitData() {
        runOnUiThread(new Runnable() {
            public void run() {
                Extension.showLoading(CuttingOrderRecordFormActivity.this);
            }
        });
        cuttingViewModel.getLayingPlanningBySerialNumberLiveData(API.getToken(CuttingOrderRecordFormActivity.this), mSerialNumber).observe(CuttingOrderRecordFormActivity.this, new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
                if (apiResponse.getData() == null) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingOrderRecordFormActivity.this);

                    alertDialogBuilder.setTitle(getString(R.string.app_name));
                    alertDialogBuilder
                            .setMessage(apiResponse.getMessage())
                            .setCancelable(false)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(CuttingOrderRecordFormActivity.this, ScanQrActivity.class));
                                    finish();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                } else if (apiResponse.getData().getCuttingOrderRecord().getStatusLayer().getName().equals("completed") && apiResponse.getData().getCuttingOrderRecord().getStatusCut().getName().equals("belum")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingOrderRecordFormActivity.this);

                    alertDialogBuilder.setTitle(getString(R.string.app_name));
                    alertDialogBuilder
                            .setMessage("Layer status " + apiResponse.getMessage() + "." + "\nApakah sudah selesai potong?")
                            .setCancelable(false)
                            .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Extension.showLoading(CuttingOrderRecordFormActivity.this);
                                        }
                                    });
                                    cuttingViewModel.postStatusCutBySerialNumberLiveData(API.getToken(CuttingOrderRecordFormActivity.this), mSerialNumber, "sudah").observe(CuttingOrderRecordFormActivity.this, new Observer<APIResponse>() {
                                        @Override
                                        public void onChanged(APIResponse apiResponse) {
                                            Toast.makeText(CuttingOrderRecordFormActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    finish();
                                }
                            }).setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(CuttingOrderRecordFormActivity.this, ScanQrActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton(R.string.not_yet_cut, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    cuttingViewModel.postStatusCutBySerialNumberLiveData(API.getToken(CuttingOrderRecordFormActivity.this), mSerialNumber, "belum").observe(CuttingOrderRecordFormActivity.this, new Observer<APIResponse>() {
                                        @Override
                                        public void onChanged(APIResponse apiResponse) {
                                            Toast.makeText(CuttingOrderRecordFormActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    finish();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else if (apiResponse.getData().getCuttingOrderRecord().getStatusLayer().getName().equals("completed") && apiResponse.getData().getCuttingOrderRecord().getStatusCut().getName().equals("sudah")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingOrderRecordFormActivity.this);

                    alertDialogBuilder.setTitle(getString(R.string.app_name));
                    alertDialogBuilder
                            .setMessage("Cutting Order ini sudah di potong.")
                            .setCancelable(false)
                            .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(CuttingOrderRecordFormActivity.this, ScanQrActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    nameUser = API.currentUser(CuttingOrderRecordFormActivity.this).getName();
                    binding.etOperator.setText(nameUser);

                    colorName = apiResponse.getData().getCuttingOrderRecord().getLayingPlanningDetail().getLayingPlanning().getColor().getName();
                    yrd = apiResponse.getData().getCuttingOrderRecord().getLayingPlanningDetail().getMarkerYard();
                    inch = apiResponse.getData().getCuttingOrderRecord().getLayingPlanningDetail().getMarkerInch();
                    binding.etColor.setText(colorName);

                    loadDataRemarks();

                    binding.etColor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(CuttingOrderRecordFormActivity.this, "Color can't edit", Toast.LENGTH_SHORT).show();
                        }
                    });
                    binding.etMarkerYard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(CuttingOrderRecordFormActivity.this, "Marker yard can't edit", Toast.LENGTH_SHORT).show();
                        }
                    });
                    binding.etMarkerYard.setText(new DecimalFormat("##.##").format(Double.parseDouble(markerYard(yrd))));
                }
            }
        });

        binding.etFabricRoll.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Tidak perlu melakukan apa-apa sebelum teks berubah
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Saat teks berubah, Anda bisa memuat data dari API berdasarkan teks yang baru
                String searchText = charSequence.toString();
                loadDataFromApi(searchText);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Tidak perlu melakukan apa-apa setelah teks berubah
            }
        });
    }

    private void loadDataFromApi(String searchText) {
        cuttingViewModel.getLayingPlanningBySerialNumberLiveData(API.getToken(CuttingOrderRecordFormActivity.this), mSerialNumber).observe(CuttingOrderRecordFormActivity.this, new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                for (int x = 0; x < apiResponse.getData().getCuttingOrderRecord().getCuttingOrderRecordDetail().size(); x++) {
                    CuttingOrderRecordDetail detail = apiResponse.getData().getCuttingOrderRecord().getCuttingOrderRecordDetail().get(x);

                    // Memeriksa apakah fabric roll sama dengan searchText dan fabric batch juga sama
                    if (detail.getFabricRoll().equals(searchText) && detail.getFabricBatch().equals(binding.etFabricBatch.getText().toString())) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Extension.showLoading(CuttingOrderRecordFormActivity.this);
                            }
                        });

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingOrderRecordFormActivity.this);
                        alertDialogBuilder.setTitle(getString(R.string.app_name));
                        alertDialogBuilder
                                .setMessage("Fabric roll sudah ada dengan fabric batch yang sama.\nPastikan fabric roll tidak sama.")
                                .setCancelable(true)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        binding.etFabricRoll.setText("");
                                    }
                                });

                        runOnUiThread(new Runnable() {
                            public void run() {
                                Extension.dismissLoading();
                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                        break; // Tambahkan break untuk menghentikan iterasi setelah menemukan kesamaan
                    }
                }
            }
        });
    }

    private void submit() {
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (validationInput()) return;

                if (binding.etFabricRoll.getText().toString().isEmpty()) {
                    binding.etFabricRoll.setError("fabric roll required");
                    binding.etFabricRoll.requestFocus();
                    return;
                }

                if (binding.etFabricBatch.getText().toString().isEmpty()) {
                    binding.etFabricBatch.setError("fabric batch required");
                    binding.etFabricBatch.requestFocus();
                    return;
                }

                if (binding.etYardage.getText().toString().isEmpty()) {
                    binding.etYardage.setError("fabric yard required");
                    binding.etYardage.requestFocus();
                    return;
                }

                if (binding.etWeight.getText().toString().isEmpty()) {
                    binding.etWeight.setError("weight required");
                    binding.etWeight.requestFocus();
                    return;
                }

                if (binding.etLayer.getText().toString().isEmpty()) {
                    binding.etLayer.setError("layer required");
                    binding.etLayer.requestFocus();
                    return;
                }

                if (binding.etJoint.getText().toString().isEmpty()) {
                    binding.etJoint.setError("joint required");
                    binding.etJoint.requestFocus();
                    return;
                }

                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.showLoading(CuttingOrderRecordFormActivity.this);
                    }
                });
                cuttingViewModel.createOptCuttingOrderLiveData(
                        API.getToken(CuttingOrderRecordFormActivity.this),
                        mSerialNumber,
                        binding.etFabricRoll.getText().toString(),
                        binding.etFabricBatch.getText().toString(),
                        colorName,
                        Double.parseDouble(binding.etYardage.getText().toString().equals("") ? "0" : binding.etYardage.getText().toString()),
                        Double.parseDouble(binding.etWeight.getText().toString().equals("") ? "0" : binding.etWeight.getText().toString()),
                        Integer.parseInt(binding.etLayer.getText().toString().equals("") ? "0" : binding.etLayer.getText().toString()),
                        binding.etJoint.getText().toString(),
                        binding.etBalanceEnd.getText().toString(),
                        spItem,
                        nameUser,
                        API.currentUser(CuttingOrderRecordFormActivity.this).getId()).observe(CuttingOrderRecordFormActivity.this, new Observer<APIResponse>() {
                    @Override
                    public void onChanged(APIResponse apiResponse) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Extension.dismissLoading();
                            }
                        });

                        if (apiResponse.getData() == null) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingOrderRecordFormActivity.this);

                            alertDialogBuilder.setTitle(getString(R.string.app_name));
                            alertDialogBuilder
                                    .setMessage(apiResponse.getMessage())
                                    .setCancelable(false)
                                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.show();
                        } else {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingOrderRecordFormActivity.this);

                            alertDialogBuilder.setTitle(getString(R.string.app_name));
                            alertDialogBuilder
                                    .setMessage(apiResponse.getMessage())
                                    .setCancelable(true)
                                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (apiResponse.getData().getCuttingOrderRecord().getStatusLayer().getName().equals("completed")) {
                                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingOrderRecordFormActivity.this);
                                                alertDialogBuilder.setTitle(getString(R.string.app_name));
                                                alertDialogBuilder
                                                        .setMessage("Layer status " + apiResponse.getData().getCuttingOrderRecord().getStatusLayer().getName() + "." + "\nScan ulang jika sudah selesai potong!")
                                                        .setCancelable(true)
                                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                Intent intent = new Intent(CuttingOrderRecordFormActivity.this, MenuActivity.class);
                                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        });

                                                AlertDialog alertDialog = alertDialogBuilder.create();
                                                alertDialog.setCanceledOnTouchOutside(false);
                                                alertDialog.show();
                                            } else {
                                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingOrderRecordFormActivity.this);

                                                alertDialogBuilder.setTitle(getString(R.string.app_name));
                                                alertDialogBuilder
                                                        .setMessage("Apakah anda ingin melanjutkan input data?")
                                                        .setCancelable(true)
                                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
//                                                            binding.etFabricRoll.setText("");
//                                                            binding.etFabricBatch.setText("");
                                                                binding.etYardage.setText("");
                                                                binding.etWeight.setText("");
                                                                binding.etLayer.setText("");
                                                                binding.etJoint.setText("");
                                                                binding.etBalanceEnd.setText("");
                                                                binding.etFabricRoll.requestFocus();
                                                            }
                                                        })
                                                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                Intent intent = new Intent(CuttingOrderRecordFormActivity.this, MenuActivity.class);
                                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        });

                                                AlertDialog alertDialog = alertDialogBuilder.create();
                                                alertDialog.setCanceledOnTouchOutside(false);
                                                alertDialog.show();
                                            }

                                        }
                                    });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }
                });
            }
        });
    }

    void showComment() {
        if (commentContentBottomSheetDialog.isAdded() == true) return;

        commentContentBottomSheetDialog.show(getSupportFragmentManager(), "CommentPages");
    }

    public TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            calculateBalanceEnd();
        }
    };

    private void calculateBalanceEnd() {
        double yardByLayer = (Double.parseDouble(binding.etYardage.getText().toString().equals("") ? "0" : binding.etYardage.getText().toString()) - (Double.parseDouble(markerYard(yrd))) * Double.parseDouble(binding.etLayer.getText().toString().equals("") ? "0" : binding.etLayer.getText().toString()));
        int res = (int) yardByLayer;
        binding.etBalanceEnd.setText(new DecimalFormat("##.##").format(yardByLayer));
    }

    /**
     * MarkerYard + (33/36) || 4 + (33/36)
     * MarkerYard + 0.916 = xx.xxx || 4 + 0.916
     *
     * @return 4.916 | 4.92
     */
    private String markerYard(double yard) {
        return String.valueOf(yard + (inch / 36f));
    }

    /**
     * fabricLayer * markerYard
     *
     * @return totalYard - Yard
     */
//    private double calculateLoseFabric(String layer, double fabricYard) {
//        double res = Double.parseDouble(layer) * Double.parseDouble(currentYard(binding.etYardage.getText().toString()));
//        return fabricYard - res;
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CuttingOrderRecordFormActivity.this);
        alertDialogBuilder.setTitle("Yakin ingin keluar ?");
        alertDialogBuilder
                .setMessage("Keluar dari halaman ini menyebabkan data kolom akan hilang")
                .setCancelable(true)
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                })
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void loadDataRemarks() {
        items = new ArrayList<>();
        CuttingRecordRemark remark = new CuttingRecordRemark();
        remark.setId(55);
        remark.setName("-");
        remark.setDescription("-");
        items.add(remark.getName());

        cuttingViewModel.getRemarksLiveData(API.getToken(CuttingOrderRecordFormActivity.this)).observe(CuttingOrderRecordFormActivity.this, new Observer<APIResponse>() {
            @Override
            public void onChanged(APIResponse apiResponse) {
                for (int x = 0; x < apiResponse.getData().getCuttingRecordRemarks().size(); x++) {
                    items.add(apiResponse.getData().getCuttingRecordRemarks().get(x).getName());
                }

                remarks = new ArrayAdapter<String>(CuttingOrderRecordFormActivity.this, android.R.layout.simple_spinner_item, items);
                remarks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spRemarks.setAdapter(remarks);

                binding.spRemarks.setSelection(0);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spItem = adapterView.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onItemClick(String item) {
        Log.i("DetailPopularActivity", "onItemClick: " + item);
    }
}