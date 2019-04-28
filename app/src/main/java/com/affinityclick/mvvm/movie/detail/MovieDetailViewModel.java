package com.affinityclick.mvvm.movie.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.affinityclick.mvvm.network.FetchResource;
import com.affinityclick.mvvm.network.TMDBRepository;
import com.affinityclick.mvvm.network.models.Movie;
import com.affinityclick.mvvm.network.models.Videos;

import javax.inject.Inject;

public class MovieDetailViewModel extends ViewModel {
  // Using the trigger and switch map lets us observe on one result and update that live data
  // based on calls. See https://www.youtube.com/watch?v=OMcDk2_4LSk&t=4m16s

  private final MutableLiveData<Integer> getMovieTrigger = new MutableLiveData<>();
  private final LiveData<FetchResource<Movie>> getMovieLiveData;

  private final MutableLiveData<Integer> getMovieVideosTrigger = new MutableLiveData<>();
  private final LiveData<FetchResource<Videos>> getMovieVideosLiveData;

  @Inject
  public MovieDetailViewModel(TMDBRepository tmdbRepository) {
    getMovieLiveData = Transformations.switchMap(getMovieTrigger, tmdbRepository::getMovie);
    getMovieVideosLiveData = Transformations.switchMap(getMovieVideosTrigger, tmdbRepository::getVideos);
  }

  // Movie Details
  public LiveData<FetchResource<Movie>> getMovieLiveData() {
    return getMovieLiveData;
  }

  public void getMovieDetail(@NonNull Integer id) {
    getMovieTrigger.setValue(id);
  }

  // Movie Videos
  public LiveData<FetchResource<Videos>> getMovieVideosLiveData() {
    return getMovieVideosLiveData;
  }

  public void getMovieVideos(@NonNull Integer id) {
    getMovieVideosTrigger.setValue(id);
  }
}
