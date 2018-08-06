package cl.desafiolatam.yerkos.flashg8.data;

import android.content.Context;
import android.content.SharedPreferences;

public class PhotoPreferences {

    private static final String GROUP_PHOTO = "cl.desafiolatam.yerkos.flashg8.data.KEY.GROUP_PHOTO";
    private static final String KEY_PHOTO = "cl.desafiolatam.yerkos.flashg8.data.KEY.KEY_PHOTO";
    private Context context;

    public PhotoPreferences(Context context) {
        this.context = context;
    }

    public void photoSave(String url) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GROUP_PHOTO, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(KEY_PHOTO, url);
        prefEditor.apply();
    }

    public String getPhoto() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GROUP_PHOTO, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PHOTO, null);
    }

}
