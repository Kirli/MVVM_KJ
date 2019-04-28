package com.affinityclick.mvvm.movie.list.topRated;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import butterknife.Unbinder;
import com.affinityclick.mvvm.R;
import com.affinityclick.mvvm.movie.list.MovieAdapter;
import dagger.android.support.AndroidSupportInjection;
import javax.inject.Inject;

public class MovieTopRatedListFragment extends Fragment {
  @Inject
  ViewModelProvider.Factory viewModelFactory;

  private MovieTopRatedListViewModel viewModel;

  private MovieAdapter movieAdapter;

  @BindView(R.id.rv_movies)
  RecyclerView recyclerView;

  private Unbinder unbinder;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    AndroidSupportInjection.inject(this);

    movieAdapter = new MovieAdapter(
      movie -> {
        MovieTopRatedListFragmentDirections.ViewMovieDetail
        action = MovieTopRatedListFragmentDirections.viewMovieDetail(movie);

        NavHostFragment.findNavController(this).navigate(action); }
    );
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieTopRatedListViewModel.class);

    viewModel.getMovieListLiveData().observe(this, movieFetchResource -> {
      switch (movieFetchResource.getState()) {
        case ERROR:
          break;

        case LOADING:
          break;

        case SUCCESS:
          movieAdapter.updateMovieList(movieFetchResource.getData());
          break;

        case UNINITIALIZED:
          break;

        default:
      }
    });

    viewModel.getMovieList(movieAdapter.getNextPage());
    //TODO: Make this an infinite scrollView
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.content_fragment, container, false);

    unbinder = ButterKnife.bind(this, view);

    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(movieAdapter);

    // attach a listener to trigger a page load when we can no longer scroll vertically.
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (!recyclerView.canScrollVertically(1)) {
          if (movieAdapter.getNextPage() != -1) {
            viewModel.getMovieList(movieAdapter.getNextPage());
          }
        } else if (!recyclerView.canScrollVertically(-1)) {
          if (movieAdapter.getPreviousPage() != -1) {
            viewModel.getMovieList(movieAdapter.getPreviousPage());
          }
        }
      }
    });

    return view;
  }

  @Override
  public void onDestroyView() {
    if (unbinder != null) {
      unbinder.unbind();
    }
    super.onDestroyView();
  }
}
