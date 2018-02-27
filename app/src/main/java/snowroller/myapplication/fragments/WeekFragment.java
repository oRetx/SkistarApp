package snowroller.myapplication.fragments;


import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import snowroller.myapplication.R;

import snowroller.myapplication.databinding.FragmentWeekBinding;
import snowroller.myapplication.viewmodels.ViewModelProvider;
import snowroller.myapplication.viewmodels.WeekViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeekFragment extends Fragment {

    private WeekViewModel viewModel;


    public WeekFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = ViewModelProvider.getInstance().getBottomNavigationViewModel().weekViewModel;

        FragmentWeekBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_week,
                container, false);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }
}
