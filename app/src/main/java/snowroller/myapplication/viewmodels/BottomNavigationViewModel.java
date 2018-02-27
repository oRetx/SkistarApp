package snowroller.myapplication.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import snowroller.myapplication.BR;
import snowroller.myapplication.fragments.SeasonFragment;
import snowroller.myapplication.fragments.TodayFragment;
import snowroller.myapplication.fragments.TotalFragment;
import snowroller.myapplication.fragments.WeekFragment;

/**
 * Created by Martin on 2018-01-20.
 */

public class BottomNavigationViewModel extends BaseObservable{

    private boolean loading;

    private TodayFragment fragment;

    private SeasonFragment seasonFragment;

    private WeekFragment weekFragment;

    private TotalFragment totalFragment;

    public void onRefresh(Context context){

        if( fragment != null)
            todayViewModel.refresh(context);

        if( weekFragment != null)
            weekViewModel.refresh(context);

        if( seasonFragment != null)
            seasonViewModel.refresh(context);

        if(totalFragment != null)
            totalViewModel.refresh();

        new Handler().postDelayed(()-> setLoading(false), 1500);
    }

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }

    public TodayViewModel todayViewModel = new TodayViewModel();

    public TotalViewModel totalViewModel = new TotalViewModel();

    public WeekViewModel weekViewModel = new WeekViewModel();

    public SeasonViewModel seasonViewModel = new SeasonViewModel();

    public void setFragment(WeekFragment fragment) {
        this.weekFragment = fragment;
    }

    public void setFragment(TodayFragment fragment) {
        this.fragment = fragment;
    }

    public void setFragment(SeasonFragment fragment) {
        this.seasonFragment = fragment;
    }

    public void setFragment(TotalFragment fragment) {
        this.totalFragment = fragment;
    }
}
