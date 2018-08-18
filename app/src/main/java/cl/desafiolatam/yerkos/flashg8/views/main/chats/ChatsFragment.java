package cl.desafiolatam.yerkos.flashg8.views.main.chats;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import cl.desafiolatam.yerkos.flashg8.R;
import cl.desafiolatam.yerkos.flashg8.adapters.ChatsAdapter;
import cl.desafiolatam.yerkos.flashg8.adapters.ChatsListener;
import cl.desafiolatam.yerkos.flashg8.data.CurrentUser;
import cl.desafiolatam.yerkos.flashg8.data.Nodes;
import cl.desafiolatam.yerkos.flashg8.models.Chat;

public class ChatsFragment extends Fragment implements ChatsListener {

    private RecyclerView chatsRecyclerView;
    public static final String CHAT_KEY = "cl.desafiolatam.yerkos.flashg8.views.main.chats.KEY.CHAT_KEY";
    public static final String CHAT_RECEIVER = "cl.desafiolatam.yerkos.flashg8.views.main.chats.KEY.CHAT_RECEIVER";

    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chatsRecyclerView = view.findViewById(R.id.chatsRecyclerView);
        chatsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatsRecyclerView.setHasFixedSize(true);

        FirebaseRecyclerOptions<Chat> options = new FirebaseRecyclerOptions.Builder<Chat>()
                .setQuery(new Nodes().userChat(new CurrentUser().uId()), Chat.class)
                .setLifecycleOwner(this)
                .build();

        ChatsAdapter chatsAdapter = new ChatsAdapter(options, this);
        chatsRecyclerView.setAdapter(chatsAdapter);
    }

    @Override
    public void chatClicked(String chatKey, String userName) {
        Intent goToDetail = new Intent(getActivity(), ChatDetailActivity.class);
        goToDetail.putExtra(CHAT_KEY, chatKey);
        goToDetail.putExtra(CHAT_RECEIVER, userName);
        startActivity(goToDetail);
    }
}
