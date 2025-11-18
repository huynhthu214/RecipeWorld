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

import java.util.List;

/**
 * Fragment để hiển thị chi tiết của một món ăn cụ thể.
 * Nó sử dụng DetailFragment (ViewModel) để lấy dữ liệu.
 */
public class MealDetailFragment extends Fragment {

    private static final String ARG_MEAL_ID = "mealId";
    private DetailFragment detailViewModel; // Đây là ViewModel (DetailFragment đã có)
    private String mealId;

    // Ví dụ về các View trong layout (giả định)
    private TextView tvMealName;
    private TextView tvInstructions;

    /**
     * Phương thức Static để tạo instance mới của Fragment với ID món ăn.
     * Đây là phương thức mà FavoriteFragment sẽ gọi.
     */
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
        // Giả định layout chi tiết là fragment_meal_detail
        View view = inflater.inflate(R.layout.fragment_meal_detail, container, false);

        // Khởi tạo Views (Thay thế R.id.tv_name và R.id.tv_instructions bằng ID thực tế của bạn)
        tvMealName = view.findViewById(R.id.tv_meal_name);
        tvInstructions = view.findViewById(R.id.tv_instructions);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo ViewModel
        detailViewModel = new ViewModelProvider(this).get(DetailFragment.class);

        // Lấy và quan sát dữ liệu chi tiết món ăn
        if (mealId != null) {
            detailViewModel.getMealDetail(mealId).observe(getViewLifecycleOwner(), this::displayMealDetails);
        } else {
            Toast.makeText(getContext(), "Không tìm thấy ID món ăn.", Toast.LENGTH_SHORT).show();
        }

        // Thêm Listener cho nút Favorite/Unfavorite tại đây (nếu có)
    }

    /**
     * Phương thức xử lý hiển thị dữ liệu lên UI.
     */
    private void displayMealDetails(List<Meal> meals) {
        if (meals != null && !meals.isEmpty()) {
            Meal meal = meals.get(0);
            tvMealName.setText(meal.getStrMeal());
            tvInstructions.setText(meal.getInstructions());
            // TODO: Load ảnh, video YouTube, v.v. tại đây
        } else {
            tvMealName.setText(getString(R.string.meal_not_found));
        }
    }
}