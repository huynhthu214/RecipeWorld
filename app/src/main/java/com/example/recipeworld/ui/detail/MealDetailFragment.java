package com.example.recipeworld.ui.detail;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipeworld.R;
import com.example.recipeworld.data.model.Meal;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MealDetailFragment extends Fragment {

    private static final String ARG_MEAL_ID = "mealId";
    private DetailFragment detailViewModel;
    private String mealId;
    private Meal currentMeal;

    private TextView tvMealName;
    private TextView tvInstructions;
    private MaterialButton btnWatchVideo;

    public static MealDetailFragment newInstance(String mealId) {
        MealDetailFragment fragment = new MealDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEAL_ID, mealId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mealId = getArguments().getString(ARG_MEAL_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_detail, container, false);

        tvMealName = view.findViewById(R.id.tv_meal_name);
        tvInstructions = view.findViewById(R.id.tv_instructions);
        btnWatchVideo = view.findViewById(R.id.btn_watch_youtube);

        btnWatchVideo.setOnClickListener(v -> {
            if (currentMeal != null && currentMeal.getStrYoutube() != null) {
                String youtubeUrl = currentMeal.getStrYoutube();
                MealVideoFragment videoFragment = MealVideoFragment.newInstance(youtubeUrl);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_container, videoFragment)
                        .addToBackStack(null)
                        .commit();
            } else {
                Toast.makeText(getContext(), "Video chưa có sẵn", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detailViewModel = new ViewModelProvider(this).get(DetailFragment.class);

        if (mealId != null) {
            detailViewModel.getMealDetail(mealId).observe(getViewLifecycleOwner(), this::displayMealDetails);
        } else {
            Toast.makeText(getContext(), "Không tìm thấy ID món ăn.", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayMealDetails(List<Meal> meals) {
        if (meals != null && !meals.isEmpty()) {
            currentMeal = meals.get(0);
            tvMealName.setText(currentMeal.getStrMeal());
            tvInstructions.setText(currentMeal.getInstructions());
        } else {
            tvMealName.setText(getString(R.string.meal_not_found));
        }
    }

    // Hàm tách YouTube videoId từ link
    private String extractYoutubeId(String url) {
        if (url == null) return null;
        if (url.contains("v=")) {
            return url.substring(url.indexOf("v=") + 2);
        } else if (url.contains("youtu.be/")) {
            return url.substring(url.lastIndexOf("/") + 1);
        } else {
            return url; // fallback
        }
    }
}
