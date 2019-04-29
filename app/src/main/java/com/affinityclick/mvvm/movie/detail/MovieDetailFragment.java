package com.affinityclick.mvvm.movie.detail;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.affinityclick.mvvm.R;
import com.affinityclick.mvvm.network.models.Genre;
import com.affinityclick.mvvm.network.models.Movie;
import com.affinityclick.mvvm.network.models.ReviewFilter;
import com.affinityclick.mvvm.network.models.Video;
import com.affinityclick.mvvm.network.models.Videos;
import com.affinityclick.mvvm.util.MovieUtil;
import com.affinityclick.mvvm.util.ScreenUtil;
import com.affinityclick.mvvm.util.VideoUtil;
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
  @BindView(R.id.trailersLabel) TextView trailersLabel;
  @BindView(R.id.movieTrailers) LinearLayout movieTrailers;
  @BindView(R.id.reviewsLabel) TextView reviewsLabel;
  @BindView(R.id.rv_reviews) RecyclerView reviewsList;

  @Inject ViewModelProvider.Factory viewModelFactory;

  private Unbinder unbinder;
  private MovieDetailViewModel viewModel;
  private Integer movieId;
  private Movie movie;
  private Videos videos;
  private ReviewAdapter reviewAdapter;

  // Track the trailers
  private ArrayList<VideoWebPlayer> webViewList;
  private boolean firstLoad = true;

  public static MovieDetailFragment newInstance() {
    return new MovieDetailFragment();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    AndroidSupportInjection.inject(this);

    reviewAdapter = new ReviewAdapter();
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

    // Load the trailer videos.
    webViewList = new ArrayList<>();

    viewModel.getMovieVideos(movieId);

    viewModel.getMovieVideosLiveData().observe(this, movieFetchResource -> {
      switch (movieFetchResource.getState()) {
        case ERROR:
          Toast.makeText(getContext(), R.string.generic_error, Toast.LENGTH_LONG).show();
          break;
        case LOADING:
          break;
        case SUCCESS:
          videos = movieFetchResource.getData();
          refreshVideoUI();
          break;
        case UNINITIALIZED:
          break;
        default:
      }
    });

    // Load the reviews. One page for now.
    viewModel.getMovieReviews(new ReviewFilter(movieId, 1));

    viewModel.getMovieReviewsLiveData().observe(this, movieFetchResource -> {
      switch (movieFetchResource.getState()) {
        case ERROR:
          Toast.makeText(getContext(), R.string.generic_error, Toast.LENGTH_LONG).show();
          break;
        case LOADING:
          break;
        case SUCCESS:
          reviewAdapter.updateReviewList(movieFetchResource.getData().getResults());
          refreshReviewUI();
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

    firstLoad = true;

    refreshUI();
    refreshVideoUI();
    refreshReviewUI();

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
    }
  }

  private void refreshVideoUI() {
    if (videos == null) {
      return;
    }

    if (firstLoad) {
      for (Video video : videos.getVideos()) {
        if (video.getType().equals("Trailer")) {
          trailersLabel.setVisibility(View.VISIBLE);
          webViewList.add(new VideoWebPlayer(movieTrailers, video.getKey()));
        }
      }

      firstLoad = false;
    }

    for (VideoWebPlayer player : webViewList) {
      //TODO: Implement a better screen size algorithm.
      Point screenDimension = ScreenUtil.getScreenDimensions((Activity) getContext());

      int width = (int) (screenDimension.x * 1.15);
      int height = (int) (width * 0.6);

      player.launch(width, height);
    }
  }

  private void refreshReviewUI() {
    if (reviewAdapter.getItemCount() > 0) {
      reviewsLabel.setVisibility(View.VISIBLE);

      reviewsList.setLayoutManager(new LinearLayoutManager(getContext()));
      reviewsList.setAdapter(reviewAdapter);

      ViewGroup.LayoutParams params = reviewsList.getLayoutParams();
      params.height = 800;
      reviewsList.setLayoutParams(params);
    } else {
      ViewGroup.LayoutParams params = reviewsList.getLayoutParams();
      params.height = 0;
      reviewsList.setLayoutParams(params);
    }
  }

  @OnClick(R.id.movieCredits)
  void onCreditsClicked() {
    MovieDetailFragmentDirections.ViewMovieCredits
        action = MovieDetailFragmentDirections.viewMovieCredits(movieId);
    NavHostFragment.findNavController(this).navigate(action);
  }
}
