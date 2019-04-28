package com.affinityclick.mvvm.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video implements Parcelable {

  @SerializedName("id") @Expose
  private String id;

  @SerializedName("iso_639_1") @Expose
  private String iso_639_1;

  @SerializedName("iso_3166_1") @Expose
  private String iso_3166_1;

  @SerializedName("key") @Expose
  private String key;

  @SerializedName("name") @Expose
  private String name;

  @SerializedName("site") @Expose
  private String site;

  @SerializedName("size") @Expose
  private int size;

  @SerializedName("type") @Expose
  private String type;

  public static final Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
    @Override
    public Video createFromParcel(Parcel in) {
      return new Video(in);
    }

    @Override
    public Video[] newArray(int size) {
      return new Video[size];
    }
  };

  protected Video(Parcel in) {
    id = in.readString();

    iso_639_1 = in.readString();

    iso_3166_1 = in.readString();

    key = in.readString();

    name = in.readString();

    site = in.readString();

    size = in.readInt();

    type = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(id);

    dest.writeString(iso_639_1);

    dest.writeString(iso_3166_1);

    dest.writeString(key);

    dest.writeString(name);

    dest.writeString(site);

    dest.writeInt(size);

    dest.writeString(type);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public String getISO_639_1() {
    return iso_639_1;
  }
  public void setISO_639_1(String iso_639_1) {
    this.iso_639_1 = iso_639_1;
  }

  public String getISO_3166_1() {
    return iso_3166_1;
  }
  public void setISO_3166_1(String iso_3166_1) {
    this.iso_3166_1 = iso_3166_1;
  }

  public String getKey() {
    return key;
  }
  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getSite() {
    return site;
  }
  public void setSite(String site) {
    this.site = site;
  }

  public Integer getSize() {
    return size;
  }
  public void setSize(Integer size) {
    this.size = size;
  }

  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
}