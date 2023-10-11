package com.example.mytickerwatchlistmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebViewModel webViewModel;
    private static final int SMS_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webViewModel = new ViewModelProvider(this).get(WebViewModel.class);

        if (savedInstanceState == null) {
            TickerListFragment tickerListFragment = new TickerListFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, tickerListFragment)
                    .commit();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.RECEIVE_SMS }, SMS_PERMISSION_CODE);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (getSupportFragmentManager().findFragmentById(R.id.ticker_list_fragment) == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.ticker_list_fragment, new TickerListFragment())
                        .add(R.id.info_web_fragment, new InfoWebFragment())
                        .commit();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "RECEIVE_SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}