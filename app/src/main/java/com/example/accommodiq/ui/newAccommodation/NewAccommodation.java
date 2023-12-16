package com.example.accommodiq.ui.newAccommodation;

import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.accommodiq.R;
import com.example.accommodiq.adapters.AvailabilityRangeListAdapter;
import com.example.accommodiq.databinding.FragmentNewAccommodationBinding;
import com.example.accommodiq.models.Availability;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewAccommodation extends Fragment {

    private NewAccommodationViewModel newAccommodationViewModel;
    private FragmentNewAccommodationBinding binding;
    private AvailabilityRangeListAdapter adapter;
    private List<Availability> availabilityList = new ArrayList<>();
    private Long selectedFromDate;
    private Long selectedToDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        newAccommodationViewModel = new ViewModelProvider(this).get(NewAccommodationViewModel.class);

        binding = FragmentNewAccommodationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new AvailabilityRangeListAdapter(availabilityList);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Sample date initialization, will be changed later
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date startDate1, endDate1, startDate2, endDate2;
        try {
            startDate1 = dateFormat.parse("03/12/2023");
            endDate1 = dateFormat.parse("05/12/2023");
            startDate2 = dateFormat.parse("10/12/2023");
            endDate2 = dateFormat.parse("15/12/2023");
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the error according to your needs
            return;
        }

        availabilityList.add(new Availability(1L, startDate1.getTime(), endDate1.getTime(), 100.00));
        availabilityList.add(new Availability(2L, startDate2.getTime(), endDate2.getTime(), 150.00));

        AvailabilityRangeListAdapter adapter = new AvailabilityRangeListAdapter(availabilityList);
        binding.recyclerViewAvailabilityRange.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewAvailabilityRange.setAdapter(adapter);

        binding.editTextSelectRange.setOnClickListener(v -> showMaterialDateRangePicker());

        binding.buttonAdd.setOnClickListener(v -> {
            if (selectedFromDate != null && selectedToDate != null) {
                String priceString = binding.editTextPrice.getText().toString();
                double price;
                try {
                    price = Double.parseDouble(priceString);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid price entered", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (price > 0) {
                    Availability newAvailability = new Availability(-1L, selectedFromDate, selectedToDate, price);
                    adapter.addItem(newAvailability);
                } else {
                    Toast.makeText(getContext(), "Price must be greater than 0", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please select a date range first.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showMaterialDateRangePicker() {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select Date Range");
        final MaterialDatePicker<Pair<Long, Long>> picker = builder.build();

        picker.addOnPositiveButtonClickListener(selection -> {
            Pair<Long, Long> dateRange = selection;
            if (dateRange.first != null && dateRange.second != null) {
                selectedFromDate = dateRange.first;
                selectedToDate = dateRange.second;
                String fromDate = formatDate(dateRange.first);
                String toDate = formatDate(dateRange.second);
                binding.editTextSelectRange.setText(String.format("%s - %s", fromDate, toDate));
            }
        });

        picker.show(getChildFragmentManager(), picker.toString());
    }

    private String formatDate(Long dateInMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(new Date(dateInMillis));
    }

}