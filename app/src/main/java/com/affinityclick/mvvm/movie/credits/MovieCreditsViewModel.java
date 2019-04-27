package com.affinityclick.mvvm.movie.credits;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.affinityclick.mvvm.network.FetchResource;
import com.affinityclick.mvvm.network.TMDBRepository;
import com.affinityclick.mvvm.network.models.Credits;

import javax.inject.Inject;

public class MovieCreditsViewModel extends ViewModel {
  private final MutableLiveData<Integer> getCreditsTrigger = new MutableLiveData<>();
  private final LiveData<FetchResource<Credits>> getCreditsLiveData;

  @Inject
  public MovieCreditsViewModel(TMDBRepository tmdbRepository) {
    getCreditsLiveData = Transformations.switchMap(getCreditsTrigger, tmdbRepository::getCredits);
  }

  public LiveData<FetchResource<Credits>> getMovieCreditsLiveData() {
    return getCreditsLiveData;
  }

  public void getMovieCredits(@NonNull int movieId) {
    getCreditsTrigger.setValue(movieId);
  }
}
