package io.github.ceosilvajr.chatme.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import io.github.ceosilvajr.chatme.R;
import org.apache.commons.lang3.StringUtils;

public class ForgotPasswordActivity extends AppCompatActivity implements OnCompleteListener<Void> {

  private static final String EMPTY_EMAIL = "";

  @BindView(R.id.edtEmail) EditText edtEmail;
  private Unbinder unbinder;
  private AlertDialog alertDialog;
  private ProgressDialog progressDialog;

  private FirebaseAuth firebaseAuth;

  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot_password);
    unbinder = ButterKnife.bind(this);
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

  private void showProgressDialog() {
    if (progressDialog != null) {
      progressDialog.show();
    }
  }

  private void showAlert(final String message) {
    if (alertDialog != null) {
      alertDialog.setMessage(message);
      alertDialog.show();
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

  @OnClick(R.id.btnReset) void resetPassword() {
    final String email = edtEmail.getText().toString().trim();
    if (StringUtils.isBlank(email)) {
      showAlert("Please enter your email.");
      return;
    }
    showProgressDialog();
    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(this);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onComplete(@NonNull Task<Void> task) {
    dismissDialogs();
    if (task.isSuccessful()) {
      Toast.makeText(this, "Reset password link has been sent to your email", Toast.LENGTH_SHORT)
          .show();
      edtEmail.setText(EMPTY_EMAIL);
    } else {
      showAlert("Something went wrong please try again.");
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    dismissDialogs();
    unbinder.unbind();
  }
}
