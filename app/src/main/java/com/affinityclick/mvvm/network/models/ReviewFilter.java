package com.affinityclick.mvvm.network.models;

public class ReviewFilter {
  public final int movieId;
  public final int page;

  public ReviewFilter(int movieId, int page) {
    this.movieId = movieId;
    this.page = page;
  }
}