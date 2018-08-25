package cl.desafiolatam.yerkos.flashg8.views.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.ServerValue;

import cl.desafiolatam.yerkos.flashg8.R;
import cl.desafiolatam.yerkos.flashg8.data.CurrentUser;
import cl.desafiolatam.yerkos.flashg8.data.Nodes;
import cl.desafiolatam.yerkos.flashg8.models.Chat;
import cl.desafiolatam.yerkos.flashg8.views.main.chats.ChatsFragment;

public class ChatDetailActivity extends AppCompatActivity {

    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        Chat chat = (Chat) getIntent().getSerializableExtra(ChatsFragment.CHAT);
        key = chat.getKey();
        new Nodes().userChat(new CurrentUser().uId()).child(key).child("timestamp").setValue(ServerValue.TIMESTAMP);
        getSupportActionBar().setTitle(chat.getReceiver());

    }

    @Override
    protected void onPause() {
        super.onPause();
        new Nodes().userChat(new CurrentUser().uId()).child(key).child("notification").setValue(false);
    }
}
