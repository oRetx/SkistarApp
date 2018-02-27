package snowroller.myapplication.activities;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Stack;

import snowroller.myapplication.R;
import snowroller.myapplication.databinding.ActivityBottomNavigationBinding;
import snowroller.myapplication.fragments.SeasonFragment;
import snowroller.myapplication.fragments.TodayFragment;
import snowroller.myapplication.fragments.TotalFragment;
import snowroller.myapplication.fragments.WeekFragment;
import snowroller.myapplication.jobs.UpdateSkierStats;
import snowroller.myapplication.viewmodels.BottomNavigationViewModel;
import snowroller.myapplication.viewmodels.ViewModelProvider;

public class BottomNavigationActivity extends AppCompatActivity {



    public static final String id = "DEFAULT_CHANNEL";

    private BottomNavigationView navigation;
    private BottomNavigationViewModel viewModel;
    private Stack<Integer> fragmentBackStack = new Stack<>();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (!fragmentBackStack.empty() && fragmentBackStack.peek() == item.getItemId())
                return true;
            switch (item.getItemId()) {
                case R.id.navigation_today:
                    TodayFragment today = new TodayFragment();
                    viewModel.setFragment(today);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, today, "TODAY").commit();
                    fragmentBackStack.push(R.id.navigation_today);
                    return true;
                case R.id.navigation_week:
                    WeekFragment week = new WeekFragment();
                    viewModel.setFragment(week);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, week, "WEEK").commit();
                    fragmentBackStack.push(R.id.navigation_week);
                    return true;
                case R.id.navigation_season:
                    SeasonFragment season = new SeasonFragment();
                    viewModel.setFragment(season);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, season, "SEASON").commit();
                    fragmentBackStack.push(R.id.navigation_season);
                    return true;
                case R.id.navigation_total:
                    TotalFragment total = new TotalFragment();
                    viewModel.setFragment(total);
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left)
                            .replace(R.id.frameLayout, total, "TOTAL").commit();
                    fragmentBackStack.push(R.id.navigation_total);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_bottom_navigation);
        ActivityBottomNavigationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_navigation);
        viewModel = ViewModelProvider.getInstance().getBottomNavigationViewModel();
        binding.setViewModel(viewModel);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        TodayFragment todayFragment = new TodayFragment();
        viewModel.setFragment(todayFragment);
        fragmentBackStack.push(R.id.navigation_today);
        getFragmentManager().beginTransaction()
                .add(R.id.frameLayout, todayFragment, "TODAY").commit();

        setSupportActionBar(findViewById(R.id.toolbar2));
        
        schedulePeriodicJob();

        if(isConnected()){
            Log.i("TRUE", "User is connected ");
        }
        else{
            Log.i("TRUE", "User is not connected");
        }
    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void schedulePeriodicJob() {
        JobScheduler jobScheduler =
                (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        jobScheduler.schedule(new JobInfo.Builder(1234,
                new ComponentName(this, UpdateSkierStats.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(15 * 60000)
                .build());

        // Call cancel to stop scheduling of job
        // jobScheduler.cancel(1234);
    }



    @Override
    public void onBackPressed() {
        fragmentBackStack.pop();
        if (!fragmentBackStack.empty()) {
            navigation.setSelectedItemId(fragmentBackStack.pop());
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
