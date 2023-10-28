package com.example.mytickerwatchlistmanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TickerListFragment extends Fragment {

    private ListView listView;
    private WebViewModel webViewModel;
    private List<String> defaultTickers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultTickers = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticker_list, container, false);

        listView = view.findViewById(R.id.list_id);

        String[] defaultTickers = {"BAC", "AAPL", "DIS"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, defaultTickers);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTicker = defaultTickers[position];

                webViewModel.setCurrentUrl(selectedTicker);

                webViewModel.setCurrentUrl("https://seekingalpha.com/symbol/" + selectedTicker);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        webViewModel = new ViewModelProvider(requireActivity()).get(WebViewModel.class);

        listView = requireView().findViewById(R.id.list_id);

        String[] defaultTickers = {"BAC", "AAPL", "DIS"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, defaultTickers);

        webViewModel.getTickerList().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> list) {
                if (list != null) {
                    List<String> mergedTickers = new ArrayList<>(Arrays.asList(defaultTickers));
                    mergedTickers.addAll(list);

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, mergedTickers);
                    listView.setAdapter(adapter);
                }
            }
        });
    }
}