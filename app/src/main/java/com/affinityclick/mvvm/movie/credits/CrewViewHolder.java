package com.affinityclick.mvvm.movie.credits;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.affinityclick.mvvm.R;
import com.affinityclick.mvvm.network.models.Crew;
import com.affinityclick.mvvm.util.CreditsUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CrewViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.crew_photo)
  ImageView crewPhoto;

  @BindView(R.id.crew_name)
  TextView crewName;

  @BindView(R.id.crew_role)
  TextView crewJob;

  public CrewViewHolder(final View itemView) {
    super(itemView);

    ButterKnife.bind(this, itemView);
  }

  public void bind(final Crew crew) {
    // Check if we have a profile picture.
    if (crew.getProfilePath() != null) {
      String imagePath = CreditsUtil.photoPathBuilder(crew.getProfilePath());

      Glide
              .with(itemView)
              .load(imagePath)
              .centerCrop()
              .apply(RequestOptions.placeholderOf(R.color.colorLoading))
              .into(crewPhoto);
    } else {
      Glide
              .with(itemView)
              .load(R.drawable.ic_profile_photo)
              .centerCrop()
              .apply(RequestOptions.placeholderOf(R.color.colorLoading))
              .into(crewPhoto);
    }

    crewName.setText(crew.getName());
    crewJob.setText(crew.getJob());
  }
}