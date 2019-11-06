package lab.glogic.common.wizard;

import android.content.Context;

import androidx.fragment.app.Fragment;

public abstract class WizardFragment extends Fragment {

    private WizardListener mWizardListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WizardListener) {
            mWizardListener = (WizardListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement WizardStepListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mWizardListener = null;
    }

    public WizardListener getWizardListener() {
        return mWizardListener;
    }

    public interface WizardListener {
        void goNext();
        void goBack();
        void goAux();
        void setChecked();
        void skipWizard();
        void finishWizard();
    }
}
