package com.example.accommodiq.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.accommodiq.R;
import com.example.accommodiq.adapters.AccommodationListAdapter;
import com.example.accommodiq.models.Accommodation;

import java.util.ArrayList;

public class AccommodationListFragment extends ListFragment {
    private AccommodationListAdapter adapter;
    private static final String ARG_PARAM = "accommodation_list";
    private ArrayList<Accommodation> accommodations;

    public static AccommodationListFragment newInstance(ArrayList<Accommodation> accommodations) {
        AccommodationListFragment fragment = new AccommodationListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, accommodations);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceBundle) {
        return inflater.inflate(R.layout.fragment_accommodation_list, container, false).getRootView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accommodations = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new AccommodationListAdapter(getActivity(), accommodations);
            setListAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
