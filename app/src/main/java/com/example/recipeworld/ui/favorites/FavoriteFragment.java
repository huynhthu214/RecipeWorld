package com.example.recipeworld.ui.favorites;

import android.os.Bundle;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.*;
import com.example.recipeworld.data.db.FavoriteMeal;
import java.util.List;

public class FavoriteFragment extends Fragment {
//    private MainViewModel vm;
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle s) {
//        return inflater.inflate(R.layout.fragment_favorites, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View v, Bundle s) {
//        RecyclerView rv = v.findViewById(R.id.rvFavorites);
//        rv.setLayoutManager(new LinearLayoutManager(getContext()));
//        FavoriteAdapter adapter = new FavoriteAdapter(getContext());
//        rv.setAdapter(adapter);
//
//        vm = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
//        vm.getFavorites().observe(getViewLifecycleOwner(), favorites -> adapter.setData(favorites));
//    }
}
