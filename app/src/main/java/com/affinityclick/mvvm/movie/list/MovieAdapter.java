package com.affinityclick.mvvm.movie.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.affinityclick.mvvm.R;
import com.affinityclick.mvvm.network.models.Movie;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
  private final MovieClickListener movieClickListener;
  private final List<Movie> movieList;

  // Track pages loaded.
  private int firstPage = 1;
  private int lastPage = 1;

  public MovieAdapter(MovieClickListener movieClickListener) {
    movieList = new ArrayList<>();
    this.movieClickListener = movieClickListener;
  }

  public int getLastPage() {
    return lastPage;
  }

  public int getNextPage() {
    lastPage++;

    return lastPage;
  }

  public synchronized void updateMovieList(@NonNull List<Movie> movies) {
    //movieList.clear();
    movieList.addAll(movies);
    notifyDataSetChanged();
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
