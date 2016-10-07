package io.github.ceosilvajr.chatme.interactors.message.impl;

import com.google.firebase.database.DatabaseReference;
import io.github.ceosilvajr.chatme.interactors.message.MessageService;
import io.github.ceosilvajr.chatme.models.Message;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ceosilvajr on 07/10/2016.
 *
 * @author ceosilvajr@gmail.com
 */

public class MessageServiceImpl implements MessageService {

  private DatabaseReference databaseReference;

  public MessageServiceImpl(DatabaseReference databaseReference) {
    this.databaseReference = databaseReference;
  }

  @Override public void send(final String email, final String message) {
    final String key = databaseReference.child("messages").push().getKey();
    final Message messageInstance = new Message(email, message);
    final Map<String, Object> messageValues = messageInstance.toMap();
    final Map<String, Object> childUpdates = new HashMap<>();
    childUpdates.put("/" + key, messageValues);
    databaseReference.updateChildren(childUpdates);
  }
}
