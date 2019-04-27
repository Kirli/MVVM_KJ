package com.affinityclick.mvvm.di;

import com.affinityclick.mvvm.network.TMDBApi;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {
  @Singleton
  @Provides
  TMDBApi provideTMDBApi() {
    Retrofit tmdbRetrofit = new Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(new OkHttpClient().newBuilder().build())
        .build();
    return tmdbRetrofit.create(TMDBApi.class);
  }
}
