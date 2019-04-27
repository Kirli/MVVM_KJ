package com.affinityclick.mvvm.movie.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.affinityclick.mvvm.R;
import com.affinityclick.mvvm.network.models.Cast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CastAdapter extends RecyclerView.Adapter<CastViewHolder> {
  private final List<Cast> castList;

  public CastAdapter() {
        castList = new ArrayList<>();
    }

  public synchronized void updateCastList(@NonNull List<Cast> cast) {
    castList.clear();
    castList.addAll(cast);
    notifyDataSetChanged();
  }

  @Override
  public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_card, parent, false);

    return new CastViewHolder(view);
  }

  @Override
  public void onBindViewHolder(CastViewHolder holder, int position) {
    Cast cast = this.castList.get(position);
    holder.bind(cast);
  }

  @Override
  public int getItemCount() {
        return this.castList.size();
    }

  @Override
  public void onViewRecycled(CastViewHolder holder) {
        super.onViewRecycled(holder);
    }
}