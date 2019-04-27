package com.affinityclick.mvvm.util;

public class CreditsUtil {
  /**
   *
   * @param imagePath A relative image path.
   * @return A String URL based on the relative image path
   */
  public static String photoPathBuilder(String imagePath) {
    return "https://image.tmdb.org/t/p/" + "w138_and_h175_face" + imagePath;
  }
}