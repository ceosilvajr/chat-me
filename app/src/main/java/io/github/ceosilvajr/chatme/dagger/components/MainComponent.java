package io.github.ceosilvajr.chatme.dagger.components;

import dagger.Component;
import io.github.ceosilvajr.chatme.dagger.annotations.PerActivity;
import io.github.ceosilvajr.chatme.dagger.modules.ActivityModule;
import io.github.ceosilvajr.chatme.ui.activities.RegisterActivity;

/**
 * Created by ceosilvajr on 07/10/2016.
 *
 * @author ceosilvajr@gmail.com
 */

@PerActivity @Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface MainComponent {
  void inject(RegisterActivity registerActivity);
}
