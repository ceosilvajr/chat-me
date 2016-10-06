package io.github.ceosilvajr.chatme.dagger;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import io.github.ceosilvajr.chatme.ChatMeApplication;
import javax.inject.Singleton;

/**
 * Created by ceosilvajr on 06/10/2016.
 *
 * @author ceosilvajr@gmail.com
 */
@Module public class AndroidModule {

  private final ChatMeApplication application;

  public AndroidModule(ChatMeApplication application) {
    this.application = application;
  }

  /**
   * Expose the application to the graph.
   */
  @Provides @Singleton Application application() {
    return application;
  }
}
