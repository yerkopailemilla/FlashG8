package cl.desafiolatam.yerkos.flashg8.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.siyamed.shapeimageview.BubbleImageView;
import com.squareup.picasso.Picasso;

import cl.desafiolatam.yerkos.flashg8.R;
import cl.desafiolatam.yerkos.flashg8.models.Chat;

public class ChatsAdapter extends FirebaseRecyclerAdapter<Chat, ChatsAdapter.ChatsViewHolder>{

    private ChatsListener chatsListener;

    public ChatsAdapter(@NonNull FirebaseRecyclerOptions<Chat> options, ChatsListener chatsListener) {
        super(options);
        this.chatsListener = chatsListener;
    }

    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat, parent, false);
        return new ChatsViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ChatsViewHolder holder, int position, @NonNull Chat model) {
        Picasso.get().load(model.getPhoto()).fit().centerCrop().into(holder.photoBiv);
        holder.emailTv.setText(model.getReceiver());

        if (model.isNotification()){
            holder.notificationV.setVisibility(View.VISIBLE);
        } else {
            holder.notificationV.setVisibility(View.INVISIBLE);
        }

        holder.lyt_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chat auxChat = getItem(holder.getAdapterPosition());
                chatsListener.chatClicked(auxChat);
            }
        });
    }

    public static class ChatsViewHolder extends RecyclerView.ViewHolder{

        private BubbleImageView photoBiv;
        private TextView emailTv;
        private View notificationV;
        private LinearLayout lyt_chat;

        public ChatsViewHolder(View itemView) {
            super(itemView);
            photoBiv = itemView.findViewById(R.id.photoBiv);
            emailTv = itemView.findViewById(R.id.emailTv);
            notificationV = itemView.findViewById(R.id.notificationV);
            lyt_chat = itemView.findViewById(R.id.lyt_chat);
        }
    }
}
