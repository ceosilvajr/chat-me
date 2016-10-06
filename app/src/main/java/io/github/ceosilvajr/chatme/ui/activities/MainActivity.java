package io.github.ceosilvajr.chatme.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.ceosilvajr.chatme.ChatMeApplication;
import io.github.ceosilvajr.chatme.R;
import io.github.ceosilvajr.chatme.dagger.components.DaggerMainComponent;
import io.github.ceosilvajr.chatme.dagger.components.MainComponent;
import io.github.ceosilvajr.chatme.utils.impl.EmailPasswordValidatorImpl;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.edtEmail) EditText edtEmail;
  @BindView(R.id.edtPassword) EditText edtPassword;

  @Inject EmailPasswordValidatorImpl emailPasswordValidator;

  private Unbinder unbinder;
  private MainComponent component;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    component().inject(this);
    setContentView(R.layout.activity_main);
    unbinder = ButterKnife.bind(this);
  }

  @OnClick(R.id.btnLogin) void loginUser() {
    final String email = edtEmail.getText().toString().trim();
    final String password = edtPassword.getText().toString().trim();
    emailPasswordValidator.validate(email, password);
    redirectUserToHome();
  }

  @OnClick(R.id.tvSignUp) void redirectUserToSignUp() {
    final Intent intent = new Intent(this, RegisterActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.tvForgotPassword) void redirectUserToForgotPassword() {
    final Intent intent = new Intent(this, ForgotPasswordActivity.class);
    startActivity(intent);
  }

  MainComponent component() {
    if (component == null) {
      component = DaggerMainComponent.builder()
          .applicationComponent(((ChatMeApplication) getApplication()).component())
          .build();
    }
    return component;
  }

  private void redirectUserToHome() {
    final Intent intent = new Intent(this, HomeActivity.class);
    startActivity(intent);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }
}