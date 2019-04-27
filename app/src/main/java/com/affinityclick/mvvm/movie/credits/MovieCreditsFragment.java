package com.affinityclick.mvvm.movie.credits;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.affinityclick.mvvm.R;

import javax.inject.Inject;

public class MovieCreditsFragment extends Fragment {

  @BindView(R.id.rv_cast)
  RecyclerView castRecyclerView;

  @BindView(R.id.rv_crew)
  RecyclerView crewRecyclerView;

  private Unbinder unbinder;

  @Inject ViewModelProvider.Factory viewModelFactory;
  private MovieCreditsViewModel viewModel;

  private CastAdapter castAdapter;
  private CrewAdapter crewAdapter;

  private Integer movieId;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    AndroidSupportInjection.inject(this);

    // Create the adapters for the cast and crew.
    castAdapter = new CastAdapter();
    crewAdapter = new CrewAdapter();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieCreditsViewModel.class);

    MovieCreditsFragmentArgs args = MovieCreditsFragmentArgs.fromBundle(getArguments());
    movieId = args.getMovieId();
    viewModel.getMovieCredits(movieId);

    viewModel.getMovieCreditsLiveData().observe(this, creditsFetchResource -> {
      switch (creditsFetchResource.getState()) {
        case ERROR:
          break;

        case LOADING:
          break;

        case SUCCESS:
          castAdapter.updateCastList(creditsFetchResource.getData().getCast());
          crewAdapter.updateCrewList(creditsFetchResource.getData().getCrew());
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
    View view = inflater.inflate(R.layout.credits_fragment, container, false);

    unbinder = ButterKnife.bind(this, view);

    castRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    castRecyclerView.setAdapter(castAdapter);

    crewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    crewRecyclerView.setAdapter(crewAdapter);

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