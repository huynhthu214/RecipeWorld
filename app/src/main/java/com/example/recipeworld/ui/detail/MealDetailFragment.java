package com.example.recipeworld.ui.detail;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recipeworld.R;
import com.example.recipeworld.data.db.FavoriteMeal;
import com.example.recipeworld.data.db.MealDatabase;
import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.ui.favorites.FavoriteViewModel;
import com.example.recipeworld.viewmodel.DetailViewModel;
import com.google.android.material.button.MaterialButton;

public class MealDetailFragment extends Fragment {

    private static final String ARG_MEAL_ID = "mealId";

    private DetailViewModel detailViewModel;
    private FavoriteViewModel favoriteViewModel;

    private String mealId;
    private Meal currentMeal;

    // Views
    private ImageView ivMealThumbnail;
    private TextView tvMealName;
    private TextView tvInstructions;
    private MaterialButton btnWatchVideo;
    private ImageButton btnSaveFavorite;

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

        tvMealName      = view.findViewById(R.id.tv_meal_name);
        tvInstructions  = view.findViewById(R.id.tv_instructions);
        ivMealThumbnail = view.findViewById(R.id.iv_meal_thumbnail);
        btnWatchVideo   = view.findViewById(R.id.btn_watch_youtube);
        btnSaveFavorite = view.findViewById(R.id.btn_save_favorite);

        btnWatchVideo.setOnClickListener(v -> openYoutubeVideo());
        btnSaveFavorite.setOnClickListener(v -> toggleFavorite());

        ImageButton btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                getParentFragmentManager().popBackStack();
            } else {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detailViewModel   = new ViewModelProvider(this).get(DetailViewModel.class);
        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        if (mealId != null) {
            loadMealDetail(mealId);
        }
    }

    /**LOAD CHI TIẾT MÓN ĂN TỪ ROOM/ONLINE*/
    private void loadMealDetail(String mealId) {
        // 1. Kiểm tra Room
        MealDatabase.getInstance(requireContext())
                .mealDao()
                .getFavoriteByIdLive(mealId)
                .observe(getViewLifecycleOwner(), favoriteMeal -> {
                    if (favoriteMeal != null) {
                        // Load từ Room (offline)
                        Meal meal = new Meal();
                        meal.setIdMeal(favoriteMeal.getIdMeal());
                        meal.setStrMeal(favoriteMeal.getStrMeal());
                        meal.setStrMealThumb(favoriteMeal.getStrMealThumb());
                        meal.setStrYoutube(favoriteMeal.getYoutube());
                        meal.setStrInstructions(favoriteMeal.getInstructions());
                        currentMeal = meal;

                        displayMeal(currentMeal);

                    } else {
                        // Load từ API
                        detailViewModel.loadDetail(mealId);
                        detailViewModel.getMeal().observe(getViewLifecycleOwner(), meal -> {
                            if (meal != null) {
                                currentMeal = meal;
                                displayMeal(meal);
                            }
                        });
                    }

                    // Cập nhật icon yêu thích
                    updateFavoriteIcon();
                });
    }

    /* HIỂN THỊ MÓN ĂN*/
    private void displayMeal(Meal meal) {
        tvMealName.setText(meal.getStrMeal());
        tvInstructions.setText(meal.getInstructions());

        String thumb = meal.getThumbnail();
        if (thumb != null && !thumb.trim().isEmpty() && !"null".equalsIgnoreCase(thumb)) {
            Glide.with(this)
                    .load(thumb)
                    .centerCrop()
                    .into(ivMealThumbnail);
        }
    }

    /* MỞ VIDEO YOUTUBE */
    private void openYoutubeVideo() {
        if (currentMeal == null || currentMeal.getStrYoutube() == null) {
            Toast.makeText(requireContext(), "Video chưa có sẵn", Toast.LENGTH_SHORT).show();
            return;
        }

        String youtubeUrl = currentMeal.getStrYoutube().trim();
        if (!youtubeUrl.startsWith("http")) {
            youtubeUrl = "https://www.youtube.com/watch?v=" + youtubeUrl;
        }

        MealVideoFragment videoFragment = MealVideoFragment.newInstance(youtubeUrl);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.main_activity_container, videoFragment)
                .addToBackStack(null)
                .commit();
    }

    /*  TOGGLE FAVORITE */
    private void toggleFavorite() {
        if (currentMeal == null) return;

        FavoriteMeal fav = new FavoriteMeal(
                currentMeal.getIdMeal(),
                currentMeal.getStrMeal(),
                currentMeal.getThumbnail(),
                currentMeal.getStrYoutube(),
                currentMeal.getInstructions()
        );

        favoriteViewModel.getFavoriteById(currentMeal.getIdMeal())
                .observe(getViewLifecycleOwner(), favorite -> {
                    if (favorite != null) {
                        favoriteViewModel.deleteFavorite(fav);
                        btnSaveFavorite.setImageResource(R.drawable.ic_favorite);
                        Toast.makeText(requireContext(), "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
                    } else {
                        favoriteViewModel.insertFavorite(fav);
                        btnSaveFavorite.setImageResource(R.drawable.ic_favorite_filled);
                        Toast.makeText(requireContext(), "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /* CẬP NHẬT ICON YÊU THÍCH */
    private void updateFavoriteIcon() {
        if (currentMeal == null) return;

        favoriteViewModel.getFavoriteById(currentMeal.getIdMeal())
                .observe(getViewLifecycleOwner(), favorite -> {
                    if (favorite != null) {
                        btnSaveFavorite.setImageResource(R.drawable.ic_favorite_filled);
                    } else {
                        btnSaveFavorite.setImageResource(R.drawable.ic_favorite);
                    }
                });
    }

}
