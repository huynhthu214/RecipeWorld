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

        // Find all views
        tvMealName      = view.findViewById(R.id.tv_meal_name);
        tvInstructions  = view.findViewById(R.id.tv_instructions);
        ivMealThumbnail = view.findViewById(R.id.iv_meal_thumbnail);
        btnWatchVideo   = view.findViewById(R.id.btn_watch_youtube);
        btnSaveFavorite = view.findViewById(R.id.btn_save_favorite);

        // Listeners
        btnWatchVideo.setOnClickListener(v -> openYoutubeVideo());
        btnSaveFavorite.setOnClickListener(v -> toggleFavorite());

        ImageButton btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo ViewModel
        detailViewModel   = new ViewModelProvider(this).get(DetailViewModel.class);
        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        // Load chi tiết món ăn
        if (mealId != null) {
            detailViewModel.loadDetail(mealId);
        }

        // Observe dữ liệu món ăn
        detailViewModel.getMeal().observe(getViewLifecycleOwner(), meal -> {
            if (meal == null) return;

            currentMeal = meal;

            tvMealName.setText(meal.getStrMeal());
            tvInstructions.setText(meal.getInstructions());

            // === LOAD ẢNH AN TOÀN 100% ===
            String thumb = meal.getThumbnail();  // bạn đã @SerializedName đúng rồi
            if (thumb != null && !thumb.trim().isEmpty() && !"null".equalsIgnoreCase(thumb)) {
                Glide.with(MealDetailFragment.this)  // Dùng Fragment → không crash
                        .load(thumb)
                        .centerCrop()
                        .into(ivMealThumbnail);
            }

            // Cập nhật icon yêu thích
            updateFavoriteIcon();
        });
    }

    // Mở video YouTube
    private void openYoutubeVideo() {
        if (currentMeal == null || currentMeal.getStrYoutube() == null || currentMeal.getStrYoutube().trim().isEmpty()) {
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

    // Thêm/Xóa yêu thích
    private void toggleFavorite() {
        if (currentMeal == null) return;

        FavoriteMeal fav = new FavoriteMeal(
                currentMeal.getIdMeal(),
                currentMeal.getStrMeal(),
                currentMeal.getThumbnail(),
                currentMeal.getStrYoutube(),
                currentMeal.getInstructions()
        );

        favoriteViewModel.isFavorite(currentMeal.getIdMeal())
                .observe(getViewLifecycleOwner(), isFavorite -> {
                    if (Boolean.TRUE.equals(isFavorite)) {
                        favoriteViewModel.deleteFavorite(fav);
                        btnSaveFavorite.setImageResource(R.drawable.ic_favorite);
                    } else {
                        favoriteViewModel.insertFavorite(fav);
                        btnSaveFavorite.setImageResource(R.drawable.ic_favorite_filled);
                    }
                });
    }

    // Cập nhật icon khi mở chi tiết
    private void updateFavoriteIcon() {
        if (currentMeal == null) return;

        favoriteViewModel.isFavorite(currentMeal.getIdMeal())
                .observe(getViewLifecycleOwner(), isFavorite -> {
                    if (Boolean.TRUE.equals(isFavorite)) {
                        btnSaveFavorite.setImageResource(R.drawable.ic_favorite_filled);
                    } else {
                        btnSaveFavorite.setImageResource(R.drawable.ic_favorite);
                    }
                });
    }
}