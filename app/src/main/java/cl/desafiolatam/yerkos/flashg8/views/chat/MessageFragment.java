package cl.desafiolatam.yerkos.flashg8.views.chat;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import cl.desafiolatam.yerkos.flashg8.R;
import cl.desafiolatam.yerkos.flashg8.adapters.MessageCallback;
import cl.desafiolatam.yerkos.flashg8.adapters.MessagesAdapter;
import cl.desafiolatam.yerkos.flashg8.data.Nodes;
import cl.desafiolatam.yerkos.flashg8.models.Chat;
import cl.desafiolatam.yerkos.flashg8.models.Message;
import cl.desafiolatam.yerkos.flashg8.views.main.chats.ChatsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment implements MessageCallback {


    private MessagesAdapter messagesAdapter;
    private RecyclerView messageRecyclerView;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messageRecyclerView = view.findViewById(R.id.messageRecyclerView);
        Chat chat = (Chat) getActivity().getIntent().getSerializableExtra(ChatsFragment.CHAT);

        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        messageRecyclerView.setHasFixedSize(true);

        FirebaseRecyclerOptions<Message> options = new FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(new Nodes().messages(chat.getKey()), Message.class)
                .setLifecycleOwner(this)
                .build();

        messagesAdapter = new MessagesAdapter(options, this);
        messageRecyclerView.setAdapter(messagesAdapter);
    }

    @Override
    public void update() {
        messageRecyclerView.scrollToPosition(messagesAdapter.getItemCount() -1);
    }

    @Override
    public void onStop() {
        super.onStop();
        messagesAdapter.stopListening();
    }
}
