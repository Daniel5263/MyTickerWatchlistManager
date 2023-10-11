package com.example.mytickerwatchlistmanager;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TickerListFragment extends Fragment {

    private ListView listView;
    private WebViewModel webViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticker_list, container, false);

        listView = view.findViewById(R.id.list_id);

        webViewModel = new ViewModelProvider(requireActivity()).get(WebViewModel.class);

        String[] tickers = {"BAC", "AAPL", "DIS"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, tickers);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTicker = tickers[position];

                webViewModel.setCurrentUrl(selectedTicker);

                InfoWebFragment infoWebFragment = new InfoWebFragment();
                Bundle args = new Bundle();
                args.putString("ticker", selectedTicker);
                infoWebFragment.setArguments(args);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content, infoWebFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}