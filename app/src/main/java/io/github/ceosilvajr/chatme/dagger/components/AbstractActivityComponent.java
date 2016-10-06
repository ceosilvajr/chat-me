package io.github.ceosilvajr.chatme.dagger.components;

import android.app.Activity;
import dagger.Component;
import io.github.ceosilvajr.chatme.dagger.modules.ActivityModule;
import io.github.ceosilvajr.chatme.dagger.annotations.PerActivity;

/**
 * Created by ceosilvajr on 06/10/2016.
 *
 * @author ceosilvajr@gmail.com
 */
@PerActivity @Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface AbstractActivityComponent {
  Activity activity(); // Expose the activity to sub-graphs.
}