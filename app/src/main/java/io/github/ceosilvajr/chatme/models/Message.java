package io.github.ceosilvajr.chatme.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ceosilvajr on 07/10/2016.
 *
 * @author ceosilvajr@gmail.com
 */

@IgnoreExtraProperties public class Message {

  private String messageId;

  private String email;

  private String message;

  @Exclude private boolean isMe;

  public Message() {
    super();
  }

  public Message(String email, String message) {
    super();
    this.email = email;
    this.message = message;
  }

  public String getMessageId() {
    return messageId;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isMe() {
    return isMe;
  }

  public void setMe(boolean me) {
    isMe = me;
  }

  @Override public String toString() {
    return "Message{" +
        "messageId='" + messageId + '\'' +
        ", email='" + email + '\'' +
        ", message='" + message + '\'' +
        ", isMe=" + isMe +
        '}';
  }

  @Exclude public Map<String, Object> toMap() {
    HashMap<String, Object> result = new HashMap<>();
    result.put("messageId", messageId);
    result.put("email", email);
    result.put("message", message);
    return result;
  }
}
