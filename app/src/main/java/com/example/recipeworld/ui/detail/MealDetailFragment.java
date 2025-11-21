package com.example.recipeworld.ui.detail;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.recipeworld.R;
import com.example.recipeworld.data.db.FavoriteMeal;
import com.example.recipeworld.data.db.SessionManager;
import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.viewmodel.DetailViewModel;
import com.example.recipeworld.ui.favorites.FavoriteFragment;
import com.google.android.material.button.MaterialButton;

public class MealDetailFragment extends Fragment {

    private static final String ARG_MEAL_ID = "mealId";

    private DetailViewModel detailViewModel;
    private String mealId;
    private Meal currentMeal;

    private ImageView ivMealThumbnail;
    private TextView tvMealName, tvInstructions;
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

        tvMealName = view.findViewById(R.id.tv_meal_name);
        tvInstructions = view.findViewById(R.id.tv_instructions);
        ivMealThumbnail = view.findViewById(R.id.iv_meal_thumbnail);
        btnWatchVideo = view.findViewById(R.id.btn_watch_youtube);
        btnSaveFavorite = view.findViewById(R.id.btn_save_favorite);

        ImageButton btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                getParentFragmentManager().popBackStack();
            } else {
                requireActivity().onBackPressed();
            }
        });

        btnWatchVideo.setOnClickListener(v -> openYoutubeVideo());
        btnSaveFavorite.setOnClickListener(v -> toggleFavorite());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detailViewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        if (mealId != null) {
            loadMealDetail(mealId);
            observeFavoriteState();
        }
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni != null && ni.isConnected();
        }
        return false;
    }


    private void loadMealDetail(String mealId) {
        int userId = new SessionManager(requireContext()).getLoggedInUserId();

        if (isOnline(requireContext())) {
            // Online → load từ API
            detailViewModel.loadDetail(mealId);
            detailViewModel.getMeal().observe(getViewLifecycleOwner(), meal -> {
                if (meal != null) {
                    currentMeal = meal;
                    displayMeal(meal);
                }
            });
        } else {
            // Offline → load từ favorite
            detailViewModel.getMealOffline(mealId).observe(getViewLifecycleOwner(), favoriteMeal -> {
                if (favoriteMeal != null) {
                    currentMeal = new Meal();
                    currentMeal.setIdMeal(favoriteMeal.getIdMeal());
                    currentMeal.setStrMeal(favoriteMeal.getStrMeal());
                    currentMeal.setThumbnail(favoriteMeal.getThumbnail());
                    currentMeal.setInstructions(favoriteMeal.getInstructions());
                    currentMeal.setStrYoutube(favoriteMeal.getYoutubeLink());

                    displayMeal(currentMeal);
                } else {
                    Toast.makeText(requireContext(), "Không có dữ liệu offline", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    private void displayMeal(Meal meal) {
        tvMealName.setText(meal.getStrMeal());
        tvInstructions.setText(meal.getInstructions());

        String thumb = meal.getThumbnail();
        if (thumb != null && !thumb.trim().isEmpty() && !"null".equalsIgnoreCase(thumb)) {
            Glide.with(this).load(thumb).centerCrop().into(ivMealThumbnail);
        }
    }

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

    private void toggleFavorite() {
        int userId = new SessionManager(requireContext()).getLoggedInUserId();
        if (userId == -1 || currentMeal == null) {
            Toast.makeText(getContext(), "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            FavoriteMeal existing = detailViewModel.checkFavorite(userId, currentMeal.getIdMeal());

            if (existing != null) {
                detailViewModel.deleteFavorite(existing);
                requireActivity().runOnUiThread(() -> {
                    btnSaveFavorite.setImageResource(R.drawable.ic_favorite);
                    Toast.makeText(getContext(), "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
                });
            } else {
                detailViewModel.toggleFavorite(currentMeal);
                requireActivity().runOnUiThread(() -> {
                    btnSaveFavorite.setImageResource(R.drawable.ic_favorite_filled);
                    Toast.makeText(getContext(), "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }


    private void observeFavoriteState() {
        detailViewModel.observeFavorite(mealId)
                .observe(getViewLifecycleOwner(), favorite -> {
                    if (favorite != null) {
                        btnSaveFavorite.setImageResource(R.drawable.ic_favorite_filled);
                    } else {
                        btnSaveFavorite.setImageResource(R.drawable.ic_favorite);
                    }
                });
    }
}
