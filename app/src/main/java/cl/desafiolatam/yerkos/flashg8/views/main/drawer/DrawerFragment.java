package cl.desafiolatam.yerkos.flashg8.views.main.drawer;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import cl.desafiolatam.yerkos.flashg8.R;
import cl.desafiolatam.yerkos.flashg8.data.CurrentUser;
import cl.desafiolatam.yerkos.flashg8.data.UploadPhoto;
import cl.desafiolatam.yerkos.flashg8.views.login.LoginActivity;

import static android.app.Activity.RESULT_OK;

public class DrawerFragment extends Fragment implements PhotoCallback{


    private MagicalPermissions magicalPermissions;
    private MagicalCamera magicalCamera;
    private int PHOTO_SIZE = 30;
    private CircularImageView user_image;

    public DrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView user_email = view.findViewById(R.id.user_email);
        user_image = view.findViewById(R.id.user_image);
        user_email.setText(new CurrentUser().email());

        String[] permissions = new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        magicalPermissions = new MagicalPermissions(this, permissions);
        magicalCamera = new MagicalCamera(getActivity(), PHOTO_SIZE, magicalPermissions);

        new PhotoValidation(getContext(), this).validatePhoto();

        TextView logout = view.findViewById(R.id.logout_textBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                    .signOut(getActivity())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent toLogin = new Intent(getActivity(), LoginActivity.class);
                            startActivity(toLogin);
                            getActivity().finish();
                        }
                    });
            }

        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        magicalPermissions.permissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        magicalCamera.resultPhoto(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            Bitmap photo = magicalCamera.getPhoto();
            String path = magicalCamera.savePhotoInMemoryDevice(photo,
                    "Avatar",
                    "Flash",
                    MagicalCamera.JPEG,
                    true);
            path = "file://"+path;
            setPhoto(path);

            new UploadPhoto(getContext()).uploadToFirebase(path);
            Log.d("PATH", path);
        } else {
            requestSelfie();
        }
    }

    private void requestSelfie(){
        new AlertDialog.Builder(getActivity())
                .setTitle("Selfie:)")
                .setMessage("Para completar el registro, debes tener una foto actualizada.")
                .setCancelable(false)
                .setPositiveButton("Selfie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        magicalCamera.takeFragmentPhoto(DrawerFragment.this);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void setPhoto(String url){
        Picasso.get().load(url).centerCrop().fit().into(user_image);
    }

    @Override
    public void emptyPhoto() {
        requestSelfie();
    }

    @Override
    public void availablePhoto(String url) {
        setPhoto(url);
    }
}
