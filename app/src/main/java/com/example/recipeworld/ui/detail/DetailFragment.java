package com.example.recipeworld.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.recipeworld.R;
import com.example.recipeworld.data.model.Meal;
import com.example.recipeworld.view.viewmodel.DetailViewModel;

public class DetailFragment extends Fragment {
    private DetailViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle s) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, Bundle s) {
        super.onViewCreated(v, s);
        ImageView img = v.findViewById(R.id.imgDetail);
        TextView tvName = v.findViewById(R.id.tvDetailName);
        TextView tvInst = v.findViewById(R.id.tvInstructions);
        Button btnWatch = v.findViewById(R.id.btnWatch);
        Button btnFav = v.findViewById(R.id.btnFavorite);

        vm = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);

        Bundle args = getArguments();
        if (args == null) return;

        String id = args.getString("meal_id");
        String name = args.getString("meal_name");
        String thumb = args.getString("meal_thumb");
        String youtube = args.getString("meal_youtube");
        String instructions = args.getString("meal_instructions");

        tvName.setText(name);
        tvInst.setText(instructions != null ? instructions : "No instructions");
        Glide.with(requireContext()).load(thumb).into(img);

        btnWatch.setOnClickListener(view -> {
            if (youtube != null && !youtube.isEmpty()) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(youtube));
                startActivity(i);
            } else {
                Toast.makeText(requireContext(), "No video available", Toast.LENGTH_SHORT).show();
            }
        });

        btnFav.setOnClickListener(view -> {
            // assemble Meal and save favorite
            Meal m = new Meal();
            m.setIdMeal(id);
            m.setStrMeal(name);
            m.setStrMealThumb(thumb);
            m.setStrYoutube(youtube);
            m.setStrInstructions(instructions);
            vm.addFavorite(m);
            Toast.makeText(requireContext(), "Saved to favorites", Toast.LENGTH_SHORT).show();
        });
    }
}
