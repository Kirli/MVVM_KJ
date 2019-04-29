package com.affinityclick.mvvm.movie.list.mostPopular;

import com.affinityclick.mvvm.network.FetchResource;
import com.affinityclick.mvvm.network.TMDBRepository;
import com.affinityclick.mvvm.network.models.Movie;
import com.affinityclick.mvvm.network.models.PageResult;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class MovieMostPopularListViewModel extends ViewModel {
  // Using the trigger and switch map lets us observe on one result and update that live data
  // based on calls. See https://www.youtube.com/watch?v=OMcDk2_4LSk&t=4m16s
  private final MutableLiveData<Integer> getMoviesTrigger = new MutableLiveData<>();
  private final LiveData<FetchResource<PageResult<Movie>>> getMoviesLiveData;

  @Inject
  public MovieMostPopularListViewModel(TMDBRepository tmdbRepository) {
    getMoviesLiveData = Transformations.switchMap(getMoviesTrigger, tmdbRepository::getPopularMovies);
  }

  public LiveData<FetchResource<PageResult<Movie>>> getMovieListLiveData() {
    return getMoviesLiveData;
  }

  public void getMovieList(@NonNull int page) {
    getMoviesTrigger.setValue(page);
  }
}
