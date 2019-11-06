package lab.glogic.common.simpledialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class LoadingDialogFragment extends DialogFragment {
    private static final String TAG = "LoadingDialog";
    private static LoadingDialogFragment loading;

    public synchronized static void show(FragmentManager fragmentManager) {
        loading = (LoadingDialogFragment) fragmentManager.findFragmentByTag(TAG);
        if(loading == null) {
            loading = new LoadingDialogFragment();
            loading.setCancelable(false);
            loading.show(fragmentManager, TAG);
        }
    }

    public synchronized static void hide(FragmentManager fragmentManager) {
        loading = (LoadingDialogFragment) fragmentManager.findFragmentByTag(TAG);
        if(loading != null) {
            loading.dismiss();
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(inflater.inflate(R.layout.dialog_loading, null));
        return builder.create();
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }
}
