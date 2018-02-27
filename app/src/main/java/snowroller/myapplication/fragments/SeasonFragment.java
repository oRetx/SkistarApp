package snowroller.myapplication.fragments;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import snowroller.myapplication.R;
import snowroller.myapplication.databinding.FragmentSeasonBinding;
import snowroller.myapplication.viewmodels.SeasonViewModel;

import snowroller.myapplication.viewmodels.ViewModelProvider;

/**
 * Created by oretx on 2018-02-10.
 */

public class SeasonFragment extends Fragment {

    private SeasonViewModel seasonViewModel;

    public SeasonFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        seasonViewModel = ViewModelProvider.getInstance().getBottomNavigationViewModel().seasonViewModel;

        FragmentSeasonBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_season,
                container, false);
        binding.setViewModel(seasonViewModel);

        return binding.getRoot();
    }
}