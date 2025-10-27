package com.example.recipeworld.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeworld.R;
import com.example.recipeworld.ui.adapter.MealAdapter;
import com.example.recipeworld.data.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private MealAdapter adapter;
    private List<Meal> mockMeals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewMeals);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mockMeals = new ArrayList<>();
        mockMeals.add(new Meal("Chicken Curry", "https://www.themealdb.com/images/media/meals/1520084413.jpg"));
        mockMeals.add(new Meal("Beef Stew", "https://www.themealdb.com/images/media/meals/sypxpx1515365095.jpg"));
        mockMeals.add(new Meal("Salmon Teriyaki", "https://www.themealdb.com/images/media/meals/xxyupu1468262513.jpg"));

        adapter = new MealAdapter(getContext(), mockMeals);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
