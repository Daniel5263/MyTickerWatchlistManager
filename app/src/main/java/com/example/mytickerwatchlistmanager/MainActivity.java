package com.example.mytickerwatchlistmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WebViewModel webViewModel;
    private static final int SMS_PERMISSION_CODE = 101;
    private String ticker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ticker_list_container, new TickerListFragment())
                    .commit();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.info_web_container, new InfoWebFragment())
                    .commit();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.RECEIVE_SMS }, SMS_PERMISSION_CODE);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        webViewModel = new ViewModelProvider(this).get(WebViewModel.class);

        if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_SEND)) {
            String smsText = intent.getExtras().getString("smsText");

            if (smsText != null) {
                ticker = extractTickerFromSmsText(smsText);

                if (ticker != null) {
                    webViewModel.addTicker(ticker);
                } else {
                    Toast.makeText(this, "Invalid ticker", Toast.LENGTH_SHORT).show();
                    Intent newActivityIntent = new Intent(this, MainActivity.class);
                    newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(newActivityIntent);
                }
            }
        }
    }

    private String extractTickerFromSmsText(String smsText) {
        String pattern = "Ticker:<<([A-Z]+)>>";
        String[] words = smsText.split("\\s+");
        java.util.regex.Pattern regex = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = regex.matcher(smsText);

        if (matcher.find()) {
            return matcher.group(1);
        } else if (words.length > 0 && words[0].matches(pattern)) {
            return words[0];
        } else {
            Toast.makeText(this, "No valid watchlist entry found", Toast.LENGTH_SHORT).show();
            Intent newActivityIntent = new Intent(this, MainActivity.class);
            newActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newActivityIntent);
            return null;
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