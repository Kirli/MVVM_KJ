package com.affinityclick.mvvm.network.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCompany implements Parcelable {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("logo_path")
  @Expose
  private String logoPath;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("origin_country")
  @Expose
  private String originCountry;

  protected ProductionCompany(Parcel in) {
    if (in.readByte() == 0) {
      id = null;
    } else {
      id = in.readInt();
    }
    logoPath = in.readString();
    name = in.readString();
    originCountry = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    if (id == null) {
      dest.writeByte((byte) 0);
    } else {
      dest.writeByte((byte) 1);
      dest.writeInt(id);
    }
    dest.writeString(logoPath);
    dest.writeString(name);
    dest.writeString(originCountry);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<ProductionCompany> CREATOR = new Creator<ProductionCompany>() {
    @Override
    public ProductionCompany createFromParcel(Parcel in) {
      return new ProductionCompany(in);
    }

    @Override
    public ProductionCompany[] newArray(int size) {
      return new ProductionCompany[size];
    }
  };

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLogoPath() {
    return logoPath;
  }

  public void setLogoPath(String logoPath) {
    this.logoPath = logoPath;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOriginCountry() {
    return originCountry;
  }

  public void setOriginCountry(String originCountry) {
    this.originCountry = originCountry;
  }
}
