package lab.glogic.common.wizard;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class WizardViewModel extends ViewModel {
    private Bundle extras = new Bundle();

    public Bundle getExtras() {
        return extras;
    }
}
