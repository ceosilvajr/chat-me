package io.github.ceosilvajr.chatme.dagger;

import java.lang.annotation.Retention;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by ceosilvajr on 06/10/2016.
 *
 * @author ceosilvajr@gmail.com
 */
@Qualifier @Retention(RUNTIME) public @interface ForApplication {
}
