package com.affinityclick.mvvm.di;

import com.affinityclick.mvvm.movie.credits.MovieCreditsFragment;
import com.affinityclick.mvvm.movie.detail.MovieDetailFragment;
import com.affinityclick.mvvm.movie.list.mostPopular.MovieMostPopularListFragment;
import com.affinityclick.mvvm.movie.list.topRated.MovieTopRatedListFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentContributorModule {
  @ContributesAndroidInjector abstract MovieTopRatedListFragment contributesMovieListFragment();
  @ContributesAndroidInjector abstract MovieMostPopularListFragment contributesMovieMostPopularListFragment();
  @ContributesAndroidInjector abstract MovieDetailFragment contributesMovieDetailFragment();
  @ContributesAndroidInjector abstract MovieCreditsFragment contributesMovieCreditsFragment();
}
