package io.github.ceosilvajr.chatme.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import io.github.ceosilvajr.chatme.R;
import io.github.ceosilvajr.chatme.adapter.ConversationAdapter;
import io.github.ceosilvajr.chatme.interactors.message.impl.MessageServiceImpl;
import io.github.ceosilvajr.chatme.models.Message;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class HomeActivity extends AppCompatActivity implements ChildEventListener {

  private static final String EMPTY_MESSAGE = "";

  @BindView(R.id.edtMessage) EditText edtMessage;
  @BindView(R.id.lvMessage) ListView lvMessage;
  private Unbinder unbinder;

  private List<Message> messagesList;
  private ConversationAdapter conversationAdapter;
  private DatabaseReference databaseReference;
  private MessageServiceImpl messageService;

  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    initFirebaseDatabase();
    initView();
    initMessageService();
  }

  private void initMessageService() {
    messageService = new MessageServiceImpl(databaseReference);
  }

  private void initFirebaseDatabase() {
    databaseReference = FirebaseDatabase.getInstance().getReference("messages");
    databaseReference.addChildEventListener(this);
  }

  private void initView() {
    unbinder = ButterKnife.bind(this);
    messagesList = new ArrayList<>();
    conversationAdapter = new ConversationAdapter(this, messagesList);
    lvMessage.setAdapter(conversationAdapter);
  }

  @OnClick(R.id.btnSend) void sendMessage() {
    final FirebaseUser firebaseUser = getUser();
    final String message = edtMessage.getText().toString().trim();
    if (firebaseUser != null && StringUtils.isNoneBlank(message)) {
      messageService.send(firebaseUser.getEmail(), message);
    }
    edtMessage.setText(EMPTY_MESSAGE);
  }

  @Override public void onChildAdded(final DataSnapshot dataSnapshot, final String s) {
    final Message message = getMessage(dataSnapshot);
    displayMessage(message);
  }

  @Override public void onChildChanged(final DataSnapshot dataSnapshot, final String s) {

  }

  @Override public void onChildRemoved(final DataSnapshot dataSnapshot) {

  }

  @Override public void onChildMoved(final DataSnapshot dataSnapshot, final String s) {

  }

  @Override public void onCancelled(final DatabaseError databaseError) {

  }

  @Override public boolean onCreateOptionsMenu(final Menu menu) {
    final MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.home, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(final MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
      case R.id.menuSignOut:
        logout();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void logout() {
    FirebaseAuth.getInstance().signOut();
    final Intent intent = new Intent(this, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
    finish();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
    if (databaseReference != null) {
      databaseReference.removeEventListener(this);
    }
  }

  private FirebaseUser getUser() {
    return FirebaseAuth.getInstance().getCurrentUser();
  }

  private void displayMessage(final Message message) {
    messagesList.add(message);
    conversationAdapter.notifyDataSetChanged();
  }

  private Message getMessage(final DataSnapshot dataSnapshot) {
    final Message message = dataSnapshot.getValue(Message.class);
    if (message.getEmail().equals(getUser().getEmail())) {
      message.setMe(true);
    }
    return message;
  }
}
