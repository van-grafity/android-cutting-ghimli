package com.app.ivans.ghimli.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.ivans.ghimli.R;
import com.app.ivans.ghimli.ui.viewmodel.AboutViewModel;
import com.app.ivans.ghimli.utils.Extension;

public class AboutFragment extends Fragment implements LocationListener {

    private AboutViewModel mViewModel;
    private TextView tvVersion;
    private Button btnUpdateVersion;
    private TextView tvWebsite;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvLocation;

    private LocationManager locationManager;
    public static final double MAX_DISTANCE = 50.0; // dalam meter

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("About");
        tvVersion = view.findViewById(R.id.tvResVersion);
        btnUpdateVersion = view.findViewById(R.id.btnUpdateVersion);
        tvWebsite = view.findViewById(R.id.tvWebsite);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvLocation = view.findViewById(R.id.tvLocation);
        tvVersion.setText(Extension.getVersion(getActivity()));

        locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

        // Request location updates
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permissions are already granted
            // Continue with location-related operations
        } else {
            // Request location permissions
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 22);
        }

        if (locationManager != null) {
            // Use your desired provider and minimum time interval for updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
        }

        btnUpdateVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Extension.vibrate(getActivity());
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + requireContext().getPackageName()));
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        tvWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://glaindonesia.lan/"));
                if (browserIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(browserIntent);
                }
            }
        });

        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriText =
                        "mailto:ivan.suhendraaja@gmail.com" +
                                "?subject=" + Uri.encode("") +
                                "&body=" + Uri.encode("");

                Uri uri = Uri.parse(uriText);

                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(uri);
                if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(Intent.createChooser(sendIntent, "Send email"));
                }
            }
        });

        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String data = String.format("geo:%s,%s", String.valueOf(Extension.TARGET_LAT), String.valueOf(Extension.TARGET_LONG));
//                if (zoomLevel != null) {
//                    data = String.format("%s?z=%s", data, zoomLevel);
//                }
                intent.setData(Uri.parse(data));
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AboutViewModel.class);
        // TODO: Use the ViewModel
    }

    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        // Menghitung jarak menggunakan rumus Haversine
        double R = 6371000; // Radius bumi dalam meter
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public static boolean isWithinDistance(double currentLat, double currentLng, double targetLat, double targetLng) {
        // Menghitung jarak antara dua titik
        double distance = haversine(currentLat, currentLng, targetLat, targetLng);

        // Memeriksa apakah jarak kurang dari atau sama dengan 50 meter
        return distance <= MAX_DISTANCE;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double currentLat = location.getLatitude();
        double currentLng = location.getLongitude();

        // Specify the desired radius in meters
        double radius = 50.0;

        // Check if the current location is within the specified radius
        if (isWithinDistance(Extension.TARGET_LAT, Extension.TARGET_LONG, currentLat, currentLng)) {
            // Jarak kurang dari atau sama dengan 50 meter
            if(getActivity() == null) return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    showToast("Jarak kurang dari atau sama dengan 50 meter");
                }
            });

        } else {
            // Jarak lebih dari 50 meter
            if(getActivity() == null) return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast("Kamu sedang tidak berada di sekitar cutting");
                }
            });
        }

        // Your existing code
        // ...
    }

    // Other LocationListener methods
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // ...
    }

    @Override
    public void onProviderEnabled(String provider) {
        // ...
    }

    @Override
    public void onProviderDisabled(String provider) {
        // ...
    }

    // Method to show a Toast message
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}