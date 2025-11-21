package com.example.recipeworld.ui.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipeworld.R;

public class MealVideoFragment extends Fragment {

    private static final String ARG_YOUTUBE_URL = "youtubeUrl";
    private String youtubeUrl;

    public static MealVideoFragment newInstance(String youtubeUrl) {
        MealVideoFragment fragment = new MealVideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_YOUTUBE_URL, youtubeUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            youtubeUrl = getArguments().getString(ARG_YOUTUBE_URL);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meal_video, container, false);

        WebView webView = view.findViewById(R.id.webview_youtube);
        ImageButton btnBack = view.findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setMediaPlaybackRequiresUserGesture(false);

        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                // Fallback mở YouTube App
                openYoutubeExternally(youtubeUrl);

                // Đóng fragment video
                if (getParentFragmentManager() != null) {
                    getParentFragmentManager().popBackStack();
                }
            }
        });

        String embedUrl = convertToEmbedUrl(youtubeUrl);

        String html =
                "<html><body style='margin:0;padding:0;'> " +
                        "<iframe width='100%' height='100%' " +
                        "src='" + embedUrl + "' " +
                        "frameborder='0' allowfullscreen></iframe>" +
                        "</body></html>";

        webView.loadData(html, "text/html", "utf-8");

        return view;
    }

    private void openYoutubeExternally(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception ignored) {
        }
    }

    private String convertToEmbedUrl(String url) {
        String videoId = "";

        if (url.contains("v=")) {
            videoId = url.split("v=")[1].split("&")[0];
        } else if (url.contains("youtu.be/")) {
            videoId = url.substring(url.lastIndexOf("/") + 1).split("\\?")[0];
        }

        return "https://www.youtube.com/embed/" + videoId;
    }
}
