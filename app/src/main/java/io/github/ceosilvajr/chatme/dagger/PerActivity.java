package io.github.ceosilvajr.chatme.dagger;

import java.lang.annotation.Retention;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by ceosilvajr on 06/10/2016.
 *
 * @author ceosilvajr@gmail.com
 */
@Scope @Retention(RUNTIME) public @interface PerActivity {
}
