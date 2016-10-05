package io.github.ceosilvajr.chatme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
  }

  private void initView() {
    final TextView signUp = (TextView) findViewById(R.id.tvSignUp);
    signUp.setOnClickListener(this);
  }

  @Override public void onClick(final View view) {
    switch (view.getId()) {
      case R.id.buttonLogin:
        loginUser();
        break;
      case R.id.tvSignUp:
        redirectUserToSignUp();
        break;
    }
  }

  private void loginUser() {

  }

  private void redirectUserToSignUp() {
    final Intent intent = new Intent(this, RegisterActivity.class);
    startActivity(intent);
  }
}
