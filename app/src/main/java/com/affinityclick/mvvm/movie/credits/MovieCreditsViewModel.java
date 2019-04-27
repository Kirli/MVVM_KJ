package com.affinityclick.mvvm.movie.credits;

import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.affinityclick.mvvm.network.TMDBRepository;
import javax.inject.Inject;

public class MovieCreditsViewModel extends ViewModel {
  // TODO: Implement the ViewModel
  @Inject
  public MovieCreditsViewModel(TMDBRepository tmdbRepository) {
  }
}
