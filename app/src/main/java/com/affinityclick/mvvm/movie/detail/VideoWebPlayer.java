package com.affinityclick.mvvm.movie.detail;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.affinityclick.mvvm.util.VideoUtil;

/*
 * For generating a web view on the fly.
 */
public class VideoWebPlayer {
  private final WebView videoWebView;
  private final String key;

  public VideoWebPlayer(LinearLayout view, String key) {
    this.key = key;

    videoWebView = new WebView(view.getContext());

    videoWebView.getSettings().setJavaScriptEnabled(true);
    videoWebView.getSettings().setBuiltInZoomControls(true);
    videoWebView.getSettings().setSupportZoom(true);
    videoWebView.getSettings().setLoadWithOverviewMode(true);
    videoWebView.getSettings().setUseWideViewPort(true);

    videoWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
    videoWebView.setVerticalScrollBarEnabled(true);

    videoWebView.setWebViewClient(new WebViewClient());

    view.addView(videoWebView);
  }

  public void launch(int width, int height) {
    // Don't use raw string.
    // TODO: Stop using a raw string.
    // TODO: Add additional validation.
    String source = "<html><body><iframe width=\"" + width + "\" height=\"" + height + "\" src=\"" +
            VideoUtil.youtubeVideoURLBuilder(key) + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
    videoWebView.loadData(source, "text/html", "utf-8");
  }
}