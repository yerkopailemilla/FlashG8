package cl.desafiolatam.yerkos.flashg8.views.main.finder;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import cl.desafiolatam.yerkos.flashg8.R;

public class FinderDialogFragment extends DialogFragment implements FinderCallback{

    private ImageButton finder_send_btn;
    private EditText finder_email_tv;
    private SpinKitView loading;

    public static FinderDialogFragment newInstance() {
        return new FinderDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_finder, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        finder_email_tv = view.findViewById(R.id.finder_email_tv);
        finder_send_btn = view.findViewById(R.id.finder_send_btn);
        loading = view.findViewById(R.id.loadingSpinKit);

        finder_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCancelable(false);
                finder_email_tv.setError(null);
                String email = finder_email_tv.getText().toString();
                finder_email_tv.setVisibility(View.GONE);
                finder_send_btn.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);

                new EmailVerification(FinderDialogFragment.this, getContext()).initVerification(email);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public void error(String error) {
        restoreViews();
        finder_email_tv.setError(error);
    }

    @Override
    public void success() {
        dismiss();
    }

    @Override
    public void notFound() {
        restoreViews();
        Toast.makeText(getContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
    }

    private void restoreViews(){
        finder_email_tv.setVisibility(View.VISIBLE);
        finder_send_btn.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        setCancelable(true);
    }
}
