package cl.desafiolatam.yerkos.flashg8.data;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import cl.desafiolatam.yerkos.flashg8.models.User;

public class UploadPhoto {

    private Context context;
    private CurrentUser currentUser = new CurrentUser();
    private EmailProcessor emailProcessor = new EmailProcessor();

    public UploadPhoto(Context context) {
        this.context = context;
    }

    public void uploadToFirebase(String path){
        String folder = emailProcessor.sanitizedEmail(currentUser.email() + "/");
        String photoName = "avatar.jpeg";
        String baseUrl = "gs://flashg8-69058.appspot.com/avatar/";
        String refUrl = baseUrl + folder + photoName;
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(refUrl);
        storageReference.putFile(Uri.parse(path)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String[] fullUrl = taskSnapshot.getStorage().getDownloadUrl().toString().split("&token");
                String url = fullUrl[0];
                Log.d("URLtoFirebase", url);
                new PhotoPreferences(context).photoSave(url);

                User user = new User();
                user.setEmail(currentUser.email());
                user.setName(currentUser.getCurrentUser().getDisplayName());
                user.setPhoto(url);
                user.setUid(currentUser.uId());
                String key = emailProcessor.sanitizedEmail(currentUser.email());
                new Nodes().user(key).setValue(user);
            }
        });

    }
}
