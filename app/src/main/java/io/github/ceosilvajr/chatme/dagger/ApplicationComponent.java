package io.github.ceosilvajr.chatme.dagger;

import android.app.Application;
import dagger.Component;
import io.github.ceosilvajr.chatme.ChatMeApplication;
import javax.inject.Singleton;

/**
 * Created by ceosilvajr on 06/10/2016.
 *
 * @author ceosilvajr@gmail.com
 */
@Singleton @Component(modules = AndroidModule.class) public interface ApplicationComponent {

  // Field injections of any dependencies of the DemoApplication
  void inject(ChatMeApplication application);

  // Exported for child-components.
  Application application();
}
