package com.affinityclick.mvvm.movie.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.affinityclick.mvvm.R;
import com.affinityclick.mvvm.network.models.Review;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
  private final List<Review> reviewList;

  public ReviewAdapter() {
    reviewList = new ArrayList<>();
  }

  public synchronized void updateReviewList(@NonNull List<Review> reviews) {
    reviewList.clear();
    reviewList.addAll(reviews);
    notifyDataSetChanged();
  }

  @Override
  public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card, parent, false);

    return new ReviewViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ReviewViewHolder holder, int position) {
    Review review = this.reviewList.get(position);
    holder.bind(review);
  }

  @Override
  public int getItemCount() {
    return this.reviewList.size();
  }

  @Override
  public void onViewRecycled(ReviewViewHolder holder) {
    super.onViewRecycled(holder);
  }
}