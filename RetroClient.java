package com.tpkb.mascater;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hakiki95 on 4/26/2017.
 */

public class RetroClient {
    private static  Retrofit retro = null;
    private static final String base_url = "http://114.141.55.163:5473/simpati/" ;

    private static Retrofit getClient()
    {
        if(retro == null)
        {
            retro  = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return  retro;
    }

    public static ApiServices getApiServices()
    {
        return  getClient().create(ApiServices.class);
    }
}
