package cl.desafiolatam.yerkos.flashg8.views.main.chats;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import cl.desafiolatam.yerkos.flashg8.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageInputFragment extends Fragment {


    public MessageInputFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_input, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String chatKey = getActivity().getIntent().getStringExtra(ChatsFragment.CHAT_KEY);
        final EditText et = view.findViewById(R.id.finder_email_tv);
        et.setHint("Escribe un mensaje...");

        ImageButton btn = view.findViewById(R.id.finder_send_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = et.getText().toString();
                new SendMessage().validateMessage(message, chatKey);
                et.setText("");
            }
        });
    }
}
