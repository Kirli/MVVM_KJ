package com.affinityclick.mvvm.network;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.affinityclick.mvvm.BuildConfig;
import com.affinityclick.mvvm.network.models.Credits;
import com.affinityclick.mvvm.network.models.Movie;
import com.affinityclick.mvvm.network.models.PageResult;
import com.affinityclick.mvvm.network.models.Videos;
import com.affinityclick.mvvm.util.AppExecutors;
import java.io.IOException;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Repository responsible for making all web calls to the movie database.
 */
public class TMDBRepository {
  private final AppExecutors appExecutors;
  private final TMDBApi tmdbApi;

  @Inject
  public TMDBRepository(AppExecutors appExecutors, TMDBApi tmdbApi) {
    this.appExecutors = appExecutors;
    this.tmdbApi = tmdbApi;
  }

  /**
   * @param page Page of top rated movies to show.
   * @return Value to be observed on to return the result
   */
  public LiveData<FetchResource<PageResult<Movie>>> getTopRatedMovies(int page) {
    FetchResource<PageResult<Movie>> getMovies = new FetchResource<>();
    MutableLiveData<FetchResource<PageResult<Movie>>> moviesListLiveResource = new MutableLiveData<>();

    moviesListLiveResource.setValue(getMovies);

    appExecutors.networkIO().execute(() -> fetchTopRatedMovieList(moviesListLiveResource, page));

    return moviesListLiveResource;
  }

  /**
   * Network call to get the top rated movies.,
   *
   * @param fetchResource LiveData to post the results to
   * @param page Results page
   * @return the value it emits through the LiveData (possibly useful for testing)
   */
  private FetchResource<PageResult<Movie>> fetchTopRatedMovieList(@Nullable MutableLiveData<FetchResource<PageResult<Movie>>> fetchResource, int page) {
    if (fetchResource != null) {
      fetchResource.postValue(FetchResource.loading());
    }

    Call<PageResult<Movie>> moviePageCall = tmdbApi.getTopRatedMovies(page, BuildConfig.TMDB_API_KEY);

    try {
      Response<PageResult<Movie>> getMoviesResponse = moviePageCall.execute();

      if (getMoviesResponse.isSuccessful()) {
        PageResult<Movie> moviePageResult = getMoviesResponse.body();
        FetchResource<PageResult<Movie>> success = FetchResource.success(moviePageResult);

        if (fetchResource != null) {
          fetchResource.postValue(success);
        }

        return success;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    FetchResource<PageResult<Movie>> errorValue = FetchResource.error(null);

    if (fetchResource != null) {
      fetchResource.postValue(errorValue);
    }

    return errorValue;
  }

  /**
   *
   * @param id Movie id to lookup
   * @return A live data to be observed on for the results of the call to get the movie
   */
  public LiveData<FetchResource<Movie>> getMovie(Integer id) {
    FetchResource<Movie> getMovie = new FetchResource<>();
    MutableLiveData<FetchResource<Movie>> moviesListLiveResource = new MutableLiveData<>();
    moviesListLiveResource.setValue(getMovie);
    appExecutors.networkIO().execute(() -> fetchMovie(moviesListLiveResource, id));
    return moviesListLiveResource;
  }

  /**
   * Network call to get a movie from a provided id.
   *
   * @param fetchResource LiveData to post the results to
   * @param id Movie id to lookup
   * @return the value it emits through the LiveData (possibly useful for testing)
   */
  private FetchResource<Movie> fetchMovie(@Nullable MutableLiveData<FetchResource<Movie>> fetchResource, Integer id) {
    if (fetchResource != null) {
      fetchResource.postValue(FetchResource.loading());
    }
    Call<Movie> movieCall = tmdbApi.getMovie(id, BuildConfig.TMDB_API_KEY);
    try {
      Response<Movie> getMovieResponse = movieCall.execute();
      if (getMovieResponse.isSuccessful()) {
        Movie moviePageResult = getMovieResponse.body();
        FetchResource<Movie> success = FetchResource.success(moviePageResult);
        if (fetchResource != null) {
          fetchResource.postValue(success);
        }
        return success;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    FetchResource<Movie> errorValue = FetchResource.error(null);
    if (fetchResource != null) {
      fetchResource.postValue(errorValue);
    }
    return errorValue;
  }

  /**
   * Gets the credits for a movie.
   *
   * @param id Movie id to lookup.
   * @return A live data to be observed on for the results of the call to get the credits.
   */
  public LiveData<FetchResource<Credits>> getCredits(Integer id) {
    FetchResource<Credits> getCredits = new FetchResource<>();
    MutableLiveData<FetchResource<Credits>> creditsLiveResource = new MutableLiveData<>();

    creditsLiveResource.postValue(getCredits);
    appExecutors.networkIO().execute(() -> fetchCredits(creditsLiveResource, id));

    return creditsLiveResource;
  }

  /**
   * Network call to get the credits for a movie.
   *
   * @param fetchResource LiveData to post the results to.
   * @param id Movie id to lookup.
   * @return The value it emits through the LiveData (possibly useful for testing).
   */
  private FetchResource<Credits> fetchCredits(@Nullable MutableLiveData<FetchResource<Credits>> fetchResource, Integer id) {
    if (fetchResource != null) {
      fetchResource.postValue(FetchResource.loading());
    }

    Call<Credits> castCall = tmdbApi.getCredits(id, BuildConfig.TMDB_API_KEY);

    try {
      Response<Credits> getCreditsResponse = castCall.execute();

      if (getCreditsResponse.isSuccessful()) {
        Credits creditsPageResult = getCreditsResponse.body();
        FetchResource<Credits> success = FetchResource.success(creditsPageResult);

        if (fetchResource != null) {
          fetchResource.postValue(success);
        }

        return success;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Failed to return success, so we return an error.
    FetchResource<Credits> errorValue = FetchResource.error(null);
    if (fetchResource != null) {
      fetchResource.postValue(errorValue);
    }

    return errorValue;
  }

  /**
   * Gets the videos for a movie.
   *
   * @param id Movie id to lookup.
   * @return A live data to be observed on for the results of the call to get the videos.
   */
  public LiveData<FetchResource<Videos>> getVideos(Integer id) {
    FetchResource<Videos> getVideos = new FetchResource<>();
    MutableLiveData<FetchResource<Videos>> videosLiveResource = new MutableLiveData<>();

    videosLiveResource.postValue(getVideos);
    appExecutors.networkIO().execute(() -> fetchVideos(videosLiveResource, id));

    return videosLiveResource;
  }

  /**
   * Network call to get the videos for a movie.
   *
   * @param fetchResource LiveData to post the results to.
   * @param id Movie id to lookup.
   * @return The value it emits through the LiveData (possibly useful for testing).
   */
  private FetchResource<Videos> fetchVideos(@Nullable MutableLiveData<FetchResource<Videos>> fetchResource, Integer id) {
    if (fetchResource != null) {
      fetchResource.postValue(FetchResource.loading());
    }

    Call<Videos> videoCall = tmdbApi.getVideos(id, BuildConfig.TMDB_API_KEY);

    try {
      Response<Videos> getVideosResponse = videoCall.execute();

      if (getVideosResponse.isSuccessful()) {
        Videos videosPageResult = getVideosResponse.body();
        FetchResource<Videos> success = FetchResource.success(videosPageResult);

        if (fetchResource != null) {
          fetchResource.postValue(success);
        }

        return success;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Failed to return success, so we return an error.
    FetchResource<Videos> errorValue = FetchResource.error(null);
    if (fetchResource != null) {
      fetchResource.postValue(errorValue);
    }

    return errorValue;
  }
}
