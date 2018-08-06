package cl.desafiolatam.yerkos.flashg8.views.main.drawer;

import android.content.Context;

import cl.desafiolatam.yerkos.flashg8.data.PhotoPreferences;

public class PhotoValidation {

    private Context context;
    private PhotoCallback photoCallback;

    public PhotoValidation(Context context, PhotoCallback photoCallback) {
        this.context = context;
        this.photoCallback = photoCallback;
    }

    public void validatePhoto(){
        String url = new PhotoPreferences(context).getPhoto();
        if (url != null){
            photoCallback.availablePhoto(url);
        } else {
            photoCallback.emptyPhoto();
        }
    }
}
