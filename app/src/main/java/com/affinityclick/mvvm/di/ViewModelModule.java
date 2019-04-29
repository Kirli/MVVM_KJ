package com.affinityclick.mvvm.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.affinityclick.mvvm.movie.credits.MovieCreditsViewModel;
import com.affinityclick.mvvm.movie.detail.MovieDetailViewModel;
import com.affinityclick.mvvm.movie.list.mostPopular.MovieMostPopularListViewModel;
import com.affinityclick.mvvm.movie.list.topRated.MovieTopRatedListViewModel;
import com.affinityclick.mvvm.util.ViewModelFactory;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
  @Binds
  @IntoMap
  @ViewModelKey(MovieTopRatedListViewModel.class)
  abstract ViewModel bindMovieListViewModel(MovieTopRatedListViewModel movieListViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(MovieMostPopularListViewModel.class)
  abstract ViewModel bindPopularMovieListViewModel(MovieMostPopularListViewModel movieListViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(MovieDetailViewModel.class)
  abstract ViewModel bindMovieDetailViewModel(MovieDetailViewModel movieDetailViewModel);

  @Binds
  @IntoMap
  @ViewModelKey(MovieCreditsViewModel.class)
  abstract ViewModel bindMovieCreditsViewModel(MovieCreditsViewModel movieCreditsViewModel);

  @Binds
  abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
