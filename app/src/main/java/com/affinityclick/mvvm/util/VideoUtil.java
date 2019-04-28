package com.affinityclick.mvvm.util;

public class VideoUtil {
  /**
   *
   * @param key Video key for Youtube.
   * @return A String URL based on the relative image path
   */
  public static String youtubeVideoURLBuilder(String key) {
    return "https://www.youtube.com/embed/" + key;
  }
}