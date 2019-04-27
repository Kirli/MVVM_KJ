package com.affinityclick.mvvm.movie.credits;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.affinityclick.mvvm.R;
import com.affinityclick.mvvm.network.models.Cast;
import com.affinityclick.mvvm.util.CreditsUtil;
import com.affinityclick.mvvm.util.MovieUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CastViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.cast_photo)
  ImageView castPhoto;

  @BindView(R.id.cast_name)
  TextView castName;

  @BindView(R.id.character_name)
  TextView characterName;

  public CastViewHolder(final View itemView) {
    super(itemView);

    ButterKnife.bind(this, itemView);
  }

  public void bind(final Cast cast) {
    // Check if we have a profile picture.
    if (cast.getProfilePath() != null) {
      String imagePath = CreditsUtil.photoPathBuilder((String) cast.getProfilePath());

      Glide
              .with(itemView)
              .load(imagePath)
              .centerCrop()
              .apply(RequestOptions.placeholderOf(R.color.colorLoading))
              .into(castPhoto);
    } else {
      Glide
              .with(itemView)
              .load(R.drawable.ic_profile_photo)
              .centerCrop()
              .apply(RequestOptions.placeholderOf(R.color.colorLoading))
              .into(castPhoto);
    }

    castName.setText(cast.getName());
    characterName.setText(cast.getCharacter());
  }
}