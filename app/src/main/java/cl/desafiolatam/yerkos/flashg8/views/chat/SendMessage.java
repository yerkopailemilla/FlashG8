package cl.desafiolatam.yerkos.flashg8.views.chat;

import cl.desafiolatam.yerkos.flashg8.data.CurrentUser;
import cl.desafiolatam.yerkos.flashg8.data.Nodes;
import cl.desafiolatam.yerkos.flashg8.models.Chat;
import cl.desafiolatam.yerkos.flashg8.models.Message;

public class SendMessage {

    public void validateMessage(String message, Chat chat){
        if (message.trim().length() > 0){
            String email = new CurrentUser().email();
            Message msg = new Message();
            msg.setContent(message);
            msg.setOwner(email);

            String chatKey = chat.getKey();
            new Nodes().messages(chatKey).push().setValue(msg);
            new Nodes().userChat(chat.getUid()).child("notification").setValue(true);
        }
    }
}
