package lab.glogic.common.simpledialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MessageDialogFragment extends DialogFragment {

    private static final String ARG_MESSAGE = "message";
    private static final String ARG_TITLE = "title";
    private static final String ARG_TITLE_RESID = "titleResId";
    private static final String ARG_ICON = "icon";
    private static final String ARG_POSITIVE_TEXTID = "positiveTextId";
    private static final String ARG_NEGATIVE_TEXTID = "negativeTextId";

    public static class Builder {
        private String message;
        private String title;
        private int titleId;
        private int iconId;
        private int positiveTextId = R.string.simpledialog_message_button_positive;
        private int negativeTextId;
        private DialogInterface.OnClickListener positiveListener;
        private DialogInterface.OnClickListener negativeListener;

        public Builder() {}

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitleId(int titleId) {
            this.titleId = titleId;
            return this;
        }

        public Builder setIconId(int iconId) {
            this.iconId = iconId;
            return this;
        }

        public Builder setPositiveListener(DialogInterface.OnClickListener positiveListener) {
            this.positiveListener = positiveListener;
            return this;
        }

        public Builder setNegativeListener(DialogInterface.OnClickListener negativeListener) {
            this.negativeListener = negativeListener;
            return this;
        }

        public Builder setPositiveTextId(int positiveTextId) {
            this.positiveTextId = positiveTextId;
            return this;
        }

        public Builder setNegativeTextId(int negativeTextId) {
            this.negativeTextId = negativeTextId;
            return this;
        }

        public MessageDialogFragment build() {
            MessageDialogFragment mdf = new MessageDialogFragment(this.positiveListener, this.negativeListener);

            Bundle args = new Bundle();
            args.putString(ARG_MESSAGE, this.message);
            args.putString(ARG_TITLE, this.title);
            args.putInt(ARG_TITLE_RESID, this.titleId);
            args.putInt(ARG_ICON, this.iconId);
            args.putInt(ARG_POSITIVE_TEXTID, this.positiveTextId);
            args.putInt(ARG_NEGATIVE_TEXTID, this.negativeTextId);
            mdf.setArguments(args);

            return mdf;
        }

        public void show(@NonNull FragmentManager manager) {
            MessageDialogFragment dialog = this.build();
            dialog.show(manager, "MessageDialog");
        }
    }

    public static class Alert extends Builder {
        public Alert(String message) {
            setPositiveTextId(R.string.simpledialog_message_button_positive);
            setMessage(message);
        }
    }

    public static class Success extends Builder {
        public Success(String message) {
            setMessage(message);
            setIconId(R.drawable.ic_check_black_24dp);
        }
    }

    public static class Info extends Builder {
        public Info(String message) {
            setPositiveTextId(R.string.simpledialog_message_button_positive);
            setMessage(message);
            setIconId(R.drawable.ic_info_outline_black_24dp);
        }
    }

    public static class Warning extends Builder {
        public Warning(String message) {
            setPositiveTextId(R.string.simpledialog_message_button_positive);
            setMessage(message);
            setIconId(R.drawable.ic_warning_black_24dp);
        }
    }

    public static class Error extends Builder {
        public Error(String message) {
            setPositiveTextId(R.string.simpledialog_message_button_positive);
            setMessage(message);
            setIconId(R.drawable.ic_error_outline_black_24dp);
            setTitleId(R.string.simpledialog_error_title);
        }
    }

    public static class ErrorRetry extends Builder {
        public ErrorRetry(String message) {
            setPositiveTextId(R.string.simpledialog_retry_button_positive);
            setNegativeTextId(R.string.simpledialog_retry_button_negative);
            setMessage(message);
            setIconId(R.drawable.ic_error_outline_black_24dp);
            setTitleId(R.string.simpledialog_error_title);
        }
    }

    public static class Confirm extends Builder {
        public Confirm(String message) {
            setPositiveTextId(R.string.simpledialog_confirm_button_positive);
            setNegativeTextId(R.string.simpledialog_confirm_button_negative);
            setMessage(message);
            setIconId(R.drawable.ic_check_black_24dp);
        }
    }

    public MessageDialogFragment() {
    }

    private DialogInterface.OnClickListener positiveConfirmListener;
    private DialogInterface.OnClickListener negativeConfirmListener;
    public MessageDialogFragment(DialogInterface.OnClickListener positiveConfirmListener, DialogInterface.OnClickListener negativeConfirmListener) {
        this.positiveConfirmListener = positiveConfirmListener;
        this.negativeConfirmListener = negativeConfirmListener;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DialogInterface.OnClickListener actionDefault = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dismiss();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        int posTextId = getArguments().getInt(ARG_POSITIVE_TEXTID);
        int negTextId = getArguments().getInt(ARG_NEGATIVE_TEXTID);
        int iconId = getArguments().getInt(ARG_ICON);

        String title = getArguments().getString(ARG_TITLE);
        if(title == null && getArguments().getInt(ARG_TITLE_RESID) > 0) {
            title = getResources().getString(getArguments().getInt(ARG_TITLE_RESID));
        }
        if(title == null && iconId > 0) {
            title = getApplicationName(getActivity());
        }


        builder.setTitle(title)
                .setMessage(getArguments().getString(ARG_MESSAGE))
                .setIcon(iconId)
                .setPositiveButton(posTextId, positiveConfirmListener != null ? positiveConfirmListener : actionDefault);
        if(negTextId > 0) {
            builder.setNegativeButton(negTextId, negativeConfirmListener != null ? negativeConfirmListener : actionDefault);
        }

        return builder.create();
    }

    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }
}
