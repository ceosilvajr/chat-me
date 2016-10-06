package io.github.ceosilvajr.chatme.dagger;

import android.app.Activity;
import dagger.Module;
import dagger.Provides;

/**
 * Created by ceosilvajr on 06/10/2016.
 *
 * @author ceosilvajr@gmail.com
 */

@Module public class ActivityModule {
  private final Activity activity;

  public ActivityModule(Activity activity) {
    this.activity = activity;
  }

  /**
   * Expose the activity to dependents in the graph.
   */
  @Provides @PerActivity Activity activity() {
    return activity;
  }
}