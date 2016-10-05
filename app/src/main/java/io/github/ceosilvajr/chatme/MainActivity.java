package io.github.ceosilvajr.chatme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

  private Unbinder unbinder;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    unbinder = ButterKnife.bind(this);
  }

  @OnClick(R.id.btnLogin) void loginUser() {
    redirectUserToHome();
  }

  @OnClick(R.id.tvSignUp) void redirectUserToSignUp() {
    final Intent intent = new Intent(this, RegisterActivity.class);
    startActivity(intent);
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