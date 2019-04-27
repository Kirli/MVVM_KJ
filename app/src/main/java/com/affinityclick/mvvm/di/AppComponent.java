package com.affinityclick.mvvm.di;

import android.app.Application;
import com.affinityclick.mvvm.SampleApplication;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    AppModule.class,
    ViewModelModule.class,
    AndroidSupportInjectionModule.class,
    FragmentContributorModule.class,
})
public interface AppComponent {
  @Component.Builder
  interface Builder {
    @BindsInstance
    Builder application(Application application);
    AppComponent build();
  }

  void inject(SampleApplication sampleApplication);
}
