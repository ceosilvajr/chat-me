package io.github.ceosilvajr.chatme;

import android.app.Application;
import io.github.ceosilvajr.chatme.dagger.AndroidModule;
import io.github.ceosilvajr.chatme.dagger.ApplicationComponent;
import io.github.ceosilvajr.chatme.dagger.DaggerApplicationComponent;

/**
 * Created by ceosilvajr on 06/10/2016.
 *
 * @author ceosilvajr@gmail.com
 */
public class ChatMeApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override public void onCreate() {
    super.onCreate();
    applicationComponent =
        DaggerApplicationComponent.builder().androidModule(new AndroidModule(this)).build();
  }

  public ApplicationComponent component() {
    return applicationComponent;
  }
}
