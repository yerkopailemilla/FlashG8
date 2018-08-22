package cl.desafiolatam.yerkos.flashg8.views.main.chats;

import cl.desafiolatam.yerkos.flashg8.data.CurrentUser;
import cl.desafiolatam.yerkos.flashg8.data.Nodes;
import cl.desafiolatam.yerkos.flashg8.models.Message;

public class SendMessage {

    public void validateMessage(String message, String chatId){
        if (message.trim().length() > 0){
            String email = new CurrentUser().email();
            Message msg = new Message();
            msg.setContent(message);
            msg.setOwner(email);

            new Nodes().messages(chatId).push().setValue(msg);
        }
    }
}
