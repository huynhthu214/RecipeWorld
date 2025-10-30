package com.example.recipeworld.ui.detail;

import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.example.recipeworld.R;
import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {
    private DetailViewModel viewModel;
    private ImageView imgMeal;
    private TextView tvMealName, tvInstructions, tvYoutubeLink;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        imgMeal = view.findViewById(R.id.imgMealDetail);
        tvMealName = view.findViewById(R.id.tvMealName);
        tvInstructions = view.findViewById(R.id.tvInstructions);
        tvYoutubeLink = view.findViewById(R.id.tvYoutubeLink);

        // Lấy mealId từ SafeArgs
        String mealId = DetailFragmentArgs.fromBundle(getArguments()).getMealId();

        // ViewModel
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        viewModel.getMealDetail(mealId).observe(getViewLifecycleOwner(), meal -> {
            if (meal != null) {
                displayMealDetail(meal);
            }
        });

        return view;
    }

    private void displayMealDetail(Meal meal) {
        tvMealName.setText(meal.getStrMeal());
        tvInstructions.setText(meal.getStrInstructions());
        tvYoutubeLink.setText(meal.getStrYoutube());
        Linkify.addLinks(tvYoutubeLink, Linkify.WEB_URLS);
        Glide.with(requireContext()).load(meal.getStrMealThumb()).into(imgMeal);
    }
}
