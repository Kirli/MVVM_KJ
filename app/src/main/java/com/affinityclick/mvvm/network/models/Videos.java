package com.affinityclick.mvvm.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Videos implements Parcelable {

  public final static Parcelable.Creator<Videos> CREATOR = new Creator<Videos>() {
    public Videos createFromParcel(Parcel in) {
      return new Videos(in);
    }

    public Videos[] newArray(int size) {
      return (new Videos[size]);
    }
  };

  @SerializedName("id") @Expose
  private Integer id;

  @SerializedName("results") @Expose
  private List<Video> results = null;

  protected Videos(Parcel in) {
    this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    in.readList(this.results, (Video.class.getClassLoader()));
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(id);
    dest.writeList(results);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public Integer getId() { return id; }
  public void setId(Integer id) {
    this.id = id;
  }

  public List<Video> getVideos() {
    return results;
  }
  public void setVideos(List<Video> videos) {
    this.results = videos;
  }
}