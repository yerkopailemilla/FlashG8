package cl.desafiolatam.yerkos.flashg8.views.main.chats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cl.desafiolatam.yerkos.flashg8.R;

public class ChatDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        String key = getIntent().getStringExtra(ChatsFragment.CHAT_KEY);
        String name = getIntent().getStringExtra(ChatsFragment.CHAT_RECEIVER);
        getSupportActionBar().setTitle(name);
    }
}
