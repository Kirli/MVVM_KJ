package com.affinityclick.mvvm.network.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Credits implements Parcelable {

  public final static Parcelable.Creator<Credits> CREATOR = new Creator<Credits>() {

    @SuppressWarnings({
        "unchecked"
    })
    public Credits createFromParcel(Parcel in) {
      return new Credits(in);
    }

    public Credits[] newArray(int size) {
      return (new Credits[size]);
    }
  };
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("cast")
  @Expose
  private List<Cast> cast = null;
  @SerializedName("crew")
  @Expose
  private List<Crew> crew = null;

  protected Credits(Parcel in) {
    this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    in.readList(this.cast, (Cast.class.getClassLoader()));
    in.readList(this.crew, (Crew.class.getClassLoader()));
  }

  public Credits() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public List<Cast> getCast() {
    return cast;
  }

  public void setCast(List<Cast> cast) {
    this.cast = cast;
  }

  public List<Crew> getCrew() {
    return crew;
  }

  public void setCrew(List<Crew> crew) {
    this.crew = crew;
  }

  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(id);
    dest.writeList(cast);
    dest.writeList(crew);
  }

  public int describeContents() {
    return 0;
  }
}
