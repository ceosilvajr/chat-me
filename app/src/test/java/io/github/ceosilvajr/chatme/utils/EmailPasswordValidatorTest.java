package io.github.ceosilvajr.chatme.utils;

import io.github.ceosilvajr.chatme.utils.impl.EmailPasswordValidatorImpl;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

/**
 * Created by ceosilvajr on 06/10/2016.
 *
 * @author ceosilvajr@gmail.com
 */

public class EmailPasswordValidatorTest {

  private static final String EXCEPTION_MESSAGE = "Expected exception not thrown";

  @InjectMocks private EmailPasswordValidator emailPasswordValidator =
      new EmailPasswordValidatorImpl();

  @BeforeClass public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test(expectedExceptions = EmailPasswordValidatorImpl.EmailException.class)
  public void test_validate_GivenEmptyEmailShouldThrowEmailException() {
    // given
    final String email = "";
    final String password = "";
    // when
    emailPasswordValidator.validate(email, password);
    fail(EXCEPTION_MESSAGE);
  }

  @Test(expectedExceptions = EmailPasswordValidatorImpl.EmailException.class)
  public void test_validate_GivenInvalidEmailShouldThrowEmailException() {
    // given
    final String email = "invalid-email";
    final String password = "";
    // when
    emailPasswordValidator.validate(email, password);
    fail(EXCEPTION_MESSAGE);
  }

  @Test(expectedExceptions = EmailPasswordValidatorImpl.PasswordException.class)
  public void test_validate_GivenEmptyPasswordShouldThrowPasswordException() {
    // given
    final String email = "ceosilvajr@gmail.com";
    final String password = "";
    // when
    emailPasswordValidator.validate(email, password);
    fail(EXCEPTION_MESSAGE);
  }

  @Test(expectedExceptions = EmailPasswordValidatorImpl.PasswordException.class)
  public void test_validate_GivenPasswordLessThanSixCharacterShouldThrowPasswordException() {
    // given
    final String email = "ceosilvajr@gmail.com";
    final String password = "ppp";
    // when
    emailPasswordValidator.validate(email, password);
    fail(EXCEPTION_MESSAGE);
  }

  @Test
  public void test_validate_IntegrationShouldNotThrowAnyExceptionIfBothEmailAndPasswordIsValid() {
    // given
    final String email = "ceosilvajr@gmail.com";
    final String password = "password123";
    // when
    emailPasswordValidator.validate(email, password);
  }
}
