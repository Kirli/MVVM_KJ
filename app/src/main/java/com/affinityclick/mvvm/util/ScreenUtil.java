package com.affinityclick.mvvm.util;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class ScreenUtil {
  /**
   * Get the screen dimensions as a point.
   *
   * @param activity An activity.
   * @return A String URL based on the relative image path
   */
  public static Point getScreenDimensions(Activity activity) {
    Point size = new Point();

    Display display = activity.getWindowManager().getDefaultDisplay();
    display.getRealSize(size);

    return size;
  }
}
