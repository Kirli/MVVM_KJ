package com.affinityclick.mvvm.movie.credits;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.affinityclick.mvvm.R;
import javax.inject.Inject;

public class MovieCreditsFragment extends Fragment {

  //TODO: create this class

  @Inject ViewModelProvider.Factory viewModelFactory;
  private MovieCreditsViewModel viewModel;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.credits_fragment, container, false);
  }

}
