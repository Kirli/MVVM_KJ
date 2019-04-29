package com.affinityclick.mvvm.movie.detail;

import android.view.View;
import android.widget.TextView;

import com.affinityclick.mvvm.R;
import com.affinityclick.mvvm.network.models.Review;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.reviewer_name)
  TextView reviewerName;

  @BindView(R.id.review_content)
  TextView reviewContent;

  public ReviewViewHolder(final View itemView) {
    super(itemView);

    ButterKnife.bind(this, itemView);
  }

  public void bind(final Review review) {
    reviewerName.setText(review.getAuthor());
    reviewContent.setText(review.getContent());
  }
}