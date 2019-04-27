package com.affinityclick.mvvm.movie.detail;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.affinityclick.mvvm.R;
import com.affinityclick.mvvm.network.models.Genre;
import com.affinityclick.mvvm.network.models.Movie;
import com.affinityclick.mvvm.util.MovieUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import dagger.android.support.AndroidSupportInjection;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class MovieDetailFragment extends Fragment {

  @BindView(R.id.movieDetailsBackdrop) ImageView movieBackdrop;
  @BindView(R.id.movieDetailsTitle) TextView movieTitle;
  @BindView(R.id.movieDetailsGenres) TextView movieGenres;
  @BindView(R.id.movieDetailsOverview) TextView movieOverview;
  @BindView(R.id.summaryLabel) TextView movieOverviewLabel;
  @BindView(R.id.movieDetailsReleaseDate) TextView movieReleaseDate;
  @BindView(R.id.movieDetailsRating) RatingBar movieRating;
  @BindView(R.id.movieTrailers) LinearLayout movieTrailers;
  @BindView(R.id.movieReviews) LinearLayout movieReviews;
  @Inject ViewModelProvider.Factory viewModelFactory;

  private Unbinder unbinder;
  private MovieDetailViewModel viewModel;
  private Integer movieId;
  private Movie movie;

  public static MovieDetailFragment newInstance() {
    return new MovieDetailFragment();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    AndroidSupportInjection.inject(this);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel.class);

    //Retrieve the arguments from the Navigation controller and trigger the load of details
    MovieDetailFragmentArgs args = MovieDetailFragmentArgs.fromBundle(getArguments());
    movie = args.getMovie();
    movieId = movie.getId();
    viewModel.getMovieDetail(movieId);

    //Observe on the result
    viewModel.getMovieLiveData().observe(this, movieFetchResource -> {
      switch (movieFetchResource.getState()) {
        case ERROR:
          Toast.makeText(getContext(), R.string.generic_error, Toast.LENGTH_LONG).show();
          break;
        case LOADING:
          break;
        case SUCCESS:
          movie = movieFetchResource.getData();
          refreshUI();
          break;
        case UNINITIALIZED:
          break;
        default:
      }
    });
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.movie_detail_fragment, container, false);
    unbinder = ButterKnife.bind(this, view);
    refreshUI();
    return view;
  }

  @Override
  public void onDestroyView() {
    if (unbinder != null) {
      unbinder.unbind();
    }
    super.onDestroyView();
  }

  private void refreshUI() {
    if (movie != null) {
      movieTitle.setText(movie.getTitle());
      movieOverviewLabel.setVisibility(View.VISIBLE);
      movieOverview.setText(movie.getOverview());
      movieRating.setVisibility(View.VISIBLE);
      movieRating.setRating((float) (movie.getVoteAverage() / 2));
      if (movie.getGenres() != null) {
        List<String> currentGenres = new ArrayList<>();
        for (Genre genre : movie.getGenres()) {
          currentGenres.add(genre.getName());
        }
        movieGenres.setText(TextUtils.join(", ", currentGenres));
      }
      movieReleaseDate.setText(movie.getReleaseDate());
      Glide.with(movieBackdrop)
          .load(MovieUtil.imagePathBuilder(movie.getBackdropPath()))
          .apply(RequestOptions.placeholderOf(R.color.colorLoading))
          .into(movieBackdrop);

      //TODO: Trailers
      //TODO: Reviews
    }
  }

  @OnClick(R.id.movieCredits)
  void onCreditsClicked() {
    MovieDetailFragmentDirections.ViewMovieCredits
        action = MovieDetailFragmentDirections.viewMovieCredits(movieId);
    NavHostFragment.findNavController(this).navigate(action);
  }
}
