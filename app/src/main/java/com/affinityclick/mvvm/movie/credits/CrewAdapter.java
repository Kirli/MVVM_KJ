package com.affinityclick.mvvm.movie.credits;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.affinityclick.mvvm.R;
import com.affinityclick.mvvm.network.models.Crew;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CrewAdapter extends RecyclerView.Adapter<CrewViewHolder> {
  private final List<Crew> crewList;

  public CrewAdapter() {
    crewList = new ArrayList<>();
  }

  public synchronized void updateCrewList(@NonNull List<Crew> crew) {
    crewList.clear();
    crewList.addAll(crew);
    notifyDataSetChanged();
  }

  @Override
  public CrewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crew_card, parent, false);

    return new CrewViewHolder(view);
  }

  @Override
  public void onBindViewHolder(CrewViewHolder holder, int position) {
    Crew crew = this.crewList.get(position);
    holder.bind(crew);
  }

  @Override
  public int getItemCount() {
    return this.crewList.size();
  }

  @Override
  public void onViewRecycled(CrewViewHolder holder) {
    super.onViewRecycled(holder);
  }
}