package io.github.ceosilvajr.chatme.utils.impl;

import io.github.ceosilvajr.chatme.utils.EmailPasswordValidator;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Created by ceosilvajr on 06/10/2016.
 *
 * @author ceosilvajr@gmail.com
 */
public class EmailPasswordValidatorImpl implements EmailPasswordValidator {

  @Inject public EmailPasswordValidatorImpl() {
  }

  @Override public void validate(final String email, final String password) {
    if (StringUtils.isBlank(email) || !EmailValidator.getInstance().isValid(email)) {
      throw new EmailException();
    }
    if (StringUtils.isBlank(password) || !(StringUtils.length(password) >= 6)) {
      throw new PasswordException();
    }
  }

  public class EmailException extends RuntimeException {

  }

  public class PasswordException extends RuntimeException {
  }
}
