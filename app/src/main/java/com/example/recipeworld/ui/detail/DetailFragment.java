package com.example.recipeworld.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.recipeworld.R;

public class DetailFragment extends Fragment {

    private ImageView imgMeal;
    private TextView tvMealName, tvMealInstructions;
    private Button btnWatchVideo, btnBack;
    private WebView webView;

    private String mealName, mealThumb, mealInstructions, mealYoutube;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        imgMeal = view.findViewById(R.id.imgMealDetail);
        tvMealName = view.findViewById(R.id.tvMealNameDetail);
        tvMealInstructions = view.findViewById(R.id.tvMealInstructions);
        btnWatchVideo = view.findViewById(R.id.btnWatchVideo);
        btnBack = view.findViewById(R.id.btnBack);
        webView = view.findViewById(R.id.webViewVideo);

        // Nhận dữ liệu từ Safe Args
        if (getArguments() != null) {
            DetailFragmentArgs args = DetailFragmentArgs.fromBundle(getArguments());
            mealName = args.getMealName();
            mealThumb = args.getMealThumb();
            mealInstructions = args.getMealInstructions();
            mealYoutube = args.getMealYoutube();
        }

        tvMealName.setText(mealName != null ? mealName : "Không có tên");
        tvMealInstructions.setText(mealInstructions != null ? mealInstructions : "Không có hướng dẫn");

        if (mealThumb != null && !mealThumb.isEmpty()) {
            Glide.with(requireContext()).load(mealThumb).into(imgMeal);
        }

        // Xử lý nút Xem video
        btnWatchVideo.setOnClickListener(v -> {
            if (mealYoutube != null && !mealYoutube.isEmpty()) {
                String url = mealYoutube.trim();
                if (!url.startsWith("http")) {
                    url = "https://www.youtube.com/watch?v=" + url;
                }

                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(url);

                // Ẩn các view khác để xem video
                imgMeal.setVisibility(View.GONE);
                tvMealName.setVisibility(View.GONE);
                tvMealInstructions.setVisibility(View.GONE);
                btnWatchVideo.setVisibility(View.GONE);
            }
        });

        // Nút Back quay lại fragment trước
        btnBack.setOnClickListener(v -> {
            if (webView.getVisibility() == View.VISIBLE) {
                // Nếu đang xem video, quay lại UI chi tiết
                webView.setVisibility(View.GONE);
                imgMeal.setVisibility(View.VISIBLE);
                tvMealName.setVisibility(View.VISIBLE);
                tvMealInstructions.setVisibility(View.VISIBLE);
                btnWatchVideo.setVisibility(View.VISIBLE);
            } else {
                // Quay về HomeFragment
                NavController navController = NavHostFragment.findNavController(DetailFragment.this);
                navController.navigateUp();
            }
        });

        return view;
    }
}
