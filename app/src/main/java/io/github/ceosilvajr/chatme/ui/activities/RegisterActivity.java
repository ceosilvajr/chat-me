package io.github.ceosilvajr.chatme.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import io.github.ceosilvajr.chatme.ChatMeApplication;
import io.github.ceosilvajr.chatme.R;
import io.github.ceosilvajr.chatme.dagger.components.DaggerMainComponent;
import io.github.ceosilvajr.chatme.dagger.components.MainComponent;
import io.github.ceosilvajr.chatme.utils.impl.EmailPasswordValidatorImpl;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

public class RegisterActivity extends AppCompatActivity implements OnCompleteListener<AuthResult> {

  @Inject EmailPasswordValidatorImpl emailPasswordValidator;

  @BindView(R.id.edtEmail) EditText edtEmail;
  @BindView(R.id.edtPassword) EditText edtPassword;
  private Unbinder unbinder;
  private AlertDialog alertDialog;
  private ProgressDialog progressDialog;

  private MainComponent component;
  private FirebaseAuth firebaseAuth;

  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    component().inject(this);
    setContentView(R.layout.activity_register);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    initFirebase();
    initViews();
  }

  private void initFirebase() {
    firebaseAuth = FirebaseAuth.getInstance();
  }

  private void initViews() {
    unbinder = ButterKnife.bind(this);
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
    progressDialog.setMessage("Signing up...");
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

  @OnClick(R.id.btnRegister) void registerUser() {
    final String email = edtEmail.getText().toString().trim();
    final String password = edtPassword.getText().toString().trim();
    try {
      emailPasswordValidator.validate(email, password);
    } catch (EmailPasswordValidatorImpl.EmailException e) {
      showAlert("Please input valid email.");
      return;
    } catch (EmailPasswordValidatorImpl.PasswordException e) {
      showAlert("Please input at least 6 characters for your password.");
      return;
    }
    showProgressDialog();
    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this);
  }

  @Override public void onComplete(@NonNull Task<AuthResult> task) {
    if (!task.isSuccessful()) {
      dismissDialogs();
      String message = this.getString(R.string.message_error_login);
      if (StringUtils.isNoneBlank(task.getException().getMessage())) {
        message = task.getException().getMessage();
      }
      showAlert(message);
    } else {
      redirectUserToHome();
    }
  }

  @Override public boolean onOptionsItemSelected(final MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private MainComponent component() {
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
    finish();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    dismissDialogs();
    unbinder.unbind();
  }
}
