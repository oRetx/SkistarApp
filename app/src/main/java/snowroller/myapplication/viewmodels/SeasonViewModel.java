package snowroller.myapplication.viewmodels;

/**
 * Created by oretx on 2018-02-10.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.preference.PreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import snowroller.myapplication.BR;
import snowroller.myapplication.models.Latest;
import snowroller.myapplication.services.Services;

/**
 * Created by oretx on 2018-02-07.
 */

public class SeasonViewModel extends BaseObservable {

    private int dropHeight;
    private int liftCount;
    private int liftRides;

    @Bindable
    public int getDropHeight() {
        return dropHeight;
    }

    public void setDropHeight(int dropHeight) {
        this.dropHeight = dropHeight;
        notifyPropertyChanged(BR.dropHeight);
        notifyPropertyChanged(BR.progress);
    }

    @Bindable
    public int getLiftCount() {
        return liftCount;
    }

    public void setLiftCount(int liftCount) {
        this.liftCount = liftCount;
        notifyPropertyChanged(BR.liftCount);
    }

    @Bindable
    public int getLiftRides() {
        return liftRides;
    }

    public void setLiftRides(int liftRides) {
        this.liftRides = liftRides;
        notifyPropertyChanged(BR.liftRides);
    }

    @Bindable
    public int getProgress()
    {
        return dropHeight/200;
    }

    public void refresh(Context context)
    {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String skierId = sharedPreferences.getString("skierid_preference","");

        Services.getService().latestStatistics(skierId).enqueue(new Callback<Latest>() {
            @Override
            public void onResponse(Call<Latest> call, Response<Latest> response) {
                Latest latest = response.body();
                setDropHeight(latest.getLatestSeasonStatistics().getDropHeight());
                setLiftCount(latest.getLatestSeasonStatistics().getLiftCount());
                setLiftRides(latest.getLatestSeasonStatistics().getLiftRides());

            }

            @Override
            public void onFailure(Call<Latest> call, Throwable t) {

            }
        });

    }
}

