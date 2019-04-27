package com.affinityclick.mvvm.util;

public class MovieUtil {

  /**
   *
   * @param imagePath A relative image path.
   * @return A String URL based on the relative image path
   */
  public static String imagePathBuilder(String imagePath) {
    return "https://image.tmdb.org/t/p/" +
        "w500" +
        imagePath;
  }
}
