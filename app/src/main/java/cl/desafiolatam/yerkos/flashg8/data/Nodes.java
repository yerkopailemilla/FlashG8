package cl.desafiolatam.yerkos.flashg8.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Nodes {

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();

    public DatabaseReference users(){
        return root.child("users");
    }

    public DatabaseReference user(String key){
        return users().child(key);
    }

    public DatabaseReference chats(){
        return root.child("chats");
    }

    public DatabaseReference userChat(String uid){
        return chats().child(uid);
    }
}
