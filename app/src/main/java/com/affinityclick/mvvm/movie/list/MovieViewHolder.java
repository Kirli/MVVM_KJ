package com.affinityclick.mvvm.movie.list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.affinityclick.mvvm.R;
import com.affinityclick.mvvm.network.models.Movie;
import com.affinityclick.mvvm.util.MovieUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MovieViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.movie_poster) ImageView moviePoster;
  @BindView(R.id.movie_release_date) TextView movieReleaseDate;
  @BindView(R.id.movie_title) TextView movieTitle;
  @BindView(R.id.movie_rating) TextView movieRating;

  public MovieViewHolder(final View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(final Movie movie, final MovieClickListener movieClickListener) {
    Glide
        .with(itemView)
        .load(MovieUtil.imagePathBuilder(movie.getPosterPath()))
        .centerCrop()
        .apply(RequestOptions.placeholderOf(R.color.colorLoading))
        .into(moviePoster);

    movieReleaseDate.setText(movie.getReleaseDate().split("-")[0]);
    movieTitle.setText(movie.getTitle());
    movieRating.setText(String.valueOf(movie.getVoteAverage()));

    itemView.setOnClickListener(v -> movieClickListener.onMovieClick(movie));
  }
}
