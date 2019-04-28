package com.affinityclick.mvvm.movie.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.affinityclick.mvvm.R;
import com.affinityclick.mvvm.network.models.Movie;
import com.affinityclick.mvvm.network.models.PageResult;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
  private final MovieClickListener movieClickListener;
  private final List<Movie> movieList;

  // Track pages loaded.
  private final ArrayList<Integer> pageSizes;
  private int pageLimit;
  private int totalPages = -1;
  private int firstPage = 1;
  private int lastPage = 0;

  public MovieAdapter(MovieClickListener movieClickListener) {
    movieList = new ArrayList<>();
    this.movieClickListener = movieClickListener;

    pageLimit = 1024;
    pageSizes = new ArrayList<>(pageLimit);
  }

  private int currentPageCount() {
    return lastPage - firstPage + 1;
  }

  public int getFirstPage() {
    return firstPage;
  }

  public int getPreviousPage() {
    if (firstPage == 1) {
      return -1;
    }

    return firstPage - 1;
  }

  public int getLastPage() {
    return lastPage;
  }

  public int getNextPage() {
    if (lastPage == totalPages) {
      return -1;
    }

    return lastPage + 1;
  }

  public synchronized void updateMovieList(@NonNull PageResult<Movie> pageResult) {
    if (pageResult.getPage() == getPreviousPage()) {
      // Clear the last page.
      if (currentPageCount() == pageLimit) {
        lastPage--;

        movieList.subList(movieList.size() - pageSizes.get(pageSizes.size() - 1), movieList.size()).clear();
        pageSizes.remove(pageSizes.size() - 1);
      }

      // Add new movies to front of list.
      movieList.addAll(0, pageResult.getResults());
      pageSizes.add(0, pageResult.getResults().size());

      // Update data
      totalPages = pageResult.getTotalPages();
      firstPage--;

      notifyDataSetChanged();

    } else if (pageResult.getPage() == getNextPage()) {
      // Clear the first page.
      if (currentPageCount() == pageLimit) {
        firstPage++;

        movieList.subList(0, pageSizes.get(0)).clear();
        pageSizes.remove(0);
      }

      // Add new movies to back of the list.
      movieList.addAll(pageResult.getResults());
      pageSizes.add(pageResult.getResults().size());

      // Update data
      totalPages = pageResult.getTotalPages();
      lastPage++;

      notifyDataSetChanged();
    }
  }

  @Override
  public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
    return new MovieViewHolder(view);
  }

  @Override
  public void onBindViewHolder(MovieViewHolder holder, int position) {
    Movie movie = this.movieList.get(position);
    holder.bind(movie, movieClickListener);
  }

  @Override
  public int getItemCount() {
    return this.movieList.size();
  }

  @Override
  public void onViewRecycled(MovieViewHolder holder) {
    super.onViewRecycled(holder);
  }
}
