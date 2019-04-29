package com.affinityclick.mvvm;

import android.os.SystemClock;

import com.affinityclick.mvvm.di.AppModule;
import com.affinityclick.mvvm.di.AppModule_ProvideTMDBApiFactory;
import com.affinityclick.mvvm.network.FetchResource;
import com.affinityclick.mvvm.network.TMDBApi;
import com.affinityclick.mvvm.network.TMDBRepository;
import com.affinityclick.mvvm.network.TMDBRepository_Factory;
import com.affinityclick.mvvm.network.models.Credits;
import com.affinityclick.mvvm.network.models.PageResult;
import com.affinityclick.mvvm.network.models.Review;
import com.affinityclick.mvvm.network.models.ReviewFilter;
import com.affinityclick.mvvm.network.models.Videos;
import com.affinityclick.mvvm.util.AppExecutors;
import com.affinityclick.mvvm.util.AppExecutors_Factory;

import org.junit.Test;

import androidx.lifecycle.LiveData;

import static com.affinityclick.mvvm.network.FetchResource.State.SUCCESS;
import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TMDBApiInstrumentedTest {
  private final Object lock = new Object();

  @Test
  public void getCreditsTest() {
    int movieId = 38142;

    AppExecutors appExecutors = AppExecutors_Factory.create().get();
    TMDBApi api = AppModule_ProvideTMDBApiFactory.create(new AppModule()).get();
    TMDBRepository repo = TMDBRepository_Factory.newTMDBRepository(appExecutors, api);

    LiveData<FetchResource<Credits>> credits = repo.getCredits(movieId);

    FetchResource<Credits> res = credits.getValue();
    assert res != null;
    FetchResource.State state = res.getState();
    assert state != null;

    long startTime = SystemClock.currentThreadTimeMillis();
    while (state != SUCCESS) {
      try {
        sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
        fail("INTERRUPT");
      }

      if (SystemClock.currentThreadTimeMillis() - startTime > 10000) {
        fail("API call timed out.");
      }

      res = credits.getValue();
      assert res != null;
      state = res.getState();
      assert state != null;
    }

    Credits result = res.getData();
    assert result != null;

    assertEquals("Kenji Mizuhashi", result.getCast().get(0).getName());

    assertEquals("Makoto Shinkai", result.getCrew().get(0).getName());

    assertEquals("Takaki Tohno (voice)", result.getCast().get(0).getCharacter());

    assertEquals("Director", result.getCrew().get(0).getJob());
  }

  @Test
  public void getVideosTest() {
    int movieId = 38142;

    AppExecutors appExecutors = AppExecutors_Factory.create().get();
    TMDBApi api = AppModule_ProvideTMDBApiFactory.create(new AppModule()).get();
    TMDBRepository repo = TMDBRepository_Factory.newTMDBRepository(appExecutors, api);

    LiveData<FetchResource<Videos>> videos = repo.getVideos(movieId);

    FetchResource<Videos> res = videos.getValue();
    assert res != null;
    FetchResource.State state = res.getState();
    assert state != null;

    long startTime = SystemClock.currentThreadTimeMillis();
    while (state != SUCCESS) {
      try {
        sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
        fail("INTERRUPT");
      }

      if (SystemClock.currentThreadTimeMillis() - startTime > 10000) {
        fail("API call timed out.");
      }

      res = videos.getValue();
      assert res != null;
      state = res.getState();
      assert state != null;
    }

    Videos result = res.getData();
    assert result != null;

    assertEquals((long)38142, (long)result.getId());

    assert result.getVideos() != null;

    assert result.getVideos().get(0) != null;

    assertEquals("5 Centimeters Per Second Trailer", result.getVideos().get(0).getName());

    assertEquals("Teaser", result.getVideos().get(0).getType());
  }

  @Test
  public void getReviewsTest() {
    int movieId = 297761;

    AppExecutors appExecutors = AppExecutors_Factory.create().get();
    TMDBApi api = AppModule_ProvideTMDBApiFactory.create(new AppModule()).get();
    TMDBRepository repo = TMDBRepository_Factory.newTMDBRepository(appExecutors, api);

    LiveData<FetchResource<PageResult<Review>>> reviews = repo.getReviews(new ReviewFilter(movieId, 1));

    FetchResource<PageResult<Review>> res = reviews.getValue();
    assert res != null;
    FetchResource.State state = res.getState();
    assert state != null;

    long startTime = SystemClock.currentThreadTimeMillis();
    while (state != SUCCESS) {
      try {
        sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
        fail("INTERRUPT");
      }

      if (SystemClock.currentThreadTimeMillis() - startTime > 10000) {
        fail("API call timed out.");
      }

      res = reviews.getValue();
      assert res != null;
      state = res.getState();
      assert state != null;
    }

    PageResult<Review> result = res.getData();
    assert result != null;

    assertEquals((long)1, (long)result.getPage());

    assert result.getResults() != null;

    assert result.getResults().get(0) != null;

    assert result.getResults().size() > 0;

    assertEquals("Frank Ochieng", result.getResults().get(0).getAuthor());

    assertEquals("https://www.themoviedb.org/review/57a814dc9251415cfb00309a", result.getResults().get(0).getURL());
  }
}