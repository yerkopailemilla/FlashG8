package cl.desafiolatam.yerkos.flashg8.views.main.finder;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import cl.desafiolatam.yerkos.flashg8.data.CurrentUser;
import cl.desafiolatam.yerkos.flashg8.data.EmailProcessor;
import cl.desafiolatam.yerkos.flashg8.data.Nodes;
import cl.desafiolatam.yerkos.flashg8.data.PhotoPreferences;
import cl.desafiolatam.yerkos.flashg8.models.Chat;
import cl.desafiolatam.yerkos.flashg8.models.User;

public class EmailVerification {

    private FinderCallback finderCallback;
    private Context context;

    public EmailVerification(FinderCallback finderCallback, Context context) {
        this.finderCallback = finderCallback;
        this.context = context;
    }

    public void initVerification(String email){
        if (email.trim().length() > 0){
            if (email.contains("@")){
                String currentEmail = new CurrentUser().email();
                if (!email.equals(currentEmail)){
                    findEmailUser(email);
                } else {
                    finderCallback.error("¿Chat contigo mismo?");
                }

            } else {
                finderCallback.error("Email mal escrito");
            }

        } else {
            finderCallback.error("Se necesita el email");
        }
    }

    private void findEmailUser(String email){
        new Nodes().user(new EmailProcessor().sanitizedEmail(email)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User otherUser = dataSnapshot.getValue(User.class);
                if (otherUser != null){
                    createChats(otherUser);
                } else {
                    finderCallback.notFound();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void createChats(User otherUser){
        FirebaseUser currentUser = new CurrentUser().getCurrentUser();
        String photo = new PhotoPreferences(context).getPhoto();

        String key = new EmailProcessor().keyEmails(otherUser.getEmail());
        Chat currentChat = new Chat();
        currentChat.setKey(key);
        currentChat.setPhoto(otherUser.getPhoto());
        currentChat.setNotification(true);
        currentChat.setReceiver(otherUser.getEmail());

        Chat otherChat = new Chat();
        otherChat.setKey(key);
        otherChat.setPhoto(photo);
        otherChat.setNotification(true);
        otherChat.setReceiver(currentUser.getEmail());

        new Nodes().userChat(currentUser.getUid()).child(key).setValue(currentChat);
        new Nodes().userChat(otherUser.getUid()).child(key).setValue(otherChat);

        finderCallback.success();

    }
}
