package com.affinityclick.mvvm.network;

import com.affinityclick.mvvm.network.models.Credits;
import com.affinityclick.mvvm.network.models.Movie;
import com.affinityclick.mvvm.network.models.PageResult;
import com.affinityclick.mvvm.network.models.Videos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Api that interacts with the movie database
 * @see <a href="https://developers.themoviedb.org/3/getting-started/introduction">The Movie Database</a>
 */
public interface TMDBApi {
  @GET("movie/top_rated")
  Call<PageResult<Movie>> getTopRatedMovies(@Query("page") int page, @Query("api_key") String userkey);

  @GET("movie/{id}")
  Call<Movie> getMovie(@Path("id") Integer id, @Query("api_key") String userkey);

  @GET("movie/{id}/credits")
  Call<Credits> getCredits(@Path("id") Integer id, @Query("api_key") String userkey);

  @GET("movie/{id}/videos")
  Call<Videos> getVideos(@Path("id") Integer id, @Query("api_key") String userkey);
}
