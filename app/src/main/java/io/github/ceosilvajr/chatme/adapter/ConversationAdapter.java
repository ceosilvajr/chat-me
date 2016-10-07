package io.github.ceosilvajr.chatme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import io.github.ceosilvajr.chatme.R;
import io.github.ceosilvajr.chatme.models.Message;
import java.util.List;

/**
 * Created by ceosilvajr on 07/10/2016.
 *
 * @author ceosilvajr@gmail.com
 */
public class ConversationAdapter extends ArrayAdapter<Message> {

  private List<Message> mMessages;
  private Context mContext;

  public ConversationAdapter(Context context, List<Message> objects) {
    super(context, 0, objects);
    this.mContext = context;
    this.mMessages = objects;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    final LayoutInflater inflater =
        (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    final View view = inflater.inflate(R.layout.conversation_item, parent, false);
    final ImageView imageView = (ImageView) view.findViewById(R.id.sender_photo);
    final TextView textViewSenderEmail = (TextView) view.findViewById(R.id.tv_sender_email);
    final TextView textViewSenderMessage = (TextView) view.findViewById(R.id.tv_sender_message);
    final TextView textViewReceiverMessage = (TextView) view.findViewById(R.id.tv_receiver_message);

    final Message message = mMessages.get(position);

    if (message.isMe()) {
      imageView.setVisibility(View.GONE);
      textViewSenderMessage.setVisibility(View.GONE);
      textViewSenderEmail.setVisibility(View.GONE);
      textViewReceiverMessage.setVisibility(View.VISIBLE);
      textViewReceiverMessage.setText(message.getMessage());
    } else {
      textViewSenderEmail.setVisibility(View.VISIBLE);
      textViewReceiverMessage.setVisibility(View.GONE);
      textViewSenderMessage.setVisibility(View.VISIBLE);
      textViewSenderMessage.setText(message.getMessage());
    }
    return view;
  }

  @Override public Message getItem(int position) {
    return mMessages.get(position);
  }
}
