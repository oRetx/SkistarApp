package snowroller.myapplication.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Martin on 2018-02-06.
 */

public final class Services {
    private Services(){}

    public static SkistarAPIService getService()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.skistar.com/myskistar/game/api/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(SkistarAPIService.class);
    }
}
