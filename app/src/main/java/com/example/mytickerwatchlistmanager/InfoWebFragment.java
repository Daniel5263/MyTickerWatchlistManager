package com.example.mytickerwatchlistmanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class InfoWebFragment extends Fragment {

    private WebView webView;
    private WebViewModel webViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_web, container, false);

        webView = view.findViewById(R.id.web_id);

        webViewModel = new ViewModelProvider(requireActivity()).get(WebViewModel.class);

        webViewModel.getCurrentUrl().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String url) {
                if (url != null) {
                    webView.loadUrl(url);
                }
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String url = "https://seekingalpha.com";
        webView.loadUrl(url);
    }
}