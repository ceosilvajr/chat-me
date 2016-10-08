package io.github.ceosilvajr.chatme.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import io.github.ceosilvajr.chatme.R;
import org.apache.commons.lang3.StringUtils;

public class MainActivity extends AppCompatActivity implements OnCompleteListener<AuthResult> {

  @BindView(R.id.splashContainer) RelativeLayout rlSplash;
  @BindView(R.id.edtEmail) EditText edtEmail;
  @BindView(R.id.edtPassword) EditText edtPassword;
  private Unbinder unbinder;

  private AlertDialog alertDialog;
  private ProgressDialog progressDialog;

  private FirebaseAuth firebaseAuth;

  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initFirebase();
    initViews();
  }

  private void initFirebase() {
    firebaseAuth = FirebaseAuth.getInstance();
    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
      redirectUserToHome();
    }
  }

  private void initViews() {
    unbinder = ButterKnife.bind(this);
    rlSplash.setVisibility(View.GONE);
    createAlertDialog();
    createProgressDialog();
  }

  private void createAlertDialog() {
    alertDialog = new AlertDialog.Builder(this).create();
    alertDialog.setTitle("Alert");
    alertDialog.setCancelable(true);
  }

  private void createProgressDialog() {
    progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("logging in...");
    progressDialog.setCancelable(true);
  }

  private void showAlert(final String message) {
    if (alertDialog != null) {
      alertDialog.setMessage(message);
      alertDialog.show();
    }
  }

  private void showProgressDialog() {
    if (progressDialog != null) {
      progressDialog.show();
    }
  }

  private void dismissDialogs() {
    if (alertDialog != null && alertDialog.isShowing()) {
      alertDialog.dismiss();
    }
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  @OnClick(R.id.btnLogin) void loginUser() {
    final String email = edtEmail.getText().toString().trim();
    final String password = edtPassword.getText().toString().trim();
    showProgressDialog();
    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this);
  }

  @Override public void onComplete(@NonNull Task<AuthResult> task) {
    dismissDialogs();
    if (!task.isSuccessful()) {
      String message = this.getString(R.string.message_error_login);
      if (StringUtils.isNoneBlank(task.getException().getMessage())) {
        message = task.getException().getMessage();
      }
      showAlert(message);
    } else {
      redirectUserToHome();
    }
  }

  @OnClick(R.id.tvSignUp) void redirectUserToSignUp() {
    final Intent intent = new Intent(this, RegisterActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.tvForgotPassword) void redirectUserToForgotPassword() {
    final Intent intent = new Intent(this, ForgotPasswordActivity.class);
    startActivity(intent);
  }

  private void redirectUserToHome() {
    final Intent intent = new Intent(this, HomeActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
    finish();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    dismissDialogs();
    unbinder.unbind();
  }
}